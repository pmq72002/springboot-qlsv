package com.pmq.spring.QLSV.repository;

import com.pmq.spring.QLSV.model.Diem;
import com.pmq.spring.QLSV.model.DiemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiemRepository extends JpaRepository<Diem, DiemId> {
List<Diem> findBySinhVien_Msv(String msv);
}
