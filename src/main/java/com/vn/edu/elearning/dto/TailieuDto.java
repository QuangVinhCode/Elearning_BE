package com.vn.edu.elearning.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Tailieu}
 */
@Data
public class TailieuDto implements Serializable {
    String matailieu;
    String tentailieu;
    String tacgia;
    String mota;
    Long giaban;
    String diachiluutru;
    Long tylephiquantri;
    Long tylethunhaptacgia;
    String trangthai;
    String madanhmuc;
    String mataikhoan;
    @JsonIgnore
    private MultipartFile pdfFile;
}