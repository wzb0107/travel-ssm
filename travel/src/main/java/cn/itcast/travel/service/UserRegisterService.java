package cn.itcast.travel.service;

import cn.itcast.travel.pojo.User;

public interface UserRegisterService {

    /**
     * 添加用户
     *
     * @param registerUser
     * @throws Exception
     */
    void addUser(User registerUser) throws Exception;

    /**
     * 激活邮箱
     *
     * @param code
     * @return
     */
    boolean active(String code);

}
