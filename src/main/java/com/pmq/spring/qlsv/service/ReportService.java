package com.pmq.spring.qlsv.service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
    private  DataSource dataSource;

    @Autowired
    public ReportService(DataSource dataSource){
        this.dataSource = dataSource;
    }
    public String exportPdf() throws JRException, SQLException {

        File file = new File("src/main/resources/reports/demo1.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> paramas = new HashMap<>();
        paramas.put("title", "Danh sách sinh viên");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,paramas,dataSource.getConnection());

        String path = "src/main/resources/reports/output.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint, path);
       return path;
    }
}
