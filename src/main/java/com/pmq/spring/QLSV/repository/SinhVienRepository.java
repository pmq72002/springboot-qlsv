package com.pmq.spring.QLSV.repository;

import com.pmq.spring.QLSV.model.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {

}
