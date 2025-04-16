package com.vn.edu.elearning.config;

import ch.qos.logback.core.status.StatusUtil;
import com.vn.edu.elearning.domain.Taikhoan;
import com.vn.edu.elearning.repository.TaikhoanRepository;
import com.vn.edu.elearning.util.Status;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DataLoaderRunner {
    @Autowired
    private final TaikhoanRepository taikhoanRepository;
    @Autowired
    public DataLoaderRunner(TaikhoanRepository taikhoanRepository) {
        this.taikhoanRepository = taikhoanRepository;
    }
    @PostConstruct
    public void init() {
        if (taikhoanRepository.count() == 0) {
            // Create admin account if no accounts exist
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Taikhoan adminAccount = new Taikhoan();
            adminAccount.setTendangnhap("admin");
            adminAccount.setMatkhau(passwordEncoder.encode("1234"));
            adminAccount.setSodienthoai("0123456789");
            adminAccount.setGmail("admin@gmail.com");
            adminAccount.setQuyenhan(Status.ADMIN.getValue());

            taikhoanRepository.save(adminAccount);
        }
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String currentDateString = currentDate.format(formatter);
        taikhoanRepository.updateTrangthaiDangtaiIfDateMatches(currentDateString);
        taikhoanRepository.updateTrangthaiBinhluanIfDateMatches(currentDateString);
    }
}
