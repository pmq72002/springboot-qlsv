package com.pmq.spring.qlsv.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sinhvien")
@Data
public class SinhVien {
    @Id
    private String msv;

    private String tensv;
    private String gioitinh;
    private String ngaysinh;
    private String lop;
    private String khoa;
}
