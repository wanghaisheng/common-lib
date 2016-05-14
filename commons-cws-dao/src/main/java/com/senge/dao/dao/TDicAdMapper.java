package com.clear.dao.dao;

import com.clear.dao.pojo.TDicAd;
import java.util.List;

public interface TDicAdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TDicAd record);

    TDicAd selectByPrimaryKey(Long id);

    List<TDicAd> selectAll();

    int updateByPrimaryKey(TDicAd record);
}