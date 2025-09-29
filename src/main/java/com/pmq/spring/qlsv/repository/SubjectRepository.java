package com.pmq.spring.qlsv.repository;

import com.pmq.spring.qlsv.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, String> {
    @Query(
            value = "SELECT subCode, subName, subNum FROM subject",
            nativeQuery = true
    )
    List<Object[]> findSubjectList();
    boolean existsBySubName(String subName);
}
