package com.pmq.spring.qlsv.controller;

import com.pmq.spring.qlsv.constant.StudentSub;
import com.pmq.spring.qlsv.dto.response.ApiResponse;
import com.pmq.spring.qlsv.dto.response.SubjectOfStudentResponse;
import com.pmq.spring.qlsv.dto.response.SubjectResponse;
import com.pmq.spring.qlsv.model.Score;
import com.pmq.spring.qlsv.model.Subject;
import com.pmq.spring.qlsv.model.SubjectList;
import com.pmq.spring.qlsv.service.ScoreService;
import com.pmq.spring.qlsv.service.SubjectService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
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
    @CacheEvict(value = "allSubjects", allEntries = true)
    public ApiResponse<SubjectResponse> createSubject(@RequestBody @Valid Subject subject) {
        ApiResponse<SubjectResponse> apiResponse = new ApiResponse<>();
        SubjectResponse created = subjectService.saveSubject(subject);
        apiResponse.setMessage("Thêm môn học mới thành công");
        apiResponse.setResult(created);
        return apiResponse;
    }

    // xem danh sach mon hoc
    @GetMapping("/subject/list")
    public ApiResponse<List<SubjectList>> getAllSubjectList() {
        log.info("------subject list------");
        List<SubjectList> subjects = subjectService.getAllSubjectList();
        ApiResponse<List<SubjectList>> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Lấy danh sách môn học thành công");
        apiResponse.setResult(subjects);
        return apiResponse;
    }

    // xem so mon sinh vien dang ki
    @GetMapping("/{stuCode}/subject")
    public ApiResponse<List<SubjectOfStudentResponse>> getSubjectAndStudent(@PathVariable String stuCode) {
        log.info("------subject student------");
        return ApiResponse.<List<SubjectOfStudentResponse>>builder()
                .result(scoreService.getSubjectAndStudent(stuCode))
                .message("Lấy môn học của sinh viên thành công")
                .build();
    }

    //edit mon hoc
    @PutMapping("/subject/{subCode}")
    @CacheEvict(value = "allSubjects", allEntries = true)
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
    @CacheEvict(value = "allSubjects", allEntries = true)
    public ApiResponse<Void> deleteSubject(@PathVariable String subCode) {
        subjectService.deleteSubject(subCode);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Xóa thành công môn học: ");
        return apiResponse;
    }
}
