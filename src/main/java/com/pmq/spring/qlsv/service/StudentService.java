package com.pmq.spring.qlsv.service;

import com.pmq.spring.qlsv.model.Student;
import com.pmq.spring.qlsv.model.StudentList;
import com.pmq.spring.qlsv.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    public List<Student> getAllSinhVien(){
        return studentRepository.findAll();
    }

    public List<StudentList> getAllStudentList(){
        return studentRepository.findAll()
                .stream()
                .map(sv -> new StudentList(sv.getStuCode(), sv.getStuName()))
                .toList();
    }

    public Student getStudentById(String msv){
        return studentRepository.findById(msv).orElse(null);
    }
    public Student saveStudent(Student student){
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    public Student updateStudent(String msv, Student studentDetails) {
        return studentRepository.findById(msv).map(student -> {
            student.setStuName(studentDetails.getStuName());
            student.setGender(studentDetails.getGender());
            student.setBirth(studentDetails.getBirth());
            student.setClassR(studentDetails.getClassR());
            student.setCourse(studentDetails.getCourse());
            student.setPassword(passwordEncoder.encode(studentDetails.getPassword()));
            return studentRepository.save(student);
        }).orElse(null);
    }
    public void deleteStudent(String msv){
        studentRepository.deleteById(msv);
    }
}
