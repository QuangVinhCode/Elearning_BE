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
@Table(name = "taikhoan")
public class Taikhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String mataikhoan;

    private String tendangnhap;

    private String matkhau;

    private Long sodu = 0L;

    private String gmail;

    private String sodienthoai;

    private String quyenhan = Status.USER.getValue();

    private String trangthaidangtai =Status.BT.getValue();

    private String trangthaibinhluan=Status.BT.getValue();

    @OneToMany(mappedBy = "taikhoan", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Dangtai> dsdangtai;

    @OneToMany(mappedBy = "taikhoan", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Thanhtoan> dsthanhtoan;

    @OneToMany(mappedBy = "taikhoan", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Baocaotailieu> dsbaocaotailieu;

    @OneToMany(mappedBy = "taikhoan", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Baocaobinhluan> dsbaocaobinhluan;

    @OneToMany(mappedBy = "taikhoan")
    @JsonIgnore
    private List<Giaodich> dsgiaodich;

    @OneToMany(mappedBy = "taikhoan")
    @JsonIgnore
    private List<Binhluan> dsbinhluan;
}