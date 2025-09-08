package com.pmq.spring.qlsv.repository;

import com.pmq.spring.qlsv.model.Score;
import com.pmq.spring.qlsv.model.ScoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, ScoreId> {
List<Score> findByStudent_stuCode(String stuCode);
}
