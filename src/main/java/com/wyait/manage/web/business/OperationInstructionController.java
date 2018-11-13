package com.wyait.manage.web.business;

import com.wyait.manage.web.basic.WorkStationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/business")
public class OperationInstructionController {
    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);

    @RequestMapping("/operationInstruction")
    public String toWorkStation(){
        return "/business/operationInstruction";
    }
}
