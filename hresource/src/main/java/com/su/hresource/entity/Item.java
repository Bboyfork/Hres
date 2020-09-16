package com.su.hresource.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 对应映射 项目表 SU_ITEM
 * @author tianyu
 * @date 2020年7月23日10:55:47
 * */
@Data
@ToString
public class Item {
    private String itemId;
    private String itemName;
    private String itemStartDate;
    private String itemEndDate;
    private String itemManager;
    private String bankItemManager;
    private String managerOpenid;
    private String managerIdcardNo;
    private String itemType;
}
