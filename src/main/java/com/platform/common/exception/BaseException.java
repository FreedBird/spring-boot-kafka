package com.platform.common.exception;

import com.platform.common.utils.MessageUtils;
import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * 自定义异常
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    @Getter
    private String module;

    /**
     * 错误码
     */
    @Getter
    private String code;

    /**
     * 错误码对应的参数
     */
    @Getter
    private Object[] args;

    /**
     * 错误消息
     */
    private String message;

    public BaseException(String message) {
        this.message = message;
    }

    public BaseException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseException(String module, String code, Object[] args, String message) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.message = message;
    }

    @Override
    public String getMessage() {
        String message = this.message;
        if (!StringUtils.isEmpty(code)) {
            message = MessageUtils.message(code, args);
        }
        return message;
    }

}
