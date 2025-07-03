package com.guoxiaohei.jwt.controller;

import com.guoxiaohei.jwt.constant.Constant;
import com.guoxiaohei.jwt.util.JwtGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Description:授权controller
 *
 * @author guoyupeng [2019/3/25]
 */
@RestController
@RequestMapping("/api")
public class AuthorizationController {

    private Logger log = LoggerFactory.getLogger(AuthorizationController.class);

    @GetMapping("auth")
    public String authorization(@RequestParam("username") String username,
                                @RequestParam("issure") String issure) {
        return JwtGenerate.getInstance()
                .createJWT(username, issure, Constant.DEFAULT_JWT_EXPIRES,
                        Base64.getEncoder().encodeToString(
                                Constant.SLAT.getBytes(StandardCharsets.UTF_8)));
    }

    @GetMapping("success")
    public String authSuccess() {
        log.debug("鉴权成功");
        return "鉴权成功";
    }

}
