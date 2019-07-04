package com.yunche.novels.service;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.bean.NovelShow;
import com.yunche.novels.mapper.NovelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CategoryService {

    /**
     * 得到所有小说按照月票排行降序的一定数量小说
     * @param start
     * @return
     */
    List<Novel> getNovelByTM(Integer start);

    /**
     * 按照相应分类下的月票数量，从 start 处返回 10 条记录
     * @param start
     * @param categoryId
     * @return
     */
     List<Novel> getNovelByTM(Integer start, Integer categoryId);

    /**
     * 返回对应小说的最近更新章节的名字和id和更新时间
     * @param id 小说id
     * @return
     */
     NovelShow getNovelShow(String id);

    /**
     * 依据对应分类的小说数量返回页数
     * @param id 分类id
     * @return
     */
    int getPageCount(Integer id);

}
