package com.vn.edu.elearning.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Lichsukiemduyet}
 */
@Data
public class LichsukiemduyetDto implements Serializable {
    String malichsukiemduyet;
    String ketqua;
    String lydo;
    String thoigiankiemduyet;
    String matailieu;
}