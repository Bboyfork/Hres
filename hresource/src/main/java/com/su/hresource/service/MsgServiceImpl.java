package com.su.hresource.service;

import com.su.hresource.entity.Msg;
import com.su.hresource.mapper.MsgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tianyu
 * @date 2020年8月7日14:22:25
 * */
@Service
public class MsgServiceImpl implements MsgService{

    @Autowired
    MsgMapper msgMapper;

    @Override
    public List<Msg> selectMsgByMsPower(String power,String type) {
        //type 0全部 1待处理 2已处理
        if("0".equals(type)){
            return msgMapper.selectAllMsgByPower(Integer.parseInt(power));
        }else if("1".equals(type) || "2".equals(type)){
            return msgMapper.selectMsgByPower(Integer.parseInt(power),type);
        }else{
            throw new RuntimeException("传入查询类型有误 请输入：type 0:全部 1:待处理 2:已处理");
        }
    }

    /**
     * 创建代办
     * */
    @Override
    public String createMsg(Msg msg) {
        String msgNo = msgMapper.createMsgNo();
        msg.setMsNo(msgNo);
        msgMapper.createMsg(msg);
        return msgNo;
    }
}
