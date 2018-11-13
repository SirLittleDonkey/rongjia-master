package com.wyait.manage.web.basic;

import com.wyait.manage.entity.UserRolesVO;
import com.wyait.manage.entity.basic.WorkStationSearchDTO;
import com.wyait.manage.entity.basic.WorkStationVO;
import com.wyait.manage.pojo.Role;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.basic.WorkStation;
import com.wyait.manage.service.basic.WorkStationService;
import com.wyait.manage.utils.PageDataResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class WorkStationController {

    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);

    @Autowired
    private WorkStationService workStationService;

    @RequestMapping("/workStation")
    public String toWorkStation(){
        return "/basic/workStation";
    }

    /**
     * 分页查询工位列表
     */
    @RequestMapping(value = "/getWorkStations", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "workStation")
    public PageDataResult getWorkStations(@RequestParam("page") Integer page,
                                          @RequestParam("limit") Integer limit, WorkStationSearchDTO workStationSearchDTO){
        logger.debug("分页查询工位列表！搜索条件：userSearch：" + workStationSearchDTO + ",page:" + page
                + ",每页记录数量limit:" + limit);
        PageDataResult pdr = new PageDataResult();
        try{
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            //获取工位列表
            pdr = workStationService.getWorkStations(page, limit, workStationSearchDTO);
            logger.debug("工位列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("工位列表查询异常！", e);
        }
        return pdr;
    }

    /**
     * 删除工位
     * @return ok/fail
     */
    @RequestMapping(value = "/delWorkStation", method = RequestMethod.POST)
    @ResponseBody
    public String delWorkStation(@RequestParam("id") Integer id) {
        logger.debug("删除工位！id:" + id );
        String msg = "";
        try {
            if (null == id ) {
                logger.debug("删除工位，结果=请求参数有误，请您稍后再试");
                return "请求参数有误，请您稍后再试";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("删除工位，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 删除工位
            msg = workStationService.setDelWorkStation(id, 1);
            logger.info("删除工位:" + msg + "！workStation=" + id + "，操作用户id:"
                    + existUser.getId() );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除用户异常！", e);
            msg = "操作异常，请您稍后再试";
        }
        return msg;
    }

    /**
     *
     * @描述：恢复工位
     * @创建人：钱康
     * @创建时间：2018年4月27日 上午9:49:14
     * @param id
     * @return
     */
    @RequestMapping(value = "/revoverWorkStation", method = RequestMethod.POST)
    @ResponseBody
    public String recoverUser(@RequestParam("id") Integer id) {
        logger.debug("恢复工位！id:" + id );
        String msg = "";
        try {
            if (null == id ) {
                logger.debug("恢复工位，结果=请求参数有误，请您稍后再试");
                return "请求参数有误，请您稍后再试";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("恢复工位，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 删除工位
            msg = workStationService.setDelWorkStation(id, 0);
            logger.info("恢复工位:" + msg + "！workStation=" + id + "，操作用户id:"
                    + existUser.getId() );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除用户异常！", e);
            msg = "操作异常，请您稍后再试";
        }
        return msg;
    }

    /**
     * 查询工位数据
     * @return map
     */
    @RequestMapping(value = "/getWorkStation", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserAndRoles(@RequestParam("id") Integer id) {
        logger.debug("查询工位数据！id:" + id);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == id) {
                logger.debug("查询用户数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            // 查询工位
            WorkStationVO workStationVO = workStationService.getWorkStation(id);
            logger.debug("查询用户数据！urvo=" + workStationVO);
            if (null != workStationVO) {
                map.put("workStation", workStationVO);
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "查询用户信息有误，请您稍后再试");
            }
            logger.debug("查询用户数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "查询用户错误，请您稍后再试");
            logger.error("查询用户数据异常！", e);
        }
        return map;
    }

    /**
     * 设置工位[新增或更新]
     * @return ok/fail
     */
    @RequestMapping(value = "/setWorkStation", method = RequestMethod.POST)
    @ResponseBody
    public String setWorkStation(WorkStation workStation) {
        logger.debug("设置工位[新增或更新]！workStation:" + workStation);
        try {
            if (null == workStation) {
                logger.debug("设置工位[新增或更新]，结果=请您填写用户信息");
                return "请您填写工位信息";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("设置工位[新增或更新]，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 设置用户[新增或更新]
            logger.info("设置工位[新增或更新]成功！workStation=" + workStation
                    + "，操作的用户ID=" + existUser.getId());
            return workStationService.setWorkStation(workStation);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置用户[新增或更新]异常！", e);
            return "操作异常，请您稍后再试";
        }
    }
}
