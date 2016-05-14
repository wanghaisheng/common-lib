package com.clear.v2.bin;

import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.clear.v2.conf.ConfigBean;
import com.clear.v2.conf.DataRowsBean;
import com.clear.v2.tpl.SqlFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.KeyedHandler;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chengweisen on 2014/12/30.
 */
public final class MyWriteRows extends ExecuteDml {

    /* tableMap 保存了对应的库及表信息-关联 */
    private TableMapEventData tableMapEventData = null;

    private QueryRunner queryRunner = null;

    private WriteRowsEventData writeRowsEventData = null;

    public MyWriteRows(WriteRowsEventData writeRowsEventData) {
        this.writeRowsEventData = writeRowsEventData;
        tableMapEventData = DitMapdb.getInstance().get(writeRowsEventData.getTableId());
        queryRunner = new QueryRunner();
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
        Connection connection = null;
        try {
            connection = targetSources.getTargetDs().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String insertSql = SqlFactory.getInsertSql(tableName, tableInfo);
        try {
            List<Serializable[]> params = writeRowsEventData.getRows();
            Object[][] insertParams = params.toArray(new Object[params.size()][]);
            queryRunner.insertBatch(connection, insertSql, new KeyedHandler<Integer>(), insertParams);
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
