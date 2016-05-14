package com.clear.v2.sql;

import com.clear.v2.bin.MySqlSync;
import com.clear.v2.bin.UtilsFreshDml;
import com.clear.v2.conf.ConfigBean;
import com.clear.v2.conf.ConfingInit;
import com.clear.v2.conf.DataRowsBean;
import com.clear.v2.tpl.SqlFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chengweisen on 2015/1/1.
 */
public class SQLSysn {

    private final Logger logger = LoggerFactory.getLogger(SQLSysn.class);

    private QueryRunner runner = new QueryRunner();

    private static final Integer PAGE_SIZE = 1000;

    private static final ExecutorService executorService = Executors.newScheduledThreadPool(5);

    public static void main(String[] args) {
        SQLSysn sqlSysn = null;
        List<ConfigBean> configBeans = ConfingInit.getInstance().getConfigBeans();
        for (ConfigBean configBean : configBeans) {
            sqlSysn = new SQLSysn();
            sqlSysn.toDataBase(configBean);
        }
        new MySqlSync(configBeans);
        executorService.shutdown();
    }

    /**
     * @param configBean
     */
    public void toDataBase(ConfigBean configBean) {
        UtilsFreshDml utilsFreshDml = new UtilsFreshDml();
        for (String tableName : configBean.getTables()) {
            /* 单独的表分页SQL列表 */
            List<DataRowsBean> tableInfo = utilsFreshDml.getTableInfo(tableName, configBean);
            configBean.setCurTableInfo(tableInfo);
            String insertSql = SqlFactory.getInsertSql(tableName, configBean.getCurTableInfo());
            List<String> stringList = syncSqlStr(tableName, configBean);
            if (stringList == null) {
                continue;
            }
            for (String sql : stringList) {
                InsertTask task = new InsertTask(sql, insertSql, configBean);
                executorService.submit(task);
            }
        }
    }

    class InsertTask implements Runnable {

        String sql = null;

        String insertSql = null;

        ConfigBean configBean = null;

        InsertTask(String sql, String insertSql, ConfigBean configBean) {
            this.sql = sql;
            this.insertSql = insertSql;
            this.configBean = configBean;
        }

        @Override
        public void run() {
            Connection connection = null;
            try {
                connection = configBean.getTargetDs().getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            List<Object[]> objects = LoadDataByPage(sql, configBean);
            if (objects.isEmpty()) {
                return;
            }
            try {
                Object[][] params = objects.toArray(new Object[objects.size()][]);
                List<Object[]> objects1 = runner.insertBatch(connection, insertSql, new ArrayListHandler(), params);
                logger.info("已同步数据条数 ==>> " + String.format("%4d", objects1.size()) + " ===>>> " + sql);
            } catch (Exception e) {
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

    public List<String> syncSqlStr(String tableName, ConfigBean configBean) {
        int sums = getCount(tableName, configBean);
        if (sums == 0) {
            return null;
        }
        int mol = sums % PAGE_SIZE;
        int page = 0;
        if (mol > 0) {
            page = sums / PAGE_SIZE + 1;
        } else {
            page = sums / PAGE_SIZE;
        }
        List<String> sqlList = new ArrayList<String>();
        StringBuilder sql = null;
        for (int i = page; i >= 1; i--) {
            sql = new StringBuilder();
            sql.append("select * from ").append(tableName);
            sql.append(" limit ").append((page - i) * PAGE_SIZE);
            sql.append(",").append(PAGE_SIZE);
            sqlList.add(sql.toString());
        }
        return sqlList;
    }

    public int getCount(String tableName, ConfigBean configBean) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select count(1) from ").append(tableName);
        Connection connection = null;
        try {
            connection = configBean.getSourceDs().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[] query = null;
        try {
            query = runner.query(connection, stringBuilder.toString(), new ArrayHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Integer.parseInt(query[0].toString());
    }

    public List<Object[]> LoadDataByPage(String sql, ConfigBean configBean) {
        Connection connection = null;
        try {
            connection = configBean.getSourceDs().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Object[]> query = null;
        try {
            query = runner.query(connection, sql, new ArrayListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return query;
    }

}
