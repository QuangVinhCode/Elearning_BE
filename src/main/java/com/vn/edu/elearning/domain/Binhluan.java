package com.vn.edu.elearning.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "binhluan")
public class Binhluan {
        @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String mabinhluan;

    @ManyToOne
    @JoinColumn(name = "matailieu", nullable = false)
    private Tailieu tailieu;

    @ManyToOne
    @JoinColumn(name = "mataikhoan", nullable = false)
    private Taikhoan taikhoan;

    private String noidung;

    private String trangthai;

    private String matbinhluandatraloi;

    private String thoigianbinhluan;

    @OneToMany(mappedBy = "binhluan", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Baocaobinhluan> dsbaocaobinhluan;
}