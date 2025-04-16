package com.vn.edu.elearning.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Danhmuc}
 */
@Data
public class DanhmucDto implements Serializable {
    String madanhmuc;
    String tendanhmuc;
}