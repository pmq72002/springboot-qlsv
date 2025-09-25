package com.pmq.spring.qlsv.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "student")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    @Id
    @Size(min = 4, message = "STUCODE_INVALID")
    private String stuCode;

    @Size(min = 8, message = "NAME_INVALID")
    private String stuName;
    @Size(min = 2, message = "GENDER_INVALID")
    private String gender;
    @Past(message = "BIRTH_INVALID")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "birth")
    private LocalDate birth;
    @Size(min = 4, message = "CLASSR_INVALID")
    private String classR;
    @Size(min = 3, message = "COURSE_INVALID")
    private String course;
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;

    private Set<String> roles;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private static List<Score> scores = new ArrayList<>();


}
