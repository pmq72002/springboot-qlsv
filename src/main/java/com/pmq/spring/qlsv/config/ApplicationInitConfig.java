package com.pmq.spring.qlsv.config;

import com.pmq.spring.qlsv.enums.Role;
import com.pmq.spring.qlsv.model.Student;
import com.pmq.spring.qlsv.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationInitConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    ApplicationRunner applicationRunner(StudentRepository studentRepository){
        return args -> {
            if (studentRepository.findById("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                Student student = Student.builder()
                        .stuCode("admin")
                        .password(passwordEncoder.encode("adminofadmin"))
                        .roles(roles)
                        .build();

                studentRepository.save(student);
                log.warn("admin user has been created with default");
            }
        };
    }
}
