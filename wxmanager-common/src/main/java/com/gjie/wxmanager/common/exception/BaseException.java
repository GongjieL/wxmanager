package com.gjie.wxmanager.common.exception;

import com.gjie.wxmanager.common.constants.enums.ErrorEnum;


public class BaseException extends RuntimeException {
    private ErrorEnum error = ErrorEnum.UN_KNOWN_ERROR;
    //错误上下文
    private ErrorContext errorContext;

    public ErrorEnum getError() {
        return error;
    }

    public void setError(ErrorEnum error) {
        this.error = error;
    }

    public ErrorContext getErrorContext() {
        return errorContext;
    }

    public void setErrorContext(ErrorContext errorContext) {
        this.errorContext = errorContext;
    }

    public BaseException(ErrorEnum error) {
        super(error.getDesc());
        this.error = error;
    }

    public BaseException() {
    }
}
