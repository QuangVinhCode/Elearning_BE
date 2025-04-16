package com.vn.edu.elearning.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Binhluan}
 */
@Value
public class ThongtinbinhluanDto implements Serializable {
    String mabinhluan;
    String mataikhoan;
    String tendangnhap;
    String noidung;
    String trangthai;
    String matbinhluandatraloi;
    String thoigianbinhluan;
    String tentailieu;
}