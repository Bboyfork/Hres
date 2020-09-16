package com.su.hresource.mapper;

import com.su.hresource.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户info相关mapper 包括关联的work item等4表的操作
 * @author tianyu
 * @date 2020年7月20日16:21:16
 * */
@Mapper
public interface UserInfoMapper {

    /**
     * 插入信息info
     * @param userInfo
     * */
    @Insert("insert into SU_USER_INFO (openid,userName,writeDate,position,sex,birthday,nativePlace,politicalStatus,educational,specialty,academicTitle,idcardNo,marryStatus,school,graduation,certificateNo,degreeCertificateNumber,address,postalCode,addressNow,residence,computerType,telephone,email,nation,qq,section,job,entryDate,turnoverDate,expectedSalary,trialSalary,positiveSalary,workingMotherland,hobbies,speciality,declarationDate,studyListId,workListId,itemListId,memberListId) values (#{openid},#{userName},to_date(#{writeDate},'yyyy-mm-dd'),#{position},#{sex},to_date(#{birthday},'yyyy-mm-dd'),#{nativePlace},#{politicalStatus},#{educational},#{specialty},#{academicTitle},#{idcardNo},#{marryStatus},#{school},to_date(#{graduation},'yyyy-mm-dd'),#{certificateNo},#{degreeCertificateNumber},#{address},#{postalCode},#{addressNow},#{residence},#{computerType},#{telephone},#{email},#{nation},#{qq},#{section},#{job},to_date(#{entryDate},'yyyy-mm-dd'),to_date(#{turnoverDate},'yyyy-mm-dd'),#{expectedSalary},#{trialSalary},#{positiveSalary},#{workingMotherland},#{hobbies},#{speciality},to_date(#{declarationDate},'yyyy-mm-dd'),#{studyListId},#{workListId},#{itemListId},#{memberListId})")
    void insertUserInfo(UserInfo userInfo);

    /**
     * 根据openid 查询info信息
     * @param openid
     * @return info
     * */
    @Select("select * from SU_USER_INFO where openid = #{openid}")
    UserInfo selectUserInfo(String openid);


    /**
     * work=======
     * */
    @Select("select to_char(SU_USER_INFO_WORK.nextval,'fm0000') from dual")
    String selectWorkId();

    @Insert("insert into SU_USER_WORK " +
            "(workListId,workStartDate,workEndDate,workUnit,workJob,workContent) values " +
            "(#{workListId},to_date(#{workStartDate},'yyyy-mm'),to_date(#{workEndDate},'yyyy-mm'),#{workUnit},#{workJob},#{workContent})")
    void insertWork(UserInfoWork userInfoWork);

    @Select("select * from SU_USER_WORK where workListId = #{workListId}")
    List<UserInfoWork> selectWork(String workListId);


    /**
     * study=======
     * */
    @Select("select to_char(SU_USER_INFO_STUDY.nextval,'fm0000') from dual")
    String selectStudyId();

    @Insert("insert into SU_USER_STUDY " +
            "(studyListId,studyStartDate,studyEndDate,studyUnit,studyContent,studyResult) values " +
            "(#{studyListId},to_date(#{studyStartDate},'yyyy-mm'),to_date(#{studyEndDate},'yyyy-mm'),#{studyUnit},#{studyContent},#{studyResult})")
    void insertStudy(UserInfoStudy userInfoStudy);

    @Select("select * from SU_USER_STUDY where studyListId = #{studyListId}")
    List<UserInfoStudy> selectStudy(String studyListId);


    /**
     * item=======
     * */
    @Select("select to_char(SU_USER_INFO_ITEM.nextval,'fm0000') from dual")
    String selectItemId();

    @Insert("insert into SU_USER_ITEM " +
            "(itemListId,itemStartDate,itemEndDate,itemName,itemUndergo,itemSkill) values " +
            "(#{itemListId},to_date(#{itemStartDate},'yyyy-mm-dd'),to_date(#{itemEndDate},'yyyy-mm-dd'),#{itemName},#{itemUndergo},#{itemSkill})")
    void insertItem(UserInfoItem userInfoItem);

    @Select("select * from SU_USER_ITEM where itemListId = #{itemListId}")
    List<UserInfoItem> selectItem(String itemListId);

    /**
     * member=======
     * */
    @Select("select to_char(SU_USER_INFO_MEMBER.nextval,'fm0000') from dual")
    String selectMemberId();

    @Insert("insert into SU_USER_MEMBER " +
            "(memberListId,memberName,memberNativePlace,memberBrithday,memberWorkUnit,memberJob,memberPhone) values " +
            "(#{memberListId},#{memberName},#{memberNativePlace},to_date(#{memberBrithday},'yyyy-mm-dd'),#{memberWorkUnit},#{memberJob},#{memberPhone})")
    void insertMember(UserInfoMember userInfoMember);

    @Select("select * from SU_USER_MEMBER where memberListId = #{memberListId}")
    List<UserInfoMember> selectMember(String memberListId);

}
