package com.vn.edu.elearning.repository;

import com.vn.edu.elearning.domain.Tailieu;
import com.vn.edu.elearning.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TailieuRepository extends JpaRepository<Tailieu, String> {

    List<Tailieu> findByDanhmuc_Madanhmuc(String madanhmuc);

    @Query(value = "SELECT tl.* FROM tailieu tl JOIN thanhtoan tt ON tl.matailieu = tt.matailieu GROUP BY tl.matailieu ORDER BY COUNT(tl.matailieu) DESC LIMIT 5", nativeQuery = true)
    List<Tailieu> findTop5TailieuByThanhtoanNhieuNhat();


    List<Tailieu> findByTentailieuContains(String tentailieu);

    @Transactional
    @Modifying
    @Query("update Tailieu t set t.trangthai = ?1 where t.matailieu = ?2")
    void updateTrangthaiByMatailieu(String trangthai, String matailieu);

    List<Tailieu> findByDanhmuc_MadanhmucAndTrangthai(String madanhmuc, String trangthai);

    @Query("SELECT new com.vn.edu.elearning.dto.KiemduyettailieuDto(t.matailieu, t.tentailieu, tk.mataikhoan, tk.tendangnhap,t.trangthai,dt.thoigiantailen)" +
            "FROM Tailieu t JOIN t.dsdangtai dt JOIN dt.taikhoan tk ORDER BY t.matailieu DESC")
    List<KiemduyettailieuDto> findDSTailieukiemduyet();

    @Query("SELECT new com.vn.edu.elearning.dto.TailieudangtaiDto(" +
            "t.matailieu, t.tentailieu, t.tacgia, t.mota, t.giaban, t.diachiluutru, " +
            "t.tylephiquantri, t.tylethunhaptacgia, t.trangthai, dm, tk.mataikhoan, " +
            "dt.thoigiantailen, dt.thoigianduocduyet) " +
            "FROM Tailieu t " +
            "JOIN t.danhmuc dm " +
            "JOIN t.dsdangtai dt " +
            "JOIN dt.taikhoan tk " +
            "WHERE tk.mataikhoan = :mataikhoan " +
            "ORDER BY t.matailieu DESC ")
    List<TailieudangtaiDto> findTailieudangtaiDtosByMataikhoan(@Param("mataikhoan") String mataikhoan);

    @Query("SELECT new com.vn.edu.elearning.dto.TailieudangtaiAdminDto(" +
            "t.matailieu, t.tentailieu, " +
            "t.trangthai, tk.tendangnhap, " +
            "dt.thoigiantailen, dt.thoigianduocduyet) " +
            "FROM Tailieu t " +
            "JOIN t.danhmuc dm " +
            "JOIN t.dsdangtai dt " +
            "JOIN dt.taikhoan tk " +
            "ORDER BY t.matailieu DESC")
    List<TailieudangtaiAdminDto> findTailieudangtaiAdminDto();

    @Query("SELECT new com.vn.edu.elearning.dto.TailieuthanhtoanDto(" +
            "t.matailieu, t.tentailieu, t.giaban, t.danhmuc.tendanhmuc,t.trangthai, tt.thoigianthanhtoan, tt.trangthai) " +
            "FROM Tailieu t " +
            "JOIN Thanhtoan tt ON t.matailieu = tt.tailieu.matailieu " +
            "WHERE tt.taikhoan.mataikhoan = :mataikhoan")
    List<TailieuthanhtoanDto> findTailieuthanhtoanByMataikhoan(@Param("mataikhoan") String mataikhoan);

    @Query("SELECT new com.vn.edu.elearning.dto.TailieuthanhtoanAdminDto(" +
            "t.matailieu, t.tentailieu,t.giaban,tt.trangthai,tt.taikhoan.tendangnhap, tt.thoigianthanhtoan) " +
            "FROM Tailieu t " +
            "JOIN Thanhtoan tt ON t.matailieu = tt.tailieu.matailieu " +
            "ORDER BY t.matailieu DESC")
    List<TailieuthanhtoanAdminDto> findTailieuthanhtoanAdmin();

    @Query("SELECT new com.vn.edu.elearning.dto.ThunhaptailieuDto(" +
            "t.matailieu, " +
            "t.tentailieu, " +
            "t.giaban, " +
            "COUNT(tt.mathanhtoan), " +
            "SUM((t.giaban * t.tylethunhaptacgia) / 100), " +
            "SUM((t.giaban * t.tylephiquantri) / 100)) " +
            "FROM Tailieu t " +
            "JOIN t.dsthanhtoan tt " +
            "JOIN t.dsdangtai dt " +
            "WHERE dt.taikhoan.mataikhoan = :mataikhoan AND tt.trangthai='Thành công'" +
            "GROUP BY t.matailieu, t.tentailieu")
    List<ThunhaptailieuDto> findThunhaptailieuByMataikhoan(@Param("mataikhoan") String mataikhoan);

    @Query("SELECT new com.vn.edu.elearning.dto.LichsuthuchiAdminDto(" +
            "SUM(CASE WHEN dt.taikhoan.quyenhan = 'Quản trị viên' THEN (t.giaban * t.tylethunhaptacgia) / 100 ELSE 0 END), " +
            "SUM(CASE WHEN dt.taikhoan.quyenhan <> 'Quản trị viên' THEN (t.giaban * t.tylephiquantri) / 100 ELSE 0 END), " +
            "SUBSTRING(tt.thoigianthanhtoan, 7, 11) " +
            ") " +
            "FROM Thanhtoan tt " +
            "JOIN tt.tailieu t " +
            "JOIN t.dsdangtai dt " +
            "GROUP BY SUBSTRING(tt.thoigianthanhtoan, 7, 11)")
    List<LichsuthuchiAdminDto> findLichsuthuchiAdmin();

    @Query("SELECT new com.vn.edu.elearning.dto.ThunhaptailieuDto(" +
            "t.matailieu, " +
            "t.tentailieu, " +
            "t.giaban, " +
            "COUNT (tt.mathanhtoan)," +
            "SUM(CASE WHEN dt.taikhoan.quyenhan = 'Quản trị viên' THEN (t.giaban * t.tylethunhaptacgia) / 100 ELSE 0 END), " +
            "SUM(CASE WHEN dt.taikhoan.quyenhan <> 'Quản trị viên' THEN (t.giaban * t.tylephiquantri) / 100 ELSE 0 END)) " +
            "FROM Thanhtoan tt " +
            "JOIN tt.tailieu t " +
            "JOIN t.dsdangtai dt " +
            "GROUP BY t.matailieu, t.tentailieu, t.giaban")
    List<ThunhaptailieuDto> findThunhaptailieu();
    @Query("select t from Tailieu t JOIN t.dsdangtai dt WHERE dt.taikhoan.quyenhan = 'Quản trị viên' ORDER BY dt.thoigiantailen DESC")
    List<Tailieu> findAllDocumentAdmin();

    List<Tailieu> findByTentailieuContainingIgnoreCaseAndTrangthai(String tentailieu, String trangthai);

    @Query("select new com.vn.edu.elearning.dto.ThongtintailieuDto("+
            "t.matailieu,t.tentailieu,t.tacgia,t.mota,t.giaban,t.diachiluutru,"+
            "t.tylephiquantri,t.tylethunhaptacgia,"+
            "t.trangthai,dt.taikhoan.mataikhoan,dt.taikhoan.tendangnhap) "+
            " from Tailieu t join t.dsdangtai dt where t.matailieu = :matailieu")
    ThongtintailieuDto findTailieuTKDangtaiByMatailieu(String matailieu);


}