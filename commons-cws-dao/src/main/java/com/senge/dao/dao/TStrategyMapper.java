package com.clear.dao.dao;

import com.clear.dao.pojo.TStrategy;
import java.util.List;

public interface TStrategyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TStrategy record);

    TStrategy selectByPrimaryKey(Long id);

    List<TStrategy> selectAll();

    int updateByPrimaryKey(TStrategy record);
}