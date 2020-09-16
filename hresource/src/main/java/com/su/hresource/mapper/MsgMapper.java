package com.su.hresource.mapper;

import com.su.hresource.entity.Msg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作代办事项接口
 * @author tianyu
 * @date 2020年8月7日10:50:43
 * */
@Mapper
public interface MsgMapper {

    /**
     * 根据 事项处理权限、代办状态(待处理,已处理)  查询代办事项
     * @param msPower 事项处理权限 1：管理员 2：项目经理 3：开发人员 9：广播
     * @param msState 代办状态  1：待处理 2：已处理 3：作废
     * @return list
     * */
    @Select("select msg.msNo AS msNo,msg.msState AS msState,msg.msPower AS msPower,msg.msHeader AS msHeader,msg.msType AS msType,msg.msBodyInfo AS msBodyInfo,msg.msBodyNumber AS msBodyNumber,to_char(msg.msCreateTime,'yyyy-mm-dd') AS msCreateTime,to_char(msg.msDealTime,'yyyy-mm-dd') AS msDealTime,msg.msCreateUserNo AS msCreateUserNo,mapp.msTypeInfo AS msTypeInfo from SU_MSG msg,SU_MSG_TYPE_MAPPING mapp where msg.msType = mapp.msType and msg.msState = #{msState} and msg.msPower >= #{msPower} order by msState asc ,msCreateTime desc")
    List<Msg> selectMsgByPower(int msPower,String msState);


    /**
     * 根据 事项处理权限 查询代办事项 、代办状态(全部状态)
     * @param msPower 事项处理权限 1：管理员 2：项目经理 3：开发人员 9：广播/公告
     * @return list
     * */
    @Select("select msg.msNo AS msNo,msg.msState AS msState,msg.msPower AS msPower,msg.msHeader AS msHeader,msg.msType AS msType,msg.msBodyInfo AS msBodyInfo,msg.msBodyNumber AS msBodyNumber,to_char(msg.msCreateTime,'yyyy-mm-dd') AS msCreateTime,to_char(msg.msDealTime,'yyyy-mm-dd') AS msDealTime,msg.msCreateUserNo AS msCreateUserNo,mapp.msTypeInfo AS msTypeInfo from SU_MSG msg,SU_MSG_TYPE_MAPPING mapp where msg.msType = mapp.msType and msPower >= #{msPower} order by msState asc ,msCreateTime desc")
    List<Msg> selectAllMsgByPower(int msPower);


    /**
     * 创建代办id
     * @return msg
     * */
    @Select("select 'SUMG'|| to_char(sysdate,'yymmdd') || to_char(SU_QUE_MSG.nextval,'fm00000') AS msgNo from dual")
    String createMsgNo();

    /**
     * 插入代办
     * @param msg
     * */
    @Insert("insert into SU_MSG (msNo,msState,msPower,msHeader,msType,msBodyInfo,msBodyNumber,msCreateTime,msDealTime,msCreateUserNo) values (#{msNo},'1',#{msPower},#{msHeader},#{msType},#{msBodyInfo},#{msBodyNumber},sysdate,to_date(#{msDealTime},'yyyy-mm-dd'),#{msCreateUserNo})")
    void createMsg(Msg msg);

}
