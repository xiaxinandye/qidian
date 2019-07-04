package com.yunche.novels.util;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class MD5UtilsTest {

    @Test
    public void getMD5() {
        String s = "sadfadfawer,中阿法";
        System.out.println(MD5Utils.getMD5(s));
        System.out.println(MD5Utils.getMD5(s).length());
    }

    @Test
    public void datetime() {
//        java.util.Date date = new java.util.Date();          // 获取一个Date对象
//        Timestamp timeStamp = new Timestamp(date.getTime());
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(123);
    }
}