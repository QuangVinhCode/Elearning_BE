package com.vn.edu.elearning.service;

import com.vn.edu.elearning.domain.*;
import com.vn.edu.elearning.dto.LichsukiemduyetDto;
import com.vn.edu.elearning.exeception.ClassException;
import com.vn.edu.elearning.repository.DangtaiRepository;
import com.vn.edu.elearning.repository.LichsukiemduyetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class KiemduyetService {
    @Autowired
    private LichsukiemduyetRepository lichsukiemduyetRepository;

    @Autowired
    private DangtaiRepository dangtaiRepository;
    public Lichsukiemduyet save(LichsukiemduyetDto dto) {
        Lichsukiemduyet entity = new Lichsukiemduyet();
        BeanUtils.copyProperties(dto,entity);
        Tailieu tailieu = new Tailieu();
        tailieu.setMatailieu(dto.getMatailieu());
        entity.setTailieu(tailieu);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        entity.setThoigiankiemduyet(LocalDateTime.now().format(formatter));
        return lichsukiemduyetRepository.save(entity);
    }


    public List<Lichsukiemduyet> findAll() {
        return lichsukiemduyetRepository.findAll();
    }

    public List<Lichsukiemduyet> findAllByDocument(String id) {
        return lichsukiemduyetRepository.findByTailieu_Matailieu(id);
    }

    public Lichsukiemduyet findById(String id) {
        Optional<Lichsukiemduyet> found = lichsukiemduyetRepository.findById(id);

        if (!found.isPresent())
        {
            throw new ClassException("Danh mục có id "+ id + "không tồn tại");
        }
        return found.get();
    }

    public void  updateCensorshipTime(String thoigian,Taikhoan taikhoan,Tailieu tailieu){
        dangtaiRepository.updateThoigianduocduyetByTaikhoanAndTailieu(thoigian, taikhoan,tailieu);
    }

    @Transactional
    public void deleteLichSuKiemDuyetByTaiLieu(Tailieu tailieu) {
        lichsukiemduyetRepository.deleteByTailieu(tailieu);
    }
}
