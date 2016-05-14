package com.clear.v3.bin;

import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.clear.v3.conf.ConfigBean;
import com.clear.v3.conf.DataRowsBean;
import com.clear.v3.tpl.SqlFactory;
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
    private TableMapEventData tableMapEventData;

    private QueryRunner queryRunner;

    private UpdateRowsEventData updateRowsEventData;

    public MyUpdateRows(UpdateRowsEventData updateRowsEventData) {
        this.updateRowsEventData = updateRowsEventData;
        tableMapEventData = DitUtils.get(updateRowsEventData.getTableId());
        queryRunner = new QueryRunner();
    }

    public void execute(List<ConfigBean> configBean) {
        String tableName = tableMapEventData.getTable();
        if (!tableExit(tableName, configBean)) {
            logger.debug(" 你要同步的表没有配置 << {} >> ", formateTableName(tableName));
            return;
        }
        DmlUtils dmlUtils = new DmlUtils();
        ConfigBean targetSources = configBean.get(0);
        List<DataRowsBean> tableInfo = dmlUtils.getTableInfo(tableName, targetSources);
        List<Map.Entry<Serializable[], Serializable[]>> rows = updateRowsEventData.getRows();
        Connection connection = null;
        String insertSql = SqlFactory.getInsertSql(tableName, tableInfo);

        try {

            List<Serializable[]> inserValues = new ArrayList<Serializable[]>();
            connection = targetSources.getTargetDs().getConnection();
            for (Map.Entry<Serializable[], Serializable[]> rowItem : rows) {
                inserValues.add(rowItem.getValue());
            }
            Object[][] params = inserValues.toArray(new Object[inserValues.size()][]);
            Map<Integer, Map<String, Object>> integerMapMap =
                    queryRunner.insertBatch(connection, insertSql, new KeyedHandler<Integer>(), params);

            logger.info(" BINLOG 更新操作表 < {} > 同步数据条数 < {} > ", formateTableName(tableName), integerMapMap.size());

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
