package com.clear.dao.dao;

import com.clear.dao.pojo.RCreativeMaterial;
import java.util.List;

public interface RCreativeMaterialMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RCreativeMaterial record);

    RCreativeMaterial selectByPrimaryKey(Long id);

    List<RCreativeMaterial> selectAll();

    int updateByPrimaryKey(RCreativeMaterial record);
}