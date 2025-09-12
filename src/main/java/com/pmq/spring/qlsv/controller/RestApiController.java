package com.pmq.spring.qlsv.controller;


import com.pmq.spring.qlsv.constant.StudentSub;
import com.pmq.spring.qlsv.model.*;
import com.pmq.spring.qlsv.response.ApiResponse;
import com.pmq.spring.qlsv.service.ScoreService;
import com.pmq.spring.qlsv.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/student")

public class RestApiController {

    private StudentService studentService;

    private ScoreService scoreService;
    @Autowired
    public RestApiController(StudentService studentService, ScoreService scoreService) {
        this.studentService = studentService;
        this.scoreService = scoreService;
    }
    
    //add sinh vien
    @PostMapping("/create")
    ApiResponse<Student>  createStudent(@RequestBody @Valid Student student){
        ApiResponse<Student> apiResponse = new ApiResponse<>();
        apiResponse.setResult(studentService.saveStudent(student));
        return apiResponse;
    }

    //1. xem danh sach sinh vien
    @GetMapping("/")
   public List<StudentList> getAllStudentList(){
        return studentService.getAllStudentList();
    }


    //2. xem thong tin 1 sv
    @GetMapping("/{stuCode}")
    public Student getStudentById(@PathVariable String stuCode){
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


    //edit sinh vien
    @PutMapping("/{stuCode}")
    public Student updateStudent(@PathVariable String stuCode, @RequestBody Student student){
        return studentService.updateStudent(stuCode, student);
    }
    //xoa sinh vien
    @DeleteMapping("/{stuCode}")
    public String deleteStudent(@PathVariable String stuCode){
        studentService.deleteStudent(stuCode);
        return "Deleted student have stuCode = " +stuCode;
    }

}
