package com.wyait.manage.web.basic;

import com.wyait.manage.entity.basic.ProcedureSearchDTO;
import com.wyait.manage.service.basic.ProcedureService;
import com.wyait.manage.utils.PageDataResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/basic")
public class ProcedureController {
    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);

    @Autowired
    private ProcedureService procedureService;

    @RequestMapping("/procedure")
    public String toWorkStation(){
        return "/basic/procedure";
    }

    @RequestMapping(value = "/getProcedures", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "procedure")
    public PageDataResult getProcedures(@RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit,
                                        ProcedureSearchDTO searchDTO){
        logger.debug("分页查询工序列表！搜索条件：searchDTO：" + searchDTO + ",page:" + page
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
            pdr = procedureService.getProcedures(page, limit, searchDTO);
            logger.debug("工序列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("工序列表查询异常！", e);
        }
        return pdr;
    }
}
