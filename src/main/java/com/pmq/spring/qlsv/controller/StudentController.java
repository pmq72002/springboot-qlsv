package com.pmq.spring.qlsv.controller;

import com.pmq.spring.qlsv.dto.request.ChangePasswordRequest;
import com.pmq.spring.qlsv.dto.response.ApiResponse;
import com.pmq.spring.qlsv.dto.response.StudentResponse;
import com.pmq.spring.qlsv.model.Student;
import com.pmq.spring.qlsv.model.StudentList;
import com.pmq.spring.qlsv.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/student")

public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //add sinh vien
    @PostMapping("/create")
    @CacheEvict(value = "allStudents", allEntries = true)
    public ApiResponse<StudentResponse> createStudent(@RequestBody @Valid Student student) {
        ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
        StudentResponse saved = studentService.saveStudent(student);
        apiResponse.setCode(1000);
        apiResponse.setMessage("Tạo sinh viên thành công!");
        apiResponse.setResult(saved);
        return apiResponse;
    }

    // xem danh sach sinh vien
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<StudentList>> getAllStudentList() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("------student list------");
        log.info("stuCode: {}", authentication.getName());
        authentication.getAuthorities()
                .forEach(grantedAuthority ->
                        log.info("role: "+grantedAuthority.getAuthority()));
        List<StudentList> students = studentService.getAllStudentList();
        ApiResponse<List<StudentList>> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Lấy danh sách sinh viên thành công");
        apiResponse.setResult(students);
        return apiResponse;
    }


    //xem thong tin 1 sv
    @GetMapping("/{stuCode}")
    public ApiResponse<StudentResponse> getStudentById(@PathVariable String stuCode) {
        StudentResponse student = studentService.getStudentById(stuCode);
        ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Lấy thông tin sinh viên: "+stuCode);
        apiResponse.setResult(student);
        return apiResponse;
    }


    //edit sinh vien
    @PutMapping("/{stuCode}")
    public ApiResponse<StudentResponse> updateStudent(@PathVariable String stuCode, @RequestBody Student student) {
        ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
        StudentResponse updated = studentService.updateStudent(stuCode, student);
        apiResponse.setMessage("Cập nhật thông tin thành công");
        apiResponse.setResult(updated);
        return apiResponse;
    }

    //doi mat khau
    @PutMapping("/{stuCode}/password")
    public ApiResponse<StudentResponse> changePasswordStudent(@PathVariable String stuCode, @RequestBody @Valid ChangePasswordRequest request) {
        ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();

        StudentResponse updated = studentService.changePasswordStudent(stuCode, request.getOldPassword(), request.getNewPassword(), request.getConfirmPassword());
        apiResponse.setMessage("Đổi mật khẩu thành công");
        apiResponse.setResult(updated);
        return apiResponse;
    }


    //xoa sinh vien
    @DeleteMapping("/{stuCode}")
    @CacheEvict(value = "allStudents", allEntries = true)
    public ApiResponse<Void> deleteStudent(@PathVariable String stuCode) {
        studentService.deleteStudent(stuCode);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Xóa thành công sinh viên: ");
        return apiResponse;
    }
}
