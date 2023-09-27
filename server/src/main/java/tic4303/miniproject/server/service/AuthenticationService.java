package tic4303.miniproject.server.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import tic4303.miniproject.server.configuration.JwtService;
import tic4303.miniproject.server.model.AuthenticationRequest;
import tic4303.miniproject.server.model.AuthenticationResponse;
import tic4303.miniproject.server.model.RegisterRequest;
import tic4303.miniproject.server.repository.UserRepository;
import tic4303.miniproject.server.user.User;

@Service
@EnableMethodSecurity
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final UserRepository userRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtSvc;

    @Autowired
    private final AuthenticationManager authenticationManager;

    // create user, save to database, returns the jwt
    public String register(RegisterRequest request) {
        var user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();

        userRepo.save(user);
        // var jwtToken = jwtSvc.generateToken(user);
        // return AuthenticationResponse.builder().token(jwtToken).build();

        // Create a map to hold the data
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Registration Successful!");

        // Convert the map to JSON using Jackson ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse;
        try {
            jsonResponse = objectMapper.writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            // Handle the exception
            jsonResponse = "{\"error\":\"Failed to create JSON response\"}";
        }

        return jsonResponse;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepo.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtSvc.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    
}
