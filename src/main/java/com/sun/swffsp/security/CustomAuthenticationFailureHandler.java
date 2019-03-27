package com.sun.swffsp.security;

import com.google.gson.Gson;
import com.sun.swffsp.dto.admin.result.base.Response;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败处理器
 *
 * @author sun
 * @date 2019/2/18 18:33
 */
public class CustomAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (httpServletRequest.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || httpServletRequest.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

            //JSON请求处理返回JSON
            httpServletResponse.setContentType("application/json;charset=utf-8");
            String message = (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) ?
                    "账号或密码错误" : "登录异常";
            String str = new Gson().toJson(Response.fail(message));
            httpServletResponse.getWriter().println(str);
        } else {
            //非JSON请求采用默认处理-跳转页面
            super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
        }
    }
}
