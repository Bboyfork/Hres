package com.su.hresource.mapper;

import com.su.hresource.entity.Item;
import com.su.hresource.entity.ItemMember;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 操作Item相关dao
 * @author tianyu
 * @date 2020年7月23日13:58:05
 * */
@Mapper
public interface ItemMapper {

    /**
     * 创建项目ID
     * */
    @Select("select 'SUIT'|| to_char(sysdate,'yymmdd') || to_char(SU_QUE_ITEM.nextval,'fm00000') AS itemId from dual")
    String creatItemId();

    /**
     * 新增 表SU_ITEM
     * @param item
     * */
    @Insert("insert into SU_ITEM (itemId,itemName,itemStartDate,itemEndDate,itemManager,bankItemManager,managerOpenid,managerIdcardNo,itemType) values ( #{itemId},#{itemName},to_date(#{itemStartDate},'yyyy-mm-dd'),to_date(#{itemEndDate},'yyyy-mm-dd'),#{itemManager},#{bankItemManager},#{managerOpenid},#{managerIdcardNo},#{itemType})")
    void insertItem(Item item);

    /**
     * 修改项目状态
     * */
    @Update("update SU_ITEM set itemType = #{itemType} where itemId = #{itemId}")
    void updateItemType(String itemId,String itemType);

    /**
     * updateItem
     * 修改项目信息
     * @param item
     * */
    @Update("update SU_ITEM set itemName = #{itemName},itemStartDate = to_date(#{itemStartDate},'yyyy-mm-dd'),itemEndDate = to_date(#{itemEndDate},'yyyy-mm-dd'),itemManager = #{itemManager},bankItemManager = #{bankItemManager},managerOpenid = #{managerOpenid},managerIdcardNo = #{managerIdcardNo} where itemId = #{itemId}")
    void updateItem(Item item);

    /**
     * 查询项目信息(单个)
     * @param itemId
     * @return item
     * */
    @Select("select * from SU_ITEM where itemId = #{itemId}")
    Item selectItem(String itemId);

    /**
     * 查询项目列表【全部】
     * @return list
     * */
    @Select("select itemId,itemName,to_char(itemStartDate,'yyyy-mm-dd') AS itemStartDate,to_char(itemEndDate,'yyyy-mm-dd') AS itemEndDate,itemManager,bankItemManager,managerOpenid,managerIdcardNo,itemType from SU_ITEM")
    List<Item> selectItemListAll();

    /**
     * 查询项目列表 根据idcardNo 【所有参与过的项目(在ResourceItem里记录的项目) 的信息】
     * @param idcardNo
     * @return list
     * */
    @Select("select item.itemId,item.itemName,to_char(item.itemStartDate,'yyyy-mm-dd') AS itemStartDate,to_char(item.itemEndDate,'yyyy-mm-dd') AS itemEndDate,item.itemManager,item.bankItemManager,item.managerOpenid,item.managerIdcardNo,item.itemType from SU_ITEM item right join ( select distinct itemId from SU_RESOURCE_ITEM where idcardNo = #{idcardNo}) hitem on hitem.itemid = item.itemid")
    List<Item> selectItemList(String idcardNo);

    /**
     * 查询本人正在进行的项目(信息)列表
     * selectItem --> selectItemListNow
     * @param idcardNo
     * @return list
     * */
    @Select("select item.itemId,item.itemName,to_char(item.itemStartDate,'yyyy-mm-dd') AS itemStartDate,to_char(item.itemEndDate,'yyyy-mm-dd') AS itemEndDate,item.itemManager,item.bankItemManager,item.managerOpenid,item.managerIdcardNo,item.itemType from SU_ITEM item, ( select distinct itemId from SU_ITEM_MEMBER where imIdcardNo = #{idcardNo} and imType = '1' ) nowItem where nowItem.itemId = item.itemid")
    List<Item> selectItemListNow(String idcardNo);

    //=========项目成员表 SU_ITEM_MEMBER===========================================//

    /**
     * 修改 项目的 成员的 出场时间(批量 ，并注意修改状态为 入场 的人员)
     * @param outDate 出场时间
     * @param itemId 要修改的项目
     * */
    @Update("update SU_ITEM_MEMBER set imOutDate = to_date(#{outDate},'yyyy-mm-dd') where itemId = #{itemId} and imType = '1'")
    void updateItemMemberOutDate(String outDate,String itemId);

    /**
     * 成员出场 itemMember处
     * 在入场的 itemId是 xxx的 idcardNo xxx的人    出场
     * */
    @Update("update SU_ITEM_MEMBER set imType = '2' where imNo = #{imNo} and imType = '1'")
    void updateItemMemberImTypeByIdcardNo(String idcardNo,String imNo);


    /**
     * 添加项目成员 SU_ITEM_MEMBER
     * @param itemMember
     * */
    @Insert("insert into SU_ITEM_MEMBER " +
            "(imNo,imIdcardNo,imUserName,itemId,imInDate,imOutDate,imActualUsers,imAccountNumber,imAccessCard,imFunctionary,imPosition,imType,imCreateDate) values " +
            "('SUIM'|| to_char(sysdate,'yymmdd') || to_char(SU_QUE_ITEM_MEMBER.nextval,'fm00000'),#{imIdcardNo},#{imUserName},#{itemId},to_date(#{imInDate},'yyyy-mm-dd'),to_date(#{imOutDate},'yyyy-mm-dd'),#{imActualUsers},#{imAccountNumber},#{imAccessCard},#{imFunctionary},#{imPosition},#{imType},sysdate)")
    void insertItemMember(ItemMember itemMember);


    /**
     * 查询项目(入场的)成员的信息
     * @param itemId
     * @return list
     * */
    @Select("select * from SU_ITEM_MEMBER where itemId = #{itemId} and imType = '1'")
    List<ItemMember> selectItemMember(String itemId);

    /**
     * 修改项目成员信息
     * @param itemMember
     * */
    @Update("update SU_ITEM_MEMBER set imUserName = #{imUserName},imInDate = to_date(#{imInDate},'yyyy-mm-dd'),imOutDate = to_date(#{imOutDate},'yyyy-mm-dd'),imActualUsers = #{imActualUsers},imAccountNumber = #{imAccountNumber}, imAccessCard = #{imAccessCard}, imFunctionary = #{imFunctionary},imPosition = #{imPosition},imWorkMsg = #{imWorkMsg}, imCheckUserName = #{imCheckUserName},imCheckIdcardNo = #{imCheckIdcardNo} where imNo = #{imNo}")
    void updateItemMember(ItemMember itemMember);
}
