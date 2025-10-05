package com.ctorres.pokequiz.service.security;

import com.ctorres.pokequiz.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository users;

    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        var user = users.findByUsernameAndActiveTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        var auths = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .toList();

        return new User(
                user.getUsername(),
                user.getPasswordHash(),
                user.isActive(),
                true,
                true,
                true,
                auths);
    }
}