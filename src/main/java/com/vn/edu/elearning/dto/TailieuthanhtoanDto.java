package com.vn.edu.elearning.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Tailieu}
 */
@Value
public class TailieuthanhtoanDto implements Serializable {
    String matailieu;
    String tentailieu;
    Long giaban;
    String tendanhmuc;
    String trangthaitailieu;
    String thoigianthanhtoan;
    String trangthai;
}