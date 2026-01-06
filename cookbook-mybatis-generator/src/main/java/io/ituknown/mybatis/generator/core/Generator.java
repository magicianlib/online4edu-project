package io.ituknown.mybatis.generator.core;

import io.ituknown.mybatis.generator.domain.TableSign;

import java.util.List;

/**
 * 文件生成
 *
 * @author magicianlib@gmail.com
 * @since 2021/03/30 20:49
 */
public interface Generator {

    /**
     * 生成具体文件
     *
     * @param tableSignList 数据表签名
     */
    void fileGenerator(List<TableSign> tableSignList);
}