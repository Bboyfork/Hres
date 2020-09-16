package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 用于显示在职人员列表的模型【其实就是多了一个sex 没办法 不能复用UserCore了】
 * @author tianyu
 * @date 2020年7月23日16:05:36
 * */
@ToString
@Data
public class UserStateMsg {
    private String openid;
    private String userName;
    private String sex;
    private String idcardNo;
    private String state;
}
