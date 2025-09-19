package com.pmq.spring.qlsv.dto.response;

import com.pmq.spring.qlsv.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class StudentResponse {
    private String stuCode;
    private String stuName;
    private String gender;
    private String birth;
    private String classR;
    private String course;
    private Set<String> roles;

    public StudentResponse(Student student) {
    }
}
