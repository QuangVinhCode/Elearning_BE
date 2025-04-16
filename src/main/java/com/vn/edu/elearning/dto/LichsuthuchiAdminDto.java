package com.vn.edu.elearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Giaodich}
 */
@Data
@AllArgsConstructor
public class LichsuthuchiAdminDto implements Serializable {
    Long thunhaptacgia;
    Long thunhapquantri;
    String thangnam;
}