package com.pmq.spring.qlsv.repository;

import com.pmq.spring.qlsv.model.Diem;
import com.pmq.spring.qlsv.model.DiemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiemRepository extends JpaRepository<Diem, DiemId> {
List<Diem> findBySinhVien_Msv(String msv);
}
