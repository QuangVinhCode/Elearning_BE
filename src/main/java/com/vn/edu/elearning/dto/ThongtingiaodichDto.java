package com.vn.edu.elearning.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.edu.elearning.domain.Tailieu}
 */
@Value
public class ThongtingiaodichDto implements Serializable {
    String magiaodich;
    String lydo;
    Long sotien;
    String trangthai;
    String thoigiangiaodich;
    String tentaikhoangiaodich;
}