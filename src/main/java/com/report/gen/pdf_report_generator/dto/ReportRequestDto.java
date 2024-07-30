package com.report.gen.pdf_report_generator.dto;

import lombok.*;

@Builder(toBuilder = true)
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDto {
    private String outReportNum;
    private String detail;
}
