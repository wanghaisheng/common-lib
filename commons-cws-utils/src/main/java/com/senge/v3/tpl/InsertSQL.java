package com.clear.v3.tpl;


import com.clear.v3.conf.DataRowsBean;

import java.util.List;

/**
 * Created by chengweisen on 2014/12/29.
 */
public class InsertSQL implements DmlSql {

    @Override
    public String getDmlSql(String tableName, List<DataRowsBean> dataRowsBeanList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("replace into " + tableName + "(");
        for (DataRowsBean key : dataRowsBeanList) {
            stringBuilder.append("`" + key.getColumnsName() + "`").append(",");
        }
        if (!dataRowsBeanList.isEmpty()) {
            stringBuilder = stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        }
        stringBuilder.append(") values(");
        for (DataRowsBean values : dataRowsBeanList) {
            stringBuilder.append("?").append(",");
        }
        if (!dataRowsBeanList.isEmpty()) {
            stringBuilder = stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
