package com.mc.saas.config;

import com.mc.saas.domain.user.User;
import com.mc.saas.domain.user.UserGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDataInitializer implements CommandLineRunner {

    private final UserGateway userGateway;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.bootstrap.admin-email:admin@motoclube.com}")
    private String adminEmail;

    @Value("${security.bootstrap.admin-password:admin123}")
    private String adminPassword;

    @Value("${security.bootstrap.admin-name:Administrador}")
    private String adminName;

    @Override
    public void run(String... args) {
        if (userGateway.existsByEmail(adminEmail)) {
            return;
        }
        User admin = User.create(adminName, adminEmail, passwordEncoder.encode(adminPassword));
        userGateway.save(admin);
        log.info("Usuário administrador criado: {} (altere a senha em produção).", adminEmail);
    }
}
