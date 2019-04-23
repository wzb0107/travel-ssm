package cn.itcast.travel.mapper;

import cn.itcast.travel.pojo.Category;
import cn.itcast.travel.pojo.Route;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RouteMapper {
    List<Route> popularity();

    List<Route> news();

    List<Route> themes();

    List<Category> queryCategoryList();


    int queryRouteCount(@Param("cid") Integer cid, @Param("rname") String rname);

    List<Route> queryRouteList(@Param("cid") Integer cid, @Param("start") int start, @Param("pageSize") int pageSize, @Param("rname") String rname);

    Route findRouteByRid(@Param("rid") Integer rid);

    void updateRouteCountByRid(@Param("rid") Integer rid);

    Integer queryRouteCountByRid(@Param("rid") Integer rid);
}
