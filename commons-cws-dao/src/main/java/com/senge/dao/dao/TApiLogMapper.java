package com.clear.dao.dao;

import com.clear.dao.pojo.TApiLog;
import java.util.List;

public interface TApiLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TApiLog record);

    TApiLog selectByPrimaryKey(Long id);

    List<TApiLog> selectAll();

    int updateByPrimaryKey(TApiLog record);
}