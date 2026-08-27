package com.exelynt.booking.service;

import com.exelynt.booking.dto.AuthRequest;
import com.exelynt.booking.dto.AuthResponse;
import com.exelynt.booking.entity.User;
import com.exelynt.booking.exception.ResourceNotFoundException;
import com.exelynt.booking.repository.UserRepository;
import com.exelynt.booking.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthResponse authenticateUser(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUsername()));

        String token = jwtUtils.generateToken(user.getUserName(), user.getRole().name());

        return new AuthResponse(token);
    }
}