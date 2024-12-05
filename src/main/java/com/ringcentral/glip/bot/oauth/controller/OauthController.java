package com.ringcentral.glip.bot.oauth.controller;

import com.ringcentral.RestClient;
import com.ringcentral.RestException;
import com.ringcentral.glip.bot.oauth.base.BaseResponse;
import com.ringcentral.glip.bot.oauth.base.ResultResponse;
import com.ringcentral.glip.bot.oauth.constant.Constant;
import com.ringcentral.definitions.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author terry.huang
 */
@RestController
@RequestMapping("oauth")
public class OauthController {
    @Value("${oauth.redirect.url}")
    private String oauthRedirectUrl;

    @Value("${oauth.client.id}")
    private String oauthClientId;

    @Value("${oauth.client.secret}")
    private String oauthClientSecret;

    @Value("${oauth.server}")
    private String oauthServer;



    @GetMapping
    public BaseResponse<String> oauth(@RequestParam String code) throws RestException, IOException {
        if (code != null) {
            RestClient rc = new RestClient(oauthClientId, oauthClientSecret, oauthServer);
            TokenInfo tokenInfo = rc.authorize(code, oauthRedirectUrl);
            System.out.println(tokenInfo.access_token);
            rc.revoke();
            return new ResultResponse<>(Constant.SUCCESS);
        } else {
            return new ResultResponse<>(Constant.CODE_NULL);
        }
    }
}
