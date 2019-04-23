package cn.itcast.travel.mapper;

import cn.itcast.travel.pojo.User;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

public interface UserLoginMapper extends Mapper<User> {

    User checkUserName(@Param("userName") String userName);

    User findUserByUnameAndPwd(@Param("userName") String userName, @Param("password") String password);

//    User login(User loginUser);
}
