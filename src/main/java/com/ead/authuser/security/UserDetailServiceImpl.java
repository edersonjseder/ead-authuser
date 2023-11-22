package com.ead.authuser.security;

import com.ead.authuser.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User could not be found"));
        return UserDetailsImpl.build(user);
    }

    public UserDetails loadUserById(UUID id) throws AuthenticationCredentialsNotFoundException {
        var user = userRepository.findUserById(id).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User could not be found"));
        return UserDetailsImpl.build(user);
    }
}
