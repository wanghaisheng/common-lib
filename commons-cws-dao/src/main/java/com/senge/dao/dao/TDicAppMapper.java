package com.clear.dao.dao;

import com.clear.dao.pojo.TDicApp;
import java.util.List;

public interface TDicAppMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TDicApp record);

    TDicApp selectByPrimaryKey(Long id);

    List<TDicApp> selectAll();

    int updateByPrimaryKey(TDicApp record);
}