package com.ringcentral.glip.bot.oauth.base;


import com.ringcentral.glip.bot.oauth.constant.Constant;

/**
 * @author terry.huang
 */
public class ErrorResponse<T> extends BaseResponse<T> {

    public ErrorResponse(String message) {

        super(Constant.ERROR_CODE, message, null, null);
    }

    public ErrorResponse(int code, String message) {

        super(code, message, null, null);
    }
}
