package com.su.hresource.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author tianyu
 * @date 2020年7月21日10:41:23
 * */
@ToString
@Data
public class ResourceInfo {
    private String isEntrance="";
    private String openid="";
    private String userName="";
    private String sex="";
    private String idcardNo="";
    private String addressNow="";
    private String telephone="";
    private String email="";
    private String companyName="";
    private String addressCheckedName="";
    private String expertise="";
    private String laborStartDate="";
    private String laborEndDate;

    private ResourceInfoEdu educationInfo;
    private List<JSONObject> workList;
    private List<JSONObject> itemList;

}
