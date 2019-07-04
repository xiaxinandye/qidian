package com.yunche.novels.service.impl;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.bean.NovelShow;
import com.yunche.novels.mapper.NovelMapper;
import com.yunche.novels.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private NovelMapper novelMapper;
    public static Map<Integer, Object> map;

    static {
        map = new HashMap<>();
        map.put(1, "奇幻");
        map.put(10, "悬疑灵异");
        map.put(12, "二次元");
        map.put(2, "武侠");
        map.put(21, "玄幻");
        map.put(22, "仙侠");
        map.put(4, "都市");
        map.put(5, "历史");
        map.put(6, "军事");
        map.put(7, "游戏");
        map.put(8, "体育");
        map.put(9, "科幻");
        map.put(-1, "全部");
    }
    /**
     * 得到所有小说按照月票排行降序的一定数量小说
     * @param start
     * @return
     */
//    @Cacheable(cacheNames = {"rank"})
    public List<Novel> getNovelByTM(Integer start) {
        List<Novel> list = novelMapper.getNovByTMDesc(start);
        return list;
    }

    /**
     * 按照相应分类下的月票数量，从 start 处返回 10 条记录
     * @param start
     * @param categoryId
     * @return
     */
//    @Cacheable(cacheNames = {"category"})
    public List<Novel> getNovelByTM(Integer start, Integer categoryId) {
        List<Novel> list = novelMapper.getNovByTMCatDesc(start, categoryId);
        return list;
    }


    /**
     * 返回对应小说的最近更新章节的名字和id和更新时间
     * @param id 小说id
     * @return
     */
    @Cacheable(cacheNames = {"novelShow"})
    public NovelShow getNovelShow(String id) {
        NovelShow novelShow = novelMapper.getNovShow(id);

        //判断排行榜上的小说是否已经存入了章节信息
        if (novelShow == null) {
            novelShow = new NovelShow();
            novelShow.setLastChapterId("0");
            novelShow.setLastChapterName("暂无最新章节");
            novelShow.setLastUpdate("暂无最新更新时间");
        }
        return  novelShow;
    }

    /**
     * 依据对应分类的小说数量返回页数
     * @param id 分类id
     * @return
     */
    public int getPageCount(Integer id) {
        int count =  novelMapper.getNovCountByCategory(id);
        int pageCount  = (int)Math.ceil((double)count / (double)10);
        return pageCount;
    }

}
