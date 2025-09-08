package com.pmq.spring.QLSV.service;

import com.pmq.spring.QLSV.model.SinhVien;
import com.pmq.spring.QLSV.model.SinhVienList;
import com.pmq.spring.QLSV.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;

    public List<SinhVien> getAllSinhVien(){
        return sinhVienRepository.findAll();
    }

    public List<SinhVienList> getAllSinhVienList(){
        return sinhVienRepository.findAll()
                .stream()
                .map(sv -> new SinhVienList(sv.getMsv(), sv.getTensv()))
                .toList();
    }

    public SinhVien getSinhVienById(String msv){
        return sinhVienRepository.findById(msv).orElse(null);
    }
    public SinhVien saveSinhVien(SinhVien sinhVien){
        return sinhVienRepository.save(sinhVien);
    }
    public SinhVien updateSinhVien(String msv, SinhVien sinhVienDetails) {
        return sinhVienRepository.findById(msv).map(sinhVien -> {
            sinhVien.setTensv(sinhVienDetails.getTensv());
            sinhVien.setGioitinh(sinhVienDetails.getGioitinh());
            sinhVien.setNgaysinh(sinhVienDetails.getNgaysinh());
            sinhVien.setLop(sinhVienDetails.getLop());
            sinhVien.setKhoa(sinhVienDetails.getKhoa());
            return sinhVienRepository.save(sinhVien);
        }).orElse(null);
    }
    public void deleteSinhVien(String msv){
        sinhVienRepository.deleteById(msv);
    }
}
