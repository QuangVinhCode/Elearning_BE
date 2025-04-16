package com.vn.edu.elearning.service;

import com.vn.edu.elearning.domain.Danhmuc;
import com.vn.edu.elearning.dto.DanhmucDto;
import com.vn.edu.elearning.exeception.ClassException;
import com.vn.edu.elearning.repository.DanhmucRepository;
import com.vn.edu.elearning.repository.TailieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DanhmucService {
    @Autowired
    private DanhmucRepository danhmucRepository;
    @Autowired
    private TailieuRepository tailieuRepository;

    public Danhmuc save(DanhmucDto entity) {
        Danhmuc danhmuc = new Danhmuc();
        danhmuc.setTendanhmuc(entity.getTendanhmuc());
        return danhmucRepository.save(danhmuc);
    }

    public Danhmuc update(String id, DanhmucDto entity) {
        Optional<Danhmuc> existed = danhmucRepository.findById(id);
        if(!existed.isPresent())
        {
            throw new ClassException("Danh mục có id " + id + " không tồn tại");
        }

        try {
            Danhmuc existedDanhmuc = existed.get();
            existedDanhmuc.setTendanhmuc(entity.getTendanhmuc());
           return danhmucRepository.save(existedDanhmuc);
        }catch (Exception ex)
        {
            throw new ClassException("Danh mục muốn cập nhật bị lỗi");
        }
    }

    public List<Danhmuc> findAll() {
        return danhmucRepository.findAll();
    }

    public Danhmuc findById(String id) {
        Optional<Danhmuc> found = danhmucRepository.findById(id);

        if (!found.isPresent())
        {
            throw new ClassException("Danh mục có id "+ id + "không tồn tại");
        }
        return found.get();
    }

    public void  deleteById(String id){
        List<?> exitList = tailieuRepository.findByDanhmuc_Madanhmuc(id);
        if (!exitList.isEmpty())
        {
            throw new ClassException("Danh mục có tồn tại tài liệu");
        }
        Danhmuc existed = findById(id);
        danhmucRepository.delete(existed);
    }
}
