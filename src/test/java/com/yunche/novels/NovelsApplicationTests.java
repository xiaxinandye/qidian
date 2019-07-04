package com.yunche.novels;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.bean.NovelShow;
import com.yunche.novels.mapper.NovelMapper;
import com.yunche.novels.service.CategoryService;
import com.yunche.novels.service.RankService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NovelsApplicationTests {
    @Autowired
    NovelMapper novelMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    RankService rankService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getNovel() {
        List<Novel> list = novelMapper.getNovByTMDesc(0);
        for (Novel n : list) {
            System.out.println(n.getName());
        }
    }

    /**
     * 测试按某一类别的月票返回10条数据
     */
    @Test
    public void getNovelByTMAndCat() {
        List<Novel> list = novelMapper.getNovByTMCatDesc(0, 9);
        for(Novel n : list) {
            System.out.println(n.getName() + ": " + n.getTicketsMonth());
        }
    }

    @Test
    public void getShowNovel() {
        NovelShow novelShow = new NovelShow();
        novelShow = novelMapper.getNovShow("107580");
        System.out.println(novelShow);
    }

    /**
     * 测试对应分类的小说的分页数量
     */
    @Test
    public void getCountByCategory(){
        System.out.println(categoryService.getPageCount(22));
        System.out.println(Math.ceil((double)41 / (double)10));
    }

    /**
     * 测试月票榜前十
     */
    @Test
    public void getTop10ByTM() {
        List<Novel> list = novelMapper.getTop10ByTM();
        for (Novel n : list) {
            System.out.println(n);
        }
    }

    /**
     * 测试推荐榜前10
     */
    @Test
    public void getTop10ByRM() {
        List<Novel> list = novelMapper.getTop10ByRM();
        for (Novel n : list) {
            System.out.println(n);
        }
    }

    @Test
    public void getAllPageCount() {
        System.out.println(rankService.getPageCount());
    }

    /**
     * 测试通过小说id返回小说
     */
    @Test
    public void getNovelById() {
        Novel novel = novelMapper.getNovelById("1001827159");
        System.out.println(novel);

    }

    @Test
    public void wordsCount() {
        Novel novel = novelMapper.getNovelById("1001827159");
        System.out.println(novel.getWordCounts());
    //    String wordsCount = String.format("%.2f", (double) novel.getWordsCount() / 10000);
      //  System.out.println(wordsCount);
    }
}

