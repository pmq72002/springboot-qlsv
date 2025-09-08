package com.pmq.spring.qlsv.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "monhoc")
@Data
public class MonHoc {
    @Id
    private String maMH;

    private String tenMH;

    @Column(name = "soTiet")
    private Integer soTiet;

    private double tileQT;
    private double tileTP;

}
