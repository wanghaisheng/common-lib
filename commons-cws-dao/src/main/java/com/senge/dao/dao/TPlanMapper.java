package com.clear.dao.dao;

import com.clear.dao.pojo.TPlan;
import java.util.List;

public interface TPlanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TPlan record);

    TPlan selectByPrimaryKey(Long id);

    List<TPlan> selectAll();

    int updateByPrimaryKey(TPlan record);
}