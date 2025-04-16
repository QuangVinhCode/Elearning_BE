package com.vn.edu.elearning.repository;

import com.vn.edu.elearning.domain.Taikhoan;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaikhoanRepository extends JpaRepository<Taikhoan, String> {
    @Modifying
    @Transactional
    @Query("UPDATE Taikhoan t SET t.sodu = :sodu WHERE t.mataikhoan = :id")
    void updateSodu(@Param("id") String id, @Param("sodu") Long sodu);

    @Modifying
    @Transactional
    @Query("UPDATE Taikhoan t SET t.sodu = t.sodu + :amount WHERE t.mataikhoan = :id")
    void incrementSodu(@Param("id") String id, @Param("amount") Long amount);

    @Modifying
    @Transactional
    @Query("UPDATE Taikhoan t SET t.sodu = t.sodu + :amount WHERE t.quyenhan = 'Quản trị viên'")
    void incrementSoduForAdmin(@Param("amount") Long amount);


    @Modifying
    @Query("UPDATE Taikhoan t SET t.trangthaidangtai = :trangthai WHERE t.mataikhoan = :id")
    @Transactional
    void updateTrangThaiDangTai(@Param("id") String id, @Param("trangthai") String trangthai);

    @Modifying
    @Query("UPDATE Taikhoan t SET t.trangthaibinhluan = :trangthai WHERE t.mataikhoan = :id")
    @Transactional
    void updateTrangThaiBinhlLuan(@Param("id") String id, @Param("trangthai") String trangthai);

    @Query("SELECT t FROM Taikhoan t WHERE t.quyenhan <> 'Quản trị viên'")
    List<Taikhoan> findAllWithoutAdmin();
    @Query("SELECT t FROM Taikhoan t WHERE t.trangthaidangtai = 'Bình thường' AND t.quyenhan <> 'Quản trị viên'")
    List<Taikhoan> findBinhThuongWithoutAdmin();

    @Query("SELECT t FROM Taikhoan t WHERE t.trangthaidangtai <> 'Bình thường' AND t.quyenhan <> 'Quản trị viên'")
    List<Taikhoan> findNotBinhThuongWithoutAdmin();

    @Modifying
    @Transactional
    @Query("UPDATE Taikhoan t SET t.trangthaidangtai = 'Bình thường' WHERE t.trangthaidangtai = ?1")
    void updateTrangthaiDangtaiIfDateMatches(String currentDate);

    @Modifying
    @Transactional
    @Query("UPDATE Taikhoan t SET t.trangthaibinhluan = 'Bình thường' WHERE t.trangthaidangtai = ?1")
    void updateTrangthaiBinhluanIfDateMatches(String currentDate);


    Optional<Taikhoan> findByTendangnhapAndGmail(String tendangnhap, String gmail);

    Optional<Taikhoan> findByTendangnhap(String tendangnhap);

    Optional<Taikhoan> findByGmail(String gmail);

    Taikhoan findByDsdangtai_Tailieu_Matailieu(String matailieu);

    Optional<Taikhoan> findByQuyenhan(String quyenhan);



}