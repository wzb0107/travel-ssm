package cn.itcast.travel.service;

import cn.itcast.travel.pojo.PageBean;
import cn.itcast.travel.pojo.Route;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public interface RouteService {
    Map<String, List<Route>> routeCareChoose();

    String queryCategoryList() throws JsonProcessingException;

    PageBean findPageBean(Integer cid, Integer curPage, String rname);

    Route findRouteByRid(Integer rid);

    void updateRouteCountByRid(Integer rid);

    Integer queryRouteCountByRid(Integer rid);
}
