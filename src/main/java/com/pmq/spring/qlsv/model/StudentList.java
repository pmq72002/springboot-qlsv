package com.pmq.spring.qlsv.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class StudentList  implements Serializable {
    private String stuCode;
    private String stuName;

    public StudentList(String stuCode, String stuName) {
        this.stuCode = stuCode;
        this.stuName = stuName;
    }

}
