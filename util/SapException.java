package com.lenovo.sap.api.util;


import com.lenovo.xframe.model.ResultBody;

import javax.annotation.Nullable;

/**
 * @author yuhao5
 * @date 3/17/22
 **/
public class SapException extends RuntimeException{

    protected final int statusHint;
    protected final String code;

    public SapException(String code, String message) {
        this(code, message, null);
    }

    public SapException(int statusHint, String code, String message) {
        this(statusHint, code, message, null);
    }

    public SapException(String code, String message, @Nullable Throwable cause) {
        this(500, code, message, cause);
    }

    public SapException(int statusHint, String code, String message, @Nullable Throwable cause) {
        super(message, cause, true, true);
        this.statusHint = statusHint;
        this.code = code;
    }

    public <T> ResultBody<T> toResultBody() {
        return ResultBody.failed(statusHint, code, getMessage());
    }

    @Override
    public String getLocalizedMessage() {
        return '[' + code + "] " + getMessage();
    }

    public int getStatusHint() {
        return this.statusHint;
    }

    public String getCode() {
        return this.code;
    }
}
