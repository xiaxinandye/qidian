package com.yunche.novels.controller;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.service.HomeService;
import com.yunche.novels.service.impl.HomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: HomeController
 * @Description:
 * @author: yunche
 * @date: 2019/02/21
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
public class HomeController {

    @Autowired
    public HomeServiceImpl homeService;

    @RequestMapping({"/", "/index.html", "/index"})
    public String homePage(Map<String, Object> map) {
        List<Novel> top10TM = homeService.getTop10ByTM();
        List<Novel> top10RM = homeService.getTop10ByRM();
        map.put("tm", top10TM);
        map.put("rm", top10RM);
        return "index";
    }
}
