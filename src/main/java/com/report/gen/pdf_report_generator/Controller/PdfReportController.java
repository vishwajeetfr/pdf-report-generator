package com.report.gen.pdf_report_generator.Controller;

import com.report.gen.pdf_report_generator.dto.ReportRequestDto;
import com.report.gen.pdf_report_generator.service.PdfReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
public class PdfReportController {

    @Autowired
    private PdfReportService pdfReportService;

    @PostMapping("/generateReport")
    public ResponseEntity<byte[]> generateReport(
            @RequestParam(defaultValue = "/Users/vishwajeetpatil/Downloads/pdf-report-generator/src/main/resources/logo.png") String logoPath,
            @RequestParam(defaultValue = "Default Report Title") String reportTitle,
            @RequestBody List<ReportRequestDto> reportRequestDtos,
            @RequestParam(defaultValue = "Default Footer Text") String footerText) {

        ByteArrayOutputStream baos;
        try {
            baos = pdfReportService.createPdfReport(logoPath, reportTitle, reportRequestDtos, footerText);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(baos.toByteArray());
    }
}
