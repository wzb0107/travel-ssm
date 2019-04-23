package cn.itcast.travel.service.impl;

import cn.itcast.travel.mapper.FavoriteMapper;
import cn.itcast.travel.pojo.Favorite;
import cn.itcast.travel.pojo.PageBean;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * author: wzb
 * Date: 2019/4/3 0003
 * Time: 11:15
 */

@Service("favoriteService")
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private RouteService routeService;

    @Override
    public boolean isFavoriteByRid(Integer rid, Integer uid) {
        Favorite favorite = favoriteMapper.isFavoriteByRid(rid, uid);
        return favorite != null;
    }

    @Override
    public Integer addFavorite(Integer rid, Integer uid) {
        favoriteMapper.addFavorite(rid, uid);
        routeService.updateRouteCountByRid(rid);
        return routeService.queryRouteCountByRid(rid);
    }

    @Override
    public PageBean<Favorite> findFavoriteByPage(Integer curPage, Integer uid) {
        PageBean<Favorite> pageBean = new PageBean<>();
        pageBean.setCurPage(curPage);
        //每页显示的大小
        int pageSize = 4;
        pageBean.setPageSize(pageSize);
        //开始查询
        int start = (curPage - 1) * 4;
        PageHelper.startPage(curPage,pageSize);
        List<Favorite> list = favoriteMapper.findFavoriteByPage(start, pageSize, uid);
        PageInfo<Favorite> favoritePageInfo = new PageInfo<>(list);
        favoritePageInfo.getTotal();
        pageBean.setData(list);
        int count = favoriteMapper.queryfavoriteCount(uid);
        pageBean.setCount(count);
        return pageBean;
    }
}
