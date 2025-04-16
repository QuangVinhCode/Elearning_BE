package com.vn.edu.elearning.dto;

import com.vn.edu.elearning.domain.Danhmuc;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Tailieu}
 */
@Value
public class TailieudangtaiAdminDto implements Serializable {
    String matailieu;
    String tentailieu;
    String trangthai;
    String tendangnhap;
    String thoigiantailen;
    String thoigianduocduyet;
}