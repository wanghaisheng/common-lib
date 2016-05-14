package com.clear.v2.bin;

import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.clear.v2.conf.ConfigBean;
import com.clear.v2.conf.DataRowsBean;
import com.clear.v2.tpl.SqlFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chengweisen on 2014/12/30.
 */
public final class MyUpdateRows extends ExecuteDml {

    private final Logger logger = LoggerFactory.getLogger(MyUpdateRows.class);

    /* tableMap 保存了对应的库及表信息-关联 */
    private TableMapEventData tableMapEventData = null;

    private QueryRunner queryRunner = null;

    private UpdateRowsEventData updateRowsEventData = null;

    public MyUpdateRows(UpdateRowsEventData updateRowsEventData) {
        this.updateRowsEventData = updateRowsEventData;
        tableMapEventData = DitMapdb.getInstance().get(updateRowsEventData.getTableId());
        queryRunner = new QueryRunner();
    }

    public void execute(List<ConfigBean> configBean) {
        String tableName = tableMapEventData.getTable();
        if (!tableExit(tableName, configBean)) {
            return;
        }
        UtilsFreshDml utilsFreshDml = new UtilsFreshDml();
        ConfigBean targetSources = configBean.get(0);
        List<DataRowsBean> tableInfo = utilsFreshDml.getTableInfo(tableName, targetSources);
        List<Map.Entry<Serializable[], Serializable[]>> rows = updateRowsEventData.getRows();
        Connection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("delete from ").append(tableName).append(" where ");
        for (DataRowsBean rowsBean : tableInfo) {
            if (rowsBean.getKey().equalsIgnoreCase("PRI")) {
                stringBuilder.append("`" + rowsBean.getColumnsName() + "`").append(" in (");
            }
        }
        for (DataRowsBean rowsBean : tableInfo) {
            if (rowsBean.getKey().equalsIgnoreCase("PRI")) {
                for (Map.Entry<Serializable[], Serializable[]> item : rows) {
                    stringBuilder.append(item.getKey()[rowsBean.getColumnsIndex()]).append(",");
                    stringBuilder.append(item.getValue()[rowsBean.getColumnsIndex()]).append(",");
                }
            }
        }
        if (!rows.isEmpty()) {
            stringBuilder = stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(",")).append(")");
        }

        try {
            connection = targetSources.getTargetDs().getConnection();
            queryRunner.update(connection, stringBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String insertSql = SqlFactory.getInsertSql(tableName, tableInfo);

        try {

            List<Serializable[]> inserValues = new ArrayList<Serializable[]>();
            connection = targetSources.getTargetDs().getConnection();
            for (Map.Entry<Serializable[], Serializable[]> rowItem : rows) {
                inserValues.add(rowItem.getValue());
            }
            Object[][] params = inserValues.toArray(new Object[inserValues.size()][]);
            queryRunner.insertBatch(connection, insertSql, new KeyedHandler<Integer>(), params);

            //logger.debug("update sql = " + insertSql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
