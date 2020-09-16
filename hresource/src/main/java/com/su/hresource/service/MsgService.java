package com.su.hresource.service;

import com.su.hresource.entity.Msg;
import java.util.List;

public interface MsgService {
    /**
     * 根据权限获取代办事项
     * @param power 权限等级
     * @param type 0全部 1待处理 2已处理
     * @return list
     * */
    List<Msg> selectMsgByMsPower(String power,String type);

    /**
     * 创建代办
     * @param msg
     * @return msNo 代办ID
     */
    String createMsg(Msg msg);

    /**
     * 生成结项
     * */

}
