package com.online4edu.dependencies.utils.result;

import com.online4edu.dependencies.utils.jackson.JacksonUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    /**
     * 响应码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 数据
     */
    private final T data;

    public Result() {
        this.data = null;
    }

    public Result(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean format) {
        return JacksonUtils.toJson(this, format);
    }
}
