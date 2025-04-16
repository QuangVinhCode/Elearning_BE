package com.vn.edu.elearning.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Taikhoan}
 */
@Data
public class TaikhoanDto implements Serializable {
    String mataikhoan;
    String tendangnhap;
    Long sodu;
    String gmail;
    String matkhau;
    String sodienthoai;
    String trangthaidangtai;
    String trangthaibinhluan;
    String quyenhan;
}