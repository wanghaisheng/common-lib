package com.clear.v3.tpl;


import com.clear.v3.conf.DataRowsBean;

import java.util.List;

/**
 * Created by chengweisen on 2014/12/29.
 */
public interface DmlSql {

    public String getDmlSql(String tableName, List<DataRowsBean> dataRowsBeanList);
}
