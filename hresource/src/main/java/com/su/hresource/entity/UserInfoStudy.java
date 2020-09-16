package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author tianyu
 * @date 2020年7月20日17:14:38
 * */
@Data
@ToString
public class UserInfoStudy {
    private String studyListId;
    private String studyStartDate;
    private String studyEndDate;
    private String studyUnit;
    private String studyContent;
    private String studyResult;

}
