package com.alexander.service.impl;

import com.alexander.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.dto.LocalUser;
import com.alexander.model.User;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocalUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        return LocalUser.createLocalUser(user);
    }

}
