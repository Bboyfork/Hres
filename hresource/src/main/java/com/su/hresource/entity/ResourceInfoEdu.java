package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author tianyu
 * @date 2020年7月21日10:41:23
 * */
@ToString
@Data
public class ResourceInfoEdu {
    private String eduNo;
    private String eduIdcardNo;
    private String openid;
    private String userName;
    private String educational;
    private String degree;
    private String school;
    private String schoolType;
    private String specialty;
    private String majorType;
    private String certificateNo;
    private String degreeCertificateNumber;
    private String startDate;
    private String endDate;
}
