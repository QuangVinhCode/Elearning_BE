package com.vn.edu.elearning.service;

import com.vn.edu.elearning.domain.Giaodich;
import com.vn.edu.elearning.dto.LichsuthuchiDto;
import com.vn.edu.elearning.repository.GiaodichRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiaodichService {
    @Autowired
    private GiaodichRepository giaodichRepository;
    public Giaodich save(Giaodich entity) {
        return giaodichRepository.save(entity);
    }

    public List<Giaodich> findAllByAccount(String id) {
        return giaodichRepository.findByTaikhoan_Mataikhoan(id);

    }

    public List<LichsuthuchiDto> findAllTransactionByAccount(String id) {
        return giaodichRepository.findLichsuthuchiByTaikhoan(id);

    }

    public List<?> findGiaodichsAdmin() {
        return giaodichRepository.findGiaodichsAdmin();

    }

}
