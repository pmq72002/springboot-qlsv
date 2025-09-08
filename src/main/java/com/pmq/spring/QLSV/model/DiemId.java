package com.pmq.spring.QLSV.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class DiemId implements Serializable {
    private String msv;
    private String maMH;
    public DiemId(){}
    public DiemId(String msv, String maMH) {
        this.msv = msv;
        this.maMH = maMH;
    }

    // Getter & Setter
    public String getMsv() { return msv; }
    public void setMsv(String msv) { this.msv = msv; }

    public String getMaMH() { return maMH; }
    public void setMaMH(String maMH) { this.maMH = maMH; }

    // hashCode và equals bắt buộc khi làm khóa composite
    @Override
    public int hashCode() {
        return msv.hashCode() + maMH.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DiemId)) return false;
        DiemId other = (DiemId) obj;
        return this.msv.equals(other.msv) && this.maMH.equals(other.maMH);
    }
}
