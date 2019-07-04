package com.yunche.novels.controller;

import com.yunche.novels.elasticsearch.EsNovel;
import com.yunche.novels.service.SearchService;
import com.yunche.novels.service.impl.SearchServiceImpl;
import com.yunche.novels.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author yunche
 * @date 2019/03/24
 */
@Controller
public class SearchController {

    @Autowired
    private SearchServiceImpl searchService;

    @GetMapping("/search")
    public String search(@RequestParam(value = "kw", required = false) String kw,  @RequestParam(value = "page", required = false) Integer page, Map<String, Object> esNovel) {
        if (StringUtils.isEmpty(kw)) {
            return "search/search";
        }
        if (page == null) {
            page = 1;
        }
        int pageCount = searchService.getPageSize(kw);
        if(pageCount == 0) {
            return "search/noresult";
        }
        page = page < 1 ? 1 : page;
        page = page > pageCount ? pageCount: page;
        List<String> pageBar = null;
        pageBar = PageHelper.getPageBarList(page, pageCount);
        List<EsNovel> esNovelList = searchService.search(kw, page);

        esNovel.put("novels", esNovelList);
        esNovel.put("pageBar", pageBar);
        esNovel.put("nowPage", page.toString());
        esNovel.put("nowPageInt", page);
        esNovel.put("kw", kw);
        return "search/search";
    }
}
