package com.vn.edu.elearning.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Giaodich}
 */
@Data
public class GiaodichDto implements Serializable {
    String magiaodich;
    Long sotien;
    String lydo;
    String trangthai;
    TaikhoanDto taikhoan;
}