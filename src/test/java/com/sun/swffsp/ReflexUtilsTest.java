package com.sun.swffsp;

import com.google.gson.Gson;
import com.sun.swffsp.dto.db.PrivilegeEntity;
import com.sun.swffsp.dto.db.RoleEntity;
import com.sun.swffsp.dto.db.UserEntity;
import com.sun.swffsp.dto.resp.Response;
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
//        UserEntity user = new UserEntity();
//        user.setMail(null);
//        String mail = (String) ReflexUtils.getFieldValue(UserEntity.class, "mail", user);
////        System.out.println(mail.length());
//        System.out.println(mail);
//        System.out.println("123".substring(0,2));

        Response.ok("ok").data("***");

    }

    @Test
    public void test2(){
        PrivilegeEntity p1 = new PrivilegeEntity();
        p1.setName("权限1");
        p1.setCode("q1");

        PrivilegeEntity p2 = new PrivilegeEntity();
        p2.setName("权限2");
        p2.setCode("q2");

        PrivilegeEntity p3 = new PrivilegeEntity();
        p3.setName("权限3");
        p3.setCode("q3");

        RoleEntity role1 = new RoleEntity();
        role1.setName("角色1");
        role1.setCode("role1");

        RoleEntity role2 = new RoleEntity();
        role2.setName("角色2");
        role2.setCode("role2");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setNickName("管理员");
        userEntity.setEmail("11@qq.com");




        List<PrivilegeEntity> ps1 = new ArrayList<>();
        ps1.add(p1);
        ps1.add(p3);

        List<PrivilegeEntity> ps2 = new ArrayList<>();
        ps2.add(p2);
        ps2.add(p3);


        role1.setPrivileges(ps1);
        role2.setPrivileges(ps2);

        List<RoleEntity> roles = new ArrayList<>();

        roles.add(role1);
        roles.add(role2);

        userEntity.setRoles(roles);

        //数据中包含数组
        //数组中包含对象

        //对象中包含对象
        //对象中包含数组 -包含的数组是对象数组不行


//        UserInfo userInfo = com.sun.swffsp.dto.resp.JSON.recursiveObject(
//                JSON.toJSONString(userEntity), UserInfo.class);

        Gson gson = new Gson();
//        UserInfo userInfo1 = gson.fromJson(JSON.toJSONString(userEntity), UserInfo.class);
//        String s1 = gson.toJson(userInfo);
//        UserInfo userInfo1 = gson.fromJson(s1, UserInfo.class);
        System.out.println();





//        gson.
//        System.out.println(userInfo);

//        String s = JSON.toJSONString(userInfo);
//        System.out.println(s);


//        JSON.ParseJ
//        JSON.parseObject("",this.getClass())



//        UserInfo userInfo = JSON.parseObject(JSON.toJSONString(userEntity), UserInfo.class);
//        JSONArray array = JSON.parseObject(JSON.toJSONString(userEntity)).getJSONArray("roles");
//        array.
//        System.out.println(userInfo);


    }


}
