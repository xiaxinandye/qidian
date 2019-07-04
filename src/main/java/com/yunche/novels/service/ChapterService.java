package com.yunche.novels.service;

import com.yunche.novels.bean.Chapter;
import com.yunche.novels.mapper.ChapterMapper;
import jdk.nashorn.internal.ir.LiteralNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface ChapterService {

    /**
     * 返回指定小说id的所有章节并按照卷名分组
     * @param nid 小说id
     * @return
     */
    List<List<Chapter>> getChaptersGroupByVolume(String nid);

    /**
     * 通过小说id和卷名返回该卷的章节数
     * @param nid 小说id
     * @param volumeName 卷名
     * @return
     */
    int getChapterCountByVolume(String nid, String volumeName);

    /**
     * 通过小说id和卷名返回该卷的总字数
     * @param nid 小说id
     * @param volumeName 卷名
     * @return
     */
    int getWordCountByVolume(String nid, String volumeName);

    /***
     * 通过章节的id返回该章节
     * @param cid 章节id
     * @return
     */
    Chapter getContentById(String cid);

    /**
     * 返回当前章节id后的10章的章节
     * @param nid 小说id
     * @param cid 当前章节id
     * @return
     */
    List<Chapter> get10ChpaterAfterNow(String nid, String cid);

}
