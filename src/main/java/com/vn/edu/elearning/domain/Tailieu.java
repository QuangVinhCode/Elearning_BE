package com.vn.edu.elearning.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vn.edu.elearning.util.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tailieu")
public class Tailieu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String matailieu;

    private String tentailieu;

    private String tacgia;

    private String mota;

    private Long giaban;

    private String diachiluutru;

    private Long tylephiquantri;

    private Long tylethunhaptacgia;

    private String trangthai = Status.CKD.getValue();

    @ManyToOne
    @JoinColumn(name = "madanhmuc")
    private Danhmuc danhmuc;

    @OneToMany(mappedBy = "tailieu", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Dangtai> dsdangtai;

    @OneToMany(mappedBy = "tailieu", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Thanhtoan> dsthanhtoan;

    @OneToMany(mappedBy = "tailieu", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Baocaotailieu> dsbaocaotailieu;

    @OneToMany(mappedBy = "tailieu", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Binhluan> dsbinhluan;

}