package com.pmq.spring.qlsv.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreSummaryResponse {
    private String stuCode;
    private String stuName;
    private String subCode;
    private String subName;
    private Integer subNum;
    private Double processPoint;
    private Double componentPoint;
    private Double summaryScore;
    private String passStatus;
  }
