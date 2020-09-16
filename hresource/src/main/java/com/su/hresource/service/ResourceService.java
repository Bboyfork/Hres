package com.su.hresource.service;

import com.su.hresource.entity.*;

import java.util.List;

/**
 * 资源池接口
 * @author tianyu
 * @date 2020年7月21日14:24:07
 * */
public interface ResourceService {

    /**
     * 向资源池插入人员信息及详细
     *      --表Info
     * @param resourceInfo
     * @return map
     * */
    void insertResourceInfo(ResourceInfo resourceInfo);

    /**
     * 向资源池插入人员信息及详细
     *      --表ResourceEdu
     * @param resourceInfoEdu
     * @return map
     * */
    void insertResourceInfoEdu(ResourceInfoEdu resourceInfoEdu);

    /**
     * 向资源池插入人员信息及详细
     *      --表ResourceWork
     * @param resourceInfoWork
     * @return map
     * */
    void insertResourceInfoWork(ResourceInfoWork resourceInfoWork);

    /**
     * 向资源池插入人员信息及详细
     *      --表ResourceItem
     * @param resourceInfoItem
     * @return map
     * */
    void insertResourceInfoItem(ResourceInfoItem resourceInfoItem);

    /**
     * 按出入场状态查询人员 其入场信息
     * @param isEntrance 0：全部     1/2：查询状态为1/2的人员信息
     * @return list
     * */
    List selectResourceInfoUserMsgList(String isEntrance);

    /**
     * 查询人力资源池中的一条 (带附属表 详细)
     * @param openid
     * @return resInfo
     * */
    ResourceInfo selectResourceInfo(String openid);

    /**
     * 查询人力资源池中的一条 (带附属表 详细) by idcardNo
     * @param idcardNo
     * @return resInfo
     * */
    ResourceInfo selectResourceInfoByIdcard(String idcardNo);


    /**
     * 人员出场
     * @param idcardNo
     * @param itemId
     * */
    void updateResourceEntrance(String idcardNo,String itemId);

    /**
     * 修改资源池信息(带附属子表)
     * @param resourceInfo
     * */
    void updateResourceInfo(ResourceInfo resourceInfo);

    /**
     * 修改资源池项目经验
     * @param resourceInfoItem
     * */
    void updateResourceItem(ResourceInfoItem resourceInfoItem);
}
