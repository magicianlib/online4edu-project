package io.ituknown.mybatis.service;

import io.ituknown.mybatis.chain.LambdaDeleteWrapperChain;
import io.ituknown.mybatis.chain.LambdaQueryWrapperChain;
import io.ituknown.mybatis.chain.LambdaUpdateWrapperChain;

import java.io.Serializable;

/**
 * 通用Service
 *
 * <p>
 * 该 Service 继承的接口都是基于 Mybatis-plus 做的扩展
 *
 * @author magicianlib@gmail.com
 * @since 2021/03/05 23:59
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper
 */
public interface BaseService<T, V extends T, Pk extends Serializable>
        extends DeleteService<T, V, Pk>, SaveService<T, V, Pk>, QueryService<T, V, Pk> {

    /**
     * Lambda 查看链
     *
     * @return chain
     */
    default LambdaQueryWrapperChain<T, V, Pk> query() {
        return new LambdaQueryWrapperChain<>(this);
    }

    /**
     * Lambda 修改链
     *
     * @return chain
     */
    default LambdaUpdateWrapperChain<T, V, Pk> update() {
        return new LambdaUpdateWrapperChain<>(this);
    }

    /**
     * Lambda 删除链
     *
     * @return chain
     */
    default LambdaDeleteWrapperChain<T, V, Pk> delete() {
        return new LambdaDeleteWrapperChain<>(this);
    }
}