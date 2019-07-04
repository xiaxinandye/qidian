package com.yunche.novels.mapper;

import com.yunche.novels.bean.Novel;
import com.yunche.novels.bean.NovelShow;
import com.yunche.novels.elasticsearch.EsNovel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName: NovelMapper
 * @Description: 小说Mapper
 * @author: yunche
 * @date: 2019/02/16
 */
@Mapper
public interface NovelMapper {

    /**
     * 按月票降序排列并从 offset 处取出一定数量的记录
     *
     * @param offset 开始的偏移量从 0 开始
     * @return
     */
    @Select("SELECT * FROM novel ORDER BY tickets_month DESC LIMIT #{offset},10")
    List<Novel> getNovByTMDesc(Integer offset);

    /**
     * 按月票降序排列并从 offset 处取出一定数量的记录
     *
     * @param offset 开始的偏移量从 0 开始
     * @return
     */
    @Select("SELECT * FROM novel ORDER BY tickets_recommend DESC LIMIT #{offset},10")
    List<Novel> getNovByRMDesc(Integer offset);

    /**
     * 按小说的种类和月票降序排列并从 offset 处取出 10 条记录
     *
     * @param offset 开始的偏移量从 0 开始
     * @param cid    小说种类
     * @return
     */
    @Select("SELECT * FROM novel WHERE category_id=#{cid} ORDER BY tickets_month DESC LIMIT #{offset},10")
    List<Novel> getNovByTMCatDesc(@Param(value = "offset") Integer offset, @Param(value = "cid") Integer cid);

    /**
     * 按小说的种类和推荐票降序排列并从 offset 处取出 10 条记录
     *
     * @param offset 开始的偏移量从 0 开始
     * @param cid    小说种类
     * @return
     */
    @Select("SELECT * FROM novel WHERE category_id=#{cid} ORDER BY tickets_recommend DESC LIMIT #{offset},10")
    List<Novel> getNovByRMCatDesc(@Param(value = "offset") Integer offset, @Param(value = "cid") Integer cid);

    /**
     * 返回对应小说的最近更新章节和更新时间
     * @param id 小说id
     * @return
     */
    @Select("SELECT name AS last_chapter_name, create_datetime As last_update, id AS last_chapter_id FROM chapter WHERE novel_id = #{id} ORDER BY create_datetime DESC LIMIT 1")
    NovelShow getNovShow(String id);

    /**
     * 返回对应分类的小说数量
     * @param id 分类id
     * @return
     */
    @Select("SELECT COUNT(*) FROM novel WHERE category_id=#{id}")
    int getNovCountByCategory(Integer id);

    /**
     * 返回月票总榜前10
     * @return
     */
    @Select("SELECT * FROM novel ORDER BY tickets_month DESC LIMIT 10")
    List<Novel> getTop10ByTM();

    /**
     * 返回推荐票总榜前10
     * @return
     */
    @Select("SELECT * FROM novel ORDER BY tickets_recommend DESC LIMIT 10")
    List<Novel> getTop10ByRM();

    /**
     * 返回所有的小说总数
     * @return
     */
    @Select("SELECT COUNT(*) FROM novel")
    int getAllNovCount();

    @Select("SELECT * FROM novel where id = #{id}")
    Novel getNovelById(String id);

    /**
     * 返回小说开始阅读的章节id
     * @param nid 小说id
     * @return
     */
    @Select("SELECT id FROM chapter WHERE novel_id = #{nid} AND volume_name != '作品相关' ORDER BY create_datetime LIMIT 1")
    String getNovelStartChaId(String nid);

    /**
     * 获取所有ES小说信息
     * @return
     */
    @Select("SELECT id, name, author, description, cover_image coverImage FROM novel")
    List<EsNovel> getEsNovel();
}
