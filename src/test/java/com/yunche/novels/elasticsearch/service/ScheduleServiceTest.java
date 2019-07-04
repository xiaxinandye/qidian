package com.yunche.novels.elasticsearch.service;

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
import org.junit.Test;
import org.junit.runner.RunWith;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.Field;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {
    @Autowired
    private NovelRepository novelRepository;
    @Autowired
    private NovelMapper novelMapper;
    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void insertNovelDocument() {
        List<EsNovel> esNovels = novelMapper.getEsNovel();
        novelRepository.saveAll(esNovels);
        System.out.println("Ok");
    }

    @Test
    public void search() {

        QueryBuilder builder = QueryBuilders.multiMatchQuery("圣墟", "name", "author", "description");
        PageRequest pageable = PageRequest.of(0, 10);
        //构造高亮信息
        HighlightBuilder.Field highlightName = new HighlightBuilder.Field("name");
        highlightName.preTags("<em style='color:red'>");
        highlightName.postTags("</em>");
        HighlightBuilder.Field highlightDescription = new HighlightBuilder.Field("description");
        highlightDescription.preTags("<em style='color:red'>");
        highlightDescription.postTags("</em>");

        SearchQuery searchQuery =new NativeSearchQueryBuilder().withQuery(builder).withPageable(pageable).withHighlightFields(highlightName, highlightDescription).build();
        AggregatedPage<EsNovel> page = template.queryForPage(searchQuery, EsNovel.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<EsNovel> esNovelList = new ArrayList<>();
                if (response.getHits().getHits().length <= 0) {
                    return null;
                }
                for(SearchHit hit : response.getHits().getHits()) {
                    EsNovel esNovel = new EsNovel();
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();

                    try {
                        BeanUtils.populate(esNovel, sourceAsMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();

                    if(!StringUtils.isEmpty(highlightFields.get("name"))) {
                        esNovel.setName(highlightFields.get("name").getFragments()[0].toString());
                    }
                    if(!StringUtils.isEmpty(highlightFields.get("description"))) {
                        esNovel.setName(highlightFields.get("description").getFragments()[0].toString());
                    }
                    esNovelList.add(esNovel);
                }
                return new AggregatedPageImpl(esNovelList, pageable, esNovelList.size());
            }
        });

        for(EsNovel novel: page.getContent()) {
            System.out.println(novel);
        }
    }
}