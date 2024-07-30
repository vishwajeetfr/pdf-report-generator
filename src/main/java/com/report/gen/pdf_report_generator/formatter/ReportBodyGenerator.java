package com.report.gen.pdf_report_generator.formatter;

import com.report.gen.pdf_report_generator.dto.ReportRequestDto;

import java.util.List;

public interface ReportBodyGenerator {
    String generateReportBody(List<ReportRequestDto> reportRequestDtos);
}
