package cn.itcast.travel.web;

import cn.itcast.travel.pojo.User;
import cn.itcast.travel.service.NewUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * author: wzb
 * Date: 2019/4/6 0006
 * Time: 19:34
 */

@Controller
@RequestMapping("rest/user")
public class NewUserController {

    @Autowired
    private NewUserService userService;

    @GetMapping("id/{uid}")
    public ResponseEntity<User> queryUserById(@PathVariable("uid") Integer uid) {
        if (uid == null || uid < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try {
            User queryUser = userService.queryUserById(uid);
            if (queryUser == null) {
//                ResponseEntity.status(HttpStatus.OK).body(queryUser);
                ResponseEntity.status(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(queryUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @GetMapping("username/{username}")
    public ResponseEntity<User> queryUserByUserName(@PathVariable("username") String userName) {
        if (userName == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            User user = userService.queryUserByUserName(userName);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("name/{name}")
    public ResponseEntity<User> queryUserByName(@PathVariable("name") String name) {
        if (name == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            User user = userService.queryUserByName(name);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping
    public ResponseEntity<Void> saveUser(User user) {
        if (user == null || StringUtils.isBlank(user.getUserName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            boolean flag = userService.saveUser(user);
            if (flag) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /*@PostMapping
    public ResponseEntity<Void> saveU(User user) {
        if (user == null || StringUtils.isBlank(user.getUserName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            Boolean flag = userService.saveU(user);
            if (flag) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }*/


    /*@PutMapping
    public ResponseEntity<Void> updateUserById(User user) {
        if (user == null || user.getUid() == null || user.getUid() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            boolean flag = userService.updateUserById(user.getUid(), user.getName());
            if (flag) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }*/

    @PutMapping
    public ResponseEntity<Void> updateUserById(@RequestParam("uid") Integer uid, @RequestParam("name") String name) {
        if (uid == null || uid <= 0 || name == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            boolean flag = userService.updateUserById(uid, name);
            if (flag) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteUserById(@RequestParam("uid") Integer uid) {
        if (uid == null || uid <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            Boolean flag = userService.deleteUserById(uid);
            if (flag) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
