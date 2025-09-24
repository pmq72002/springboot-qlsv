package com.pmq.spring.qlsv.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectOfStudentResponse {
    private String stuCode;
    private String stuName;
    private String subCode;
    private String subName;
    private Integer subNum;
}
