package com.vn.edu.elearning.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Baocaotailieu}
 */
@Value
public class ThongtinbaocaobinhluanDto implements Serializable {
    Long solanbaocao;
    String noidungbinhluan;
    String tentaikhoanbitocao;
    String mabinhluan;
}