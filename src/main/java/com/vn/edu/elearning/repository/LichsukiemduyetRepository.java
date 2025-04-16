package com.vn.edu.elearning.repository;

import com.vn.edu.elearning.domain.Lichsukiemduyet;
import com.vn.edu.elearning.domain.Tailieu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LichsukiemduyetRepository extends JpaRepository<Lichsukiemduyet, String> {
    List<Lichsukiemduyet> findByTailieu_Matailieu(String matailieu);

    long deleteByTailieu(Tailieu tailieu);

}