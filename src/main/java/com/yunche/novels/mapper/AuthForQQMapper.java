package com.yunche.novels.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @author yunche
 * @date 2019/04/05
 */
@Mapper
public interface AuthForQQMapper {

    /**
     * 检查该openId是否已经注册过
     * @param openId
     * @return
     */
    @Select("SELECT COUNT(*) FROM oauth_detail WHERE open_id=#{openId} and app_type='qq'")
    Integer checkIsExists(String openId);

    /**
     * 存储该OpenId
     * @param openId
     * @param userId
     * @return
     */
    @Insert("INSERT INTO oauth_detail(open_id, app_type, user_id, status, create_time) VALUES(#{openId},'qq',#{userId},1,#{createTime})")
    Integer storeOpenIdByUser(@Param(value = "openId") String openId, @Param(value = "userId") Integer userId, @Param(value = "createTime") Date createTime);

    @Select("SELECT user_name FROM user, oauth_detail WHERE user_id=user.id AND open_id = #{openId}")
    String getUserNameByOpenId(String openId);
}
