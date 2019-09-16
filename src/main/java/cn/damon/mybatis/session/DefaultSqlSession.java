package cn.damon.mybatis.session;

import cn.damon.mybatis.binding.MapperProxy;
import cn.damon.mybatis.config.Configuration;
import cn.damon.mybatis.config.MappedStatement;
import cn.damon.mybatis.executor.DefaultExecutor;
import cn.damon.mybatis.executor.Executor;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @ClassName DefaultSqlSession
 * @Description
 * @Author Damon
 * @Date 2019/9/15 23:08
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
public class DefaultSqlSession implements SqlSession {

    private final Configuration config;

    private final Executor executor;

    DefaultSqlSession(Configuration config) {
        this.config = config;
        this.executor = new DefaultExecutor(config);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T selectOne(String statement, Object parameter) {
        List<Object> resultList = this.selectList(statement, parameter);
        if(resultList == null || resultList.size() == 0){
            return null;
        }
        if(resultList.size() > 1){
            throw new RuntimeException("too many result");
        }else{
            return (T)resultList.get(0);
        }
    }

    @Override
    public <T> List<T> selectList(String statement, Object parameter) {
        MappedStatement ms = config.getMapperStatements().get(statement);
        return executor.query(ms,parameter);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clazz) {
        MapperProxy mp = new MapperProxy(this);
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},mp);
    }
}
