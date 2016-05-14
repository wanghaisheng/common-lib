package com.clear.dao.dao;

import com.clear.dao.pojo.TAdvertisers;
import java.util.List;

public interface TAdvertisersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TAdvertisers record);

    TAdvertisers selectByPrimaryKey(Long id);

    List<TAdvertisers> selectAll();

    int updateByPrimaryKey(TAdvertisers record);
}