package com.sun.swffsp.security.handler;

import com.google.gson.Gson;
import com.sun.swffsp.dto.admin.result.base.Response;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理器
 *
 * @author sun
 * @date 2019/2/18 18:28
 */
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (httpServletRequest.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || httpServletRequest.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            //JSON请求处理返回JSON
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            String str = new Gson().toJson(Response.success("登录成功!欢迎" + authentication.getName()));
            httpServletResponse.getWriter().println(str);
        } else {
            //非JSON请求采用默认处理-跳转页面
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        }
    }
}
