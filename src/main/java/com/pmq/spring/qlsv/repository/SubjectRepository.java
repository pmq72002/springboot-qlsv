package com.pmq.spring.qlsv.repository;

import com.pmq.spring.qlsv.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, String> {
    boolean existsBySubName(String subName);
}
