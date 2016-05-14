package com.clear.v2.tpl;


import com.clear.v2.conf.DataRowsBean;

import java.util.List;

/**
 * Created by chengweisen on 2014/12/29.
 */
public interface DmlSql {

    public String getDmlSql(String tableName, List<DataRowsBean> dataRowsBeanList);
}
