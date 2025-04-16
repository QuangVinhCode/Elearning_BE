package com.vn.edu.elearning.repository;

import com.vn.edu.elearning.domain.Dangtai;
import com.vn.edu.elearning.domain.Madangtai;
import com.vn.edu.elearning.domain.Taikhoan;
import com.vn.edu.elearning.domain.Tailieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DangtaiRepository extends JpaRepository<Dangtai, Madangtai> {

    @Transactional
    @Modifying
    @Query("update Dangtai d set d.thoigianduocduyet = ?1 where d.taikhoan = ?2 and d.tailieu = ?3")
    void updateThoigianduocduyetByTaikhoanAndTailieu(String thoigianduocduyet, Taikhoan taikhoan, Tailieu tailieu);

    Dangtai findByTaikhoan_MataikhoanAndTailieu_Matailieu(String mataikhoan, String matailieu);

    @Modifying
    @Query("DELETE FROM Dangtai d WHERE d.tailieu.matailieu = :matailieu")
    void deleteByMatailieu(String matailieu);



}