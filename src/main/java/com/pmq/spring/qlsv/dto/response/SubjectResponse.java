package com.pmq.spring.qlsv.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class SubjectResponse {
    private String subCode;
    private String subName;
    private Integer subNum;
    private Double ratioProcess;
    private Double ratioComponent;

}
