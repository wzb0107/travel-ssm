package cn.itcast.travel.service;

import cn.itcast.travel.pojo.Favorite;
import cn.itcast.travel.pojo.PageBean;
import cn.itcast.travel.pojo.Route;

public interface FavoriteService {
    boolean isFavoriteByRid(Integer rid, Integer uid);

    Integer addFavorite(Integer rid, Integer uid);

    PageBean<Favorite> findFavoriteByPage(Integer curPage, Integer uid);
}
