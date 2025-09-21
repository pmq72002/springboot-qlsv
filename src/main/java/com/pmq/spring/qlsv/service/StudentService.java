package com.pmq.spring.qlsv.service;

import com.pmq.spring.qlsv.dto.response.StudentResponse;
import com.pmq.spring.qlsv.dto.response.SubjectResponse;
import com.pmq.spring.qlsv.enums.Role;
import com.pmq.spring.qlsv.exception.AppException;
import com.pmq.spring.qlsv.exception.ErrorCode;
import com.pmq.spring.qlsv.model.Student;
import com.pmq.spring.qlsv.model.StudentList;
import com.pmq.spring.qlsv.model.Subject;
import com.pmq.spring.qlsv.model.SubjectList;
import com.pmq.spring.qlsv.repository.StudentRepository;
import com.pmq.spring.qlsv.repository.SubjectRepository;
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
    private SubjectRepository subjectRepository;
    @Autowired
    public StudentService(PasswordEncoder passwordEncoder, StudentRepository studentRepository,SubjectRepository subjectRepository){
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    
    public List<StudentList> getAllStudentList(){
        log.info("IN method get List");
        return studentRepository.findAll()
                .stream()
                .filter(sv -> sv.getRoles() == null
                        || sv.getRoles().stream().noneMatch(r -> r.equals(Role.ADMIN.name())))
                .map(sv -> new StudentList(sv.getStuCode(), sv.getStuName()))
                .toList();
    }

    public List<SubjectList> getAllSubjectList() {
        return subjectRepository.findAll()
                .stream()
                .map(sj -> new SubjectList(sj.getSubCode(), sj.getSubName(), sj.getSubNum()))
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

    public SubjectResponse saveSubject(Subject subject){
        subject.setRatioProcess(0.3);
        subject.setRatioComponent(0.7);

        if(subjectRepository.existsById(subject.getSubCode()))
            throw  new AppException(ErrorCode.SUBJECT_EXISTED);
        if(subjectRepository.existsBySubName(subject.getSubName()))
            throw new AppException(ErrorCode.SUBJECT_EXISTED);
        Subject created = subjectRepository.save(subject);
        return new SubjectResponse(
                created.getSubCode(),
                created.getSubName(),
                created.getSubNum(),
                created.getRatioProcess(),
                created.getRatioComponent()
        );
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

    public SubjectResponse updateSubject(String subCode, Subject subjectDetails){
        return subjectRepository.findById(subCode).map(subject -> {
            subject.setSubName(subjectDetails.getSubName());
            subject.setSubNum(subjectDetails.getSubNum());
            Subject updated = subjectRepository.save(subject);

            SubjectResponse response = new SubjectResponse();
            response.setSubName(updated.getSubName());
            response.setSubNum(updated.getSubNum());
            return response;
        }).orElseThrow(() -> new RuntimeException("Subject not found with code: " + subCode));
    }

    public StudentResponse updateStudent(String stuCode, Student studentDetails) {
        return studentRepository.findById(stuCode).map(student -> {
            student.setStuName(studentDetails.getStuName());
            student.setGender(studentDetails.getGender());
            student.setBirth(studentDetails.getBirth());
            student.setClassR(studentDetails.getClassR());
            student.setCourse(studentDetails.getCourse());
            Student updated = studentRepository.save(student);


            StudentResponse response = new StudentResponse();
            response.setStuCode(updated.getStuCode());
            response.setStuName(updated.getStuName());
            response.setGender(updated.getGender());
            response.setBirth(updated.getBirth());
            response.setClassR(updated.getClassR());
            response.setCourse(updated.getCourse());
            response.setRoles(updated.getRoles());
            return response;
        }).orElse(null);
    }

    public StudentResponse changePasswordStudent(String stuCode, String oldPassword, String newPassword, String confirmPassword) {
        return studentRepository.findById(stuCode).map(student -> {
            if (!passwordEncoder.matches(oldPassword, student.getPassword())) {
                throw new AppException(ErrorCode.OLD_PASSWORD_INCORRECT);
            }
            if (!newPassword.equals(confirmPassword)) {
                throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
            }
            student.setPassword(passwordEncoder.encode(newPassword));
            studentRepository.save(student);
            return new StudentResponse(student);
        }).orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND)) ;
    }

    public void deleteSubject(String subCode){
        subjectRepository.deleteById(subCode);
    }
    public void deleteStudent(String stuCode){
        studentRepository.deleteById(stuCode);
    }
}
