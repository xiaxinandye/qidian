package com.yunche.novels.service;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.mapper.NovelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RankService {

    /**
     * 返回月票总榜前10
     *
     * @return
     */
    List<Novel> getTop10ByTM();

    /**
     * 返回推荐票总榜前10
     *
     * @return
     */
    public List<Novel> getTop10ByRM();

    /**
     * 返回对应种类的月票前10
     *
     * @param cid 分类id
     * @return
     */
    List<Novel> getTop10ByCatTM(Integer cid);

    /**
     * 返回对应种类的推荐票前10
     *
     * @param cid 分类id
     * @return
     */
    List<Novel> getTop10ByCatRM(Integer cid);

    /**
     * 返回所有小说分页后的分页数
     *
     * @return
     */
    int getPageCount();

    /**
     * 得到所有小说按照推荐票排行降序的一定数量小说
     *
     * @param start
     * @return
     */
    List<Novel> getNovelByRM(Integer start);

    /**
     * 按照相应分类下的推荐票数量，从 start 处返回 10 条记录
     *
     * @param start
     * @param categoryId
     * @return
     */
    List<Novel> getNovelByCatRM(Integer start, Integer categoryId);
}
