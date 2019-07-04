package com.yunche.novels.elasticsearch.repository;

import com.yunche.novels.elasticsearch.EsNovel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author yunche
 * @date 2019/03/23
 */
public interface NovelRepository extends ElasticsearchRepository<EsNovel, String> {

}
