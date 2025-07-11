package com.online4edu.dependencies.mybatis.chain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import com.online4edu.dependencies.mybatis.service.BaseService;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * Lambda 删除 Wrapper
 *
 * @author magicianlib@gmail.com
 * @since 2021/03/06 00:04
 */
public class LambdaDeleteWrapperChain<T, V extends T, Pk extends Serializable>
        extends AbstractChainWrapper<T, SFunction<T, ?>, LambdaDeleteWrapperChain<T, V, Pk>, LambdaQueryWrapper<T>>
        implements Query<LambdaDeleteWrapperChain<T, V, Pk>, T, SFunction<T, ?>> {

    private final BaseService<T, V, Pk> baseService;

    public LambdaDeleteWrapperChain(BaseService<T, V, Pk> baseService) {
        super();
        this.baseService = baseService;
        super.wrapperChildren = new LambdaQueryWrapper<>();
    }

    @SafeVarargs
    @Override
    public final LambdaDeleteWrapperChain<T, V, Pk> select(SFunction<T, ?>... columns) {
        wrapperChildren.select(columns);
        return typedThis;
    }

    @Override
    public LambdaDeleteWrapperChain<T, V, Pk> select(Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(predicate);
        return typedThis;
    }

    @Override
    public LambdaDeleteWrapperChain<T, V, Pk> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(entityClass, predicate);
        return typedThis;
    }

    @Override
    public String getSqlSelect() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSelect");
    }

    @SuppressWarnings("unchecked")
    public boolean execute() {
        return baseService.delete(getWrapper());
    }

}