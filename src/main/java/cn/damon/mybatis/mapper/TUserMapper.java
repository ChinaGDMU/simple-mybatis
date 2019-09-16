package cn.damon.mybatis.mapper;

import cn.damon.mybatis.entity.TUser;

import java.util.List;

public interface TUserMapper {
    TUser selectByPrimaryKey(Integer id);
    List<TUser>  selectAll();
}
