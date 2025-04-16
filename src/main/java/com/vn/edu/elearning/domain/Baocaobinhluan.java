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
@Table(name = "baocaobinhluan")
public class Baocaobinhluan {
    @EmbeddedId
    private Mabaocaobinhluan mabaocaobinhluan;

    @ManyToOne
    @MapsId("mataikhoan")
    @JoinColumn(name = "mataikhoan")
    private Taikhoan taikhoan;

    @ManyToOne
    @MapsId("mabinhluan")
    @JoinColumn(name = "mabinhluan")
    private Binhluan binhluan;

    @Column(name = "thoigianbaocao",length = 50,nullable = false)
    private String thoigianbaocao;

    @Column(length = 250, nullable = false)
    private String lydo;

}
