package com.clear.v2.bin;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.clear.v2.conf.ConfigBean;
import com.clear.v2.conf.DataRowsBean;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chengweisen on 2014/12/30.
 */
public final class MyDeleteRows extends ExecuteDml {

    private final Logger logger = LoggerFactory.getLogger(MyDeleteRows.class);

    /* tableMap 保存了对应的库及表信息-关联 */
    private TableMapEventData tableMapEventData = null;

    private QueryRunner queryRunner = null;

    private DeleteRowsEventData deleteRowsEventData = null;

    public MyDeleteRows(DeleteRowsEventData deleteRowsEventData) {
        this.deleteRowsEventData = deleteRowsEventData;
        tableMapEventData = DitMapdb.getInstance().get(deleteRowsEventData.getTableId());
        this.queryRunner = new QueryRunner();
    }

    @Override
    public void execute(List<ConfigBean> configBean) {
        String tableName = tableMapEventData.getTable();
        if (!tableExit(tableName, configBean)) {
            return;
        }
        UtilsFreshDml utilsFreshDml = new UtilsFreshDml();
        ConfigBean targetSources = configBean.get(0);
        List<DataRowsBean> tableInfo = utilsFreshDml.getTableInfo(tableName, targetSources);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("delete from ").append(tableName).append(" where ");
        for (DataRowsBean dataRows : tableInfo) {
            if (dataRows.getKey().equalsIgnoreCase("PRI")) {
                stringBuilder.append("`" + dataRows.getColumnsName() + "`").append(" in (");
            }
        }
        List<Serializable[]> rows = deleteRowsEventData.getRows();
        for (Serializable[] row : rows) {
            for (DataRowsBean dataRows : tableInfo) {
                if (dataRows.getKey().equalsIgnoreCase("PRI")) {
                    stringBuilder.append(row[dataRows.getColumnsIndex()]).append(",");
                }
            }
        }
        if (!rows.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        }
        stringBuilder.append(")");
        Connection connection = null;
        try {
            connection = targetSources.getTargetDs().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            queryRunner.update(connection, stringBuilder.toString());
            //logger.debug("delete SQL：" + stringBuilder.toString());
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
