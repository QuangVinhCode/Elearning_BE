package com.vn.edu.elearning.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Thanhtoan}
 */
@Data
public class ThanhtoanDto implements Serializable {
    String matailieu;
    String mataikhoan;
    String thoigianthanhtoan;
    String trangthai;
}