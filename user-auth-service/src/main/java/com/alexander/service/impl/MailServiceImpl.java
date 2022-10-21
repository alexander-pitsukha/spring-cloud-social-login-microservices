package com.alexander.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.alexander.service.MailService;
import com.alexander.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.alexander.config.AppProperties;
import com.alexander.model.User;

import freemarker.template.Configuration;

import static com.alexander.config.AppConstants.BASE_URL;
import static com.alexander.config.AppConstants.LINE_BREAK;
import static com.alexander.config.AppConstants.SUPPORT_EMAIL;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final MessageService messageService;
    private final JavaMailSender mailSender;
    private final Environment env;
    private final Configuration freemarkerConfiguration;
    private final AppProperties appProperties;

    @Async
    @Override
    public void sendVerificationToken(String token, User user) {
        final String confirmationUrl = appProperties.getClient().getBaseUrl() + "verify?token=" + token;
        final String message = messageService.getMessage("message.mail.verification");
        sendHtmlEmail("Registration Confirmation", message + LINE_BREAK + confirmationUrl, user);
    }

    private String geFreeMarkerTemplateContent(Map<String, Object> model, String templateName) {
        StringBuilder content = new StringBuilder();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(templateName), model));
            return content.toString();
        } catch (Exception e) {
            log.info("Exception occured while processing fmtemplate: {}", e.getMessage());
        }
        return "";
    }

    private void sendHtmlEmail(String subject, String msg, User user) {
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getDisplayName());
        model.put("msg", msg);
        model.put("title", subject);
        model.put(BASE_URL, appProperties.getClient().getBaseUrl());
        try {
            sendHtmlMail(env.getProperty(SUPPORT_EMAIL), user.getEmail(), subject, geFreeMarkerTemplateContent(model, "mail/verification.ftl"));
        } catch (MessagingException e) {
            log.error("Failed to send mail", e);
        }
    }

    public void sendHtmlMail(String from, String to, String subject, String body) throws MessagingException {
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
        helper.setFrom(from);
        if (to.contains(",")) {
            helper.setTo(to.split(","));
        } else {
            helper.setTo(to);
        }
        helper.setSubject(subject);
        helper.setText(body, true);
        mailSender.send(mail);
        log.info("Sent mail: {}", subject);
    }

}
