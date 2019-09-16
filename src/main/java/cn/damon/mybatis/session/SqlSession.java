package cn.damon.mybatis.session;

import java.util.List;

public interface SqlSession {
    /**
     * 查询一条数据
     * @param statement 命名空间+id
     * @param parameter 参数
     * @param <T> 任意类型
     * @return T
     */
    <T> T selectOne(String statement,Object parameter);

    /**
     * 查询结果列表
     * @param statement 命名空间+id
     * @param parameter 参数
     * @param <T> 泛型
     * @return T
     */
    <T> List<T> selectList(String statement,Object parameter);


    /**
     * 获取mapper动态代理实现类
     * @param clazz clazz
     * @param <T> 泛型
     * @return T
     */
    <T> T getMapper(Class<T> clazz);
}
