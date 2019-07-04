package com.yunche.novels.bean;

import java.util.List;

/**
 * @ClassName: ChapterShow
 * @Description: 用于在小说详细页时显示相应的信息
 * @author: yunche
 * @date: 2019/02/23
 */
public class ChapterShow {
    private List<Chapter> volume;
    private String volumeInfo;

    public List<Chapter> getVolume() {
        return volume;
    }

    public ChapterShow(List<Chapter> volume, String volumeInfo) {
        this.volume = volume;
        this.volumeInfo = volumeInfo;
    }

    public void setVolume(List<Chapter> volume) {
        this.volume = volume;
    }

    public String getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(String volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}
