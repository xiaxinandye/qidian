package com.yunche.novels.elasticsearch.service;

import com.yunche.novels.elasticsearch.EsNovel;
import com.yunche.novels.elasticsearch.repository.NovelRepository;
import com.yunche.novels.mapper.NovelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author yunche
 * @date 2019/03/23
 */
@Service
public class ScheduleService {

    @Autowired
    private NovelRepository novelRepository;
    @Autowired
    private NovelMapper novelMapper;

    /**
     * cron 表达式时间的设置分为 6 个部分：
     * second(秒), minute（分）, hour（时）, day of month（日）, month（月）, day of week（周几）.每个部分以空格分隔。
     * 更新文档
     */
    @Scheduled(cron = "0 0 4 * * *") // 代表每天的4点执行
    public void insertNovelDocument() {
        List<EsNovel> esNovels = novelMapper.getEsNovel();
        novelRepository.saveAll(esNovels);
    }
}
