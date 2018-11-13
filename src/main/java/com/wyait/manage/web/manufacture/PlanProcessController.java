package com.wyait.manage.web.manufacture;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manufacture")
public class PlanProcessController {

    @RequestMapping("/planProcess")
    public String toWorkStation(){
        return "/manufacture/planProcess";
    }
}
