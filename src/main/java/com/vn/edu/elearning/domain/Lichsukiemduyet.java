package com.vn.edu.elearning.domain;

import com.vn.edu.elearning.util.Status;
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
@Table(name = "lichsukiemduyet")
public class Lichsukiemduyet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String malichsukiemduyet;

    private String ketqua = Status.CKD.getValue();

    private String lydo;

    private String thoigiankiemduyet;

    @ManyToOne
    @JoinColumn(name = "matailieu")
    private Tailieu tailieu;
}
