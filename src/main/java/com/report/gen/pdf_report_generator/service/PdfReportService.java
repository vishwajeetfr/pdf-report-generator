package com.report.gen.pdf_report_generator.service;


import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.report.gen.pdf_report_generator.dto.ReportRequestDto;
import com.report.gen.pdf_report_generator.formatter.ReportBodyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfReportService {

    private static final Color BLUE = new DeviceRgb(0, 0, 255);

    @Autowired
    private ReportBodyGenerator reportBodyGenerator;

    public ByteArrayOutputStream createPdfReport(String logoPath, String reportTitle, List<ReportRequestDto> reportRequestDtos, String footerText) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            // Add logo
            Image logo = new Image(ImageDataFactory.create(logoPath))
                    .setWidth(100) // Set width (adjust as needed)
                    .setHeight(100); // Set height (adjust as needed)

            // Calculate positions
            float pageWidth = pdfDoc.getDefaultPageSize().getWidth();
            float pageHeight = pdfDoc.getDefaultPageSize().getHeight();
            // Get height in points
            float margin = 20; // Margin from edges
            final var logoWidth = logo.getImageWidth(); // Get width in points
            final var  logoHeight = logo.getImageHeight();
            // Position logo at top-right corner
            logo.setFixedPosition(
                    pageWidth - logoWidth - margin, // x-coordinate (right margin)
                    pageHeight - logoHeight - margin // y-coordinate (top margin)
            );
            //logo.setTextAlignment(TextAlignment.JUSTIFIED_ALL);
            document.add(logo);


            // Add header text
            Paragraph header = new Paragraph(reportTitle)
                    .setFontColor(BLUE)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(header);

            // Add blue line
            ILineDrawer blueLine = new SolidLine(2);
            blueLine.setColor(BLUE);
            LineSeparator ls = new LineSeparator(blueLine);
            ls.setWidth(UnitValue.createPercentValue(100));
            document.add(ls);

            // Generate and add report body
            String reportBody = reportBodyGenerator.generateReportBody(reportRequestDtos);
            Paragraph body = new Paragraph(reportBody);
            document.add(body);

            // Add another blue line
             document.add(ls);

            // Add footer
            Paragraph footer = new Paragraph(footerText)
                    .setFontColor(BLUE)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(footer);

        } catch (Exception e) {
            throw new Exception("Error creating PDF report", e);
        }

        return baos;
    }
}