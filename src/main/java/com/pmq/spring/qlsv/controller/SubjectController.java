package com.pmq.spring.qlsv.controller;

import com.pmq.spring.qlsv.constant.StudentSub;
import com.pmq.spring.qlsv.dto.response.ApiResponse;
import com.pmq.spring.qlsv.dto.response.SubjectResponse;
import com.pmq.spring.qlsv.model.Score;
import com.pmq.spring.qlsv.model.Subject;
import com.pmq.spring.qlsv.model.SubjectList;
import com.pmq.spring.qlsv.service.ScoreService;
import com.pmq.spring.qlsv.service.SubjectService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/student")
public class SubjectController {
    private final SubjectService subjectService;
    private final ScoreService scoreService;

    @Autowired
    public SubjectController(SubjectService subjectService, ScoreService scoreService) {
        this.subjectService = subjectService;
        this.scoreService = scoreService;
    }

    //add mon hoc
    @PostMapping("/subject/create")
    public ApiResponse<SubjectResponse> createSubject(@RequestBody @Valid Subject subject) {
        ApiResponse<SubjectResponse> apiResponse = new ApiResponse<>();
        SubjectResponse created = subjectService.saveSubject(subject);
        apiResponse.setMessage("Thêm môn học mới thành công");
        apiResponse.setResult(created);
        return apiResponse;
    }

    // xem danh sach mon hoc
    @GetMapping("/subject/list")
    public List<SubjectList> getAllSubjectList() {
        return subjectService.getAllSubjectList();
    }

    // xem so mon sinh vien dang ki
    @GetMapping("/{stuCode}/subject")
    public List<Map<String, Object>> getSubjectAndStudent(@PathVariable String stuCode) {
        List<Map<String, Object>> fullList = scoreService.getSubjectAndStudent(stuCode);

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

    // xem diem mon hoc cua sinh vien
    @GetMapping("/{stuCode}/score")
    public List<Map<String, Object>> getScore(@PathVariable String stuCode) {
        return scoreService.getSubjectAndStudent(stuCode);
    }

    //edit mon hoc
    @PutMapping("/subject/{subCode}")
    public ApiResponse<SubjectResponse> updateSubject(@PathVariable String subCode, @RequestBody Subject subject) {
        ApiResponse<SubjectResponse> apiResponse = new ApiResponse<>();
        SubjectResponse updated = subjectService.updateSubject(subCode, subject);
        apiResponse.setMessage("Cập nhật thông tin thành công");
        apiResponse.setResult(updated);
        return apiResponse;
    }

    // dang ky mon hoc
    @PostMapping("/{stuCode}/register/{subCode}")
    public ApiResponse<Score> registerSubject(@PathVariable String stuCode, @PathVariable String subCode) {
        ApiResponse<Score> apiResponse = new ApiResponse<>();
        Score score = scoreService.registerSubject(stuCode, subCode);
        apiResponse.setMessage("Đăng ký môn học thành công");
        apiResponse.setResult(score);
        return apiResponse;
    }

    // xoa mon hoc
    @DeleteMapping("/subject/{subCode}")
    public ResponseEntity<Map<String, String>> deleteSubject(@PathVariable String subCode) {
        subjectService.deleteSubject(subCode);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Xóa thành công môn học có mã môn học: " + subCode);
        return ResponseEntity.ok(response);
    }
}
