package cn.itcast.travel.mapper;


import cn.itcast.travel.pojo.User;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

public interface UserRegisterMapper extends Mapper<User> {
    //    检验用户名是否已经存在
    //User queryUserByUserName(@Param("username") String username);

    //添加用户
//    void addUser(User registerUser);


//    User queryUserByUserCode(String code);

//    void activeBySetStatus(String code);

}
