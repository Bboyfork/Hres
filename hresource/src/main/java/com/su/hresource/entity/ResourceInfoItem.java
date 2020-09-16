package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author tianyu
 * @date 2020年7月21日10:41:23
 * */
@ToString
@Data
public class ResourceInfoItem {
    private String itemNo;
    private String idcardNo;
    private String openid;
    private String userName;
    private String itemId;
    private String itemName;
    private String construction;
    private String framework;
    private String fourBank;
    private String otherBank;
    private String typeService;
    private String itemStartDate;
    private String itemEndDate;
    private String role;
    private String reterence;
    private String telNo;
    private String itemUndergo;
    private String duty;
    private String tools;
}
