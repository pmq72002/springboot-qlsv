package com.pmq.spring.qlsv.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subject")
@Data
public class Subject {
    @Id
    private String subCode;

    private String subName;

    @Column(name = "subNum")
    private Integer subNum;

    private double ratioProcess;
    private double ratioComponent;

}
