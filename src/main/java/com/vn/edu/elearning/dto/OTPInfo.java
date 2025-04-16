package com.vn.edu.elearning.dto;

public class OTPInfo {
    private String otp;
    private long creationTime;

    public OTPInfo(String otp, long creationTime) {
        this.otp = otp;
        this.creationTime = creationTime;
    }

    public String getOtp() {
        return otp;
    }

    public long getCreationTime() {
        return creationTime;
    }
}
