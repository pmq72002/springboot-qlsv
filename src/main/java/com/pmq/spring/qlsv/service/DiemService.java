package com.pmq.spring.qlsv.service;

import com.pmq.spring.qlsv.model.Diem;
import com.pmq.spring.qlsv.model.DiemId;
import com.pmq.spring.qlsv.repository.DiemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiemService {

    private DiemRepository diemRepository;
    @Autowired
    public DiemService (DiemRepository diemRepository){
        this.diemRepository = diemRepository;
    }

    public List<Diem> getDiemListBySinhVien(String msv) {
        return diemRepository.findBySinhVien_Msv(msv);
    }
    public List<Map<String, Object>> getMonHocVaSinhVien(String msv) {
        List<Diem> diemList = diemRepository.findBySinhVien_Msv(msv);

        return diemList.stream().map(diem -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("msv", diem.getSinhVien().getMsv());
            map.put("maMH", diem.getMonHoc().getMaMH());
            map.put("tensv", diem.getSinhVien().getTensv());
            map.put("tenMH", diem.getMonHoc().getTenMH());
            map.put("soTiet", diem.getMonHoc().getSoTiet());
            map.put("diemQT", diem.getDiemQT());
            map.put("diemTP", diem.getDiemTP());
            return map;
        }).toList();
    }
    public double tinhDiemTongKet(Diem diem) {
        double diemQT = diem.getDiemQT();
        double diemTP = diem.getDiemTP();
        double tileQT = diem.getMonHoc().getTileQT();
        double tileTP = diem.getMonHoc().getTileTP();

        double diemTongKet = diemQT * tileQT + diemTP * tileTP;

        // Làm tròn 2 chữ số
        return Math.round(diemTongKet * 100.0) / 100.0;
    }
    public boolean daQuaMon(Diem diem) {
        return tinhDiemTongKet(diem) >= 4.0;
    }
    public Diem updateDiem(String msv,String maMH, Diem diemDetails) {
        DiemId id = new DiemId(msv, maMH);
        return diemRepository.findById(id).map(diem ->{
            diem.setDiemQT(diemDetails.getDiemQT());
            diem.setDiemTP(diemDetails.getDiemTP());
            return diemRepository.saveAndFlush(diem);
        }).orElse(null);
    }
}

