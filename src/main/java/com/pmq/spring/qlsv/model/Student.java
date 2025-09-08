package com.pmq.spring.qlsv.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "student")
@Data
public class Student {
    @Id
    private String stuCode;

    private String stuName;
    private String gender;
    private String birth;
    private String classR;
    private String course;
}
