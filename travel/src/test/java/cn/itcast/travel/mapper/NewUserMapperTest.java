package cn.itcast.travel.mapper;


import cn.itcast.travel.pojo.User;
import cn.itcast.travel.utils.UuidUtil;
import com.github.abel533.entity.Example;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext*.xml")
public class NewUserMapperTest {
    @Autowired
    private NewUserMapper newUserMapper;

    /**
     * 由于该方法时查询一条记录的方法，返回值只有一个user。
     * 因此在设置查询条件的时候，只能设置一个唯一的不会重复的查询条件。
     */

    @Test
    public void selectOne() {
        User user = new User();
        user.setUid(9);
        User queryUser = newUserMapper.selectOne(user);
        System.out.println(queryUser);
    }


    /*

     * 如果条件为null或者是一个空的new User():那么会查询所有数据
     * 如果user中设置了数据：就按user中封装的数据进行条件查询
     * 条件只能按照=来查询，不能按照>或者<等方式来查询。
     */


    @Test
    public void testSelect() {
        List<User> userList = newUserMapper.select(null);
        for (User user : userList) {
            System.out.println(user);

        }
    }


    /**
     * 统计查询：
     * 1、赋值为null或者new User()：统计所有记录数
     */


    @Test
    public void testSelectCount() {
        int count = newUserMapper.selectCount(null);
        System.out.println(count);
        int i = newUserMapper.selectCount(new User());
        System.out.println(i);
        User user = new User();
        user.setName("啊哈哈");
        System.out.println(newUserMapper.selectCount(user));
    }

    /**
     * 通过主键进行查询：
     * 1、参数类型一定要主键一致
     * 2、必须在对象中通过@Id指定主键
     */

    @Test
    public void testSelectPrimaryKey() {
        System.out.println(newUserMapper.selectByPrimaryKey(1));
    }


    /**
     * 新增方法：按全属性插入数据，如果不设置属性，自动设置为null
     */
    @Test
    public void testInsert() {
        User user = new User();
        user.setUserName("admin3");
        user.setPassword("123456");
        user.setName("用户3");
        user.setBirthday("2012-10-22");
        user.setTelephone("13391010299");
        user.setCode("asdf");
        user.setSex("男");
        user.setEmail("admin3@itcast.cn");
        user.setStatus("Y");
        newUserMapper.insert(user);
    }

    /**
     * 按属性插入数据：如果不设置属性，自动设置为null
     */

    @Test
    public void testInsertSelectiv() {
        User user = new User();
        user.setUserName("admin6");
        user.setPassword("123456");
        user.setName("用户6");
        user.setBirthday("2012-10-22");
        user.setTelephone("13391010299");
        user.setCode(UuidUtil.getUuid());
        //user.setSex("男");
        //user.setEmail("admin3@itcast.cn");
        user.setStatus("Y");
        newUserMapper.insertSelective(user);
    }

    /*
     *
     * 将uid为10的用户，修改除电话和邮件以外的所有属性
     * 条件更新：没有设置属性的都更新为null.
     */

    @Test
    public void testUpdateByExample() {
        Example example = new Example(User.class);

        Example.Criteria criteria = example.createCriteria();
        //添加条件
        criteria.andEqualTo("uid", 10);

        //修改除电话和邮件以外的所有的属性
        User user = new User();
        user.setUserName("admin100");
        user.setPassword("123456");
        user.setName("用户100");
        user.setBirthday("2012-10-22");
        //user.setTelephone("13391010299");
        user.setCode("sadfdsaf");
        user.setSex("男");
        //user.setEmail("admin100@itcast.cn");
        user.setStatus("Y");
        newUserMapper.updateByExample(user, example);
    }


    /**
     * 通过条件统计记录数
     * 1、需要初始化Example对象
     * 2、通过example获取criteria来设置条件
     */
    @Test
    public void testSelectCountByExample() {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andBetween("uid", 10, 20);
        int count = newUserMapper.selectCountByExample(example);
        System.out.println(count);
    }

    /**
     * 通过条件进行查询：
     * 查询uid在1-30之间的并且用户名中有zhang的用户，或性别是男的用户。
     * 显示的时候通过birthday倒序排序，如果birthday一样那么根据uid正序执行。
     * 1、初始化Example对象
     * 2、通过Example对象获取Criteria来设置查询条件
     * 3、可以通过example设置并集查询-or
     * 4、可以通过example设置排序查询
     */

    @Test
    public void testSelectByExample() {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sex", "男").andEqualTo("name", "啊哈哈").andBetween("uid", 10, 20);
        example.or(criteria);
        example.setOrderByClause("birthday desc,uid asc");
        List<User> users = newUserMapper.selectByExample(example);
        for (User user : users) {
            System.out.println(user);
        }
    }

}