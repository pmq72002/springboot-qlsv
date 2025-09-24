package com.pmq.spring.qlsv.controller;

import com.pmq.spring.qlsv.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("api/student")
public class ReportController {
    private final ReportService reportService;
    @Autowired
    public ReportController(ReportService reportService){
        this.reportService=reportService;
    }

    @GetMapping("/report/pdf")
    public String generatePdf() throws JRException, SQLException {
        return reportService.exportPdf();
    }
}
