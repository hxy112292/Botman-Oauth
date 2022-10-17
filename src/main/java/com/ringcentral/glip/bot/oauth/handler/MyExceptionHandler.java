package com.ringcentral.glip.bot.oauth.handler;

import com.ringcentral.glip.bot.oauth.base.BaseResponse;
import com.ringcentral.glip.bot.oauth.base.ErrorResponse;
import com.ringcentral.glip.bot.oauth.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author terry.huang
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse handlerRuntimeException(RuntimeException ex) {

        ex.printStackTrace();

        if (ex.getMessage() == null || ex.getMessage().isEmpty()) {
            return new ErrorResponse(ErrorMessage.SERVICE_EXCEPTION);
        }

        return new ErrorResponse(ex.getMessage());
    }
}
