package cn.itcast.travel.service.impl;

import cn.itcast.travel.exception.UserExistsException;
import cn.itcast.travel.exception.UserNameOrPasswordErrorException;
import cn.itcast.travel.exception.UserNoActiveException;
import cn.itcast.travel.mapper.UserLoginMapper;
import cn.itcast.travel.pojo.User;
import cn.itcast.travel.service.UserLoginService;
import cn.itcast.travel.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * author: wzb
 * Date: 2019/4/2 0002
 * Time: 9:46
 */
@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserLoginMapper userLoginMapper;

    @Override
    public boolean checkUserName(String userName) {
        User user = userLoginMapper.checkUserName(userName);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean findUserByUnameAndPwd(String userName, String password) {
        User user = userLoginMapper.findUserByUnameAndPwd(userName, password);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public User login(User loginUser) throws Exception {
        loginUser.setPassword(Md5Util.encodeByMd5(loginUser.getPassword()));
        // User queryUser = userLoginMapper.login(loginUser);
        User queryUser = userLoginMapper.selectOne(loginUser);
        if (queryUser == null) {
            throw new UserNameOrPasswordErrorException("用户名或密码错误");
        }

        if (queryUser.getStatus().equals("N")) {
            throw new UserNoActiveException("邮箱尚未激活");
        }
        return queryUser;
    }
}
