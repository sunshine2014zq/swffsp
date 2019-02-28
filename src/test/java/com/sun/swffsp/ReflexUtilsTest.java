package com.sun.swffsp;

import com.sun.swffsp.dto.db.UserEntity;
import com.sun.swffsp.utils.ReflexUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

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
        System.out.println("123".substring(0,2));
    }
}
