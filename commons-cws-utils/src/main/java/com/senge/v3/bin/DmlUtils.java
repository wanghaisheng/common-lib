package com.clear.v3.bin;

import com.clear.v3.conf.ConfigBean;
import com.clear.v3.conf.DataRowsBean;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengweisen on 2014/12/29.
 */
public final class DmlUtils {

    private QueryRunner queryRunner;

    private static final Logger logger = LoggerFactory.getLogger(DmlUtils.class);

    public DmlUtils() {
        this.queryRunner = new QueryRunner();
    }

    /**
     * 取表的字段信息
     *
     * @param tableName
     * @return
     */
    public List<DataRowsBean> getTableInfo(String tableName, ConfigBean configBean) {
        List<DataRowsBean> itemColumns = new ArrayList<DataRowsBean>();
        DataRowsBean dataRowsBean;
        Connection connection = null;
        try {
            connection = configBean.getTargetDs().getConnection();
        } catch (SQLException e) {
            logger.debug("get connection is error..................");
            e.printStackTrace();
        }
        try {
            String sql = "show full fields from " + tableName;
            List<Object[]> query = queryRunner.query(connection, sql, new ArrayListHandler());
            for (int i = 0, j = query.size(); i < j; i++) {
                dataRowsBean = new DataRowsBean();
                dataRowsBean.setColumnsName(query.get(i)[0].toString());
                dataRowsBean.setColumnsIndex(i);
                dataRowsBean.setKey(query.get(i)[4].toString());
                itemColumns.add(dataRowsBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return itemColumns;
    }

}
