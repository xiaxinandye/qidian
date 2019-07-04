package com.yunche.novels.controller;

import com.yunche.novels.bean.BookShelf;
import com.yunche.novels.bean.Chapter;
import com.yunche.novels.bean.ChapterShow;
import com.yunche.novels.bean.Novel;
import com.yunche.novels.service.CategoryService;
import com.yunche.novels.service.ChapterService;
import com.yunche.novels.service.NovelService;
import com.yunche.novels.service.UserService;
import com.yunche.novels.service.impl.CategoryServiceImpl;
import com.yunche.novels.service.impl.ChapterServiceImpl;
import com.yunche.novels.service.impl.NovelServiceImpl;
import com.yunche.novels.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: NovelController
 * @Description:
 * @author: yunche
 * @date: 2019/02/22
 */
@Controller
public class NovelController {

    @Autowired
    public NovelServiceImpl novelService;
    @Autowired
    public ChapterServiceImpl chapterService;
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/info/{nid}")
    public String detail(@PathVariable("nid") String id, Map<String, Object> map, HttpSession session, Principal principal) {
        //分情况，是三方登录，还是本地登录
        if(session.getAttribute("user") != null) {
           if (userService.isExistNidInBookShelf((String) session.getAttribute("user"), id) ) { //存在
               String username = (String) session.getAttribute("user");
               BookShelf book = userService.getTheBookInShelf(userService.getUserByName(username).getId(), id);
               map.put("book", book);
               map.put("bookshelf", "exist");
           }
        }
        else if(principal != null){ // 本地登录
            String username = principal.getName();
            if (userService.isExistNidInBookShelf(username, id) ) { //存在
                BookShelf book = userService.getTheBookInShelf(userService.getUserByName(username).getId(), id);
                map.put("book", book);
                map.put("bookshelf", "exist");
            }
        }
        Novel novel = novelService.getNovelById(id);
        String categoryName = (String) CategoryServiceImpl.map.get(novel.getCategoryId());
        String wordCounts = "";
        try{
            wordCounts = String.format("%.2f", (double) novel.getWordCounts() / 10000);
        }
        catch (NullPointerException e) {
            wordCounts = "0.00";
        }
        List<List<Chapter>> cs = chapterService.getChaptersGroupByVolume(id);

        List<ChapterShow> chapterShowList = new LinkedList<>();
        ChapterShow chapterShow = null;
        for (List<Chapter> volume : cs) {
            String volumeName = volume.get(0).getVolumeName();
            int chapterCount = chapterService.getChapterCountByVolume(id, volumeName);
            int volumeWordCount = chapterService.getWordCountByVolume(id, volumeName);
            String volumeInfo = volumeName + "<i>·</i>共" + chapterCount + "章<em class='count ml-2'>本卷共<cite>" +
                    volumeWordCount + "</cite>字</em>";
            chapterShow = new ChapterShow(volume, volumeInfo);
            chapterShowList.add(chapterShow);
        }

        map.put("description", "<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;简介：<span/>" + novel.getDescription());
        map.put("wordCounts", wordCounts + "<cite>万字</cite>");
        map.put("chapterCounts", novel.getChapterCounts() + "<cite>章</cite>");
        map.put("tm", novel.getTicketsMonth() + "<cite>月票</cite>");
        map.put("rm", novel.getTicketsRecommend() + "<cite>周推荐</cite>");
        map.put("cname", categoryName);
        map.put("novel", novel);
        map.put("cs", cs);
        map.put("start", novelService.getStartChapterId(id));
        map.put("volumes", chapterShowList);
        return "novel/detail";
    }

    @RequestMapping("/info/{nid}/{cid}")
    public String chapter(@PathVariable("nid") String nid, @PathVariable("cid") String cid, Map<String, Object> map, HttpSession session, Principal principal) {
        //分情况，是三方登录，还是本地登录
        if(session.getAttribute("user") != null) {
            if (userService.isExistNidInBookShelf((String) session.getAttribute("user"), nid) ) { //存在
                map.put("bookshelf", "exist");
            }
        }
        else if(principal != null){ // 本地登录
            String username = principal.getName();
            if (userService.isExistNidInBookShelf(username, nid) ) { //存在
                map.put("bookshelf", "exist");
            }
        }

        Chapter c = chapterService.getContentById(cid);
        if (c == null) {
            //跳转到错误页面
            return "error/404";
        }
        map.put("c", c);
        map.put("nid", nid);
        return "chapter/content";
    }

    /**
     * 将当前章节的后面10章转变为json格式输出
     * @param nid
     * @param cid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/chapterajax/{nid}/{cid}")
    public List<Chapter> get10ChpaterByAjax(@PathVariable("nid") String nid, @PathVariable("cid") String cid) {
        List<Chapter> chapterList = chapterService.get10ChpaterAfterNow(nid,cid);
        return chapterList;
    }
}
