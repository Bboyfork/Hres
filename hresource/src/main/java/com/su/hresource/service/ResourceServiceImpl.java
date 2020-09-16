package com.su.hresource.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.su.hresource.entity.ResourceInfo;
import com.su.hresource.entity.ResourceInfoEdu;
import com.su.hresource.entity.ResourceInfoItem;
import com.su.hresource.entity.ResourceInfoWork;
import com.su.hresource.mapper.ItemMapper;
import com.su.hresource.mapper.ResourceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源池接口实现
 * @author tianyu
 * @date 2020年7月21日14:25:27
 * */
@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService{

    @Autowired
    ResourceInfoMapper resourceInfoMapper;

    @Autowired
    ItemMapper itemMapper;

    /**
     * 新建人员
     * */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void insertResourceInfo(ResourceInfo resourceInfo) {

        //新建人员 设置默认为出场      1：入场    2：出场
        resourceInfo.setIsEntrance("2");

        String userName = resourceInfo.getUserName();
        String idcardNo = resourceInfo.getIdcardNo();
        String openid = resourceInfo.getOpenid();

        ResourceInfoEdu educationInfo = resourceInfo.getEducationInfo();
        educationInfo.setUserName(userName);
        educationInfo.setEduIdcardNo(idcardNo);
        educationInfo.setOpenid(openid);

        insertResourceInfoEdu(educationInfo);

        List<JSONObject> workList = resourceInfo.getWorkList();
        List<JSONObject> itemList = resourceInfo.getItemList();

        for (JSONObject jsonObject:itemList) {
            ResourceInfoItem resourceInfoItem = jsonObject.toJavaObject(ResourceInfoItem.class);
            resourceInfoItem.setUserName(userName);
            resourceInfoItem.setIdcardNo(idcardNo);
            resourceInfoItem.setOpenid(openid);
            insertResourceInfoItem(resourceInfoItem);
        }
        for (JSONObject jsonObject:workList) {
            ResourceInfoWork resourceInfoWork = jsonObject.toJavaObject(ResourceInfoWork.class);
            resourceInfoWork.setIdcardNo(idcardNo);
            resourceInfoWork.setUserName(userName);
            resourceInfoWork.setOpenid(openid);
            insertResourceInfoWork(resourceInfoWork);
        }
            resourceInfoMapper.insertResourceInfo(resourceInfo);
    }

    /**
     * 生成id 并插入edu
     * */
    @Override
    public void insertResourceInfoEdu(ResourceInfoEdu resourceInfoEdu) {
        resourceInfoEdu.setEduNo(resourceInfoMapper.creatResourceInfoEduId());
        resourceInfoMapper.insertResourceInfoEdu(resourceInfoEdu);
    }

    /**
     * 生成id 并插入work
     * */
    @Override
    public void insertResourceInfoWork(ResourceInfoWork resourceInfoWork) {
        resourceInfoWork.setWorkNo(resourceInfoMapper.creatResourceInfoWorkId());
        resourceInfoMapper.insertResourceInfoWork(resourceInfoWork);
    }

    /**
     * 生成id 并插入item
     * */
    @Override
    public void insertResourceInfoItem(ResourceInfoItem resourceInfoItem) {
        resourceInfoItem.setItemNo(resourceInfoMapper.creatResourceInfoItemId());
        resourceInfoMapper.insertResourceInfoItem(resourceInfoItem);
    }

    /**
     * 查询人员 及其入场信息，
     * */
    @Override
    public List selectResourceInfoUserMsgList(String isEntrance) {
        if("0".equals(isEntrance)){
            return resourceInfoMapper.selectResourceUserMsgAll();
        }else if("1".equals(isEntrance) || "2".equals(isEntrance)){
            return resourceInfoMapper.selectResourceUserMsg(isEntrance);
        }else {
            throw new RuntimeException("输入isEntrance有误,请传入0/1/2");
        }
    }

    /**
     * 查询人力资源池中的一条 (带附属表 详细)
     * */
    @Override
    public ResourceInfo selectResourceInfo(String openid) {
        ResourceInfo resourceInfo = resourceInfoMapper.selectResourceInfoByOpenid(openid);
        resourceInfo.setLaborStartDate(resourceInfo.getLaborStartDate().substring(0,10));
        resourceInfo.setLaborEndDate(resourceInfo.getLaborEndDate().substring(0,10));
        //String idcardNo = resourceInfo.getIdcardNo();

        ResourceInfoEdu resourceInfoEdu = resourceInfoMapper.selectResourceInfoEduByOpenid(openid);
        if(resourceInfoEdu != null){
            resourceInfoEdu.setStartDate(resourceInfoEdu.getStartDate().substring(0,10));
            resourceInfoEdu.setEndDate(resourceInfoEdu.getEndDate().substring(0,10));
            resourceInfo.setEducationInfo(resourceInfoEdu);
        }

        List<ResourceInfoItem> resourceInfoItems = resourceInfoMapper.selectResourceInfoItemByOpenid(openid);
        List<ResourceInfoWork> resourceInfoWorks = resourceInfoMapper.selectResourceInfoWorkByOpenid(openid);

        List<JSONObject> workLists = new ArrayList<>();
        List<JSONObject> itemLists = new ArrayList<>();

        for (ResourceInfoItem resourceInfoItem:resourceInfoItems) {
            resourceInfoItem.setItemStartDate(resourceInfoItem.getItemStartDate().substring(0,10));
            resourceInfoItem.setItemEndDate(resourceInfoItem.getItemEndDate().substring(0,10));
            itemLists.add((JSONObject) JSON.toJSON(resourceInfoItem));
        }
        for (ResourceInfoWork resourceInfoWork:resourceInfoWorks) {
            resourceInfoWork.setWorkStartDate(resourceInfoWork.getWorkStartDate().substring(0,10));
            resourceInfoWork.setWorkEndDate(resourceInfoWork.getWorkEndDate().substring(0,10));
            workLists.add((JSONObject) JSON.toJSON(resourceInfoWork));
        }
        resourceInfo.setWorkList(workLists);
        resourceInfo.setItemList(itemLists);

        return resourceInfo;
    }

    /**
     * 查询人力资源池中的一条 (带附属表 详细)  by idcardNo
     * */
    @Override
    public ResourceInfo selectResourceInfoByIdcard(String idcardNo) {
        ResourceInfo resourceInfo = resourceInfoMapper.selectResourceInfoByIdcardNo(idcardNo);
        resourceInfo.setLaborStartDate(resourceInfo.getLaborStartDate().substring(0,10));
        resourceInfo.setLaborEndDate(resourceInfo.getLaborEndDate().substring(0,10));
        //String idcardNo = resourceInfo.getIdcardNo();

        ResourceInfoEdu resourceInfoEdu = resourceInfoMapper.selectResourceInfoEduByIdcardNo(idcardNo);
        if(resourceInfoEdu != null){
            resourceInfoEdu.setStartDate(resourceInfoEdu.getStartDate().substring(0,10));
            resourceInfoEdu.setEndDate(resourceInfoEdu.getEndDate().substring(0,10));
            resourceInfo.setEducationInfo(resourceInfoEdu);
        }

        List<ResourceInfoItem> resourceInfoItems = resourceInfoMapper.selectResourceInfoItemByIdcardNo(idcardNo);
        List<ResourceInfoWork> resourceInfoWorks = resourceInfoMapper.selectResourceInfoWorkByIdcardNo(idcardNo);

        List<JSONObject> workLists = new ArrayList<>();
        List<JSONObject> itemLists = new ArrayList<>();

        for (ResourceInfoItem resourceInfoItem:resourceInfoItems) {
            resourceInfoItem.setItemStartDate(resourceInfoItem.getItemStartDate().substring(0,10));
            resourceInfoItem.setItemEndDate(resourceInfoItem.getItemEndDate().substring(0,10));
            itemLists.add((JSONObject) JSON.toJSON(resourceInfoItem));
        }
        for (ResourceInfoWork resourceInfoWork:resourceInfoWorks) {
            resourceInfoWork.setWorkStartDate(resourceInfoWork.getWorkStartDate().substring(0,10));
            resourceInfoWork.setWorkEndDate(resourceInfoWork.getWorkEndDate().substring(0,10));
            workLists.add((JSONObject) JSON.toJSON(resourceInfoWork));
        }
        resourceInfo.setWorkList(workLists);
        resourceInfo.setItemList(itemLists);

        return resourceInfo;
    }

    /**
     * 人员状态置为 2：出场
     * */
    @Override
    public void updateResourceEntrance(String idcardNo,String imNo) {
        resourceInfoMapper.updateUserEntranceByIdcardNo(idcardNo,"2");
        itemMapper.updateItemMemberImTypeByIdcardNo(idcardNo,imNo);
    }

    /**
     * 修改人力资源池信息
     * */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateResourceInfo(ResourceInfo resourceInfo) {
        String idcardNo = resourceInfo.getIdcardNo();
        String userName = resourceInfo.getUserName();
        String openid = resourceInfo.getOpenid();

        resourceInfoMapper.updateResourceInfo(resourceInfo);

        ResourceInfoEdu educationInfo = resourceInfo.getEducationInfo();
        educationInfo.setOpenid(openid);
        educationInfo.setEduIdcardNo(idcardNo);
        educationInfo.setUserName(userName);
        if("".equals(educationInfo.getEduNo()) || null == educationInfo.getEduNo()){
            insertResourceInfoEdu(educationInfo);
        }else {
            resourceInfoMapper.updateResourceInfoEdu(educationInfo);
        }

        //resourceInfoMapper

        List<JSONObject> itemList = resourceInfo.getItemList();
        for (JSONObject jsonObject:itemList) {
            ResourceInfoItem resourceInfoItem = jsonObject.toJavaObject(ResourceInfoItem.class);
            resourceInfoItem.setOpenid(openid);
            resourceInfoItem.setUserName(userName);
            resourceInfoItem.setIdcardNo(idcardNo);
            if("".equals(resourceInfoItem.getItemNo()) || null == resourceInfoItem.getItemNo()){
                insertResourceInfoItem(resourceInfoItem);
            }else{
                resourceInfoMapper.updateResourceInfoItem(resourceInfoItem);
            }
        }

        List<JSONObject> workList = resourceInfo.getWorkList();
        for (JSONObject jsonObject:workList) {
            ResourceInfoWork resourceInfoWork = jsonObject.toJavaObject(ResourceInfoWork.class);
            resourceInfoWork.setOpenid(openid);
            resourceInfoWork.setIdcardNo(idcardNo);
            resourceInfoWork.setUserName(userName);

            if("".equals(resourceInfoWork.getWorkNo()) || null == resourceInfoWork.getWorkNo()){
                insertResourceInfoWork(resourceInfoWork);
            }else{
                resourceInfoMapper.updateResourceInfoWork(resourceInfoWork);
            }
        }

    }

    @Override
    public void updateResourceItem(ResourceInfoItem resourceInfoItem) {
        resourceInfoMapper.updateResourceInfoItem(resourceInfoItem);
    }


}
