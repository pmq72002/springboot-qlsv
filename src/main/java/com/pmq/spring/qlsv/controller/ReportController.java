package com.pmq.spring.qlsv.controller;

import com.pmq.spring.qlsv.service.ReportService;
import com.pmq.spring.qlsv.service.StudentProducer;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/student")
public class ReportController {
    private final ReportService reportService;
    private final StudentProducer studentProducer;
    @Autowired
    public ReportController(ReportService reportService, StudentProducer studentProducer){
        this.reportService=reportService;
        this.studentProducer = studentProducer;
    }
    //pdf in code
    @GetMapping("/report/demoPdf")
    public String generatePdf() throws JRException{

        return reportService.exportPdf();
    }
    //download pdf
    @GetMapping("/report/pdf/studentList")
    public ResponseEntity<byte[]> downloadStudentListPdf() throws JRException{
        byte[] pdfBytes = reportService.downloadStudentListPdf();
        String message = "PDF downloaded: " ;
        studentProducer.sendPdfExport(message);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=studentList.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
    @GetMapping("/report/pdf/scoreList/{stuCode}")
    public ResponseEntity<byte[]> downloadScoreListPdf(@PathVariable String stuCode) throws JRException{
        byte[] pdfBytes = reportService.downLoadScoreListPdf(stuCode);
        String message = "PDF downloaded: " ;
        studentProducer.sendPdfExport(message);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=scoreSummary_" + stuCode + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
