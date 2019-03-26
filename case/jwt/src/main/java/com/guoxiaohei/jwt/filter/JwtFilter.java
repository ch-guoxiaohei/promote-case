package com.guoxiaohei.jwt.filter;

import com.guoxiaohei.jwt.constant.Constant;
import com.guoxiaohei.jwt.util.JwtGenerate;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Objects;

/**
 * Description:
 * @author guoyupeng [2019/3/25]
 */
@Slf4j
public class JwtFilter implements Filter {

    /**
     * 授权前缀
     */
    private static String AUTHORIZATION_PREFIX = "auth";

    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String auth = request.getHeader("Authorization");
        if (Objects.isNull(auth) || auth.length() == 0) {
            log.warn("接口没有授权");
            filterMessage(response, "接口没有授权");
            return;
        }

        String authPrefix = auth.substring(0, AUTHORIZATION_PREFIX.length());
        if (!AUTHORIZATION_PREFIX.equals(authPrefix)) {
            filterMessage(response, "授权字符串不存在前缀");
            return;
        }

        String authCode = auth.substring(AUTHORIZATION_PREFIX.length());
        Claims claims = JwtGenerate.getInstance().parseJWT(authCode,
                Base64.getEncoder().encodeToString(
                        Constant.SLAT.getBytes(Charset.forName("utf-8"))));
        // 可以根据具体的业务逻辑进行判断,比如和subject进行对比，此case没有设置subject
        if (Objects.nonNull(claims)) {
            log.debug(claims.get("unique_name").toString());
            log.debug(claims.getSubject());
            try {
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (ServletException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            filterMessage(response, "接口没有授权");
        }

    }

    private void filterMessage(HttpServletResponse response, String message)
            throws IOException {
        response.setContentType("application/json;charset=utf-8");
        JSONObject auth = new JSONObject();
        try {
            auth.put("code", 200);
            auth.put("message", message);
        } catch (JSONException e) {
            log.error("解析json异常");
        }
        response.getWriter().println(auth.toString());
    }
}
