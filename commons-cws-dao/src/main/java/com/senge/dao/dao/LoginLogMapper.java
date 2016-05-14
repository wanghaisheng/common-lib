package com.clear.dao.dao;

import com.clear.dao.pojo.LoginLog;
import java.util.List;

public interface LoginLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoginLog record);

    LoginLog selectByPrimaryKey(Long id);

    List<LoginLog> selectAll();

    int updateByPrimaryKey(LoginLog record);
}