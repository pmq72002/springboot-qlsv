package com.pmq.spring.qlsv.controller;


import com.pmq.spring.qlsv.constant.SinhVienSub;
import com.pmq.spring.qlsv.model.*;
import com.pmq.spring.qlsv.service.DiemService;
import com.pmq.spring.qlsv.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/sinhvien")
public class RestApiController {

    private SinhVienService sinhVienService;

    private DiemService diemService;
    @Autowired
    public RestApiController(SinhVienService sinhVienService, DiemService diemService) {
        this.sinhVienService = sinhVienService;
        this.diemService = diemService;
    }
    
    //add sinh vien
    @PostMapping
    public SinhVien createSinhVien(@RequestBody SinhVien sinhVien){
        return sinhVienService.saveSinhVien(sinhVien);
    }

    //1. xem danh sach sinh vien
    @GetMapping("/")
    public String getAllSinhVienLists(Model model){
        List<SinhVienList> list = sinhVienService.getAllSinhVienList();
        model.addAttribute("sinhviens", list);
        return "svlist";
    }


    //2. xem thong tin 1 sv
    @GetMapping("/{msv}")
    public String getSinhVienById(@PathVariable String msv, Model model){
        SinhVien sv = sinhVienService.getSinhVienById(msv);
        model.addAttribute("sv", sv);
        return "svdetails";
    }


    //3. xem so mon sinh vien dang ki
    @GetMapping("/{msv}/monhoc")
    public String getMonHocVaSinhVien(@PathVariable String msv, Model model){
        List<Map<String,Object>> fullList = diemService.getMonHocVaSinhVien(msv);
        SinhVien sv = sinhVienService.getSinhVienById(msv);

        List<Map<String,Object>> result = fullList.stream().map(item -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(SinhVienSub.MSV, item.get(SinhVienSub.MSV));
            map.put(SinhVienSub.NAMESV, item.get(SinhVienSub.NAMESV));
            map.put(SinhVienSub.NAMESUB, item.get(SinhVienSub.NAMESUB));
            map.put(SinhVienSub.NUMSUB, item.get(SinhVienSub.NUMSUB));
            return map;
        }).toList();
        model.addAttribute("list",result);
        model.addAttribute("sv", sv);
        return "svsubject";
    }



    //4. xem diem mon hoc cua sinh vien
    @GetMapping("/{msv}/diem")
    public String getDiem(@PathVariable String msv, Model model){
        List<Map<String, Object>> diem = diemService.getMonHocVaSinhVien(msv);
        SinhVien sv = sinhVienService.getSinhVienById(msv);
        model.addAttribute("diem", diem);
        model.addAttribute("sv", sv);
        return "svscore";
    }

    //5. Nhap diem cua sinh vien
    @PostMapping("/{msv}/diem/{maMH}")
    public String updateDiem(@PathVariable String msv,
                             @PathVariable String maMH,
                             @RequestParam Double diemQT,
                             @RequestParam Double diemTP) {
        Diem diem = new Diem();
        diem.setDiemQT(diemQT);
        diem.setDiemTP(diemTP);

        diemService.updateDiem(msv, maMH, diem);

        return "redirect:/sinhvien/" + msv + "/diem";
    }

    //6. Xem ket qua do truot cua sinh vien
    @GetMapping("/{msv}/monhoc/tongket")
    public String getDiemTongKet(@PathVariable String msv, Model model){
        List<Diem> diemList = diemService.getDiemListBySinhVien(msv);
        SinhVien sv = sinhVienService.getSinhVienById(msv);
        List<Map<String,Object>> kq = diemList.stream().map(diem -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(SinhVienSub.MSV,diem.getSinhVien().getMsv());
            map.put(SinhVienSub.NAMESV,diem.getSinhVien().getTensv());
            map.put(SinhVienSub.NUMSUB,diem.getMonHoc().getSoTiet());
            map.put(SinhVienSub.NAMESUB, diem.getMonHoc().getTenMH());
            map.put(SinhVienSub.SCOREQT, diem.getDiemQT());
            map.put(SinhVienSub.SCORETP, diem.getDiemTP());

            double diemTK = diemService.tinhDiemTongKet(diem);
            map.put(SinhVienSub.SCORESUM, diemTK);

            map.put(SinhVienSub.PASS, diemService.daQuaMon(diem) ? "Đỗ" : "Trượt");

            return map;
        }).toList();
        model.addAttribute("kq",kq);
        model.addAttribute("sv", sv);

        return "svpass";
    }


    //edit sinh vien
    @PutMapping("/{msv}")
    public SinhVien updateSinhVien(@PathVariable String msv, @RequestBody SinhVien sinhVien){
        return sinhVienService.updateSinhVien(msv, sinhVien);
    }
    //xoa sinh vien
    @DeleteMapping("/{msv}")
    public String deleteSinhVien(@PathVariable String msv){
        sinhVienService.deleteSinhVien(msv);
        return "Delete sinh vien co msv = " +msv;
    }
}
