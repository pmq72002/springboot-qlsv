package com.pmq.spring.qlsv.controller;

import com.pmq.spring.qlsv.constant.StudentSub;
import com.pmq.spring.qlsv.model.Score;
import com.pmq.spring.qlsv.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public List<Map<String, Object>> getSummaryScore(@PathVariable String stuCode) {
        List<Score> scoreList = scoreService.getScoreListByStudent(stuCode);
        return scoreList.stream().map(score -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(StudentSub.STUCODE, score.getStudent().getStuCode());
            map.put(StudentSub.STUNAME, score.getStudent().getStuName());
            map.put(StudentSub.SUBCODE, score.getSubject().getSubCode());
            map.put(StudentSub.SUBNUM, score.getSubject().getSubNum());
            map.put(StudentSub.SUBNAME, score.getSubject().getSubName());
            map.put(StudentSub.PROCESSPOINT, score.getProcessPoint());
            map.put(StudentSub.COMPONENTPOINT, score.getComponentPoint());

            double summaryScore = scoreService.summaryScoreCal(score);
            map.put(StudentSub.SUMMARYSCORE, summaryScore);

            map.put(StudentSub.PASSSUB, scoreService.passedSub(score) ? StudentSub.PASS : StudentSub.FAILED);

            return map;
        }).toList();
    }
}
