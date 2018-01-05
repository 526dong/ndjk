package com.ndjk.manage.brandservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 品牌服务controller
 * Created by zfwlz on 2017/12/17.
 */
@Controller
public class BrandServcieController {

    @RequestMapping(value = "/api/test")
    @ResponseBody
    public String Test(){
        return "1232131";
    }
}
