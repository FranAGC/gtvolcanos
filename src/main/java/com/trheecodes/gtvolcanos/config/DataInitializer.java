package com.trheecodes.gtvolcanos.config;

import com.trheecodes.gtvolcanos.user.User;
import com.trheecodes.gtvolcanos.user.UserRepository;
import com.trheecodes.gtvolcanos.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = User.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .firstName("Admin")
                    .lastName("System")
                    .role(UserRole.ADMIN)
                    .active(true)
                    .build();

            userRepository.save(admin);

            log.info("Usuario administrador creado: {}", adminEmail);
        } else {
            log.debug("Usuario administrador ya existe, omitiendo creación");
        }
    }
}