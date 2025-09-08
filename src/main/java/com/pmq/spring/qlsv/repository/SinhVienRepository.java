package com.pmq.spring.qlsv.repository;

import com.pmq.spring.qlsv.model.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {

}
