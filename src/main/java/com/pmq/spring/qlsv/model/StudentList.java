package com.pmq.spring.qlsv.model;

public class StudentList {
    private String stuCode;
    private String stuName;
    public StudentList(String stuCode, String stuName){
        this.stuCode = stuCode;
        this.stuName = stuName;
    }
    public String getStuCode() {return stuCode;}
    public void setStuCode(String stuCode){this.stuCode = stuCode;}

    public String getStuName() { return stuName; }
    public void setStuName(String stuName) { this.stuName = stuName; }
}
