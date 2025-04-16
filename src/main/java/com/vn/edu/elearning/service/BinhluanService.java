package com.vn.edu.elearning.service;

import com.vn.edu.elearning.domain.Binhluan;
import com.vn.edu.elearning.domain.Taikhoan;
import com.vn.edu.elearning.domain.Tailieu;
import com.vn.edu.elearning.dto.BinhluanDto;
import com.vn.edu.elearning.dto.BinhluanTheoTailieuDto;
import com.vn.edu.elearning.dto.ThongtinbinhluanDto;
import com.vn.edu.elearning.exeception.ClassException;
import com.vn.edu.elearning.repository.BinhluanRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BinhluanService {
    @Autowired
    private BinhluanRepository binhluanRepository;

    @Autowired
    private TaikhoanService taikhoanService;

    public Binhluan save(BinhluanDto dto) {
        Binhluan entity = new Binhluan();
        BeanUtils.copyProperties(dto,entity);
        Taikhoan taikhoan = taikhoanService.findById(dto.getMataikhoan());
        if (taikhoan.getTrangthaibinhluan().equals("Bình thường"))
        {
            entity.setTrangthai("Thành công");
        }
        else {
            entity.setTrangthai("Thất bại");
        }
        Tailieu tailieu = new Tailieu();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        entity.setThoigianbinhluan(LocalDateTime.now().format(formatter));
        tailieu.setMatailieu(dto.getMatailieu());
        entity.setTaikhoan(taikhoan);
        entity.setTailieu(tailieu);
        return binhluanRepository.save(entity);
    }

    public List<Binhluan> findAll() {
        return binhluanRepository.findAll();
    }

    public List<Binhluan> findAllByAccount(String id) {
        return binhluanRepository.findByTaikhoan_Mataikhoan(id);
    }

    public List<BinhluanTheoTailieuDto> findBinhluansByMatailieu(String matl) {
        return binhluanRepository.findBinhluansByMatailieu(matl);
    }

    public List<ThongtinbinhluanDto> findBinhluans() {
        return binhluanRepository.findBinhluans();
    }

    public Binhluan findById(String id) {
        Optional<Binhluan> found = binhluanRepository.findById(id);

        if (!found.isPresent())
        {
            throw new ClassException("Bình luận có id "+ id + "không tồn tại");
        }
        return found.get();
    }



    public void  deleteById(String id){

        Binhluan existed = findById(id);
        binhluanRepository.delete(existed);
    }

    public void blockCommentAndReplies(String mabinhluan) {
        blockCommentAndRepliesRecursive(mabinhluan, "Chặn");
        // Attempt to update the main comment's status
//        int updated = binhluanRepository.updateCommentStatus(mabinhluan, "Chặn");
//
//        // Only proceed if the comment status update was successful
//        if (updated > 0) {
//            // Update the status of all replies only if the main comment status was successfully updated
//            binhluanRepository.updateRepliesStatus(mabinhluan, "Chặn", "Thành công");
//        }
    }

    private void blockCommentAndRepliesRecursive(String mabinhluan, String status) {
        // Cập nhật trạng thái của bình luận hiện tại
        int updated = binhluanRepository.updateCommentStatus(mabinhluan, status);

        // Nếu cập nhật thành công, tiếp tục cập nhật trạng thái của các bình luận trả lời
        if (updated > 0) {
            // Cập nhật trạng thái của tất cả các bình luận trả lời có mã bình luận trả lời là mabinhluan
            List<Binhluan> replies = binhluanRepository.findByMatbinhluandatraloi(mabinhluan);

            for (Binhluan reply : replies) {
                blockCommentAndRepliesRecursive(reply.getMabinhluan(), status);
            }
        } else {
            // Nếu không thành công, ném ngoại lệ hoặc xử lý theo cách khác
            throw new RuntimeException("Failed to update the status of the comment with ID " + mabinhluan);
        }
    }
}
