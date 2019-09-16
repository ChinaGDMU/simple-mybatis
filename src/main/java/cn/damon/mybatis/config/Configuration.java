package cn.damon.mybatis.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Configuration
 * @Description
 * @Author Damon
 * @Date 2019/9/15 23:04
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
public class Configuration {
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;

    private Map<String,MappedStatement>  mapperStatements = new HashMap<>();


    public Map<String, MappedStatement> getMapperStatements() {
        return mapperStatements;
    }

    public void setMapperStatements(Map<String, MappedStatement> mapperStatements) {
        this.mapperStatements = mapperStatements;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }
}
