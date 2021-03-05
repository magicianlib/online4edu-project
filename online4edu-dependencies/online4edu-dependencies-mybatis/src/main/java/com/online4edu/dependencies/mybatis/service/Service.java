package com.online4edu.dependencies.mybatis.service;

import com.online4edu.dependencies.mybatis.chain.LambdaDeleteWrapperChain;
import com.online4edu.dependencies.mybatis.chain.LambdaQueryWrapperChain;
import com.online4edu.dependencies.mybatis.chain.LambdaUpdateWrapperChain;

import java.io.Serializable;

/**
 * 通用Service
 *
 * <p>
 * 该 Service 继承的接口都是基于 Mybatis-plus 做的扩展
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/05 23:59
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper
 */
public interface Service<T, DTO extends T, PK extends Serializable>
        extends BaseDelete<T, DTO, PK>, BaseUpdate<T, DTO, PK>, BaseQuery<T, DTO, PK> {

    /**
     * Lambda 查看链
     *
     * @return chain
     */
    default LambdaQueryWrapperChain<T, DTO, PK> query() {
        return new LambdaQueryWrapperChain<>(this);
    }

    /**
     * Lambda 修改链
     *
     * @return chain
     */
    default LambdaUpdateWrapperChain<T, DTO, PK> update() {
        return new LambdaUpdateWrapperChain<>(this);
    }

    /**
     * Lambda 删除链
     *
     * @return chain
     */
    default LambdaDeleteWrapperChain<T, DTO, PK> delete() {
        return new LambdaDeleteWrapperChain<>(this);
    }
}