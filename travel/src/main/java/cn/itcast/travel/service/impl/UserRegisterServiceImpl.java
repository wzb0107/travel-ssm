package cn.itcast.travel.service.impl;

import cn.itcast.travel.exception.UserExistsException;
import cn.itcast.travel.mapper.UserRegisterMapper;
import cn.itcast.travel.pojo.User;
import cn.itcast.travel.service.UserRegisterService;
import cn.itcast.travel.utils.MailUtil;
import cn.itcast.travel.utils.Md5Util;
import cn.itcast.travel.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * author: wzb
 * Date: 2019/4/1 0001
 * Time: 10:20
 */

@Service("userService")
public class UserRegisterServiceImpl implements UserRegisterService {
    @Autowired
    private UserRegisterMapper userRegisterMapper;


    @Override
    public void addUser(User registerUser) throws Exception {
        // User queryUser = userRegisterMapper.queryUserByUserName(registerUser.getUserName());
        User user = new User();
        user.setUserName(registerUser.getUserName());
        User queryUser = userRegisterMapper.selectOne(user);
        if (queryUser != null) {
            throw new UserExistsException("用户名已存在");
        }
        registerUser.setStatus("N");
        registerUser.setCode(UuidUtil.getUuid());
        registerUser.setPassword(Md5Util.encodeByMd5(registerUser.getPassword()));
        String content = "<a href='http://localhost:8888/user/activeMail?code=" + registerUser.getCode() + "'>点击激活【黑马旅游网】</a>";
        MailUtil.sendMail(registerUser.getEmail(), content, "ItHeiMaTravel");
//        userRegisterMapper.addUser(registerUser);
        userRegisterMapper.insert(registerUser);
    }

    /**
     * 邮箱激活
     *
     * @param code
     * @return
     */

    @Override
    public boolean active(String code) {
        if (StringUtils.isNotEmpty(code)) {
//           User user = userRegisterMapper.queryUserByUserCode(code);
            User user_ = new User();
            user_.setCode(code);
            User user = userRegisterMapper.selectOne(user_);
            if (user != null) {
                if (user.getStatus().equals("N")) {
//           userRegisterMapper.activeBySetStatus(user.getCode());
                    user.setStatus("Y");
                    userRegisterMapper.updateByPrimaryKey(user);
                    return true;
                }
            }
        }
        return false;
    }

}
