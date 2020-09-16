package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author tianyu
 * @date 2020年7月20日17:16:26
 * */
@Data
@ToString
public class UserInfoMember {
    private String memberListId;
    private String memberName;
    private String memberNativePlace;
    private String memberBrithday;
    private String memberWorkUnit;
    private String memberJob;
    private String memberPhone;

}
