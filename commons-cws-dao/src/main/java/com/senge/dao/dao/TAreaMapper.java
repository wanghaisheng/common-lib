package com.clear.dao.dao;

import com.clear.dao.pojo.TArea;
import java.util.List;

public interface TAreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TArea record);

    TArea selectByPrimaryKey(Long id);

    List<TArea> selectAll();

    int updateByPrimaryKey(TArea record);
}