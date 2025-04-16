package com.vn.edu.elearning.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Baocaotailieu}
 */
@Value
public class TheodoibaocaoDto implements Serializable {
    Long solanbaocao;
    String tentaikhoanbitocao;
    String mataikhoan;
}