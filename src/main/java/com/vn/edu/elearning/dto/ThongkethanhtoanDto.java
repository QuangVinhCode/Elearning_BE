package com.vn.edu.elearning.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Tailieu}
 */
@Value
public class ThongkethanhtoanDto implements Serializable {
    String mathanhtoan;
    String tentailieu;
    String tentaidangnhapthanhtoan;
    String tendangnhapchusohuu;
    Long giaban;
    double tongthunhaptacgia;
    double tongphiquantri;
    String thoigianthanhtoan;
}