package com.vn.edu.elearning.controller;

import com.vn.edu.elearning.domain.Giaodich;
import com.vn.edu.elearning.domain.Taikhoan;
import com.vn.edu.elearning.domain.Tailieu;
import com.vn.edu.elearning.domain.Thanhtoan;
import com.vn.edu.elearning.dto.PaymentDTO;
import com.vn.edu.elearning.dto.ThanhtoanDto;
import com.vn.edu.elearning.exeception.ResponseObject;
import com.vn.edu.elearning.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/thanhtoan")
@RequiredArgsConstructor
public class ThanhtoanController {
    private final PaymentService paymentService;

    private Long amount;
    private String account;

    @Autowired
    TailieuService tailieuService;

    @Autowired
    private TaikhoanService taikhoanService;

    @Autowired
    GiaodichService giaodichService;

    @Autowired
    ThanhtoanService thanhtoanService;

    @Autowired
    DangtaiService dangtaiService;

    @GetMapping("/check/{tk}/{tl}")
    public ResponseEntity<?> checkDocumentViewingPermissions(@PathVariable("tk") String tk, @PathVariable("tl") String tl) {
        String check = "Chưa thanh toán";
        boolean checkSalesAccount = dangtaiService.checkDangtai(tk,tl);
        boolean checkBuyAccount = thanhtoanService.checkThanhtoan(tk,tl);
        Tailieu tailieu = tailieuService.findById(tl);
        if (tailieu != null && tailieu.getGiaban()== 0)
        {
                check = "Miễn phí";
        }
        if (checkSalesAccount)
        {
            check = "Chủ sở hữu";
        }
        if (checkBuyAccount)
        {
            check = "Đã thanh toán";
        }
        return new ResponseEntity<>(check, HttpStatus.OK);
    }
    @PostMapping("/pay/{tk}/{tl}")
    public ResponseEntity<?> payDocument(@PathVariable("tk") String tk,@PathVariable("tl") String tl) {
        ThanhtoanDto dto = new ThanhtoanDto();
        dto.setMataikhoan(tk);
        dto.setMatailieu(tl);
        Thanhtoan thanhtoan = thanhtoanService.save(dto);
        return new ResponseEntity<>(thanhtoan, HttpStatus.OK);
    }
    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        amount = Long.parseLong(request.getParameter("amount"));
        account = request.getParameter("account");

        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }
    @GetMapping("/vn-pay-callback")
    public ResponseEntity<Void> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        System.out.println("amount" + amount);
        System.out.println("account" + account);
        Giaodich giaodich = new Giaodich();
        giaodich.setLydo("Nạp tiền vào tài khoản");
        System.out.println(giaodich.getLydo());
        giaodich.setSotien(amount);
        Taikhoan taikhoan = taikhoanService.findById(account);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        giaodich.setThoigiangiaodich(LocalDateTime.now().format(formatter));
        giaodich.setTaikhoan(taikhoan);
        String url = "";
        if ("00".equals(status)) {
            giaodich.setTrangthai("Thành công");
            taikhoanService.incrementSodu(account,amount);
            url= "http://localhost:3000/users/profile/accountsettings?message=success";
        } else {
            giaodich.setTrangthai("Thất bại");
            url= "http://localhost:3000/users/profile/accountsettings?message=error";

        }
        giaodichService.save(giaodich);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", url)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getPays(){
        return new ResponseEntity<>(thanhtoanService.findAllPay(),HttpStatus.OK);
    }

}
