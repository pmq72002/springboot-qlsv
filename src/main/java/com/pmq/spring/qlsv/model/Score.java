package com.pmq.spring.qlsv.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("subCode")
    @JoinColumn(name = "subCode")
    private Subject subject;
}
