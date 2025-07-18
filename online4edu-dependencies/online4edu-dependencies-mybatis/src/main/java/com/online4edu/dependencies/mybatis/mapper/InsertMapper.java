package com.online4edu.dependencies.mybatis.mapper;

import java.io.Serializable;
import java.util.List;

/**
 * 基础 Mapper
 *
 * <p>
 * 基于 Mybatis Plus 做删减
 *
 * @author magicianlib@gmail.com
 * @since 2021/03/07 16:52
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper
 */
public interface InsertMapper<T, V extends T, Pk extends Serializable> {

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return 操作结果
     */
    int insert(T entity);

    /**
     * 批量新增数据,自选字段 insert
     *
     * @param entityList 实体对象
     * @return 操作结果
     */
    int insertBatchSomeColumn(List<T> entityList);
}
