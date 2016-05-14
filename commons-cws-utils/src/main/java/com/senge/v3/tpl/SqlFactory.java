package com.clear.v3.tpl;


import com.clear.v3.conf.DataRowsBean;

import java.util.List;

/**
 * Created by chengweisen on 2014/12/29.
 */
public class SqlFactory {

    public static String getInsertSql(String tableName, List<DataRowsBean> dataRowsBeanList) {
        return new InsertSQL().getDmlSql(tableName, dataRowsBeanList);
    }
}
