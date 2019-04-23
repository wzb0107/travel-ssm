package cn.itcast.travel.web;

import cn.itcast.travel.pojo.PageBean;
import cn.itcast.travel.pojo.ResultInfo;
import cn.itcast.travel.pojo.Route;
import cn.itcast.travel.service.RouteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * author: wzb
 * Date: 2019/4/2 0002
 * Time: 12:45
 */

@Controller
@RequestMapping("route")
public class RouteController {

    @Autowired
    private ResultInfo resultInfo;

    @Autowired
    private RouteService routeService;

    /**
     * 精选
     *
     * @return
     */
    @RequestMapping("routeCareChoose")
    @ResponseBody
    public ResultInfo routeCareChoose() {
        try {
            resultInfo.setFlag(true);
            resultInfo.setData(routeService.routeCareChoose());
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("服务器崩溃....");
        }
        return resultInfo;
    }


    /**
     * 路线
     *
     * @return
     */
    @RequestMapping("queryCategoryList")
    @ResponseBody
    public String queryCategoryList() {
        String jsonData = null;
        try {
            jsonData = routeService.queryCategoryList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("服务器炸了....");
            try {
                jsonData = new ObjectMapper().writeValueAsString(resultInfo);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
        return jsonData;
    }


    /**
     * 线路分页
     *
     * @param cid
     * @param curPage
     * @param rname
     * @return
     */
    @RequestMapping("findPageBean")
    @ResponseBody
    public ResultInfo findPageBean(@RequestParam(value = "cid", required = false) Integer cid, @RequestParam(value = "curPage", defaultValue = "1") Integer curPage, @RequestParam(value = "rname", required = false) String rname) {
        
        try {
            PageBean<Route> pageBean = routeService.findPageBean(cid, curPage, rname);
            resultInfo.setFlag(true);
            resultInfo.setData(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("服务器炸了...分页");
        }
        return resultInfo;
    }


    @RequestMapping("findRouteByRid")
    @ResponseBody
    public ResultInfo findRouteByRid(@RequestParam("rid") Integer rid) {
        try {
            Route route = routeService.findRouteByRid(rid);
            resultInfo.setFlag(true);
            resultInfo.setData(route);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("服务炸了....页面详情");
        }
        return resultInfo;
    }

}
