package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * SU_USER_CORE 映射
 * @author tianyu
 * @date 2020年7月17日10:45:23
 * */
@Data
@ToString
public class UserCore {
    private String openid;
    private String userName;
    private String idcardNo;
    private String accessLevel;
    private String createDate;
    private String state;
}
