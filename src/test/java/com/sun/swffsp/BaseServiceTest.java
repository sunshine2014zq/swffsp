package com.sun.swffsp;

import com.sun.swffsp.dto.core.UserEntity;
import com.sun.swffsp.service.SecurityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author sun
 * @date 2019/2/28 18:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseServiceTest {

    @Autowired
    private SecurityService userService;

    @Test
    public void test1(){
//        List list = new ArrayList();
//        list.add("3");
//        list.add("4");
//        userService.delete(list);

    }

    @Test
    public void test2(){
        UserEntity userEntity = new UserEntity();
//        userEntity.setUsername("adf");
//        userEntity.setPassword("111111");
//        userService.save(userEntity);
    }

}
