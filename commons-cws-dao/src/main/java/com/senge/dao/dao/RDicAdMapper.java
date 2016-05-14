package com.clear.dao.dao;

import com.clear.dao.pojo.RDicAd;
import java.util.List;

public interface RDicAdMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RDicAd record);

    RDicAd selectByPrimaryKey(Integer id);

    List<RDicAd> selectAll();

    int updateByPrimaryKey(RDicAd record);
}