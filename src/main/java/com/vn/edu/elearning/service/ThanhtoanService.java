package com.vn.edu.elearning.service;

import com.vn.edu.elearning.domain.*;
import com.vn.edu.elearning.dto.ThanhtoanDto;
import com.vn.edu.elearning.dto.ThongkethanhtoanDto;
import com.vn.edu.elearning.exeception.ClassException;
import com.vn.edu.elearning.repository.GiaodichRepository;
import com.vn.edu.elearning.repository.ThanhtoanRepository;
import com.vn.edu.elearning.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ThanhtoanService {
    @Autowired
    private ThanhtoanRepository thanhtoanRepository;

    @Autowired
    TaikhoanService taikhoanService;

    @Autowired
    TailieuService tailieuService;

    @Autowired
    GiaodichService giaodichService;
    @Autowired
    private GiaodichRepository giaodichRepository;

    public Thanhtoan save(ThanhtoanDto dto) {
        Thanhtoan entity = new Thanhtoan();
        Tailieu tailieu = tailieuService.findById(dto.getMatailieu());
        Taikhoan taikhoan = taikhoanService.findById(dto.getMataikhoan());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        if (tailieu==null)
        {
            throw  new ClassException("Tài liệu không tồn tại");
        }
        if (taikhoan==null)
        {
            throw  new ClassException("Yêu cầu đăng nhập");
        }
        if (tailieu.getTrangthai().equals(Status.CAM.getValue()))
        {
            throw  new ClassException("Tài liệu đã bị quản trị viên chặn");
        }
        if (tailieu.getTrangthai().equals(Status.CCS.getValue()))
        {
            throw  new ClassException("Tài liệu đang trong quá trình kiểm tra lại");
        }
        if (taikhoan.getSodu() < tailieu.getGiaban())
        {
            dto.setTrangthai(Status.TB.getValue());
            Giaodich giaodichThanhtoan = new Giaodich();
            giaodichThanhtoan.setLydo("Thanh toán tài liệu " + tailieu.getTentailieu());
            giaodichThanhtoan.setSotien(tailieu.getGiaban());
            giaodichThanhtoan.setThoigiangiaodich(LocalDateTime.now().format(formatter));
            giaodichThanhtoan.setTaikhoan(taikhoan);
            giaodichThanhtoan.setTrangthai(Status.TB.getValue());
            giaodichRepository.save(giaodichThanhtoan);
        }
        else {
            Taikhoan taikhoandangtai  = taikhoanService.findByPostedDocuments(dto.getMatailieu());
            Long giaban = tailieu.getGiaban();
            Giaodich giaodichTacgia = new Giaodich();
            Giaodich giaodichAdmin = new Giaodich();
            Giaodich giaodichThanhtoan = new Giaodich();
            dto.setTrangthai(Status.TC.getValue());
            if (taikhoandangtai.getQuyenhan().equals(Status.ADMIN.getValue()))
            {
                Long soduTK = taikhoan.getSodu() - tailieu.getGiaban();
                taikhoanService.incrementSodu(taikhoandangtai.getMataikhoan(),giaban);
                taikhoanService.updateSodu(taikhoan.getMataikhoan(),soduTK);

                giaodichTacgia.setLydo("Thu nhập từ bán tài liệu " + tailieu.getTentailieu());
                giaodichTacgia.setSotien(giaban);
                giaodichTacgia.setThoigiangiaodich(LocalDateTime.now().format(formatter));
                giaodichTacgia.setTaikhoan(taikhoandangtai);
                giaodichTacgia.setTrangthai(Status.TC.getValue());
                giaodichRepository.save(giaodichTacgia);

                giaodichThanhtoan.setLydo("Thanh toán tài liệu " + tailieu.getTentailieu());
                giaodichThanhtoan.setSotien(giaban);
                giaodichThanhtoan.setThoigiangiaodich(LocalDateTime.now().format(formatter));
                giaodichThanhtoan.setTaikhoan(taikhoan);
                giaodichThanhtoan.setTrangthai(Status.TC.getValue());
                giaodichRepository.save(giaodichThanhtoan);

            }else {
                Long phiquangtri = (tailieu.getGiaban() / 100) * tailieu.getTylephiquantri();
                Long thunhaptacgia = (tailieu.getGiaban() / 100) * tailieu.getTylethunhaptacgia();
                Long soduTK = taikhoan.getSodu() - tailieu.getGiaban();
                System.out.println("phiquangtri " + phiquangtri);
                System.out.println("thunhaptacgia " + thunhaptacgia);
                System.out.println("soduTK " + soduTK);
                taikhoanService.incrementSoduForAdmin(phiquangtri);
                taikhoanService.incrementSodu(taikhoandangtai.getMataikhoan(),thunhaptacgia);
                taikhoanService.updateSodu(taikhoan.getMataikhoan(),soduTK);

                giaodichTacgia.setLydo("Thu nhập từ bán tài liệu " + tailieu.getTentailieu());
                giaodichTacgia.setSotien(thunhaptacgia);
                giaodichTacgia.setThoigiangiaodich(LocalDateTime.now().format(formatter));
                giaodichTacgia.setTaikhoan(taikhoandangtai);
                giaodichTacgia.setTrangthai(Status.TC.getValue());
                giaodichRepository.save(giaodichTacgia);

                Taikhoan taikhoanadmin = taikhoanService.findAccountRole(Status.ADMIN.getValue());
                giaodichAdmin.setLydo("Thu nhập từ thu phí quản trị tài liệu " + tailieu.getTentailieu());
                giaodichAdmin.setSotien(phiquangtri);
                giaodichAdmin.setThoigiangiaodich(LocalDateTime.now().format(formatter));
                giaodichAdmin.setTaikhoan(taikhoanadmin);
                giaodichAdmin.setTrangthai(Status.TC.getValue());
                giaodichRepository.save(giaodichAdmin);

                giaodichThanhtoan.setLydo("Thanh toán tài liệu " + tailieu.getTentailieu());
                giaodichThanhtoan.setSotien(giaban);
                giaodichThanhtoan.setThoigiangiaodich(LocalDateTime.now().format(formatter));
                giaodichThanhtoan.setTaikhoan(taikhoan);
                giaodichThanhtoan.setTrangthai(Status.TC.getValue());
                giaodichRepository.save(giaodichThanhtoan);
            }

        }
        entity.setTailieu(tailieu);
        entity.setTaikhoan(taikhoan);
        entity.setTrangthai(dto.getTrangthai());
        entity.setThoigianthanhtoan(LocalDateTime.now().format(formatter));
        return thanhtoanRepository.save(entity);
    }
    public List<Thanhtoan> findAll() {
        return thanhtoanRepository.findAll();
    }

    public List<ThongkethanhtoanDto> findAllPay() {
        return thanhtoanRepository.findThongkethanhtoanDto();
    }

    public boolean checkThanhtoan(String taikhoan, String tailieu) {
        Thanhtoan thanhtoan = thanhtoanRepository.findByTaikhoan_MataikhoanAndTailieu_MatailieuAndTrangthai(taikhoan,tailieu,"Thành công");
        if (thanhtoan != null)
        {
            return  true;
        }
        return  false;
    }


}
