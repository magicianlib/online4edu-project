package com.online4edu.dependencies.utils.converter;

import com.online4edu.dependencies.utils.jackson.JacksonUtils;

import java.io.Serializable;

/**
 * Bean Json 输出
 *
 * @author magicianlib@gmail.com
 * @since 2022/04/22 16:11
 */
public abstract class PrintFriendliness extends Convert implements Serializable {

    private static final long serialVersionUID = 6346134566802719199L;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":" + JacksonUtils.toJson(this);
    }
}
