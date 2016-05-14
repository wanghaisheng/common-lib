package com.clear.dao.dao;

import com.clear.dao.pojo.TCreative;
import java.util.List;

public interface TCreativeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TCreative record);

    TCreative selectByPrimaryKey(Long id);

    List<TCreative> selectAll();

    int updateByPrimaryKey(TCreative record);
}