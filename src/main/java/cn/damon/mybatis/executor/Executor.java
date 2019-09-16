package cn.damon.mybatis.executor;

import cn.damon.mybatis.config.MappedStatement;

import java.util.List;

public interface Executor {
    /**
     * 查询结果
     *
     * @param ms 封装了sql语句的实体
     * @param parameter 参数
     * @param <E> 泛型
     * @return list
     */
    <E> List<E> query(MappedStatement ms, Object parameter);
}
