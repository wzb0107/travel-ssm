package cn.itcast.travel.service;

import cn.itcast.travel.pojo.User;

public interface UserLoginService {
    boolean checkUserName(String userName);

    boolean findUserByUnameAndPwd(String userName, String password);


    User login(User loginUser) throws Exception;

}
