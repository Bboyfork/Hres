package com.su.hresource.service;

import com.alibaba.fastjson.JSONObject;
import com.su.hresource.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 登录相关接口
 * @author tianyu
 * @date 2020年7月17日09:43:24
 * */
public interface LoginService {

    /**
     * @param openid
     * @param userName
     * @param idcardNo
     * @return map
     * String idcardNo 根据身份证号 查询 是否有详细信息，
     * 再查询 是否匹配有openid
     * */
    Map isOlderWorker(String openid, String userName, String idcardNo);

    /**
     * 编辑老员工
     * @param openid
     * @param idcardNo
     * @return map
     * */
    void editOlderWorker(String openid, String userName, String idcardNo);

    /**
     * 根据code 从微信获取 openid 等
     * @param code
     * @return map
     * */
    Map getOpenid(String code);

    String insertWork(List<JSONObject> workList);

    String insertStudy(List<JSONObject> workList);

    String insertItem(List<JSONObject> workList);

    String insertMember(List<JSONObject> workList);

    String insertInfo(UserInfo userInfo);

    /**
     * 查询本人登记信息
     * @param map
     * @param openid
     * @return map
     * */
    Map getMyRegisterInfo(Map map, String openid);


    /**
     * 根据openid 获取其权限
     * @param openid
     * @return String 权限
     * */
    String selectUserLevel(String openid);

    /**
     * 根据idcardNo 获取其权限
     * @param idcardNo
     * @return String
     */
    String selectUserLevelByIdcardNo(String idcardNo);

    /**
     *
     *
     */
    void updateAccessLevel(String humans);


    /**
     * 查询在职/不在职列表，
     * */
    List selectStateMsg(String state);
}
