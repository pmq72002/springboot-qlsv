package com.pmq.spring.qlsv.service;

import com.pmq.spring.qlsv.dto.response.StudentResponse;
import com.pmq.spring.qlsv.enums.Role;
import com.pmq.spring.qlsv.exception.AppException;
import com.pmq.spring.qlsv.exception.ErrorCode;
import com.pmq.spring.qlsv.model.Student;
import com.pmq.spring.qlsv.model.StudentList;
import com.pmq.spring.qlsv.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private PasswordEncoder passwordEncoder;
    private StudentRepository studentRepository;
    @Autowired
    public StudentService(PasswordEncoder passwordEncoder, StudentRepository studentRepository){
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllSinhVien(){
        return studentRepository.findAll();
    }

    
    public List<StudentList> getAllStudentList(){
        log.info("IN method get List");
        return studentRepository.findAll()
                .stream()
                .map(sv -> new StudentList(sv.getStuCode(), sv.getStuName()))
                .toList();
    }

    
    public StudentResponse getStudentById(String stuCode){
        log.info("In method get student by ID");
        Student student = studentRepository.findById(stuCode)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED));

        StudentResponse dto = new StudentResponse();
        dto.setStuCode(student.getStuCode());
        dto.setStuName(student.getStuName());
        dto.setGender(student.getGender());
        dto.setBirth(student.getBirth());
        dto.setClassR(student.getClassR());
        dto.setCourse(student.getCourse());
        dto.setRoles(student.getRoles());
        log.info("DEBUG >> DB stuCode={}, Auth.name={}", dto.getStuCode(), SecurityContextHolder.getContext().getAuthentication().getName());
        return dto;
    }
    public StudentResponse saveStudent(Student student){
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        if (studentRepository.existsById(student.getStuCode()))
            throw new AppException(ErrorCode.STUDENT_EXISTED);

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        student.setRoles(roles);
        Student saved = studentRepository.save(student);

        return new StudentResponse(
                saved.getStuCode(),
                saved.getStuName(),
                saved.getGender(),
                saved.getBirth(),
                saved.getClassR(),
                saved.getCourse(),
                saved.getRoles()
        );
    }
    public Student updateStudent(String stuCode, Student studentDetails) {
        return studentRepository.findById(stuCode).map(student -> {
            student.setStuName(studentDetails.getStuName());
            student.setGender(studentDetails.getGender());
            student.setBirth(studentDetails.getBirth());
            student.setClassR(studentDetails.getClassR());
            student.setCourse(studentDetails.getCourse());
            student.setPassword(passwordEncoder.encode(studentDetails.getPassword()));
            return studentRepository.save(student);
        }).orElse(null);
    }


    public void deleteStudent(String stuCode){
        studentRepository.deleteById(stuCode);
    }
}
