package com.pmq.spring.qlsv.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "score")
@Data
public class Score {
    @EmbeddedId
    private ScoreId id;

    private double processPoint;
    private double componentPoint;

    @ManyToOne
    @MapsId("stuCode")
    @JoinColumn(name = "stuCode")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("subCode")
    @JoinColumn(name = "subCode")
    private Subject subject;
}
