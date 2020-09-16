package com.su.hresource.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.su.hresource.entity.*;
import com.su.hresource.mapper.LoginMapper;
import com.su.hresource.mapper.UserInfoMapper;
import com.su.hresource.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author tianyu
 * @date 2020年7月17日09:42:32
 * */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 接口2 根据idcardNo 查询在两表中的情况
     * */
    @Override
    public Map isOlderWorker(String openid, String userName, String idcardNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口2");
        boolean flag = false;
        //先查出是否有详细信息
        UserCore userCore = loginMapper.selectCoreByIdCard(idcardNo);
        if(userCore == null){
            map.put("isRegister",false);
        }else {
            map.put("isRegister",true);
            flag = true;
            if(!openid.equals(userCore.getOpenid()) ){
                map.put("errCode","0003");
                map.put("errMsg","记录openid 与本次传入openid有差异/@/ 本次："+openid +"记录："+userCore.getOpenid());
            }
        }

        UserInfo userInfo = loginMapper.selectInfoByIdCard(idcardNo);

        if(userInfo == null){
            //info表中没有信息
            map.put("isOlderWork",false);

        }else {
            map.put("isOlderWork",true);
            if("".equals(userInfo.getOpenid()) || userInfo.getOpenid()==null){
                //info表中没有openid

            }else {
                //info表中有openid
                if(flag){
                    //core表中有数据 则 校验一下是否核对正确
                    if(!userCore.getOpenid().equals(userInfo.getOpenid())){
                        map.put("errCode","0004");
                        map.put("errMsg","core表与info表中openid不一致，请联系管理员");
                    }
                }else {
                    // core表中没有记录 并且 info 表中 openid 有值
                }

            }
        }
        String str = JSON.toJSONString(map);
        log.info("本次处理map："+str);
        return map;
    }

    /**
     * 接口3 更新表CORE
     * */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void editOlderWorker(String openid, String userName, String idcardNo) {
        loginMapper.insertCore(openid, userName, idcardNo);
        loginMapper.updateInfo(openid,idcardNo);
    }

    /**
     * 调取微信登录接口
     * https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
     * param:
     *      appid: 'wxce5aad371556b878',
     *      secret:'60ea5eeb93200da6bfee5c943ed81452',
     *      grant_type: 'authorization_code'
     *      code: 传入
     * */
    @Override
    public Map getOpenid(String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
/*
        //第一版http请求出去 我想不通 怎么就不好使
        HashMap<String, String> httpMap = new HashMap<>();
        httpMap.put("appid","wxce5aad371556b878");
        httpMap.put("secret","60ea5eeb93200da6bfee5c943ed81452");
        httpMap.put("grant_type","authorization_code");
        httpMap.put("js_code",code);
        String s = HttpClientUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session", "UTF-8", "", httpMap);
        System.out.println("d8ut49===>"+s);
*/

        String forkAppid="wx9834a99d90b1dea6";
        String forkSecret = "f3ff7e2ea4af97887afbd5daa8e406a3";
//        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxce5aad371556b878&secret=60ea5eeb93200da6bfee5c943ed81452&grant_type=authorization_code&js_code="+code;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+forkAppid+"&secret="+forkSecret+"&grant_type=authorization_code&js_code="+code;
        String get = HttpClientUtil.httpsAllRequest(url, "GET", "");
        HashMap getMap = JSON.parseObject(get, HashMap.class);
        map.put("session_key",getMap.get("session_key"));
        map.put("openid",getMap.get("openid"));
        if(getMap.get("errcode")!=null){
            map.put("errCode","0013");
            map.put("errMsg","调取微信接口出错，error："+getMap.get("errcode")+getMap.get("errmsg"));
        }
        log.info("调取微信接口后返回结果："+map);
        return map;
    }

    @Override
    public String insertWork(List<JSONObject> workList) {
        String date = new SimpleDateFormat("yyMMdd").format(new Date());
        String workId = "USIFW" + date + userInfoMapper.selectWorkId();
        JSONArray workArr = JSON.parseArray(workList.toString());
        for (Object obj:workArr) {
            UserInfoWork userInfoWork = JSONObject.parseObject(((JSONObject) obj).toString(), UserInfoWork.class);
            userInfoWork.setWorkListId(workId);
            userInfoMapper.insertWork(userInfoWork);
        }
        return workId;
    }

    @Override
    public String insertStudy(List<JSONObject> workList) {
        String date = new SimpleDateFormat("yyMMdd").format(new Date());

        String studyId = "USIFS" + date + userInfoMapper.selectStudyId();
        JSONArray workArr = JSON.parseArray(workList.toString());
        for (Object obj:workArr) {
            UserInfoStudy userInfoStudy = JSONObject.parseObject(((JSONObject) obj).toString(), UserInfoStudy.class);
            userInfoStudy.setStudyListId(studyId);
            userInfoMapper.insertStudy(userInfoStudy);
        }
        return studyId;
    }

    @Override
    public String insertItem(List<JSONObject> workList) {
        String date = new SimpleDateFormat("yyMMdd").format(new Date());

        String itemListId = "USIFI" + date + userInfoMapper.selectItemId();
        JSONArray workArr = JSON.parseArray(workList.toString());
        for (Object obj:workArr) {
            UserInfoItem userInfoItem = JSONObject.parseObject(((JSONObject) obj).toString(), UserInfoItem.class);
            userInfoItem.setItemListId(itemListId);
            userInfoMapper.insertItem(userInfoItem);
        }
        return itemListId;
    }

    @Override
    public String insertMember(List<JSONObject> workList) {
        String date = new SimpleDateFormat("yyMMdd").format(new Date());

        String memberId = "USIFM" + date + userInfoMapper.selectMemberId();
        JSONArray workArr = JSON.parseArray(workList.toString());
        for (Object obj:workArr) {
            UserInfoMember userInfoMember = JSONObject.parseObject(((JSONObject) obj).toString(), UserInfoMember.class);
            userInfoMember.setMemberListId(memberId);
            userInfoMapper.insertMember(userInfoMember);
        }
        return memberId;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public String insertInfo(UserInfo userInfo) {
        String workListId = insertWork(userInfo.getWorkList());
        String studyListId = insertStudy(userInfo.getStudyList());
        String itemListId = insertItem(userInfo.getItemList());
        String memberListId = insertMember(userInfo.getMemberList());

        log.info("即将插入的各子表ID为："+workListId+"===="+studyListId+"===="+itemListId+"===="+memberListId);
        userInfo.setWorkListId(workListId);
        userInfo.setStudyListId(studyListId);
        userInfo.setItemListId(itemListId);
        userInfo.setMemberListId(memberListId);


        userInfoMapper.insertUserInfo(userInfo);

        //添加一条至 CORE表
        loginMapper.insertCore(userInfo.getOpenid(),userInfo.getUserName(),userInfo.getIdcardNo());

        return userInfo.getIdcardNo();
    }


    @Override
    public Map getMyRegisterInfo(Map map, String openid) {
        UserInfo userInfo = userInfoMapper.selectUserInfo(openid);
        userInfo.setWriteDate(userInfo.getWriteDate().substring(0,10));
        userInfo.setBirthday(userInfo.getBirthday().substring(0,10));
        userInfo.setGraduation(userInfo.getGraduation().substring(0,10));
        userInfo.setEntryDate(userInfo.getEntryDate().substring(0,10));
        userInfo.setTurnoverDate(userInfo.getTurnoverDate().substring(0,10));
        userInfo.setDeclarationDate(userInfo.getDeclarationDate().substring(0,10));

        List<UserInfoWork> userInfoWorks = userInfoMapper.selectWork(userInfo.getWorkListId());
        List<UserInfoStudy> userInfoStudies = userInfoMapper.selectStudy(userInfo.getStudyListId());
        List<UserInfoItem> userInfoItems = userInfoMapper.selectItem(userInfo.getItemListId());
        List<UserInfoMember> userInfoMembers = userInfoMapper.selectMember(userInfo.getMemberListId());

        ArrayList<JSONObject> jsonWorks = new ArrayList<>();
        ArrayList<JSONObject> jsonStudies = new ArrayList<>();
        ArrayList<JSONObject> jsonItems = new ArrayList<>();
        ArrayList<JSONObject> jsonMembers = new ArrayList<>();
        for (UserInfoWork userInfoWork:userInfoWorks) {
            userInfoWork.setWorkStartDate(userInfoWork.getWorkStartDate().substring(0,10));
            userInfoWork.setWorkEndDate(userInfoWork.getWorkEndDate().substring(0,10));
            jsonWorks.add((JSONObject)JSON.toJSON(userInfoWork));
        }
        for (UserInfoStudy userInfoStudy:userInfoStudies) {
            userInfoStudy.setStudyStartDate(userInfoStudy.getStudyStartDate().substring(0,10));
            userInfoStudy.setStudyEndDate(userInfoStudy.getStudyStartDate().substring(0,10));
            jsonStudies.add((JSONObject)JSON.toJSON(userInfoStudy));
        }
        for (UserInfoItem userInfoItem:userInfoItems) {
            userInfoItem.setItemEndDate(userInfoItem.getItemEndDate().substring(0,10));
            userInfoItem.setItemStartDate(userInfoItem.getItemStartDate().substring(0,10));
            jsonItems.add((JSONObject)JSON.toJSON(userInfoItem));
        }
        for (UserInfoMember userInfoMember:userInfoMembers) {
            userInfoMember.setMemberBrithday(userInfoMember.getMemberBrithday().substring(0,10));
            jsonMembers.add((JSONObject)JSON.toJSON(userInfoMember));
        }
        userInfo.setWorkList(jsonWorks);
        userInfo.setStudyList(jsonStudies);
        userInfo.setItemList(jsonItems);
        userInfo.setMemberList(jsonMembers);
        map.put("data",JSON.toJSON(userInfo));
        //===
        return map;
    }

    /**
     * 根据openid 查询其权限等级
     * */
    @Override
    public String selectUserLevel(String openid) {
        return loginMapper.selectUserLevel(openid);
    }

    /**
     * 根据idcardNo 查询其权限等级
     * */
    @Override
    public String selectUserLevelByIdcardNo(String idcardNo) {
        return loginMapper.selectUserLevelByIdcareNo(idcardNo);
    }

    @Override
    public void updateAccessLevel(String humans) {
        JSONObject humansJsonObject = JSONObject.parseObject(humans);
        String userIdcardNo = humansJsonObject.getObject("userIdcardNo", String.class);
        String editIdcardNo = humansJsonObject.getObject("editIdcardNo", String.class);
        String accessLevel = humansJsonObject.getObject("accessLevel", String.class);


        String userLevel = loginMapper.selectUserLevelByIdcareNo(userIdcardNo);
        String editLevel = loginMapper.selectUserLevelByIdcareNo(editIdcardNo);

        if((Integer.parseInt(userLevel) - Integer.parseInt(editLevel)) < 0 ){
            if((Integer.parseInt(userLevel) - Integer.parseInt(accessLevel)) <0 ){
                loginMapper.updateAccessLeve(editIdcardNo,accessLevel);
            }else{
                throw new RuntimeException("无权限");
            }


        }else{
            throw new RuntimeException("不能修改比自己高或者与自己想同等级的用户");
        }

    }

    @Override
    public List selectStateMsg(String state) {
        if("0".equals(state)){
            return loginMapper.selectStateMsgAll();
        }else if("1".equals(state)||"2".equals(state)){
            return loginMapper.selectStateMsg(state);
        }else {
            throw new RuntimeException();
        }
    }

}
