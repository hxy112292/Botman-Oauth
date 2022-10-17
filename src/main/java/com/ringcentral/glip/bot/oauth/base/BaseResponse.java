package com.ringcentral.glip.bot.oauth.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author terry.huang
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BaseResponse<T> {

    private Integer code;

    private String message;

    private Long total;

    private T result;
}
