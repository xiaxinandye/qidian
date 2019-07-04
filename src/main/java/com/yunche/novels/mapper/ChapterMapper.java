package com.yunche.novels.mapper;

import com.yunche.novels.bean.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName: ChapterMapper
 * @Description:
 * @author: yunche
 * @date: 2019/02/23
 */
@Mapper
public interface ChapterMapper {

    /**
     * 通过小说id，获取小说所有的卷名字并按照创建日期升序排列
     * @param nid
     * @return
     */
    @Select("SELECT volume_name FROM chapter WHERE novel_id =#{nid} GROUP BY volume_name ORDER BY create_datetime")
    List<String> getVolumeNameByNovId(String nid);

    @Select("SELECT * FROM chapter WHERE novel_id=#{nid}")
    List<Chapter> getChaptersByNovId(String nid);

    /**
     * 通过小说的id和卷名并按照创建日期升序返回该卷的所有章节
     * @param nid
     * @param vname
     * @return
     */
    @Select("SELECT * FROM chapter WHERE novel_id=#{nid} AND volume_name=#{vname} ORDER BY create_datetime")
    List<Chapter> getChaptersByNIdAndVloNmae(@Param(value = "nid") String nid , @Param(value = "vname") String vname);

    /**
     * 通过小说的id和卷名返回对应卷的章节数
     * @param nid 小说id
     * @param vname 卷名
     * @return
     */
    @Select("SELECT COUNT(*) FROM chapter WHERE novel_id=#{nid} AND volume_name=#{vname}")
    int getChapterCountByNovAndVol(@Param(value = "nid") String nid , @Param(value = "vname") String vname);

    /**
     * 通过小说的id和卷名返回对应卷的总字数
     * @param nid 小说id
     * @param vname 卷名
     * @return
     */
    @Select("SELECT SUM(word_counts) FROM chapter WHERE novel_id=#{nid} AND volume_name=#{vname}")
    int getWordCountByNovAndVol(@Param(value = "nid") String nid , @Param(value = "vname") String vname);

    /**
     * 通过章节的id返回该章节
     * @param id
     * @return
     */
    @Select("SELECT * FROM chapter WHERE id = #{id}")
    Chapter getChapterContentById(String id);

    /**
     * 根据小说当前的章节id返回后面10条章节
     * @param nid 当前小说id
     * @param cid 当前章节id
     * @return
     */
    @Select("SELECT * FROM chapter WHERE volume_name!= '作品相关' AND novel_id=#{nid} AND id>#{cid} ORDER BY create_datetime LIMIT 10")
    List<Chapter> get10ChapterIdAfterId(@Param(value = "nid") String nid, @Param(value = "cid") String cid);

    /**
     * 通过章节id返回卷名
     * @param id 章节id
     * @return
     */
    @Select("SELECT volume_name FROM chapter where id = #{id}")
    String getVolumeNameById(String id);

    /**
     * 返回剩下的作品相关的章节
     * @param nid
     * @param cid
     * @return
     */
    @Select("SELECT * FROM chapter WHERE volume_name= '作品相关' AND novel_id=#{nid} AND id>#{cid} ORDER BY create_datetime")
    List<Chapter> getVolumeNamedAout(@Param(value = "nid") String nid, @Param(value = "cid") String cid);

    /**
     * 返回小说正文的第一章
     * @param nid 小说id
     * @return
     */
    @Select("SELECT * FROM chapter WHERE novel_id = #{nid} AND volume_name != '作品相关' ORDER BY create_datetime LIMIT 1")
    Chapter getNovelStartChaId(String nid);
}
