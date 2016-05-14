package com.clear.v3;

import com.clear.v3.bin.DmlUtils;
import com.clear.v3.bin.MySqlBinRecives;
import com.clear.v3.conf.ConfigBean;
import com.clear.v3.conf.ConfingInit;
import com.clear.v3.conf.DataRowsBean;
import com.clear.v3.tpl.SqlFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chengweisen on 2015/1/1.
 */
public final class MysqlSync {

    private static final Logger logger = LoggerFactory.getLogger(MysqlSync.class);

    private static QueryRunner runner = new QueryRunner();

    private static final Integer PAGE_SIZE = 500;

    private static final ExecutorService executorService = Executors.newScheduledThreadPool(9);

    public static void main(String[] args) {
        String osbase = System.getProperty("os.base");
        String configFilename = osbase + "/conf/log4j.properties";
        PropertyConfigurator.configure(configFilename);
        List<ConfigBean> configBeans = ConfingInit.getInstance().getConfigBeans();
        for (ConfigBean configBean : configBeans) {
            new MysqlSync().toDataBase(configBean);
        }
        new MySqlBinRecives(configBeans);
        new Timer().schedule(new CheckExecutorServiceIsTerminated(), 60000, 1000);
    }

    /**
     * @param configBean
     */
    public void toDataBase(ConfigBean configBean) {
        for (String tableName : configBean.getTables()) {
            executorService.submit(new TableTask(tableName, configBean));
        }
    }

    /**
     * 检测SQL同步的线程池任务是否都完成
     */
    static class CheckExecutorServiceIsTerminated extends TimerTask {
        @Override
        public void run() {
            if (executorService.isTerminated()) {
                executorService.shutdown();
            }
        }
    }

    class TableTask implements Runnable {

        String tableName;

        DmlUtils dmlUtils;

        ConfigBean configBean;

        public TableTask(String tableName, ConfigBean configBean) {
            this.tableName = tableName;
            this.dmlUtils = new DmlUtils();
            this.configBean = configBean;
        }

        @Override
        public void run() {
            List<DataRowsBean> tableInfo = dmlUtils.getTableInfo(tableName, configBean);
            configBean.setCurTableInfo(tableInfo);
            String insertSql = SqlFactory.getInsertSql(tableName, configBean.getCurTableInfo());
            List<String> stringList = syncSqlStr(tableName, configBean);
            if (stringList == null) {
                return;
            }
            for (String sql : stringList) {
                InsertTask task = new InsertTask(sql, insertSql, configBean);
                executorService.submit(task);
            }
        }
    }

    class InsertTask implements Runnable {

        String sql;

        String insertSql;

        ConfigBean configBean;

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
        int page;
        if (mol > 0) {
            page = sums / PAGE_SIZE + 1;
        } else {
            page = sums / PAGE_SIZE;
        }
        List<String> sqlList = new ArrayList<String>();
        StringBuilder sql;
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
