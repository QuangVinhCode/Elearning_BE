package com.vn.edu.elearning.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Tailieu}
 */
@Value
public class ThongtintailieuDto implements Serializable {
    String matailieu;
    String tentailieu;
    String tacgia;
    String mota;
    Long giaban;
    String diachiluutru;
    Long tylephiquantri;
    Long tylethunhaptacgia;
    String trangthai;
    String mataikhoandangtai;
    String tentaikhoandangtai;
}