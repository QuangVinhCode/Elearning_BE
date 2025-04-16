package com.vn.edu.elearning.repository;

import com.vn.edu.elearning.domain.Binhluan;
import com.vn.edu.elearning.dto.BinhluanTheoTailieuDto;
import com.vn.edu.elearning.dto.ThongtinbinhluanDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BinhluanRepository extends JpaRepository<Binhluan, String> {
    @Query("SELECT new com.vn.edu.elearning.dto.BinhluanTheoTailieuDto(b.mabinhluan, b.taikhoan.mataikhoan, b.taikhoan.tendangnhap, b.noidung, b.trangthai, b.matbinhluandatraloi, b.thoigianbinhluan) " +
            "FROM Binhluan b " +
            "WHERE b.tailieu.matailieu = :matailieu " +
            "AND b.trangthai = 'Thành công'")
    List<BinhluanTheoTailieuDto> findBinhluansByMatailieu(@Param("matailieu") String matailieu);

    @Query("SELECT new com.vn.edu.elearning.dto.ThongtinbinhluanDto(" +
            "b.mabinhluan, b.taikhoan.mataikhoan," +
            " b.taikhoan.tendangnhap, b.noidung, b.trangthai," +
            " b.matbinhluandatraloi, b.thoigianbinhluan," +
            "b.tailieu.tentailieu) " +
            "FROM Binhluan b " +
            "ORDER BY b.mabinhluan DESC ")
    List<ThongtinbinhluanDto> findBinhluans();

    List<Binhluan> findByTaikhoan_Mataikhoan(String mataikhoan);

    @Transactional
    @Modifying
    @Query("UPDATE Binhluan b SET b.trangthai = :status WHERE b.mabinhluan = :mabinhluan")
    int updateCommentStatus(String mabinhluan, String status);

    @Transactional
    @Modifying
    @Query("UPDATE Binhluan b SET b.trangthai = :status WHERE b.matbinhluandatraloi = :mabinhluan AND b.trangthai = :parentStatus")
    int updateRepliesStatus(String mabinhluan, String status, String parentStatus);

    List<Binhluan> findByMatbinhluandatraloi(String matbinhluandatraloi);
}