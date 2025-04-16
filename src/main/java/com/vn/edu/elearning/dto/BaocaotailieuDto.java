package com.vn.edu.elearning.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Baocaotailieu}
 */
@Value
public class BaocaotailieuDto implements Serializable {
    String mataikhoan;
    String matailieu;
    String thoigianbaocao;
    String lydo;
    String trangthai;

}