package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 代办消息实体类
 * @author tianyu
 * @date 2020年8月7日10:47:25
 * */
@ToString
@Data
public class Msg {
    private String msNo;
    private String msState;
    private String msPower;
    private String msHeader;
    private String msType;
    private String msTypeInfo;
    private String msBodyInfo;
    private String msBodyNumber;
    private String msCreateTime;
    private String msDealTime;
    private String msCreateUserNo;
}
