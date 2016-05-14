package com.clear.v2.tpl;


import com.clear.v2.conf.DataRowsBean;

import java.util.List;

/**
 * Created by chengweisen on 2014/12/29.
 */
public class UpdateSQL implements DmlSql {


    @Override
    public String getDmlSql(String tableName, List<DataRowsBean> dataRowsBeanList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("update ").append(tableName).append(" set ");
        for (DataRowsBean keyColums : dataRowsBeanList) {
            if (keyColums.getKey() == "PRI") {
                stringBuilder.append(keyColums.getColumnsName()).append("=").append("?").append(",");
            }
        }
        if (!dataRowsBeanList.isEmpty()) {
            stringBuilder = stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        }
        stringBuilder.append(" where ");
        for (DataRowsBean keyColums : dataRowsBeanList) {
            if (keyColums.getKey() == "PRI") {
                stringBuilder.append("`" + keyColums.getColumnsName() + "`").append(" = ").append("?");
            }
        }
        return stringBuilder.toString();
    }
}
