package com.su.hresource.service;

import com.su.hresource.entity.Item;
import com.su.hresource.entity.ItemMember;
import com.su.hresource.mapper.ItemMapper;
import com.su.hresource.mapper.ResourceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作项目相关的接口
 * @author tianyu
 * @date 2020年7月23日12:31:07
 * */
@Slf4j
@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ResourceInfoMapper resourceInfoMapper;

    /**
     * 新建一个审核阶段的项目，
     * //并将其项目经理的状态改为入场    //不做了
     * */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void newItem(Item item) {
        //项目状态 (0:待处理,审核阶段；1:项目进行中；2:项目完结；3:项目作废)
        item.setItemType("0");
        String itemId = itemMapper.creatItemId();
        item.setItemId(itemId);
        itemMapper.insertItem(item);

/*
这里不做了
        //更新项目经理在人力资源池中的状态
        resourceInfoMapper.updateUserEntranceByOpenid(item.getManagerOpenid(),"1");

        //添加经理至项目成员中
        ItemMember managerMsg = new ItemMember();
        managerMsg.setImIdcardNo(item.getManagerIdcardNo());
        managerMsg.setImUserName(item.getItemManager());
        managerMsg.setItemId(itemId);
        managerMsg.setImInDate(item.getItemStartDate());
        managerMsg.setImOutDate(item.getItemEndDate());
        //1：管理员	2：项目经理	3：开发人员
        managerMsg.setImPosition("2");
        //1：入场	2：出场
        managerMsg.setImType("1");
        itemMapper.insertItemMember(managerMsg);
*/
        checkItem(itemId);
    }

    /**
     * 审批项目， 将项目状态改 0：审批中--》1：进行
     * */
    @Override
    public void checkItem(String itemId) {
        itemMapper.updateItemType(itemId,"1");
    }


    /**
     * 查询项目列表
     * type:0 查询全部项目
     * type:1【所有参与过的项目(在ResourceItem里记录的项目) 的信息】
     * type:2 【查询本人正在参与的项目】
     * type:3 【1和2】
     * */
    @Override
    public List<Item> getItemList(String type, String openid, String idcardNo) {
        if("0".equals(type)){
            return itemMapper.selectItemListAll();
        }else{
            if("".equals(idcardNo) || idcardNo == null){
                throw new RuntimeException("idcardNo 不能为空或null");
            }
        }
        if("1".equals(type)){
            return itemMapper.selectItemList(idcardNo);
        }else if("2".equals(type)){
            return itemMapper.selectItemListNow(idcardNo);
        }else if("3".equals(type)){
            List<Item> items = itemMapper.selectItemList(idcardNo);
            List<Item> items1 = itemMapper.selectItemListNow(idcardNo);
            for (Item item:items1) {
                items.add(item);
            }
            return items;
        }else {
            throw new RuntimeException("传入数据有误");
        }
    }

    @Override
    public Item getItemOne(String itemId) {
        Item item = itemMapper.selectItem(itemId);
        item.setItemStartDate(item.getItemStartDate().substring(0,10));
        item.setItemEndDate(item.getItemEndDate().substring(0,10));
        return item;
    }

    /**
     * 修改项目信息【同步修改 项目的结束时间 到 人员的出场时间】
     * @param item
     * */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateItem(Item item) {
        if("".equals(item.getItemId()) || item.getItemId() == null){
            throw new RuntimeException("更新 SU_ITEM 时 id不能为空");
        }
        itemMapper.updateItem(item);
        itemMapper.updateItemMemberOutDate(item.getItemEndDate(),item.getItemId());
    }

    /**
     * 新建项目成员 并且修改其成员在人力资源池中的状态
     * */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void createItemMember(List<ItemMember> list) {
        for (ItemMember itemMember:list) {
            //设置入场状态           1：入场状态  2：出场状态
            itemMember.setImType("1");
            //1：管理员	2：项目经理	3：开发人员
            itemMember.setImPosition("3");
            itemMapper.insertItemMember(itemMember);

            resourceInfoMapper.updateUserEntranceByIdcardNo(itemMember.getImIdcardNo(),"1");
        }
    }


    @Override
    public Map selectItemMember(String itemId) {
        HashMap<String, Object> resultMap = new HashMap<>();
        List<ItemMember> itemMembers = itemMapper.selectItemMember(itemId);
        Item item = itemMapper.selectItem(itemId);
        for (ItemMember itemMember:itemMembers) {
            itemMember.setImInDate(itemMember.getImInDate().substring(0,10));
            itemMember.setImOutDate(itemMember.getImOutDate().substring(0,10));
            itemMember.setImCreateDate(itemMember.getImCreateDate().substring(0,10));
        }
        resultMap.put("membersInfo",itemMembers);
        resultMap.put("itemMsg",item);
        return resultMap;
    }


    @Override
    public void updateItemMember(ItemMember itemMember) {
        itemMapper.updateItemMember(itemMember);
    }
}
