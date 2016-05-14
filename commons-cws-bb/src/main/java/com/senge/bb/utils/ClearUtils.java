package com.clear.bb.utils;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.KeyedHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by chengweisen on 2015/4/2.
 */
public class ClearUtils {

    private static QueryRunner runner = new QueryRunner();

    public static void main(String[] args) {
        List<ConfigBean> configBeans = LoadConfig.getConfigBeans();
        Map<Number, Object> sources = getSources(configBeans);
        Map<Number, Object> targets = getTargets(configBeans);
        for (Number key : targets.keySet()) {
            if (!sources.containsKey(key)) {
                deleteRecords(configBeans, key);
            }
        }
    }

    private static void deleteRecords(List<ConfigBean> configBeans, Number parmas) {
        ConfigBean configBean = configBeans.get(0);
        Connection connection = null;
        try {
            connection = configBean.getTargetDs().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "delete from " + configBean.getTables()[0] + " where id = ?";
        try {
            int update = runner.update(connection, sql, parmas);
            System.out.println("============" + update + "============");
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

    private static Map<Number, Object> getSources(List<ConfigBean> configBeans) {
        ConfigBean configBean = configBeans.get(0);
        Connection connection = null;
        Map<Number, Object> query = null;
        try {
            connection = configBean.getSourceDs().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            query = (Map) runner.query(connection, "select id from " + configBean.getTables()[0], new KeyedHandler("id"));
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

    private static Map<Number, Object> getTargets(List<ConfigBean> configBeans) {
        ConfigBean configBean = configBeans.get(0);
        Connection connection = null;
        Map<Number, Object> query = null;
        try {
            connection = configBean.getTargetDs().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            query = (Map) runner.query(connection, "select id from " + configBean.getTables()[0], new KeyedHandler("id"));
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
