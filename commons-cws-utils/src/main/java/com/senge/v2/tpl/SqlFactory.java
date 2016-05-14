package com.clear.v2.tpl;


import com.clear.v2.conf.DataRowsBean;

import java.util.List;

/**
 * Created by chengweisen on 2014/12/29.
 */
public class SqlFactory {

    public static String getInsertSql(String tableName, List<DataRowsBean> dataRowsBeanList) {
        return new InsertSQL().getDmlSql(tableName, dataRowsBeanList);
    }

    public static String getUpdateSql(String tableName, List<DataRowsBean> dataRowsBeanList) {
        return new UpdateSQL().getDmlSql(tableName, dataRowsBeanList);
    }
}
