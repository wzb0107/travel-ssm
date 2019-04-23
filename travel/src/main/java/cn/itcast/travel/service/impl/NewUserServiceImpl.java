package cn.itcast.travel.service.impl;

import cn.itcast.travel.mapper.NewUserMapper;
import cn.itcast.travel.pojo.User;
import cn.itcast.travel.service.NewUserService;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * author: wzb
 * Date: 2019/4/6 0006
 * Time: 19:43
 */

@Service("newUserService")
public class NewUserServiceImpl implements NewUserService {

    @Autowired
    private NewUserMapper userMapper;

    @Override
    public User queryUserById(Integer uid) {
        User user = new User();
        user.setUid(uid);
        return userMapper.selectOne(user);
    }

    @Override
    public User queryUserByUserName(String userName) {
        User user = new User();
        user.setUserName(userName);
        return userMapper.selectOne(user);
    }

    @Override
    public User queryUserByName(String name) {
        User user = new User();
        user.setName(name);
        return userMapper.selectOne(user);
    }

    @Override
    public boolean saveUser(User user) {
        int i = userMapper.insert(user);
        return i == 1;
    }

    @Override
    public Boolean saveU(User user) {
        int i = userMapper.insertSelective(user);
        return i == 1;
    }

    @Override
    public boolean updateUserById(Integer uid, String name) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid);
        User user = new User();
        user.setName(name);
        int i = userMapper.updateByExampleSelective(user, example);
        return i == 1;
    }

    @Override
    public Boolean deleteUserById(Integer uid) {
        User user = new User();
        user.setUid(uid);
        int delete = userMapper.delete(user);
        return delete == 1;
    }
}
