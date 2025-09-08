package com.pmq.spring.qlsv.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ScoreId implements Serializable {
    private String stuCode;
    private String subCode;
    public ScoreId(){}
    public ScoreId(String stuCode, String subCode) {
        this.stuCode = stuCode;
        this.subCode = subCode;
    }

    // Getter & Setter
    public String getStuCode() { return stuCode; }
    public void setMsv(String stuCode) { this.stuCode = stuCode; }

    public String getSubCode() { return subCode; }
    public void setSubCode(String subCode) { this.subCode = subCode; }

    // hashCode và equals bắt buộc khi làm khóa composite
    @Override
    public int hashCode() {
        return stuCode.hashCode() + subCode.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ScoreId)) return false;
        ScoreId other = (ScoreId) obj;
        return this.stuCode.equals(other.stuCode) && this.subCode.equals(other.subCode);
    }
}
