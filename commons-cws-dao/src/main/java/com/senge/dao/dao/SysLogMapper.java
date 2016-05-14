package com.clear.dao.dao;

import com.clear.dao.pojo.SysLog;
import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    List<SysLog> selectAll();

    int updateByPrimaryKey(SysLog record);
}