package com.ringcentral.glip.bot.oauth.controller;

import com.alibaba.fastjson.JSONObject;
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
@RequestMapping("botman-oauth")
public class OauthController {
    @Value("${oauth.redirect.url}")
    private String oauthRedirectUrl;

    @Value("${oauth.client.id}")
    private String oauthClientId;

    @Value("${oauth.client.secret}")
    private String oauthClientSecret;

    @Value("${oauth.server}")
    private String oauthServer;

    @Value("${platform.api.version}")
    private String apiVersion;

    @Value("${user.email}")
    private String userEmail;



    @GetMapping
    public BaseResponse<String> oauth(@RequestParam String code) throws RestException, IOException {
        if (code != null) {
            RestClient rc = new RestClient(oauthClientId, oauthClientSecret, oauthServer);
            TokenInfo tokenInfo = rc.authorize(code, oauthRedirectUrl);

            SearchDirectoryEntriesRequest searchDirectoryEntriesRequest = new SearchDirectoryEntriesRequest();
            searchDirectoryEntriesRequest.searchString = userEmail;

            DirectoryResource directoryResource = rc.restapi(apiVersion).account().directory().entries().search().post(searchDirectoryEntriesRequest);


            CreateGlipMember member = new CreateGlipMember();
            member = member.email(userEmail);
            member = member.id(directoryResource.records[0].id);

            CreateGlipMember[] members = new CreateGlipMember[]{member};
            CreateGlipConversationRequest createGlipConversationRequest = new CreateGlipConversationRequest();
            createGlipConversationRequest = createGlipConversationRequest.members(members);

            GlipConversationInfo glipConversationInfo = rc.restapi(apiVersion).glip().conversations().post(createGlipConversationRequest);

            GlipPostPostBody glipPostPostBody = new GlipPostPostBody();
            glipPostPostBody.text = JSONObject.toJSONString(tokenInfo);
            rc.restapi(apiVersion).glip().chats(glipConversationInfo.id).posts().post(glipPostPostBody);

            rc.revoke();

            return new ResultResponse<>(Constant.SUCCESS);
        } else {
            return new ResultResponse<>(Constant.CODE_NULL);
        }
    }
}
