package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author tianyu
 * @date 2020年7月27日16:45:26
 * */
@Data
@ToString
public class ItemMember {
    private String imNo;
    private String imIdcardNo = "";
    private String imUserName = "";
    private String itemId = "";
    private String imInDate = "";
    private String imOutDate = "";
    private String imActualUsers = "";
    private String imAccountNumber = "";
    private String imAccessCard = "";
    private String imFunctionary = "";
    private String imPosition = "";
    private String imWorkMsg = "";
    private String imType = "";
    private String imCreateDate = "";
    private String imCheckUserName = "";
    private String imCheckIdcardNo = "";
}
