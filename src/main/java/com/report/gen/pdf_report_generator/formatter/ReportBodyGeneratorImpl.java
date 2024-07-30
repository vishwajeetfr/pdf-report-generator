package com.report.gen.pdf_report_generator.formatter;

import com.report.gen.pdf_report_generator.dto.ReportRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportBodyGeneratorImpl implements ReportBodyGenerator {

    @Override
    public String generateReportBody(List<ReportRequestDto> reportRequestDtos) {
        StringBuilder reportBodyBuilder = new StringBuilder();

        for (ReportRequestDto requestDto : reportRequestDtos) {
            reportBodyBuilder.append(String.format("REPORT NUM: %-8.8s DETAIL: %s\n",
                    requestDto.getOutReportNum(),
                    requestDto.getDetail()));
        }

        return reportBodyBuilder.toString();
    }
}


