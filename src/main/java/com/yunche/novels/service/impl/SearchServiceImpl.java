package com.yunche.novels.service.impl;

import com.yunche.novels.elasticsearch.EsNovel;
import com.yunche.novels.elasticsearch.repository.NovelRepository;
import com.yunche.novels.service.SearchService;
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
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private NovelRepository novelRepository;

    @Autowired
    private ElasticsearchTemplate template;

    /**
     * 返回搜索结果的总条数
     * @param kw
     * @return
     */
    public int getCount(String kw) {
        QueryBuilder builder = QueryBuilders.multiMatchQuery(kw, "name", "author", "description");
        Iterable<EsNovel> searchIt = novelRepository.search(builder);
        int count = 0;
        Iterator<EsNovel> iterator = searchIt.iterator();
        while (iterator.hasNext()) {
            count++;
            iterator.next();
        }
        return count;
    }

    /**
     * 返回搜索结果的页数
     * @param kw
     * @return
     */
    public int getPageSize(String kw) {
        int pageCount  = (int)Math.ceil((double)getCount(kw) / (double)10);
        return pageCount;
    }


    /**
     * 根据关键字和页数返回搜索结果
     * @param kw
     * @param nowPage
     * @return
     */
    public List<EsNovel> search(String kw, Integer nowPage) {
        QueryBuilder builder = QueryBuilders.multiMatchQuery(kw, "name", "author", "description");
        PageRequest pageable = PageRequest.of(nowPage - 1, 10);
        //构造高亮信息
        HighlightBuilder.Field highlightName = new HighlightBuilder.Field("name");
        highlightName.preTags("<span style='color:red'>");
        highlightName.postTags("</span>");
        HighlightBuilder.Field highlightDescription = new HighlightBuilder.Field("description");
        highlightDescription.preTags("<span style='color:red'>");
        highlightDescription.postTags("</span>");

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
                        esNovel.setDescription(highlightFields.get("description").getFragments()[0].toString());
                    }
                    esNovelList.add(esNovel);
                }
                return new AggregatedPageImpl(esNovelList, pageable, esNovelList.size());
            }
        });
        return page.getContent();
    }
}
