package com.vn.edu.elearning.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "thanhtoan")
public class Thanhtoan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String mathanhtoan;

    @ManyToOne
    @JoinColumn(name = "mataikhoan", nullable = false)
    private Taikhoan taikhoan;

    @ManyToOne
    @JoinColumn(name = "matailieu", nullable = false)
    private Tailieu tailieu;

    private String thoigianthanhtoan;

    private String trangthai;
}
