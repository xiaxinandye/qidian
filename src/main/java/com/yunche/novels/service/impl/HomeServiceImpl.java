package com.yunche.novels.service.impl;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.mapper.NovelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl {
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
}
