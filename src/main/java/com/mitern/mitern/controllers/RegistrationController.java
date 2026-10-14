
package com.mitern.mitern.controllers;

import java.util.Locale;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class RegistrationController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(
            UserDetailsManager userDetailsManager,
            PasswordEncoder passwordEncoder) {

        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegistrationRequest request) {

        String email = request.email() == null
                ? ""
                : request.email().trim()
                .toLowerCase(Locale.ROOT);

        String password = request.password();

        if (!email.matches(
                "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid email address"));
        }

        if (password == null ||
                password.length() < 12 ||
                password.length() > 128) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "Password must be 12-128 characters"
                    ));
        }

        if (userDetailsManager.userExists(email)) {
            return ResponseEntity.status(409)
                    .body(Map.of("error", "User already exists"));
        }

        UserDetails user = User.withUsername(email)
                .password(passwordEncoder.encode(password))
                .roles("STUDENT")
                .build();

        userDetailsManager.createUser(user);

        return ResponseEntity.status(201)
                .body(Map.of(
                        "message",
                        "Account created successfully!"
                ));
    }

    public record RegistrationRequest(
            String email,
            String password
    ) {}
}
