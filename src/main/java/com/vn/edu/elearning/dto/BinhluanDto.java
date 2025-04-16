package com.vn.edu.elearning.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Binhluan}
 */
@Data
public class BinhluanDto implements Serializable {
    String mabinhluan;
    String mataikhoan;
    String matailieu;
    @Size(min = 1, max = 50, message = "Nội dung bình luận chỉ 50 ký tự")
    String noidung;
    String trangthai;
    Long matbinhluandatraloi;
    String thoigianbinhluan;
}