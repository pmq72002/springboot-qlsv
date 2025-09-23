package com.pmq.spring.qlsv.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentList {
    private String stuCode;
    private String stuName;

    public StudentList(String stuCode, String stuName) {
        this.stuCode = stuCode;
        this.stuName = stuName;
    }

}
