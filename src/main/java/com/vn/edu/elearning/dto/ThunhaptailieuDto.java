package com.vn.edu.elearning.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Tailieu}
 */
@Value
public class ThunhaptailieuDto implements Serializable {
    String matailieu;
    String tentailieu;
    Long giaban;
    Long solanthanhtoan;
    Long tongthunhaptacgia;
    Long tongphiquantri;
}