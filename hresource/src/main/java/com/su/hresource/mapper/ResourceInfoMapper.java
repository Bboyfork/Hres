package com.su.hresource.mapper;

import com.su.hresource.entity.ResourceInfo;
import com.su.hresource.entity.ResourceInfoEdu;
import com.su.hresource.entity.ResourceInfoItem;
import com.su.hresource.entity.ResourceInfoWork;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * SU_RESOURSE_INFO 相关表的操作
 * @author tianyu
 * @date 2020年7月21日10:38:31
 * */
@Mapper
public interface ResourceInfoMapper {

    /**
     * 查询表info
     * @param idcardNo
     * @return info
     * */
    @Select("select * from SU_RESOURCE_INFO where idcardNo = #{idcardNo}")
    ResourceInfo selectResourceInfoByIdcardNo(String idcardNo);

    /**
     * 查询表info
     * @param openid
     * @return info
     * */
    @Select("select * from SU_RESOURCE_INFO where openid = #{openid}")
    ResourceInfo selectResourceInfoByOpenid(String openid);

    /**
     * 插入数据，向表info
     * @param resourceInfo
     * */
    @Insert("insert into SU_RESOURCE_INFO (isEntrance,openid,userName,sex,idcardNo,addressNow,telephone,email,companyName,addressCheckedName,expertise,laborStartDate,laborEndDate) values (#{isEntrance},#{openid},#{userName},#{sex},#{idcardNo},#{addressNow},#{telephone},#{email},#{companyName},#{addressCheckedName},#{expertise},to_date(#{laborStartDate},'yyyy-mm-dd'),to_date(#{laborEndDate},'yyyy-mm-dd'))")
    void insertResourceInfo(ResourceInfo resourceInfo);

    /**
     * 修改info数据
     * @param resourceInfo
     * */
    @Update("update SU_RESOURCE_INFO set userName=#{userName},sex=#{sex},addressNow=#{addressNow},telephone=#{telephone},email=#{email},companyName=#{companyName},addressCheckedName=#{addressCheckedName},expertise=#{expertise},laborStartDate=to_date(#{laborStartDate},'yyyy-mm-dd'),laborEndDate=to_date(#{laborEndDate},'yyyy-mm-dd') where openid = #{openid} and idcardNo=#{idcardNo}")
    void updateResourceInfo(ResourceInfo resourceInfo);






    /**
     * 查询表infoEdu
     * @param idcardNo
     * @return infoEdu
     * */
    @Select("select * from SU_RESOURCE_EDU where eduIdcardNo = #{idcardNo}")
    ResourceInfoEdu selectResourceInfoEduByIdcardNo(String idcardNo);

    /**
     * 查询表infoEdu
     * @param openid
     * @return infoEdu
     * */
    @Select("select * from SU_RESOURCE_EDU where openid = #{openid}")
    ResourceInfoEdu selectResourceInfoEduByOpenid(String openid);

    /**
     * 查询序列 创建eduId
     * @return str id
     * */
    @Select("select 'SUREE' || to_char(sysdate,'yyMMdd') || to_char(SU_QUE_RESOURCE_EDU.nextval,'fm00000') from dual")
    String creatResourceInfoEduId();

    /**
     * 插入数据，向表infoEdu
     * @param resourceInfoEdu
     * */
    @Insert("insert into SU_RESOURCE_EDU (eduNo,eduIdcardNo,openid,userName,educational,degree,school,schoolType,specialty,majorType,certificateNo,degreeCertificateNumber,startDate,endDate) values (#{eduNo},#{eduIdcardNo},#{openid},#{userName},#{educational},#{degree},#{school},#{schoolType},#{specialty},#{majorType},#{certificateNo},#{degreeCertificateNumber},to_date(#{startDate},'yyyy-mm-dd'),to_date(#{endDate},'yyyy-mm-dd'))")
    void insertResourceInfoEdu(ResourceInfoEdu resourceInfoEdu);

    /**
     * 修改infoEdu
     * @param resourceInfoEdu
     * */
    @Update("update SU_RESOURCE_EDU set eduNo=#{eduNo},userName=#{userName},educational=#{educational},degree=#{degree},school=#{school},schoolType=#{schoolType},specialty=#{specialty},majorType=#{majorType},certificateNo=#{certificateNo},degreeCertificateNumber=#{degreeCertificateNumber},startDate=to_date(#{startDate},'yyyy-mm-dd'),endDate=to_date(#{endDate},'yyyy-mm-dd') where openid= #{openid} and eduIdcardNo = #{eduIdcardNo} and eduNo = #{eduNo}")
    void updateResourceInfoEdu(ResourceInfoEdu resourceInfoEdu);




    /**
     * 查询表infoWork
     * @param idcardNo
     * @return infoWork
     * */
    @Select("select * from SU_RESOURCE_WORK where idcardNo = #{idcardNo}")
    List<ResourceInfoWork> selectResourceInfoWorkByIdcardNo(String idcardNo);

    /**
     * 查询表infoWork
     * @param openid
     * @return infoWork
     * */
    @Select("select * from SU_RESOURCE_WORK where openid = #{openid}")
    List<ResourceInfoWork> selectResourceInfoWorkByOpenid(String openid);

