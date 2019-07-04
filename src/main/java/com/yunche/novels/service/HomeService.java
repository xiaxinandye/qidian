package com.yunche.novels.service;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.mapper.NovelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface HomeService {

    /**
     * 返回月票总榜前10
     * @return
     */
    List<Novel> getTop10ByTM();

    /**
     * 返回推荐票总榜前10
     * @return
     */
    List<Novel> getTop10ByRM();
}
