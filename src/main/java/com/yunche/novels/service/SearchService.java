package com.yunche.novels.service;

import com.yunche.novels.elasticsearch.EsNovel;
import com.yunche.novels.elasticsearch.repository.NovelRepository;
import com.yunche.novels.mapper.NovelMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author yunche
 * @date 2019/03/24
 */
public interface SearchService {

    /**
     * 返回搜索结果的总条数
     * @param kw
     * @return
     */
    int getCount(String kw);

    /**
     * 返回搜索结果的页数
     * @param kw
     * @return
     */
     int getPageSize(String kw);


    /**
     * 根据关键字和页数返回搜索结果
     * @param kw
     * @param nowPage
     * @return
     */
    List<EsNovel> search(String kw, Integer nowPage);
}
