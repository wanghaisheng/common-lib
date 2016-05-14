package com.clear.dao.dao;

import com.clear.dao.pojo.TAdPosition;
import java.util.List;

public interface TAdPositionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TAdPosition record);

    TAdPosition selectByPrimaryKey(Long id);

    List<TAdPosition> selectAll();

    int updateByPrimaryKey(TAdPosition record);
}