package com.vn.edu.elearning.service;

import com.vn.edu.elearning.domain.*;
import com.vn.edu.elearning.repository.DangtaiRepository;
import com.vn.edu.elearning.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DangtaiService {
    @Autowired
    private DangtaiRepository dangtaiRepository;

    public Dangtai save(Taikhoan taikhoan, Tailieu tailieu) {
        Dangtai entity = new Dangtai();
        Madangtai madangtai = new Madangtai();
        madangtai.setMataikhoan(taikhoan.getMataikhoan());
        madangtai.setMatailieu(tailieu.getMatailieu());
        entity.setMadangtai(madangtai);
        entity.setTailieu(tailieu);
        entity.setTaikhoan(taikhoan);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        entity.setThoigiantailen(LocalDateTime.now().format(formatter));
        if(taikhoan.getQuyenhan().equals(Status.ADMIN.getValue()))
        {
            entity.setThoigianduocduyet(LocalDateTime.now().format(formatter));
        }
        return dangtaiRepository.save(entity);
    }
    public List<Dangtai> findAll() {
        return dangtaiRepository.findAll();
    }

    public boolean checkDangtai(String taikhoan, String tailieu) {
        Dangtai dangtai = dangtaiRepository.findByTaikhoan_MataikhoanAndTailieu_Matailieu(taikhoan,tailieu);
        if (dangtai != null)
        {
            return  true;
        }
        return  false;
    }

    public void  deleteById(String matl){
        dangtaiRepository.deleteByMatailieu(matl);
    }
}
