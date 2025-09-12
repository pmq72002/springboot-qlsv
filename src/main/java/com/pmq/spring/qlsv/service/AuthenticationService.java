package com.pmq.spring.qlsv.service;

import com.pmq.spring.qlsv.repository.StudentRepository;
import com.pmq.spring.qlsv.request.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final StudentRepository studentRepository;

    @Autowired
    public AuthenticationService (StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public boolean authenticate(AuthenticationRequest request){
        var student = studentRepository.findById(request.getStuCode())
                .orElseThrow(() -> new RuntimeException("Student not found with code: " + request.getStuCode()));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(request.getPassword(), student.getPassword());
    }
}
