package com.yunche.novels.service.impl;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.mapper.NovelMapper;
import com.yunche.novels.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: RankService
 * @Description:
 * @author: yunche
 * @date: 2019/02/22
 */
@Service
public class RankServiceImpl implements RankService {
    @Autowired
    public NovelMapper novelMapper;
    /**
     * 返回月票总榜前10
     * @return
     */
    public List<Novel> getTop10ByTM() {
        return novelMapper.getTop10ByTM();
    }

    /**
     * 返回推荐票总榜前10
     * @return
     */
    public List<Novel> getTop10ByRM() {
        return novelMapper.getTop10ByRM();
    }

    /**
     * 返回对应种类的月票前10
     * @param cid 分类id
     * @return
     */
    public List<Novel> getTop10ByCatTM(Integer cid) {
        return novelMapper.getNovByTMCatDesc(0, cid);
    }

    /**
     * 返回对应种类的推荐票前10
     * @param cid 分类id
     * @return
     */
    public List<Novel> getTop10ByCatRM(Integer cid) {
        return  novelMapper.getNovByRMCatDesc(0, cid);
    }

    /**
     * 返回所有小说分页后的分页数
     * @return
     */
    public int getPageCount() {
        int count =  novelMapper.getAllNovCount();
        int pageCount  = (int)Math.ceil((double)count / (double)10);
        return pageCount;
    }

    /**
     * 得到所有小说按照推荐票排行降序的一定数量小说
     * @param start
     * @return
     */
//    @Cacheable(cacheNames = {"rankRM"})
    public List<Novel> getNovelByRM(Integer start) {
        List<Novel> list = novelMapper.getNovByRMDesc(start);
        return list;
    }

    /**
     * 按照相应分类下的推荐票数量，从 start 处返回 10 条记录
     * @param start
     * @param categoryId
     * @return
     */
//    @Cacheable(cacheNames = {"rankRMCat"})
    public List<Novel> getNovelByCatRM(Integer start, Integer categoryId) {
        List<Novel> list = novelMapper.getNovByRMCatDesc(start, categoryId);
        return list;
    }
}
