package com.pmq.spring.qlsv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectList {
    private String subCode;
    private String subName;
    private Integer subNum;
}
