package com.pmq.spring.qlsv.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "subject")
@Data
public class Subject implements Serializable {
    @Id
    @Size(min = 3, message = "SUBCODE_INVALID")
    private String subCode;

    private String subName;

    @Column(name = "subNum")
    private Integer subNum;

    private double ratioProcess;
    private double ratioComponent;

}
