package com.yunche.novels.mapper;

import com.yunche.novels.bean.BookShelf;
import com.yunche.novels.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author yunche
 * @date 2019/04/05
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(user_name, password) VALUES(#{userName}, #{password}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer storeUser(User user);

    @Select("SELECT COUNT(*) FROM user where user_name=#{name}")
    Integer checkUserNameIsExists(String name);

    @Select("SELECT COUNT(*) FROM user where user_name=#{username} and password=#{password}")
    Integer validate(@Param("username") String username, @Param("password") String password);

    @Select("SELECT id, user_name, password FROM user where user_name = #{username}")
    User loadUserByName(String username);

    @Select("SELECT COUNT(*) FROM bookshelf where user_id=#{userId} AND novel_id=#{nid}")
    Integer isExistNidInBookShelf(@Param("userId") Integer userId, @Param("nid") String nid);

    @Update("UPDATE bookshelf SET chapter_id=#{lastCid}, read_time=#{readTime} WHERE user_id=#{uid} AND novel_id=#{nid}")
    Integer updateLastReadInBookShelf(@Param("uid") Integer uid, @Param("nid") String nid, @Param("lastCid") String lastCid, @Param("readTime") Date readTime);

    @Insert("INSERT INTO bookshelf(user_id,novel_id,chapter_id, read_time) VALUES(#{uid},#{nid},#{startChapterId}, #{readTime})")
    void addBookToShelf(@Param("uid") Integer uid, @Param("nid") String nid, @Param("startChapterId") String startChapterId, @Param("readTime") Date readTime);

    @Select("SELECT * FROM bookshelf WHERE user_id = #{uid}")
    List<BookShelf> getBookShelf(Integer uid);

    @Delete("DELETE FROM bookshelf WHERE user_id=#{uid} AND novel_id=#{nid}")
    void removeBookFromShelf(@Param("uid") Integer uid, @Param("nid") String nid);

    @Select("SELECT * FROM bookshelf WHERE user_id = #{uid} AND novel_id=${nid}")
    BookShelf getTheBookInShelf(@Param("uid") Integer uid, @Param("nid") String nid);
}
