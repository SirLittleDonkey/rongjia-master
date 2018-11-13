package com.wyait.manage.web.manufacture;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manufacture")
public class QualityController {
    @RequestMapping("/quality")
    public String toWorkStation(){
        return "/manufacture/quality";
    }
}
