package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author tianyu
 * @date 2020年7月20日16:26:03
 * */
@Data
@ToString
public class UserInfoWork {
    private String workListId;
    private String workStartDate;
    private String workEndDate;
    private String workUnit;
    private String workJob;
    private String workContent;
}
