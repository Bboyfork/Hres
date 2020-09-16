package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author tianyu
 * @date 2020年7月20日17:15:09
 * */
@Data
@ToString
public class UserInfoItem {
    private String itemListId;
    private String itemStartDate;
    private String itemEndDate;
    private String itemName;
    private String itemUndergo;
    private String itemSkill;
}
