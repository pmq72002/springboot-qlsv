package com.pmq.spring.QLSV.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "diem")
@Data
public class Diem {
    @EmbeddedId
    private DiemId id;

    private double diemQT;
    private double diemTP;

    @ManyToOne
    @MapsId("msv")
    @JoinColumn(name = "msv")
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("maMH")
    @JoinColumn(name = "maMH")
    private MonHoc monHoc;
}
