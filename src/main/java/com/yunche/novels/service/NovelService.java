package com.yunche.novels.service;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.mapper.NovelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface NovelService {

    Novel getNovelById(String id);

    String getStartChapterId(String nid);
}
