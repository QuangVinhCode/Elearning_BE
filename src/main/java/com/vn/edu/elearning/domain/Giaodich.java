package com.vn.edu.elearning.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "giaodich")
public class Giaodich {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String magiaodich;

    private Long sotien;

    private String lydo;

    private String trangthai;

    private String thoigiangiaodich;

    @ManyToOne
    @JoinColumn(name = "mataikhoan")
    private Taikhoan taikhoan;
}
