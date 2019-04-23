package cn.itcast.travel.web;

import cn.itcast.travel.pojo.*;
import cn.itcast.travel.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * author: wzb
 * Date: 2019/4/3 0003
 * Time: 11:06
 */

@Controller
@RequestMapping("favorite")
public class FavoriteController {

    @Autowired
    private ResultInfo resultInfo;
    @Autowired
    private FavoriteService favoriteService;


    /**
     * 显示是否收藏
     *
     * @param rid
     * @param session
     * @return
     */
    @RequestMapping("isFavoriteByRid")
    @ResponseBody
    public ResultInfo isFavoriteByRid(@RequestParam("rid") Integer rid, HttpSession session) {
        boolean flag;
        try {
            User login_user = (User) session.getAttribute("login_user");
            if (login_user != null) {
                flag = favoriteService.isFavoriteByRid(rid, login_user.getUid());
                resultInfo.setFlag(true);
                resultInfo.setData(flag);
            } else {
                resultInfo.setFlag(true);
                resultInfo.setData(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setErrorMsg("服务器繁忙...路线收藏");
            resultInfo.setFlag(false);
        }
        return resultInfo;
    }

    /**
     * 添加收藏
     *
     * @param rid
     * @param session
     * @return
     */
    @RequestMapping("addFavorite")
    @ResponseBody
    public ResultInfo addFavorite(@RequestParam("rid") Integer rid, HttpSession session) {
        //现判断是否已登录:data 0未登录 data>0 已登录
        try {
            User login_user = (User) session.getAttribute("login_user");
            if (login_user != null) {
                resultInfo.setFlag(true);
                Integer count = favoriteService.addFavorite(rid, login_user.getUid());
                resultInfo.setData(count);
            } else {
                resultInfo.setFlag(true);
                resultInfo.setData(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("已经收藏....请勿再次收藏");
        }
        return resultInfo;
    }


    /**
     * 你的收藏
     *
     * @return
     */
    @RequestMapping("findFavoriteByPage")
    @ResponseBody
    public ResultInfo findFavoriteByPage(@RequestParam(value = "curPage",defaultValue = "1") Integer curPage, HttpSession session) {
        try {
            User login_user = (User) session.getAttribute("login_user");
            PageBean<Favorite> pageBean = favoriteService.findFavoriteByPage(curPage,login_user.getUid());
            System.out.println(pageBean);
            resultInfo.setFlag(true);
            resultInfo.setData(pageBean);
            System.out.println(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("服务器繁忙.. 我的收藏功能");
        }
        return resultInfo;
    }
}
