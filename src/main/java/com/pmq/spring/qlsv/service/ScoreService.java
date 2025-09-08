package com.pmq.spring.qlsv.service;

import com.pmq.spring.qlsv.constant.StudentSub;
import com.pmq.spring.qlsv.model.Score;
import com.pmq.spring.qlsv.model.ScoreId;
import com.pmq.spring.qlsv.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    @Autowired
    public ScoreService(ScoreRepository scoreRepository){
        this.scoreRepository = scoreRepository;
    }

    public List<Score> getScoreListByStudent(String stuCode) {
        return scoreRepository.findByStudent_stuCode(stuCode);
    }
    public List<Map<String, Object>> getSubjectAndStudent(String stuCode) {
        List<Score> scoreList = scoreRepository.findByStudent_stuCode(stuCode);

        return scoreList.stream().map(score -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(StudentSub.STUCODE, score.getStudent().getStuCode());
            map.put(StudentSub.SUBCODE, score.getSubject().getSubCode());
            map.put(StudentSub.STUNAME, score.getStudent().getStuName());
            map.put(StudentSub.SUBNAME, score.getSubject().getSubName());
            map.put(StudentSub.SUBNUM, score.getSubject().getSubNum());
            map.put(StudentSub.PROCESSPOINT, score.getProcessPoint());
            map.put(StudentSub.COMPONENTPOINT, score.getComponentPoint());
            return map;
        }).toList();
    }
    public double summaryScoreCal(Score score) {
        double processPoint = score.getProcessPoint();
        double componentPoint = score.getComponentPoint();
        double ratioProcess = score.getSubject().getRatioProcess();
        double ratioComponent = score.getSubject().getRatioComponent();

        double summaryScore = processPoint * ratioProcess + componentPoint * ratioComponent;

        // Làm tròn 2 chữ số
        return Math.round(summaryScore * 100.0) / 100.0;
    }
    public boolean passedSub(Score score) {
        return summaryScoreCal(score) >= 4.0;
    }
    public Score updateScore(String stuCode,String subCode, Score scoreDetails) {
        ScoreId id = new ScoreId(stuCode, subCode);
        return scoreRepository.findById(id).map(score ->{
            score.setProcessPoint(scoreDetails.getProcessPoint());
            score.setComponentPoint(scoreDetails.getComponentPoint());
            return scoreRepository.saveAndFlush(score);
        }).orElse(null);
    }
}

