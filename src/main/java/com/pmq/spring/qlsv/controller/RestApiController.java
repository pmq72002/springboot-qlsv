package com.pmq.spring.qlsv.controller;


import com.pmq.spring.qlsv.constant.StudentSub;
import com.pmq.spring.qlsv.model.*;
import com.pmq.spring.qlsv.service.ScoreService;
import com.pmq.spring.qlsv.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/student")
public class RestApiController {

    private StudentService studentService;

    private ScoreService scoreService;
    @Autowired
    public RestApiController(StudentService studentService, ScoreService scoreService) {
        this.studentService = studentService;
        this.scoreService = scoreService;
    }
    
    //add sinh vien
    @PostMapping
    public Student createStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
    }

    //1. xem danh sach sinh vien
    @GetMapping("/")
    public String getAllSinhVienLists(Model model){
        List<StudentList> list = studentService.getAllStudentList();
        model.addAttribute("students", list);
        return "stulist";
    }


    //2. xem thong tin 1 sv
    @GetMapping("/{stuCode}")
    public String getStudentById(@PathVariable String stuCode, Model model){
        Student stu = studentService.getStudentById(stuCode);
        model.addAttribute("stu", stu);
        return "studetails";
    }


    //3. xem so mon sinh vien dang ki
    @GetMapping("/{stuCode}/subject")
    public String getSubjectAndStudent(@PathVariable String stuCode, Model model){
        List<Map<String,Object>> fullList = scoreService.getSubjectAndStudent(stuCode);
        Student stu = studentService.getStudentById(stuCode);

        List<Map<String,Object>> result = fullList.stream().map(item -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(StudentSub.STUCODE, item.get(StudentSub.STUCODE));
            map.put(StudentSub.STUNAME, item.get(StudentSub.STUNAME));
            map.put(StudentSub.SUBNAME, item.get(StudentSub.SUBNAME));
            map.put(StudentSub.SUBNUM, item.get(StudentSub.SUBNUM));
            return map;
        }).toList();
        model.addAttribute("list",result);
        model.addAttribute("stu", stu);
        return "stusubject";
    }



    //4. xem diem mon hoc cua sinh vien
    @GetMapping("/{stuCode}/score")
    public String getScore(@PathVariable String stuCode, Model model){
        List<Map<String, Object>> score = scoreService.getSubjectAndStudent(stuCode);
        Student stu = studentService.getStudentById(stuCode);
        model.addAttribute("score", score);
        model.addAttribute("stu", stu);
        return "stuscore";
    }

    //5. Nhap diem cua sinh vien
    @PostMapping("/{stuCode}/score/{subCode}")
    public String updateScore(@PathVariable String stuCode,
                             @PathVariable String subCode,
                             @RequestParam Double processPoint,
                             @RequestParam Double componentPoint) {
        Score score = new Score();
        score.setProcessPoint(processPoint);
        score.setComponentPoint(componentPoint);

        scoreService.updateScore(stuCode, subCode, score);

        return "redirect:/student/" + stuCode + "/score";
    }

    //6. Xem ket qua do truot cua sinh vien
    @GetMapping("/{stuCode}/subject/summary")
    public String getSummaryScore(@PathVariable String stuCode, Model model){
        List<Score> scoreList = scoreService.getScoreListByStudent(stuCode);
        Student stu = studentService.getStudentById(stuCode);
        List<Map<String,Object>> result = scoreList.stream().map(score -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(StudentSub.STUCODE,score.getStudent().getStuCode());
            map.put(StudentSub.STUNAME,score.getStudent().getStuName());
            map.put(StudentSub.SUBNUM,score.getSubject().getSubNum());
            map.put(StudentSub.SUBNAME, score.getSubject().getSubName());
            map.put(StudentSub.PROCESSPOINT, score.getProcessPoint());
            map.put(StudentSub.COMPONENTPOINT, score.getComponentPoint());

            double summaryScore = scoreService.summaryScoreCal(score);
            map.put(StudentSub.SUMMARYSCORE, summaryScore);

            map.put(StudentSub.PASSSUB, scoreService.passedSub(score) ? "Pass" : "Failed");

            return map;
        }).toList();
        model.addAttribute("result",result);
        model.addAttribute("stu", stu);

        return "stupass";
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
