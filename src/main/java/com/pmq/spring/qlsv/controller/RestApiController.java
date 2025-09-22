package com.pmq.spring.qlsv.controller;


import com.pmq.spring.qlsv.constant.StudentSub;
import com.pmq.spring.qlsv.dto.request.ChangePasswordRequest;
import com.pmq.spring.qlsv.dto.response.StudentResponse;
import com.pmq.spring.qlsv.dto.response.SubjectResponse;
import com.pmq.spring.qlsv.model.*;
import com.pmq.spring.qlsv.dto.response.ApiResponse;
import com.pmq.spring.qlsv.service.ScoreService;
import com.pmq.spring.qlsv.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/student")

public class RestApiController {

    private final StudentService studentService;

    private final ScoreService scoreService;
    @Autowired
    public RestApiController(StudentService studentService, ScoreService scoreService) {
        this.studentService = studentService;
        this.scoreService = scoreService;
    }
    //add mon hoc
    @PostMapping("/subject/create")
    public ApiResponse<SubjectResponse> createSubject(@RequestBody @Valid Subject subject){
        ApiResponse<SubjectResponse> apiResponse = new ApiResponse<>();
        SubjectResponse created = studentService.saveSubject(subject);
        apiResponse.setMessage("Thêm môn học mới thành công");
        apiResponse.setResult(created);
        return apiResponse;
    }
    //add sinh vien
    @PostMapping("/create")
    public ApiResponse<StudentResponse>  createStudent(@RequestBody @Valid Student student){
        ApiResponse<StudentResponse > apiResponse = new ApiResponse<>();
        StudentResponse saved = studentService.saveStudent(student);
        apiResponse.setCode(1000);
        apiResponse.setMessage("Tạo sinh viên thành công!");
        apiResponse.setResult(saved);
        return apiResponse;
    }
    // xem danh sach mon hoc
    @GetMapping("/subject/list")
    public List<SubjectList> getAllSubjectList(){
        return studentService.getAllSubjectList();
    }
    //1. xem danh sach sinh vien
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
   public List<StudentList> getAllStudentList(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("stuCode: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return studentService.getAllStudentList();
    }


    //2. xem thong tin 1 sv
    @GetMapping("/{stuCode}")
    public StudentResponse getStudentById(@PathVariable String stuCode){
        return  studentService.getStudentById(stuCode);
    }


    //3. xem so mon sinh vien dang ki
    @GetMapping("/{stuCode}/subject")
    public List<Map<String,Object>> getSubjectAndStudent(@PathVariable String stuCode){
        List<Map<String,Object>> fullList = scoreService.getSubjectAndStudent(stuCode);

        return fullList.stream().map(item -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(StudentSub.STUCODE, item.get(StudentSub.STUCODE));
            map.put(StudentSub.STUNAME, item.get(StudentSub.STUNAME));
            map.put(StudentSub.SUBCODE, item.get(StudentSub.SUBCODE));
            map.put(StudentSub.SUBNAME, item.get(StudentSub.SUBNAME));
            map.put(StudentSub.SUBNUM, item.get(StudentSub.SUBNUM));
            return map;
        }).toList();
    }



    //4. xem diem mon hoc cua sinh vien
    @GetMapping("/{stuCode}/score")
    public List<Map<String, Object>> getScore(@PathVariable String stuCode){
       return scoreService.getSubjectAndStudent(stuCode);
    }

    //5. Nhap diem cua sinh vien
    @PutMapping("/{stuCode}/score/{subCode}")
    public Score updateScore(@PathVariable String stuCode,
                             @PathVariable String subCode,
                             @RequestBody Score score){
        return scoreService.updateScore(stuCode,subCode,score);
    }


    //6. Xem ket qua do truot cua sinh vien
    @GetMapping("/{stuCode}/subject/summary")
    public  List<Map<String,Object>> getSummaryScore(@PathVariable String stuCode){
        List<Score> scoreList = scoreService.getScoreListByStudent(stuCode);
        return scoreList.stream().map(score -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(StudentSub.STUCODE,score.getStudent().getStuCode());
            map.put(StudentSub.STUNAME,score.getStudent().getStuName());
            map.put(StudentSub.SUBCODE,score.getSubject().getSubCode());
            map.put(StudentSub.SUBNUM,score.getSubject().getSubNum());
            map.put(StudentSub.SUBNAME, score.getSubject().getSubName());
            map.put(StudentSub.PROCESSPOINT, score.getProcessPoint());
            map.put(StudentSub.COMPONENTPOINT, score.getComponentPoint());

            double summaryScore = scoreService.summaryScoreCal(score);
            map.put(StudentSub.SUMMARYSCORE, summaryScore);

            map.put(StudentSub.PASSSUB, scoreService.passedSub(score) ? StudentSub.PASS : StudentSub.FAILED);

            return map;
        }).toList();
    }

    //edit mon hoc
    @PutMapping("/subject/{subCode}")
    public ApiResponse<SubjectResponse> updateSubject(@PathVariable String subCode, @RequestBody Subject subject){
        ApiResponse<SubjectResponse> apiResponse = new ApiResponse<>();
        SubjectResponse updated = studentService.updateSubject(subCode, subject);
        apiResponse.setMessage("Cập nhật thông tin thành công");
        apiResponse.setResult(updated);
        return apiResponse;
    }
    //edit sinh vien
    @PutMapping("/{stuCode}")
    public ApiResponse<StudentResponse> updateStudent(@PathVariable String stuCode, @RequestBody Student student){
        ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
        StudentResponse updated =  studentService.updateStudent(stuCode, student);
        apiResponse.setMessage("Cập nhật thông tin thành công");
        apiResponse.setResult(updated);
        return apiResponse;
    }

    //doi mat khau
    @PutMapping("/{stuCode}/password")
    public ApiResponse<StudentResponse> changePasswordStudent(@PathVariable String stuCode, @RequestBody @Valid ChangePasswordRequest request){
        ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();

        StudentResponse updated =  studentService.changePasswordStudent(stuCode, request.getOldPassword(), request.getNewPassword(), request.getConfirmPassword());
        apiResponse.setMessage("Đổi mật khẩu thành công");
        apiResponse.setResult(updated);
        return apiResponse;
    }
    // dang ky mon hoc
    @PostMapping("/{stuCode}/register/{subCode}")
    public ApiResponse<Score> registerSubject(@PathVariable String stuCode, @PathVariable String subCode){
        ApiResponse<Score> apiResponse = new ApiResponse<>();
        Score score = scoreService.registerSubject(stuCode, subCode);
        apiResponse.setMessage("Đăng ký môn học thành công");
        apiResponse.setResult(score);
        return apiResponse;
    }
    // xoa mon hoc
    @DeleteMapping("/subject/{subCode}")
    public ResponseEntity<Map<String, String>> deleteSubject(@PathVariable String subCode){
        studentService.deleteSubject(subCode);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Xóa thành công môn học có mã môn học: "+ subCode);
        return ResponseEntity.ok(response);
    }
    //xoa sinh vien
    @DeleteMapping("/{stuCode}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable String stuCode){
        studentService.deleteStudent(stuCode);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Xóa thành công sinh viên có msv: " + stuCode);
        return ResponseEntity.ok(response);
    }

}
