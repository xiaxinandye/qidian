package com.yunche.novels.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: PageHelper
 * @Description: 自定义分页条实现
 * @author: yunche
 * @date: 2019/02/21
 */
public class PageHelper {
    /**
     * 返回存储有分页Bar的的list
     * @param page 当前请求页
     * @param pageCount 分页总页数
     * @return
     */
    public static List<String> getPageBarList(int page, int pageCount) {
        List<String> pageBar = new LinkedList<>();
        int start = page - 2 < 2 ? 2 : page -2;
        int end = page + 2 > pageCount - 1  ? pageCount -1: page + 2;
        if (end - start < 3) {
            if (pageCount - end <3) {
                start -= 3 - (end -start);
                start = start < 2 ? 2 : start;
                end = pageCount - 1;
            } else {
                end += 3 - (end - start);
            }
        }
        pageBar.add("?page=1");
        if (start != 2) {
            pageBar.add("?=...");
        }
        for (int i = start; i <= end; i++) {
            pageBar.add("?page="+i);
        }
        if (end != pageCount -1) {
            pageBar.add("?=...");
        }
        pageBar.add("?page="+pageCount);
        return pageBar;
    }
}
