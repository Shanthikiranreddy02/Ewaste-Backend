package com.ewaste.config;

import com.ewaste.entity.User;
import com.ewaste.enums.Role;
import com.ewaste.enums.Status;
import com.ewaste.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository,
                                         PasswordEncoder passwordEncoder) {

        return args -> {

            String adminEmail = "admin@ewaste.com";

            if (!userRepository.existsByEmail(adminEmail)) {

                User admin = new User();

                admin.setName("Default Admin");
                admin.setUsername("admin");
                admin.setPhone("9999999999");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("admin123"));

                admin.setRole(Role.ADMIN);
                admin.setStatus(Status.APPROVED);

                userRepository.save(admin);

                System.out.println("Default Admin Created Successfully");

            } else {

                System.out.println("Admin already exists");

            }

        };
    }
}