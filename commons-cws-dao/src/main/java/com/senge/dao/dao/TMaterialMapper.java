package com.clear.dao.dao;

import com.clear.dao.pojo.TMaterial;
import java.util.List;

public interface TMaterialMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TMaterial record);

    TMaterial selectByPrimaryKey(Long id);

    List<TMaterial> selectAll();

    int updateByPrimaryKey(TMaterial record);
}