package com.vn.edu.elearning.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ADMIN("Admin"),
    USER("User"),
    BT("Bình thường"),
    CHAN("Chặn"),
    CAM("Cấm"),
    CKD("Chờ kiểm duyệt"),
    DKD("Đã kểm duyệt"),
    CCS("Cần chỉnh sửa"),
    TC("Thành công"),
    TB("Thất bại");
    private final String value;

}
