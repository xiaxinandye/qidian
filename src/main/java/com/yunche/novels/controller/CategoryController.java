package com.yunche.novels.controller;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.bean.NovelShow;
import com.yunche.novels.service.CategoryService;
import com.yunche.novels.service.impl.CategoryServiceImpl;
import com.yunche.novels.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CategoryController
 * @Description:
 * @author: yunche
 * @date: 2019/02/16
 */
@Controller
public class CategoryController {
    @Autowired
    private CategoryServiceImpl service;


    @GetMapping("/category/{id}")
    public String category(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, Map<String, Object> category) {

        if (page == null) {
            page = 1;
        }
        int pageCount = service.getPageCount(id);
        page = page < 1 ? 1 : page;
        page = page > pageCount ? pageCount: page;
        List<String> pageBar = null;
        pageBar = PageHelper.getPageBarList(page, pageCount);
        int offset = (page - 1) * 10;
        List<Novel> novelList = service.getNovelByTM(offset, id);
        List<NovelShow> novelShows = new LinkedList<>();
        for (Novel novel : novelList) {
            NovelShow novelShow = service.getNovelShow(novel.getId());
            novelShows.add(novelShow);
        }

        category.put("title", CategoryServiceImpl.map.get(id));
        category.put("novels", novelList);
        category.put("shows", novelShows);
        category.put("pageBar", pageBar);
        category.put("nowPage", page.toString());
        category.put("nowPageInt", page);
        return "category/list";
    }


}
