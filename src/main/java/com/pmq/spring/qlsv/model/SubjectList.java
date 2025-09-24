package com.pmq.spring.qlsv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectList implements Serializable {
    private String subCode;
    private String subName;
    private Integer subNum;
}
