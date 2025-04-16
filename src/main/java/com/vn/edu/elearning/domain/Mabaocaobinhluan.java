package com.vn.edu.elearning.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Mabaocaobinhluan implements Serializable {
    private String mataikhoan;
    private String mabinhluan;
}
