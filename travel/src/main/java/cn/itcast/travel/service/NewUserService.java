package cn.itcast.travel.service;

import cn.itcast.travel.pojo.User;

public interface NewUserService {
    User queryUserById(Integer uid);


    User queryUserByUserName(String userName);

    User queryUserByName(String name);

    boolean saveUser(User user);

    Boolean saveU(User user);

    boolean updateUserById(Integer uid,String name);

    Boolean deleteUserById(Integer uid);
}
