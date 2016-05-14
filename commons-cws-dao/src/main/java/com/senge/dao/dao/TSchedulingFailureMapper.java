package com.clear.dao.dao;

import com.clear.dao.pojo.TSchedulingFailure;
import java.util.List;

public interface TSchedulingFailureMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSchedulingFailure record);

    TSchedulingFailure selectByPrimaryKey(Long id);

    List<TSchedulingFailure> selectAll();

    int updateByPrimaryKey(TSchedulingFailure record);
}