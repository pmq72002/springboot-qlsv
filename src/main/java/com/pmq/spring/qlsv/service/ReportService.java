package com.pmq.spring.qlsv.service;
import com.pmq.spring.qlsv.dto.response.ScoreSummaryResponse;
import com.pmq.spring.qlsv.model.StudentList;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
    private  DataSource dataSource;
    private StudentService studentService;
    private ScoreService scoreService;
    private static final String TITLE_PARAM = "title";

    @Autowired
    public ReportService(DataSource dataSource , StudentService studentService, ScoreService scoreService){
        this.dataSource = dataSource;
        this.studentService = studentService;
        this.scoreService = scoreService;
    }

    //pdf in code
    public String exportPdf() throws JRException{

        List<StudentList> students = studentService.getAllStudentList();
        File file = new File("src/main/resources/reports/studentList.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, "Danh sách sinh viên");

        JRBeanCollectionDataSource dataSourceB = new JRBeanCollectionDataSource(students);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSourceB);

        String path = "src/main/resources/reports/studentList.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint, path);
        return path;
    }
    //download pdf
    public byte[] downloadStudentListPdf() throws JRException {
        List<StudentList> students = studentService.getAllStudentList();
        File file = new File("src/main/resources/reports/studentList.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, "Danh sách sinh viên");
        JRBeanCollectionDataSource dataSourceB = new JRBeanCollectionDataSource(students);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSourceB);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    public byte[] downLoadScoreListPdf(String stuCode) throws JRException {
        List<ScoreSummaryResponse> scores = scoreService.getScoreListByStudent(stuCode);
        String stuName = scores.isEmpty() ? "Không xác định" :scores.get(0).getStuName();
        File file = new File("src/main/resources/reports/scoreList.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, "Bảng điểm sinh viên: "+ stuName);
        JRBeanCollectionDataSource dataSourceB = new JRBeanCollectionDataSource(scores);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSourceB);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
    }
