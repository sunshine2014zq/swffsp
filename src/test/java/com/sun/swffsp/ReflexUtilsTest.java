package com.sun.swffsp;

import com.google.gson.Gson;
import com.sun.swffsp.dto.core.PrivilegeDto;
import com.sun.swffsp.dto.core.RoleDto;
import com.sun.swffsp.dto.admin.result.base.Response;
import com.sun.swffsp.dto.core.UserDto;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sun
 * @date 2019/2/28 14:36
 */
public class ReflexUtilsTest {

    @Test
    public void test1() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        UserDto user = new UserDto();
//        user.setMail(null);
//        String mail = (String) ReflexUtils.getFieldValue(UserDto.class, "mail", user);
////        System.out.println(mail.length());
//        System.out.println(mail);
//        System.out.println("123".substring(0,2));

        Response.success("success").data("***");

    }

    @Test
    public void test2(){
        PrivilegeDto p1 = new PrivilegeDto();
        p1.setName("权限1");
        p1.setCode("q1");

        PrivilegeDto p2 = new PrivilegeDto();
        p2.setName("权限2");
        p2.setCode("q2");

        PrivilegeDto p3 = new PrivilegeDto();
        p3.setName("权限3");
        p3.setCode("q3");

        RoleDto role1 = new RoleDto();
        role1.setName("角色1");
        role1.setCode("role1");

        RoleDto role2 = new RoleDto();
        role2.setName("角色2");
        role2.setCode("role2");

        UserDto userEntity = new UserDto();
        userEntity.setUsername("admin");
        userEntity.setNickName("管理员");
        userEntity.setEmail("11@qq.com");




        List<PrivilegeDto> ps1 = new ArrayList<>();
        ps1.add(p1);
        ps1.add(p3);

        List<PrivilegeDto> ps2 = new ArrayList<>();
        ps2.add(p2);
        ps2.add(p3);


        role1.setPrivileges(ps1);
        role2.setPrivileges(ps2);

        List<RoleDto> roles = new ArrayList<>();

        roles.add(role1);
        roles.add(role2);

        userEntity.setRoles(roles);

        //数据中包含数组
        //数组中包含对象

        //对象中包含对象
        //对象中包含数组 -包含的数组是对象数组不行


//        UserInfoResult userInfo = com.sun.swffsp.dto.admin.result.JSON.recursiveObject(
//                JSON.toJSONString(userEntity), UserInfoResult.class);

        Gson gson = new Gson();
//        UserInfoResult userInfo1 = gson.fromJson(JSON.toJSONString(userEntity), UserInfoResult.class);
//        String s1 = gson.toJson(userInfo);
//        UserInfoResult userInfo1 = gson.fromJson(s1, UserInfoResult.class);
        System.out.println();





//        gson.
//        System.out.println(userInfo);

//        String s = JSON.toJSONString(userInfo);
//        System.out.println(s);


//        JSON.ParseJ
//        JSON.parseObject("",this.getClass())



//        UserInfoResult userInfo = JSON.parseObject(JSON.toJSONString(userEntity), UserInfoResult.class);
//        JSONArray array = JSON.parseObject(JSON.toJSONString(userEntity)).getJSONArray("roles");
//        array.
//        System.out.println(userInfo);


    }


}
