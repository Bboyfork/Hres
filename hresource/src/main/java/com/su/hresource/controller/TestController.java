package com.su.hresource.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.su.hresource.entity.*;
import com.su.hresource.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试controller
 * @author tianyu
 * */
@Slf4j
@RestController
@RequestMapping("/TestCon")
public class TestController {

    @RequestMapping("/test01")
    public String test01(){
        return "success";
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    SqlSession sqlSession;

    /**
     * 接口1 test02 数据库连接测试
     * */
    @RequestMapping("/test02")
    public String test02(@RequestBody TestBean testBean){

        /*
        System.out.println(dataSource.getClass());
        try {
            System.out.println(dataSource.getConnection());
        }catch (Exception e){
            e.printStackTrace();
        }
        TestMapper mapper = sqlSession.getMapper(TestMapper.class);
        List<TestBean> testBeans = mapper.selectTest();

        for (TestBean bean:testBeans) {
            System.out.println("test02 -- bean.getAccessLevel() ===>"+bean.getAccessLevel()+"<===");
            System.out.println("test02 -- bean.getIdcardNo() ===>"+bean.getIdcardNo()+"<===");
            System.out.println("test02 -- bean.getOpenid() ===>"+bean.getOpenid()+"<===");
            System.out.println("test02 -- bean.getCreateDate() ===>"+bean.getCreateDate()+"<===");
        }
*/
        /*===================================*/

        String jsonTest = "{\"openid\":\"3362203199601193512\",\"userName\":\"3364556gfdg2203199\",\"idcardNo\":\"3\",\"accessLevel\":\"777\",\"createDate\":\"3\",\"state\":\"3\"}";

//        TestBean testBean1 = JSONObject.parseObject(jsonTest, TestBean.class);
//
//        System.out.println(testBean1.toString());

        byte[] bytes = JSONObject.toJSONBytes(JSONObject.parseObject(jsonTest));

        JSONObject o = JSON.parseObject(bytes, JSONObject.class);

        String s = o.toJSONString();
        System.out.println(s);

        return "success";
    }



    //-----------
    //===================
    //-----------

    @Autowired
    LoginService logInService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    ItemService itemService;

    @Autowired
    MsgService msgService;

    /**
     * 接口2 isOlderWorker
     * 判断是否是老员工，
     * 并判断是否已经注册过，
     * return:
     *   code:200，  //调接口成功与失败的返回
     *   errMsg:"调接口失败", //调用接口错误与成功的提示信息
     *   data：{
     *     isOlderWork:true, // 是否是老员工
     *     isRegister:false  //是否注册过
     *   }
     * */
    @RequestMapping(value = "/isOlderWorker",method = RequestMethod.POST)
    public Map isOlderWorker(@RequestParam String openid
            , @RequestParam String userName, @RequestParam String idcardNo){
        log.info("接口2 isOlderWorker openid=>"+openid+" userName=>"+userName+" idcardNo==>"+idcardNo);
        return logInService.isOlderWorker(openid,userName,idcardNo);
    }

    /**
     * 接口3
     * */
    @RequestMapping(value = "/editOlderWorker", method = RequestMethod.POST)
    public Map editOlderWorker(@RequestParam String openid
            , @RequestParam String userName, @RequestParam String idcardNo){
        log.info("接口3 editOlderWorker openid=>"+openid+" userName=>"+userName+" idcardNo==>"+idcardNo);
        HashMap<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口3");
        try {
            logInService.editOlderWorker(openid,userName,idcardNo);
        }catch (Exception e){
            e.printStackTrace();
            map.put("errCode","0010");
            map.put("errMsg","插入表CORE/INFO出错");
        }
        return map;
    }

    /**
     * 接口4
     * */
    @RequestMapping(value = "/getOpenid",method = RequestMethod.GET)
    public Map getOpenid(@RequestParam String code){
        log.info("接口4 getOpenid code=>"+code);
        return logInService.getOpenid(code);
    }

    /**
     * 接口5
     * 添加新员工 同时应在CORE表中 添加一条
     * */
    @RequestMapping(value = "/newWorkRegister",method = RequestMethod.POST)
    public Map newWorkRegister(@RequestBody UserInfo userInfo){
        log.info("接口5 newWorkRegister userInfo=>"+userInfo.toString());
        HashMap<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口5");

        try {
            logInService.insertInfo(userInfo);
        }catch (Exception e){
            map.put("errCode","0015");
            map.put("errMsg","插入用户信息出错");
            e.printStackTrace();
        }

        log.info(map.toString());
        return map;
    }

    /**
     * 接口6
     * getWorkerList
     * 查询 【在职 不在职 】相应条件的人员<列表> 详细信息
     * */
    @RequestMapping(value = "/getWorkerList",method = RequestMethod.GET)
    public Map getWorkerList(@RequestParam String state){
        log.info("接口6 getWorkerList state=>"+state);
        HashMap<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口6");
        try {
            map.put("workerList",logInService.selectStateMsg(state));
        }catch (RuntimeException e1){
            map.put("errCode","0035");
            map.put("errMsg","传入state不合法");
        }catch (Exception e){
            map.put("errCode","0036");
            map.put("errMsg","查询列表失败");
            e.printStackTrace();
        }
        log.info(map.toString());
        return map;
    }


    /**
     * 接口7
     * 查询本人登记信息
     * */
    @RequestMapping(value = "/getMyRegisterInfo",method = RequestMethod.GET)
    public Map getMyRegisterInfo(@RequestParam String openid){
        log.info("接口7 getMyRegisterInfo openid=>"+openid);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口7");
        try {
            map = logInService.getMyRegisterInfo(map, openid);
            log.info("接口7："+map);
            return map;
        }catch (Exception e){
            map.put("errCode","0018");
            map.put("errMsg","查询本人登记信息失败");
            log.info("接口7："+map);
            e.printStackTrace();
            return map;
        }
    }


    /**
     * 接口8 向资源池插入人员信息及详细
     * 人力资源池开始===========================@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)===========
     * */
    @RequestMapping(value = "/resourceRegister",method = RequestMethod.POST)
    public Map resourceRegister(@RequestBody ResourceInfo resourceInfo){
        log.info("接口8 resourceRegister resourceInfo=>"+resourceInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("interface","接口8");
        try {
            resourceService.insertResourceInfo(resourceInfo);
        }catch (Exception e){
            e.printStackTrace();
            map.put("errCode","0020");
            map.put("errMsg","插入Info出错");
        }
        log.info(map.toString());
        return map;
    }

    /**
     * 接口9
     * getResourceList
     * 获取资源池列表 即获取人员列表
     * */
    @RequestMapping(value = "/getResourceList",method = RequestMethod.GET)
    public Map getResourceList(@RequestParam String isEntrance){
        log.info("接口9 getResourceList isEntrance=>"+isEntrance);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口9");
        try {
            map.put("resourceList",resourceService.selectResourceInfoUserMsgList(isEntrance));
        }catch (RuntimeException e1){
            map.put("errCode","0025");
            map.put("errMsg",e1.getMessage());
        }catch (Exception e){
            map.put("errCode","0026");
            map.put("errMsg","查询资源池人员列表失败");
            e.printStackTrace();
        }
        log.info(map.toString());
        return map;
    }


    /**
     * 接口10 getJurisdiction
     * 获取权限
     * */
    @RequestMapping(value = "/getJurisdiction",method = RequestMethod.GET)
    public Map getJurisdiction(@RequestParam String openid){
        log.info("接口10 getJurisdiction openid=>"+openid);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口10");
        try {
            map.put("accessLevel",logInService.selectUserLevel(openid));
        }catch (Exception e){
            map.put("errCode","0028");
            map.put("errMsg","查询用户等级出错");
        }
        log.info(map.toString());
        return map;
    }


    /**
     * 接口11 newItem 新增项目
     *
     * 此接口 添加一个将项目审批为正在进行的service
     * 留待以后有了代办事项 有了审批之后再分开
     * */
    @RequestMapping(value = "/newItem",method = RequestMethod.POST)
    public Map newItem(@RequestBody Item item){
        log.info("接口11 newItem item=>"+item);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口11");

        try {
            itemService.newItem(item);
        }catch (Exception e){
            map.put("errCode","0032");
            map.put("errMsg","新建项目出错");
        }
        log.info(map.toString());
        return map;
    }

    /**
     * 接口12
     * 查询项目列表 getItemList
     * */
    @RequestMapping(value = "/getItemList",method = RequestMethod.GET)
    public Map getItemList(@RequestParam String type, String openid, String idcardNo){
        log.info("接口12 getItemList type=>"+type+" openid==>"+openid +" idcardNo==>" + idcardNo);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口12");

        try {
            map.put("itemList",itemService.getItemList(type, openid, idcardNo));
        }catch (RuntimeException e1){
            map.put("errCode","0039");
            map.put("errMsg",e1.getMessage());
        }catch (Exception e){
            map.put("errCode","0040");
            map.put("errMsg","查询项目列表失败");
            e.printStackTrace();
        }
        log.info(map.toString());
        return map;
    }

    /**
     * 接口13
     * updateItem 修改项目信息【同步修改 项目的结束时间 到 人员的出场时间】
     * */
    @RequestMapping(value = "/updateItem",method = RequestMethod.POST)
    public Map updateItem(@RequestBody Item item){
        log.info("接口13 updateItem item=>"+item);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口13");
        try {
            itemService.updateItem(item);
        }catch (RuntimeException e1){
            map.put("errCode","0045");
            map.put("errMsg",e1.getMessage());
        }catch (Exception e){
            map.put("errCode","0046");
            map.put("errMsg","更新SU_ITEM失败");
            e.printStackTrace();
        }
        log.info("接口13"+map.toString());
        return map;
    }

    /**
     * 接口14
     * 创建项目成员【成员入场(添加成员信息到项目的附属子表里)、(更改成员的入场状态)】
     * */
    @RequestMapping(value = "/projectMembersInfo",method = RequestMethod.POST)
    Map projectMembersInfo(@RequestBody List<ItemMember> list){
        log.info("接口14 projectMembersInfo list=>"+list);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口14");
        try {
            itemService.createItemMember(list);
        }catch (Exception e){
            e.printStackTrace();
            map.put("errCode","0049");
            map.put("errMsg","批量创建项目成员失败");
        }

        log.info("接口14:"+map);
        return map;
    }

    /**
     * 接口15
     * getProjectMembersInfo 查询项目成员详细信息
     * */
    @RequestMapping(value = "getProjectMembersInfo",method = RequestMethod.GET)
    public Map getProjectMembersInfo(@RequestParam String itemId){
        log.info("接口15 getProjectMembersInfo itemId=>"+itemId);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口15");
        try {
            Map map1 = itemService.selectItemMember(itemId);
            map.put("membersInfo",map1.get("membersInfo"));
            map.put("itemMsg",map1.get("itemMsg"));
        }catch (Exception e){
            e.printStackTrace();
            map.put("errCode","0051");
            map.put("errMsg","查询项目成员列表失败");
        }
        log.info("接口15:"+map);
        return map;
    }

    /**
     * 接口16
     * updateProjectMembersInfo 修改项目成员的信息 (单条)
     * */
    @RequestMapping(value = "/updateProjectMembersInfo", method = RequestMethod.POST)
    public Map updateProjectMembersInfo(@RequestBody ItemMember itemMember){
        log.info("接口16 updateProjectMembersInfo itemMember=>"+itemMember);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口16");
        try {
            //1 入场 2出场
            String imType = itemMember.getImType();
            itemService.updateItemMember(itemMember);

            if("1".equals(imType)){
                //正常修改 不做额外处理
            }else if("2".equals(imType)){
                //生成代办

                //明天再搞吧
            }else {
                throw new RuntimeException("传入出入场imType类型有误");
            }

        }catch(RuntimeException e1){
            map.put("errCode","0052");
            map.put("errMsg",e1.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            map.put("errCode","0053");
            map.put("errMsg","更新人员"+itemMember.getImIdcardNo()+"信息失败");
        }
        log.info("接口16:"+map);
        return map;
    }

    /**
     * 接口17 查询 资源池信息
     * getMembersResourceInfo
     * */
    @RequestMapping(value = "/getMembersResourceInfo",method = RequestMethod.GET)
    public Map getMembersResourceInfo(@RequestParam String openid){
        log.info("接口17 getMembersResourceInfo openid=>"+openid);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口17");
        try {
            map.put("resourceDate",resourceService.selectResourceInfo(openid));
        }catch (Exception e){
            e.printStackTrace();
            map.put("errCode","0053");
            map.put("errMsg","查询资源池信息失败");
        }
        log.info("接口17:"+map);
        return map;
    }

    /**
     * 接口18
     * addResourceItem 添加资源池中个人项目经验
     * */
    @RequestMapping(value = "/addResourceItem",method = RequestMethod.POST)
    public Map addResourceItem(@RequestBody List<ResourceInfoItem> itemList){
        log.info("接口18 addResourceItem itemList=>"+itemList);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口18");
        try {
            for (ResourceInfoItem resourceInfoItem:itemList) {
                if("".equals(resourceInfoItem.getIdcardNo())|| null == resourceInfoItem.getIdcardNo()){
                    throw new RuntimeException("idcardNo 不可为空");
                }
                resourceService.insertResourceInfoItem(resourceInfoItem);
            }
        }catch (RuntimeException e1){
            e1.printStackTrace();
            map.put("errCode","0055");
            map.put("errMsg",e1.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            map.put("errCode","0056");
            map.put("errMsg","添加项目经验失败");
        }
        log.info("接口18:"+map);
        return map;
    }


    /**
     * 接口19 根据权限等级 获取其能处理的代办事项
     * */
    @RequestMapping(value = "/getBacklogList",method = RequestMethod.GET)
    public Map getBacklogList(@RequestParam String idcardNo,@RequestParam String type){
        log.info("接口19 getBacklogList 查询资源池 by idcardNo=>"+idcardNo);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口19");
        try {
            String accessLevel = logInService.selectUserLevelByIdcardNo(idcardNo);
            map.put("backlogList",msgService.selectMsgByMsPower(accessLevel, type));
        }catch (RuntimeException e1){
            map.put("errCode","0104");
            map.put("errMsg",e1.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            map.put("errCode","0105");
            map.put("errMsg","获取代办列表失败");
        }
        log.info("接口19:"+map);
        return map;
    }


    /**
     * 接口22
     * 获取项目的信息(单条)
     * */
    @RequestMapping(value = "/getItemInfo",method = RequestMethod.GET)
    public Map getItemInfo(@RequestParam String itemId){
        log.info("接口22 getItemInfo itemId=>"+itemId);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口22");
        try {
            map.put("itemInfo",itemService.getItemOne(itemId));
        }catch (Exception e){
            e.printStackTrace();
            map.put("errCode","0070");
            map.put("errMsg","查询项目失败");
        }

        log.info("接口22:"+map);
        return map;
    }


    /**
     * 接口23 资源池人员出场
     * outResource
     * */
    @RequestMapping(value = "/outResource",method = RequestMethod.GET)
    public Map outResource(@RequestParam String idcardNo,@RequestParam String imNo){
        log.info("接口23 outResource idcardNo=>"+idcardNo + " imNo==>" +imNo);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口23");
        try {
            resourceService.updateResourceEntrance(idcardNo,imNo);
        } catch (Exception e){
            map.put("errCode","0080");
            map.put("errMsg","人员出场失败");
        }
        log.info("接口23:"+map);
        return map;
    }

    /**
     * updateResourceRegister
     * 接口24 修改资源池信息
     * */
    @RequestMapping(value = "/updateResourceRegister",method = RequestMethod.POST)
    public Map updateResourceRegister(@RequestBody ResourceInfo resourceInfo){
        log.info("接口24 updateResourceRegister resourceInfo=>"+resourceInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口24");

        try {
            resourceService.updateResourceInfo(resourceInfo);
        }catch (RuntimeException e){
            e.printStackTrace();
            map.put("errCode","0085");
            map.put("errMsg","修改资源池信息失败："+e.getMessage());
        }
        log.info("接口24:"+map);
        return map;
    }

    /**
     * getResourceInfo
     * 查询资源池 by idcardNo
     * 接口25
     * */
    @RequestMapping(value = "/getResourceInfo",method = RequestMethod.GET)
    public Map getResourceInfo(@RequestParam String idcardNo){
        log.info("接口25 getResourceInfo 查询资源池 by idcardNo=>"+idcardNo);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口25");
        try {
            map.put("resourceDate",resourceService.selectResourceInfoByIdcard(idcardNo));
        }catch (Exception e){
            e.printStackTrace();
            map.put("errCode","0090");
            map.put("errMsg","byIdcard查询资源池信息失败");
        }
        log.info("接口25:"+map);
        return map;
    }

    /**
     * 接口26 
     * 修改人员权限等级
     * */
    @RequestMapping(value = "/editJurisdiction",method = RequestMethod.POST)
    public Map editJurisdiction(@RequestBody String humans){
        log.info("接口26 editJurisdiction humans==>"+humans);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口26");
        
        try {
            logInService.updateAccessLevel(humans);
        }catch (RuntimeException e1){
            map.put("errCode","0120");
            map.put("errMsg",e1.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            map.put("errCode","0121");
            map.put("errMsg",e.getMessage());
        }
        log.info("接口26:"+map);
        return map;

    }


    /**
     * 接口27
     * 编辑人力资源池项目经验
     * */
    @RequestMapping(value = "/editResourceItem",method = RequestMethod.POST)
    public Map editResourceItem(@RequestBody ResourceInfoItem resourceInfoItem){
        log.info("接口27 editResourceItem resourceInfoItem==>"+resourceInfoItem);
        Map<String, Object> map = new HashMap<>();
        map.put("errCode","0");
        map.put("errMsg","");
        map.put("interface","接口27");
        try {
            resourceService.updateResourceItem(resourceInfoItem);
        }catch (Exception e){
            e.printStackTrace();
            map.put("errCode","0124");
            map.put("errMsg","修改出错 请注意检查传入数据，itemNo&idcardNo是判断必传项");
        }
        log.info("接口27:"+map);
        return map;
    }



}
