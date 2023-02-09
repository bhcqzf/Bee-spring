package com.fastyw.bee.dao;


import com.fastyw.bee.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;



@Mapper
@Repository
public interface UserDao {
    // 用于添加用户
    @Insert("insert into user(openId,nick,avatarUrl,gender) value(#{openId},#{nick},#{avatarUrl},#{gender})")
    int insertUser(User user);

    // 用于删除用户
    int deleteUser(Long userId);

    // 用于更新用户
    int updateUser(User user);

    // 用于查询用户
    @Select("select * from user where openId = #{openId}")
    User getUser(String openId);

    //查询用户是否存在
    @Select("select count(0) from user where openId = #{openId}")
    Integer existUser(String openId);

    //根据openid设置手机号

}


