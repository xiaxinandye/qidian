package com.yunche.novels.service.impl;

import com.yunche.novels.bean.Chapter;
import com.yunche.novels.mapper.ChapterMapper;
import com.yunche.novels.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    public ChapterMapper chapterMapper;

    /**
     * 返回指定小说id的所有章节并按照卷名分组
     * @param nid 小说id
     * @return
     */
    public List<List<Chapter>> getChaptersGroupByVolume(String nid) {
        List<String> volumeName = chapterMapper.getVolumeNameByNovId(nid);
        //将作品相关的这一卷放在最前面
        int pos = volumeName.indexOf("作品相关");
        if (pos != -1) {
            volumeName.remove(pos);
            List<String> tmp = new LinkedList<>();
            tmp.add("作品相关");
            for (String vname : volumeName) {
                tmp.add(vname);
            }
            volumeName = tmp;
            tmp = null;
        }

        List<List<Chapter>> volumeList = new LinkedList<>();
        for(String vname : volumeName) {
            volumeList.add(chapterMapper.getChaptersByNIdAndVloNmae(nid, vname));
        }
        return volumeList;
    }

    /**
     * 通过小说id和卷名返回该卷的章节数
     * @param nid 小说id
     * @param volumeName 卷名
     * @return
     */
    public int getChapterCountByVolume(String nid, String volumeName) {
        return chapterMapper.getChapterCountByNovAndVol(nid, volumeName);
    }

    /**
     * 通过小说id和卷名返回该卷的总字数
     * @param nid 小说id
     * @param volumeName 卷名
     * @return
     */
    public int getWordCountByVolume(String nid, String volumeName) {
        return chapterMapper.getWordCountByNovAndVol(nid, volumeName);
    }

    /***
     * 通过章节的id返回该章节
     * @param cid 章节id
     * @return
     */
    public Chapter getContentById(String cid) {
        return chapterMapper.getChapterContentById(cid);
    }

    /**
     * 返回当前章节id后的10章的章节
     * @param nid 小说id
     * @param cid 当前章节id
     * @return
     */
    public List<Chapter> get10ChpaterAfterNow(String nid, String cid) {
        String volumeName= chapterMapper.getVolumeNameById(cid);
        if ("作品相关".equals(volumeName)) {
            List<Chapter> chapters = chapterMapper.getVolumeNamedAout(nid, cid);
            chapters.add(chapterMapper.getNovelStartChaId(nid));
            return chapters;
        } else {
            return chapterMapper.get10ChapterIdAfterId(nid, cid);
        }
    }
}
