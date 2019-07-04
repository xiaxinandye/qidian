package com.yunche.novels.controller;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.bean.NovelShow;
import com.yunche.novels.service.CategoryService;
import com.yunche.novels.service.RankService;
import com.yunche.novels.service.impl.CategoryServiceImpl;
import com.yunche.novels.service.impl.RankServiceImpl;
import com.yunche.novels.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RankController
 * @Description:
 * @author: yunche
 * @date: 2019/02/21
 */
@Controller
public class RankController {

    @Autowired
    public RankServiceImpl rankService;
    @Autowired
    public CategoryServiceImpl categoryService;

    @RequestMapping("/rank")
    public String rank(Map<String, Object> map, @RequestParam(value = "cid", required = false) Integer cid) {
        List<Novel> tm = null;
        List<Novel> rm = null;
        if (cid == null || cid < 0) {
            tm = rankService.getTop10ByTM();
            rm = rankService.getTop10ByRM();
            cid = -1;
        } else {
            tm = rankService.getTop10ByCatTM(cid);
            rm = rankService.getTop10ByCatRM(cid);
        }
        if (tm.isEmpty() || rm.isEmpty()) {
            tm = rankService.getTop10ByTM();
            rm = rankService.getTop10ByRM();
            cid = -1;
        }
        map.put("cid", cid);
        map.put("tm", tm);
        map.put("rm", rm);
        return "rank/rank";
    }

    @RequestMapping("/rank/yuepiao")
    public String ticketsMonth(Map<String, Object> map, @RequestParam(value = "cid", required = false) Integer cid, @RequestParam(value = "page", required = false) Integer page) {
        List<Novel> tm = null;
        List<String> pageBar = null;
        if (cid == null || cid < 0) {
            cid = -1;
            if (page == null) {
                page = 1;
            }
            int pageCount = rankService.getPageCount();
            page = page < 1 ? 1 : page;
            page = page > pageCount ? pageCount: page;
            pageBar = PageHelper.getPageBarList(page, pageCount);
            int offset = (page - 1) * 10;
            tm = categoryService.getNovelByTM(offset);
        } else {
            if (page == null) {
                page = 1;
            }
            int pageCount = categoryService.getPageCount(cid);
            page = page < 1 ? 1 : page;
            page = page > pageCount ? pageCount: page;
            pageBar = PageHelper.getPageBarList(page, pageCount);
            int offset = (page - 1) * 10;
            tm = categoryService.getNovelByTM(offset, cid);
        }
        List<NovelShow> novelShows = new LinkedList<>();
        for (Novel novel : tm) {
            novelShows.add(categoryService.getNovelShow(novel.getId()));
        }

        map.put("title", CategoryServiceImpl.map.get(cid));
        map.put("shows", novelShows);
        map.put("pageBar", pageBar);
        map.put("nowPage", page.toString());
        map.put("nowPageInt", page);
        map.put("tm", tm);
        map.put("cid", cid);
        return "rank/tickets_month";
    }

    @RequestMapping("/rank/recom")
    public String ticketsRecommend(Map<String, Object> map, @RequestParam(value = "cid", required = false) Integer cid, @RequestParam(value = "page", required = false) Integer page) {

        List<Novel> tm = null;
        List<String> pageBar = null;
        if (cid == null || cid < 0) {
            cid = -1;
            if (page == null) {
                page = 1;
            }
            int pageCount = rankService.getPageCount();
            page = page < 1 ? 1 : page;
            page = page > pageCount ? pageCount: page;
            pageBar = PageHelper.getPageBarList(page, pageCount);
            int offset = (page - 1) * 10;
            tm = rankService.getNovelByRM(offset);
        } else {
            if (page == null) {
                page = 1;
            }
            int pageCount = categoryService.getPageCount(cid);
            page = page < 1 ? 1 : page;
            page = page > pageCount ? pageCount: page;
            pageBar = PageHelper.getPageBarList(page, pageCount);
            int offset = (page - 1) * 10;
            tm = rankService.getNovelByCatRM(offset, cid);
        }
        List<NovelShow> novelShows = new LinkedList<>();
        for (Novel novel : tm) {
            novelShows.add(categoryService.getNovelShow(novel.getId()));
        }

        map.put("title", CategoryServiceImpl.map.get(cid));
        map.put("shows", novelShows);
        map.put("pageBar", pageBar);
        map.put("nowPage", page.toString());
        map.put("nowPageInt", page);
        map.put("tm", tm);
        map.put("cid", cid);
        return "rank/tickets_recommend";
    }

}