    /**
     * 查询序列 创建workId
     * @return str id
     * */
    @Select("select 'SUREE' || to_char(sysdate,'yyMMdd') || to_char(SU_QUE_RESOURCE_WORK.nextval,'fm00000') from dual")
    String creatResourceInfoWorkId();

    /**
     * 插入数据，向表infoWork
     * @param resourceInfoWork
     * */
    @Insert("insert into SU_RESOURCE_WORK (workNo,idcardNo,openid,userName,corporateName,workStartDate,workEndDate,job,content) values (#{workNo},#{idcardNo},#{openid},#{userName},#{corporateName},to_date(#{workStartDate},'yyyy-mm'),to_date(#{workEndDate},'yyyy-mm'),#{job},#{content})")
    void insertResourceInfoWork(ResourceInfoWork resourceInfoWork);

    /**
     * 修改infoWork
     * @param resourceInfoWork
     * */
    @Update("update SU_RESOURCE_WORK set userName=#{userName},corporateName=#{corporateName},workStartDate=to_date(#{workStartDate},'yyyy-mm-dd'),workEndDate=to_date(#{workEndDate},'yyyy-mm-dd'),job=#{job},content=#{content} where idcardNo = #{idcardNo} and openid = #{openid} and workNo = #{workNo}")
    void updateResourceInfoWork(ResourceInfoWork resourceInfoWork);



    /**
     * 查询表infoItem
     * @param idcardNo
     * @return infoItem
     * */
    @Select("select * from SU_RESOURCE_ITEM where idcardNo = #{idcardNo}")
    List<ResourceInfoItem> selectResourceInfoItemByIdcardNo(String idcardNo);

    /**
     * 查询表infoItem
     * @param openid
     * @return infoItem
     * */
    @Select("select * from SU_RESOURCE_ITEM where openid = #{openid}")
    List<ResourceInfoItem> selectResourceInfoItemByOpenid(String openid);

    /**
     * 查询序列 创建itemId
     * @return str id
     * */
    @Select("select 'SUREE' || to_char(sysdate,'yyMMdd') || to_char(SU_QUE_RESOURCE_ITEM.nextval,'fm00000') from dual")
    String creatResourceInfoItemId();

    /**
     * 插入数据，向表infoItem
     * @param resourceInfoItem
     * */
    @Insert("insert into SU_RESOURCE_ITEM (itemNo,idcardNo,openid,userName,itemId,itemName,construction,framework,fourBank,otherBank,typeService,itemStartDate,itemEndDate,role,reterence,telNo,itemUndergo,duty,tools) values (#{itemNo},#{idcardNo},#{openid},#{userName},#{itemId},#{itemName},#{construction},#{framework},#{fourBank},#{otherBank},#{typeService},to_date(#{itemStartDate},'yyyy-mm-dd'),to_date(#{itemEndDate},'yyyy-mm-dd'),#{role},#{reterence},#{telNo},#{itemUndergo},#{duty},#{tools})")
    void insertResourceInfoItem(ResourceInfoItem resourceInfoItem);


    /**
     * 修改infoItem
     * @param resourceInfoItem
     * */
    @Update("update SU_RESOURCE_ITEM set userName=#{userName},itemId=#{itemId},itemName=#{itemName},construction=#{construction},framework=#{framework},fourBank=#{fourBank},otherBank=#{otherBank},typeService=#{typeService},itemStartDate=to_date(#{itemStartDate},'yyyy-mm-dd'),itemEndDate=to_date(#{itemEndDate},'yyyy-mm-dd'),role=#{role},reterence=#{reterence},telNo=#{telNo},itemUndergo=#{itemUndergo},duty=#{duty},tools=#{tools} where itemNo = #{itemNo} and idcardNo = #{idcardNo} and openid = #{openid}")
    void updateResourceInfoItem(ResourceInfoItem resourceInfoItem);


    /**
     * 查询人员信息列表【根据出入场状态】
     * @param isEntrance
     * @return resourceInfo 姓名 身份证号 出入场状态
     * */
    @Select("select userName,idcardNo,isEntrance,openid,sex from SU_RESOURCE_INFO where isEntrance = #{isEntrance}")
    List<ResourceInfo> selectResourceUserMsg(String isEntrance);

    /**
     * 查询人员信息列表【全部】
     * @return resourceInfo 姓名 身份证号 出入场状态
     * */
    @Select("select userName,idcardNo,isEntrance,openid,sex from SU_RESOURCE_INFO")
    List<ResourceInfo> selectResourceUserMsgAll();

    /**
     * 更新人员状态
     * @param openid 人员openid
     * @param isEntrance 是否入场 状态
     * */
    @Update("update SU_RESOURCE_INFO set isEntrance = #{isEntrance} where openid = #{openid}")
    void updateUserEntranceByOpenid(String openid, String isEntrance);

    /**
     * 更新 资源池 人员状态
     * @param idcardNo 人员idcardNo
     * @param isEntrance 是否入场 状态
     * */
    @Update("update SU_RESOURCE_INFO set isEntrance = #{isEntrance} where idcardNo = #{idcardNo}")
    void updateUserEntranceByIdcardNo(String idcardNo, String isEntrance);
}
