package cn.itcast.travel.web;

import cn.itcast.travel.exception.UserExistsException;
import cn.itcast.travel.exception.UserNameOrPasswordErrorException;
import cn.itcast.travel.exception.UserNoActiveException;
import cn.itcast.travel.pojo.ResultInfo;
import cn.itcast.travel.pojo.User;
import cn.itcast.travel.service.UserLoginService;
import cn.itcast.travel.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * author: wzb
 * Date: 2019/4/1 0001
 * Time: 10:05
 */

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private ResultInfo resultInfo;

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserLoginService userLoginService;


    /**
     * 添加用户
     *
     * @param registerUser
     * @return
     */
    @RequestMapping(value = "register")
//    @ResponseBody
    public ResponseEntity<ResultInfo> register(User registerUser, @RequestParam("check") String checkCode, HttpSession session) {
        try {
            //验证码检验
            String check = (String) session.getAttribute("check");
            session.removeAttribute("check");
            if (StringUtils.isNotEmpty(checkCode)) {
                if (checkCode.equalsIgnoreCase(check)) {
                    userRegisterService.addUser(registerUser);
                    resultInfo.setFlag(true);
                    return ResponseEntity.ok(resultInfo);
                } else {
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("验证码输入有误");
                    ResponseEntity<ResultInfo> body = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultInfo);
                    System.out.println(body);
                    return body;
                }
            } else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("请输入验证码");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultInfo);
            }
        } catch (UserExistsException e) {
            e.printStackTrace();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("服务器繁忙");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultInfo);
    }


    /**
     * 邮件激活
     *
     * @param code
     * @return
     */
    @RequestMapping("activeMail")
    public String activeMail(@RequestParam("code") String code) {
        try {
            boolean flag = userRegisterService.active(code);
            if (!flag) {
                return "redirect:/error/500.html";
            } else {
                return "redirect:/error/200.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500.html";
        }


    }

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(User loginUser, @RequestParam("check") String checkCode, HttpSession session) {
        String userName = loginUser.getUserName();
        String password = loginUser.getPassword();
        String check = (String) session.getAttribute("check");
        session.removeAttribute("check");
        if (StringUtils.isNotEmpty(checkCode)) {
            if (checkCode.equalsIgnoreCase(check)) {
                if (StringUtils.isNotEmpty(userName)) {
                    if (userLoginService.checkUserName(userName)) {
                        if (userLoginService.findUserByUnameAndPwd(userName, password)) {
                            resultInfo.setFlag(true);

                        } else {
                            resultInfo.setFlag(false);
                            resultInfo.setErrorMsg("密码输入有误");
                        }
                    } else {
                        resultInfo.setFlag(false);
                        resultInfo.setErrorMsg("用户名不存在");
                    }
                    resultInfo.setFlag(false);
                } else {
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("用户名不能为空");
                }
            } else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("验证码输入有误");
            }
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("请输入验证码");
        }
        return resultInfo;
    }


    /**
     * 通过异进行验证登录
     *
     * @param loginUser
     * @param checkCode
     * @param session
     * @return
     */
    @RequestMapping("login2")
    @ResponseBody
    public ResultInfo login2(User loginUser, @RequestParam("check") String checkCode, HttpSession session) {
        String check = (String) session.getAttribute("check");
        session.removeAttribute("check");
        try {
            if (StringUtils.isNotEmpty(checkCode)) {
                if (checkCode.equalsIgnoreCase(check)) {
                    User user = userLoginService.login(loginUser);
                    session.setAttribute("login_user", user);
                    resultInfo.setFlag(true);
                } else {
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("验证码输入有误");
                }
            } else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("请输入验证码");
            }
        } catch (UserNameOrPasswordErrorException e) {
            e.printStackTrace();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg(e.getMessage());
        } catch (UserNoActiveException e) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("服务器繁忙，请稍后重试....");
        }
        return resultInfo;
    }


    /**
     * 获取用户登录信息
     *
     * @param session
     * @return
     */
    @RequestMapping("getLoginUserData")
    @ResponseBody
    public ResultInfo getLoginUserData(HttpSession session) {
        User login_user = (User) session.getAttribute("login_user");
        if (login_user != null) {
            resultInfo.setFlag(true);
            resultInfo.setData(login_user.getUserName());
        } else {

            resultInfo.setFlag(false);
        }
        return resultInfo;
    }

    @RequestMapping("loginOut")
    public String loginOut(HttpSession session) {
        //session.removeAttribute("login_user)");
        //销毁session
        session.invalidate();
        return "redirect:/index.html";

    }


}

