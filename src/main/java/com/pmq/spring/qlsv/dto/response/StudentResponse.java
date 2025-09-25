package com.pmq.spring.qlsv.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmq.spring.qlsv.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class StudentResponse {
    private String stuCode;
    private String stuName;
    private String gender;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birth;
    private String classR;
    private String course;
    private Set<String> roles;

    public StudentResponse(Student student) {
    }
}
