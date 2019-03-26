package com.guoxiaohei.jwt.controller;

import com.guoxiaohei.jwt.constant.Constant;
import com.guoxiaohei.jwt.util.JwtGenerate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Description:授权controller
 * @author guoyupeng [2019/3/25]
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class AuthorizationController {

    @GetMapping("auth")
    public String authorization(@RequestParam("username") String username,
            @RequestParam("issure") String issure) {
        try {
            String jwt = JwtGenerate.getInstance()
                    .createJWT(username, issure, Constant.DEFAULT_JWT_EXPAIR,
                            Base64.getEncoder().encodeToString(
                                    Constant.SLAT.getBytes("utf-8")));
            return jwt;
        } catch (UnsupportedEncodingException e) {
            log.error("编码不支持", e);
        }
        return null;
    }

    @GetMapping("success")
    public String authSccess() {
        log.debug("鉴权成功");
        return "鉴权成功";
    }

}
