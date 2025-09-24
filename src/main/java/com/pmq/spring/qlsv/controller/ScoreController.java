package com.pmq.spring.qlsv.controller;

import com.pmq.spring.qlsv.dto.response.ApiResponse;
import com.pmq.spring.qlsv.dto.response.ScoreSummaryResponse;
import com.pmq.spring.qlsv.model.Score;
import com.pmq.spring.qlsv.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/student")
public class ScoreController {
    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    // Nhap diem cua sinh vien
    @PutMapping("/{stuCode}/score/{subCode}")
    public Score updateScore(@PathVariable String stuCode,
                             @PathVariable String subCode,
                             @RequestBody Score score) {
        return scoreService.updateScore(stuCode, subCode, score);
    }

    // Xem ket qua do truot cua sinh vien
    @GetMapping("/{stuCode}/subject/summary")
    public ApiResponse<List<ScoreSummaryResponse>> getSummaryScore(@PathVariable String stuCode) {
       return ApiResponse.<List<ScoreSummaryResponse>>builder()
                .result(scoreService.getScoreListByStudent(stuCode))
                .message("lấy danh sách tổng kết thành công")
                .build();
    }
}
