package com.su.hresource.service;

import com.su.hresource.entity.Item;
import com.su.hresource.entity.ItemMember;

import java.util.List;
import java.util.Map;

/**
 * @author tianyu
 * @date 2020年7月23日12:30:25
 * 操作项目相关的接口
 * */
public interface ItemService {

    /**
     * newItem 新建项目
     * @param item
     * */
    void newItem(Item item);

    /**
     * 审批项目， 将项目状态改 0：审批中--》1：进行
     *
     * @param itemId
     * */
    void checkItem(String itemId);

    /**
     * 获取项目列表
     * @param type 查询方式 只查自己/查全部
     * @param idcardNo
     * @param openid
     * @return list 项目列表
     * */
    List<Item> getItemList(String type, String openid, String idcardNo);

    /**
     * 查询项目信息 只查一条
     * @param itemId
     * @return item
     * */
    Item getItemOne(String itemId);

    /**
     * 修改项目信息【同步修改 项目的结束时间 到 人员的出场时间】
     * @param item
     * */
    void updateItem(Item item);

    /**
     * 新建项目成员 并且修改其成员在人力资源池中的状态
     * @param list
     * */
    void createItemMember(List<ItemMember> list);

    /**
     * 查询项目的成员信息
     * */
    Map selectItemMember(String itemId);

    /**
     * 修改项目成员信息
     * @param itemMember
     * */
    void updateItemMember(ItemMember itemMember);

}
