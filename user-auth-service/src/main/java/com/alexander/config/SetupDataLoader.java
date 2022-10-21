package com.alexander.config;

import java.util.Set;

import com.alexander.dto.SocialProvider;
import com.alexander.model.Role;
import com.alexander.model.User;
import com.alexander.repo.RoleRepository;
import com.alexander.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private boolean alreadySetup = false;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        Role userRole = createRoleIfNotFound(AppConstants.ROLE_USER);
        Role adminRole = createRoleIfNotFound(AppConstants.ROLE_ADMIN);
        Role modRole = createRoleIfNotFound(AppConstants.ROLE_MODERATOR);
        createUserIfNotFound("admin@test.com", Set.of(userRole, adminRole, modRole));
        alreadySetup = true;
    }

    private void createUserIfNotFound(final String email, final Set<Role> roles) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setDisplayName("Admin");
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(roles);
            user.setProvider(SocialProvider.LOCAL.getProviderType());
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    private Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = roleRepository.save(new Role(name));
        }
        return role;
    }

}
