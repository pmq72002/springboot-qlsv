package com.pmq.spring.qlsv.model;

import jakarta.persistence.Embeddable;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
public class ScoreId implements Serializable {
    private String stuCode;
    @Setter
    private String subCode;

    public ScoreId() {
    }

    public ScoreId(String stuCode, String subCode) {
        this.stuCode = stuCode;
        this.subCode = subCode;
    }

    // Getter & Setter
    public String getStuCode() {
        return stuCode;
    }

    public void setMsv(String stuCode) {
        this.stuCode = stuCode;
    }

    public String getSubCode() {
        return subCode;
    }


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
