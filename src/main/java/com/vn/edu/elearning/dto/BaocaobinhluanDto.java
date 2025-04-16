package com.vn.edu.elearning.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Baocaobinhluan}
 */
@Value
public class BaocaobinhluanDto implements Serializable {
    String mataikhoan;
    String mabinhluan;
    String thoigianbaocao;
    String lydo;
    String trangthai;
}