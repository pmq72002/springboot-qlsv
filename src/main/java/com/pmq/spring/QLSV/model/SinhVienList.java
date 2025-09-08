package com.pmq.spring.QLSV.model;

public class SinhVienList {
    private String msv;
    private String tensv;
    public SinhVienList(String msv, String tensv){
        this.msv = msv;
        this.tensv = tensv;
    }
    public String getMsv() {return msv;}
    public void setMsv(String msv){this.msv = msv;}

    public String getTensv() { return tensv; }
    public void setTensv(String tensv) { this.tensv = tensv; }
}
