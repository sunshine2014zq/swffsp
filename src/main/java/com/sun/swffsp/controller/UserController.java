package com.sun.swffsp.controller;

import com.sun.swffsp.dto.condition.UserCondition;
import com.sun.swffsp.dto.db.UserEntity;
import com.sun.swffsp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户模块
 *
 * @author sun
 * @date 2019/2/21 10:52
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/info")
    @ResponseBody
    public Object info() {
        return userService.info();
    }

    @RequestMapping("/list")
    @ResponseBody
    public Page<UserEntity> list(@RequestBody UserCondition userCondition){
        return userService.list(userCondition);
    }

    @RequestMapping("/modified")
    public Object modified(@RequestBody UserEntity userEntity){
        return userService.modified(userEntity);
    }

    @RequestMapping("/delete")
    public Object delete(@RequestBody List<String> ids){
        return userService.delete(ids);
    }


}
