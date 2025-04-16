package com.vn.edu.elearning.repository;

import com.vn.edu.elearning.domain.Thanhtoan;
import com.vn.edu.elearning.dto.ThongkethanhtoanDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ThanhtoanRepository extends JpaRepository<Thanhtoan, String> {

    @Transactional
    @Modifying
    @Query("update Thanhtoan t set t.trangthai = ?1 where t.mathanhtoan = ?2")
    void updateTrangthaiByMathanhtoan(String trangthai, String mathanhtoan);

    Thanhtoan findByTaikhoan_MataikhoanAndTailieu_Matailieu(String mataikhoan, String matailieu);

    Thanhtoan findByTaikhoan_MataikhoanAndTailieu_MatailieuAndTrangthai(String mataikhoan, String matailieu, String trangthai);

    List<Thanhtoan> findByTailieu_Matailieu(String matailieu);

    @Query("SELECT new com.vn.edu.elearning.dto.ThongkethanhtoanDto(" +
            "tt.mathanhtoan, " +
            "t.tentailieu, " +
            "tt.taikhoan.tendangnhap, " +
            "dt.taikhoan.tendangnhap, " +
            "t.giaban, " +
            "(t.giaban * t.tylethunhaptacgia / 100), " +
            "(t.giaban * t.tylephiquantri / 100), " +
            "tt.thoigianthanhtoan" +
            ") " +
            "FROM Thanhtoan tt " +
            "JOIN tt.tailieu t " +
            "JOIN t.dsdangtai dt " +
            "ORDER BY tt.mathanhtoan DESC ")
    List<ThongkethanhtoanDto> findThongkethanhtoanDto();
}