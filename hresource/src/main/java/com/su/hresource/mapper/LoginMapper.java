package com.su.hresource.mapper;

import com.su.hresource.entity.UserCore;
import com.su.hresource.entity.UserInfo;
import com.su.hresource.entity.UserStateMsg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 登录相关mapper
 * @author tianyu
 * @date 2020年7月17日10:42:13
 * */
@Mapper
public interface LoginMapper {

    /**
     * 根据身份证号查询core相关信息
     * @param idcardNo
     * @return userCore
     * */
    @Select("select * from SU_USER_CORE where idcardNo = #{idcardNo}")
    UserCore selectCoreByIdCard(String idcardNo);

    /**
     * 根据身份证号查询info相关信息
     * @param idcardNo
     * @return userInfo
     * */
    @Select("select * from SU_USER_INFO where idcardNo = #{idcardNo}")
    UserInfo selectInfoByIdCard(String idcardNo);

    @Insert("insert INTO SU_USER_CORE (openid,userName,idcardNo,accessLevel,createDate,state)values(#{openid},#{userName},#{idcardNo},1,sysdate,'1')")
    int insertCore(String openid, String userName, String idcardNo);

    @Update("update SU_USER_INFO set openId = #{openid} where idcardNo = #{idcardNo}")
    int updateInfo(String openid, String idcardNo);

    /**
     * 根据openid 查询其权限等级
     * @param openid
     * @return accessLevel
     * */
    @Select("select accessLevel from SU_USER_CORE where OPENID = #{openid}")
    String selectUserLevel(String openid);

    /**
     * 根据idcardNo 查询其权限等级
     * @param idcardNo
     * @return accessLevel
     * */
    @Select("select accessLevel from SU_USER_CORE where idcardNo = #{idcardNo}")
    String selectUserLevelByIdcareNo(String idcardNo);

    /**
     * 根据idcardNo 修改其权限等级
     * @param idcardNo
     * @param accessLevel
     * */
    @Update("update SU_USER_CORE set accessLevel = #{accessLevel} where idcardNo - #{idcardNo}")
    void updateAccessLeve(String idcardNo,String accessLevel);

    /**
     * 根据条件查询在职/不在职人员信息
     * */
    @Select("select core.openid,core.userName,info.sex,core.idcardNo,core.state from SU_USER_CORE core,SU_USER_INFO info where core.idcardno = info.idcardno and core.state = #{state}")
    List<UserStateMsg> selectStateMsg(String state);

    /**
     * 查询全部人员信息 【不论在职与否】
     * */
    @Select("select core.openid,core.userName,info.sex,core.idcardNo,core.state from SU_USER_CORE core,SU_USER_INFO info where core.idcardno = info.idcardno")
    List<UserStateMsg> selectStateMsgAll();

}
