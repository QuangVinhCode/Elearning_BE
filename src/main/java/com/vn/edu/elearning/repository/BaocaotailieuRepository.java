package com.vn.edu.elearning.repository;

import com.vn.edu.elearning.domain.Baocaotailieu;
import com.vn.edu.elearning.domain.Taikhoan;
import com.vn.edu.elearning.domain.Tailieu;
import com.vn.edu.elearning.dto.TheodoibaocaoDto;
import com.vn.edu.elearning.dto.ThongtinbaocaotailieuDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BaocaotailieuRepository extends JpaRepository<Baocaotailieu, String> {

    List<Baocaotailieu> findByTaikhoan_Mataikhoan(String mataikhoan);

    @Query("SELECT new com.vn.edu.elearning.dto.ThongtinbaocaotailieuDto(" +
            "COUNT(b), " +
            "t.tentailieu, " +
            "tk.taikhoan.tendangnhap, " +
            "t.matailieu ) " +
            "FROM Baocaotailieu b " +
            "JOIN b.tailieu t " +
            "JOIN t.dsdangtai tk " +
            "GROUP BY t.tentailieu, tk.taikhoan.tendangnhap,t.matailieu " +
            "ORDER BY COUNT(b) DESC")
    List<ThongtinbaocaotailieuDto> findReportedDocumentInfo();

    @Query("SELECT new com.vn.edu.elearning.dto.TheodoibaocaoDto(" +
            "COUNT(b), tk.taikhoan.tendangnhap,tk.taikhoan.mataikhoan) " +
            "FROM Baocaotailieu b " +
            "JOIN b.tailieu t " +
            "JOIN t.dsdangtai tk " +
            "GROUP BY tk.taikhoan.tendangnhap,tk.taikhoan.mataikhoan " +
            "ORDER BY COUNT(b) DESC")
    List<TheodoibaocaoDto> findReportMonitor();

    @Query("SELECT b FROM Baocaotailieu b WHERE b.taikhoan = :taikhoan AND b.tailieu = :tailieu " +
            "ORDER BY b.thoigianbaocao DESC")
    Optional<Baocaotailieu> findLatestReportForDocument(@Param("taikhoan") Taikhoan taikhoan,
                                                        @Param("tailieu") Tailieu tailieu);

    List<Baocaotailieu> findByTailieu_Matailieu(String matailieu);

    List<Baocaotailieu> findByTailieu_Dsdangtai_Taikhoan(Taikhoan taikhoan);


}