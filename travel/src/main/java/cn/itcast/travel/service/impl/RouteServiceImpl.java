package cn.itcast.travel.service.impl;

import cn.itcast.travel.mapper.RouteMapper;
import cn.itcast.travel.pojo.Category;
import cn.itcast.travel.pojo.PageBean;
import cn.itcast.travel.pojo.Route;
import cn.itcast.travel.service.RouteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * author: wzb
 * Date: 2019/4/2 0002
 * Time: 12:49
 */

@Service("routeService")
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * huoqujingxuan
     *
     * @return
     */
    @Override
    public Map<String, List<Route>> routeCareChoose() {
        Map<String, List<Route>> routeMap = new HashMap<>();
        List<Route> popularity = routeMapper.popularity();
        List<Route> news = routeMapper.news();
        List<Route> themes = routeMapper.themes();
        routeMap.put("popularity", popularity);
        routeMap.put("news", news);
        routeMap.put("themes", themes);
        return routeMap;
    }

    @Override
    public String queryCategoryList() throws JsonProcessingException {
        // Jedis jedis = JedisUtil.getJedis();
        //jsonData = jedis.get("categoryList");
        String jsonData = null;
        jsonData = (String) redisTemplate.opsForValue().get("categoryList");
        if (StringUtils.isBlank(jsonData)) {
            List<Category> list = routeMapper.queryCategoryList();
            //将查询出来的数据转换为json数据
            jsonData = new ObjectMapper().writeValueAsString(list);
            redisTemplate.opsForValue().set("categoryList", jsonData);
        }
        return jsonData;
    }

    @Override
    public PageBean findPageBean(Integer cid, Integer curPage, String rname) {
        PageBean<Route> routePageBean = new PageBean<>();
        //当前页
        routePageBean.setCurPage(curPage);
        //总记录数
        int count = routeMapper.queryRouteCount(cid, rname);
        routePageBean.setCount(count);
        //每页显示数量
        int pageSize = 5;
        routePageBean.setPageSize(pageSize);
        //每页开始数
        int start = (curPage - 1) * pageSize;
        List<Route> routeList = routeMapper.queryRouteList(cid, start, pageSize, rname);
        //每页显示的数据
        routePageBean.setData(routeList);
        return routePageBean;
    }

    @Override
    public Route findRouteByRid(Integer rid) {
        return routeMapper.findRouteByRid(rid);
    }

    @Override
    public void updateRouteCountByRid(Integer rid) {
        routeMapper.updateRouteCountByRid(rid);
    }

    @Override
    public Integer queryRouteCountByRid(Integer rid) {
        return routeMapper.queryRouteCountByRid(rid);
    }
}
