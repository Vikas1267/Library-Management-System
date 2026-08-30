package com.library.librarymanagement.config;

import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.Role;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(UserRepository userRepository,
                           BookRepository bookRepository,
                           PasswordEncoder passwordEncoder,
                           JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            jdbcTemplate.execute("ALTER TABLE users MODIFY COLUMN role VARCHAR(20)");
        } catch (Exception ignored) {
        }

        User admin = userRepository.findByEmail("admin@library.com").orElse(new User());
        admin.setName("Admin User");
        admin.setEmail("admin@library.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);
        System.out.println("Default ADMIN ready: admin@library.com / admin123");

        User member = userRepository.findByEmail("member@library.com").orElse(new User());
        member.setName("Member User");
        member.setEmail("member@library.com");
        member.setPassword(passwordEncoder.encode("member123"));
        member.setRole(Role.MEMBER);
        userRepository.save(member);
        System.out.println("Default MEMBER ready: member@library.com / member123");

        if (bookRepository.count() == 0) {
            bookRepository.save(new Book("Clean Code", "Robert C. Martin", "9780132350884"));
            bookRepository.save(new Book("Clean Architecture", "Robert C. Martin", "9780134494166"));
            bookRepository.save(new Book("Design Patterns", "Erich Gamma", "9780201633610"));
            System.out.println("Sample books initialized in database.");
        }
    }
}
