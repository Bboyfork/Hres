package com.su.hresource.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * SU_USER_INFO 映射
 * @author tianyu
 * @date 2020年7月17日10:45:23
 * */
@Data
@ToString
public class UserInfo {
    private String openid="";
    private String userName="";
    private String writeDate="";
    private String position="";
    private String sex="";
    private String birthday="";
    private String nation="";
    private String nativePlace="";
    private String politicalStatus="";
    private String educational="";
    private String specialty="";
    private String academicTitle="";
    private String idcardNo="";
    private String marryStatus="";
    private String school="";
    private String graduation="";
    private String certificateNo="";
    private String degreeCertificateNumber="";
    private String address="";
    private String postalCode="";
    private String addressNow="";
    private String residence="";
    private String computerType="";
    private String telephone="";
    private String email="";
    private String qq="";
    private String section="";
    private String job="";
    private String entryDate="";
    private String turnoverDate="";
    private String expectedSalary="";
    private String trialSalary="";
    private String positiveSalary="";
    private String workingMotherland="";
    private String hobbies="";
    private String speciality="";
    private String declarationDate="";

    /**
     * studyId
     * */
    private String studyListId="";
    private String workListId="";
    private String itemListId="";
    private String memberListId="";

    private List<JSONObject> studyList;
    private List<JSONObject> workList;
    private List<JSONObject> itemList;
    private List<JSONObject> memberList;


}
