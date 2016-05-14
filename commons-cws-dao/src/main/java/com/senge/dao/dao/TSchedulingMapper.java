package com.clear.dao.dao;

import com.clear.dao.pojo.TScheduling;
import java.util.List;

public interface TSchedulingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TScheduling record);

    TScheduling selectByPrimaryKey(Long id);

    List<TScheduling> selectAll();

    int updateByPrimaryKey(TScheduling record);
}