package com.pmq.spring.qlsv.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "student")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @Size(min=4, message = "Mã sinh viên ít nhất 4 ký tự")
    private String stuCode;

    @Size(min=8, message = "NAME_INVALID")
    private String stuName;
    @Size(min = 2, message = "GENDER_INVALID")
    private String gender;
    @Size(min = 10,max = 10, message = "BIRTH_INVALID")
    private String birth;
    @Size(min = 4, message = "CLASSR_INVALID")
    private String classR;
    @Size(min = 3, message = "COURSE_INVALID")
    private String course;
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;

    private Set<String> roles;

}
