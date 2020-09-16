package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * TestBean
 * @author tianyu
 * @date 2020年7月27日10:27:16
 * */
@Data
@ToString
public class TestBean {
    private String openid;
    private String userName;
    private String idcardNo;
    private String accessLevel;
    private String createDate;
    private String state;
}
