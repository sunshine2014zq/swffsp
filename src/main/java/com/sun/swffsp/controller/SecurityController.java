package com.sun.swffsp.controller;

import com.sun.swffsp.dto.condition.UserCondition;
import com.sun.swffsp.dto.db.UserEntity;
import com.sun.swffsp.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账户全选管理
 *
 * @author sun
 * @date 2019/2/21 10:52
 */
@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping("/userInfo")
    @ResponseBody
    public Object info() {
        return securityService.info();
    }

    @RequestMapping("/userList")
    @ResponseBody
    public Page<UserEntity> list(@RequestBody UserCondition userCondition){
        return securityService.list(userCondition);
    }

    @RequestMapping("/modifiedUser")
    public Object modified(@RequestBody UserEntity userEntity){
        return securityService.modified(userEntity);
    }

    @RequestMapping("/deleteUsers")
    public Object delete(@RequestBody List<String> ids){
        return securityService.delete(ids);
    }


}
