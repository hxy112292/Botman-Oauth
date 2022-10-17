package com.ringcentral.glip.bot.oauth.base;

import com.ringcentral.glip.bot.oauth.constant.Constant;

/**
 * @author terry.huang
 */
public class ResultResponse<T> extends BaseResponse<T> {

    public ResultResponse(T result) {

        super(Constant.SUCCESS_CODE, null, null, result);
    }
}
