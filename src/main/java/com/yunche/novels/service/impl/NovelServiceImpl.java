package com.yunche.novels.service.impl;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.mapper.NovelMapper;
import com.yunche.novels.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NovelServiceImpl implements NovelService {
    @Autowired
    public NovelMapper novelMapper;

    public Novel getNovelById(String id) {
        return novelMapper.getNovelById(id);
    }

    public String getStartChapterId(String nid) {
        return novelMapper.getNovelStartChaId(nid);
    }
}
