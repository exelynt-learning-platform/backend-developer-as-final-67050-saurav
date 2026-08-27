package com.exelynt.booking.security;

import com.exelynt.booking.entity.User;
import com.exelynt.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

      return new org.springframework.security.core.userdetails.User(
        user.getUserName(),
        user.getPassword(),
        Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().name())
        )
);
    }
}