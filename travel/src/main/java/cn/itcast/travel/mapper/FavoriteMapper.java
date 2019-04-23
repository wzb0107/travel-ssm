package cn.itcast.travel.mapper;

import cn.itcast.travel.pojo.Favorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FavoriteMapper {
    Favorite isFavoriteByRid(@Param("rid") Integer rid, @Param("uid") Integer uid);

    boolean addFavorite(@Param("rid") Integer rid, @Param("uid") Integer uid);

    int queryfavoriteCount(@Param("uid") Integer uid);

    List<Favorite> findFavoriteByPage(@Param("start") int start, @Param("pageSize") int pageSize, @Param("uid") Integer uid);

}
