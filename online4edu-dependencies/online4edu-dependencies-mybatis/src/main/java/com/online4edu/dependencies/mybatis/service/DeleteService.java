package com.online4edu.dependencies.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.io.Serializable;
import java.util.Collection;

/**
 * 基础 DeleteService
 *
 * <p>
 * 基于 Mybatis-plus 做 Service 层扩展
 *
 * @author magicianlib@gmail.com
 * @since 2021/03/05 23:44
 */
public interface DeleteService<T, V extends T, Pk extends Serializable> {

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     * @return 删除成功返回 true, 否则删除失败
     */
    boolean deleteById(Pk id);

    /**
     * 批量删除
     *
     * @param idList 主键ID集合
     * @return 删除成功个数
     */
    int deleteBatch(Collection<Pk> idList);

    /**
     * 删除所有记录
     *
     * @return 删除成功返回 true, 否则删除失败
     */
    default boolean deleteAll() {
        return delete(Wrappers.emptyWrapper());
    }

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体包装类
     * @return 删除成功返回 true, 否则删除失败
     * @see com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     */
    boolean delete(Wrapper<T> queryWrapper);
}
