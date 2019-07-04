package com.yunche.novels.service.impl;

import com.yunche.novels.bean.BookShelf;
import com.yunche.novels.bean.User;
import com.yunche.novels.mapper.UserMapper;
import com.yunche.novels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author yunche
 * @date 2019/04/05
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public boolean IsExistsName(String userName) {
        return userMapper.checkUserNameIsExists(userName) > 0;
    }

    /**
     * 用户是否注册成功
     * @param user
     * @return
     */
    public boolean insertUser(User user) {
        return userMapper.storeUser(user) > 0;
    }

    /**
     * 验证用户的用户名和密码是否匹配
     * @param username
     * @param password
     * @return
     */
    public boolean validate(String username, String password) {
        return userMapper.validate(username, password) > 0;
    }

    public User getUserByName(String username) {
        return userMapper.loadUserByName(username);
    }

    /**
     * 判断用户的书架中是否存在该书籍
     * @param userName 用户名
     * @param nid 小说id
     * @return
     */
    public boolean isExistNidInBookShelf(String userName, String nid) {
        int userId = getUserByName(userName).getId();
        return userMapper.isExistNidInBookShelf(userId,  nid) > 0;
    }

    /**
     * 更新用户中指定书籍的最新阅读进度
     * @param userName 用户名
     * @param nid 小说id
     * @param lastCid 最新阅读章节id
     * @param readTime 最新阅读时间
     * @return
     */
    public void updateLastReadInBookShelf(String userName, String nid, String lastCid, Date readTime) {
        Integer uid = getUserByName(userName).getId();
        userMapper.updateLastReadInBookShelf(uid, nid, lastCid, readTime);
    }

    /**
     * 书架添加书籍
     * @param username 用户名
     * @param nid 小说id
     * @param startChapterId 开始的章节id
     */
    public void insertBookToShelf(String username, String nid, String startChapterId) {
        Integer uid = getUserByName(username).getId();
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        userMapper.addBookToShelf(uid, nid, startChapterId, timeStamp);
    }

    /**
     * 返回指定用户的书架
     * @param uname 用户名
     * @return
     */
    public List<BookShelf> getBookShelf(String uname) {
        Integer uid = getUserByName(uname).getId();
        return userMapper.getBookShelf(uid);
    }

    public void removeBookFromShelf(String username, String nid) {
        Integer uid = getUserByName(username).getId();
        userMapper.removeBookFromShelf(uid, nid);
    }

    /**
     * 返回用户书架中某一书籍的阅读情况
     * @param uid
     * @param nid
     * @return
     */
    public BookShelf getTheBookInShelf(Integer uid, String nid) {
        return userMapper.getTheBookInShelf(uid, nid);
    }
}
