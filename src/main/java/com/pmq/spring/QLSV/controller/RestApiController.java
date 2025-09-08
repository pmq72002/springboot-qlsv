package com.pmq.spring.QLSV.controller;


import com.pmq.spring.QLSV.model.*;
import com.pmq.spring.QLSV.service.DiemService;
import com.pmq.spring.QLSV.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/sinhvien")
public class RestApiController {
    @Autowired
    private SinhVienService sinhVienService;
    @Autowired
    private DiemService diemService;
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
            map.put("msv", item.get("msv"));
            map.put("tensv", item.get("tensv"));
            map.put("tenMH", item.get("tenMH"));
            map.put("soTiet", item.get("soTiet"));
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
            map.put("msv",diem.getSinhVien().getMsv());
            map.put("tensv",diem.getSinhVien().getTensv());
            map.put("soTiet",diem.getMonHoc().getSoTiet());
            map.put("tenMH", diem.getMonHoc().getTenMH());
            map.put("diemQT", diem.getDiemQT());
            map.put("diemTP", diem.getDiemTP());

            double diemTK = diemService.tinhDiemTongKet(diem);
            map.put("diemTongKet", diemTK);

            map.put("quaMon", diemService.daQuaMon(diem) ? "Đỗ" : "Trượt");

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
