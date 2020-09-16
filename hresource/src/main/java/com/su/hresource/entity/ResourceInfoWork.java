package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author tianyu
 * @date 2020年7月21日10:41:23
 * */
@ToString
@Data
public class ResourceInfoWork {
    private String workNo;
    private String idcardNo;
    private String openid;
    private String userName;
    private String corporateName;
    private String workStartDate;
    private String workEndDate;
    private String job;
    private String content;
}
