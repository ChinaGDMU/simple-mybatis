package cn.damon.mybatis.executor;

import cn.damon.mybatis.config.Configuration;
import cn.damon.mybatis.config.MappedStatement;
import cn.damon.mybatis.reflection.ReflectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DefaultExecutor
 * @Description
 * @Author Damon
 * @Date 2019/9/15 23:14
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
public class DefaultExecutor implements Executor {

    private final Configuration conf;

    public DefaultExecutor(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) {
        //定义返回的结果集
        List<E> result = new ArrayList<>();
        try {
            Class.forName(conf.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(conf.getJdbcUrl(), conf.getJdbcUsername(), conf.getJdbcPassword());
            pst = conn.prepareStatement(ms.getSql());
            //处理参数
            parameterize(pst, parameter);
            rs = pst.executeQuery();
            handlerResultSet(rs, result, ms.getResultType());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return result;
    }


    //对prepareStatement中的占位符进行处理
    private void parameterize(PreparedStatement prepareStatement, Object parameter) throws SQLException {
        if (parameter instanceof Integer) {
            prepareStatement.setInt(1, (int) parameter);
        } else if (parameter instanceof Long) {
            prepareStatement.setLong(1, (long) parameter);
        } else if (parameter instanceof String) {
            prepareStatement.setString(1, (String) parameter);
        }

    }


    //读取resultset中的数据，并转换成目标对象
    private <E> void handlerResultSet(ResultSet resultSet, List<E> ret, String className) {
        Class<E> clazz = null;
        try {
            //通过反射获取类对象
            clazz = (Class<E>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                if (clazz == null) {
                    continue;
                }
                //通过反射实例化对象
                Object entity = clazz.newInstance();
                //使用反射工具将resultSet中的数据填充到entity中
                ReflectionUtil.setPropToBeanFromResultSet(entity, resultSet);
                //对象加入返回集合中
                ret.add((E) entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
