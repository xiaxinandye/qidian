package com.yunche.novels.service;

import com.yunche.novels.bean.BookShelf;
import com.yunche.novels.bean.User;
import com.yunche.novels.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author yunche
 * @date 2019/04/05
 */
public interface UserService {
    boolean IsExistsName(String userName);

    /**
     * 用户是否注册成功
     *
     * @param user
     * @return
     */
    boolean insertUser(User user);

    /**
     * 验证用户的用户名和密码是否匹配
     *
     * @param username
     * @param password
     * @return
     */
    boolean validate(String username, String password);

    User getUserByName(String username);

    /**
     * 判断用户的书架中是否存在该书籍
     *
     * @param userName 用户名
     * @param nid      小说id
     * @return
     */
    boolean isExistNidInBookShelf(String userName, String nid);

    /**
     * 更新用户中指定书籍的最新阅读进度
     *
     * @param userName 用户名
     * @param nid      小说id
     * @param lastCid  最新阅读章节id
     * @param readTime 最新阅读时间
     * @return
     */
    void updateLastReadInBookShelf(String userName, String nid, String lastCid, Date readTime);

    /**
     * 书架添加书籍
     *
     * @param username       用户名
     * @param nid            小说id
     * @param startChapterId 开始的章节id
     */
    void insertBookToShelf(String username, String nid, String startChapterId);

    /**
     * 返回指定用户的书架
     *
     * @param uname 用户名
     * @return
     */
    List<BookShelf> getBookShelf(String uname);

    void removeBookFromShelf(String username, String nid);

    /**
     * 返回用户书架中某一书籍的阅读情况
     *
     * @param uid
     * @param nid
     * @return
     */
    BookShelf getTheBookInShelf(Integer uid, String nid);
}
