package com.clear.v3.bin;

import com.github.shyiko.mysql.binlog.event.QueryEventData;
import com.clear.v3.conf.ConfigBean;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chengweisen on 2015/4/6.
 */
public class MyddlOperate extends ExecuteDml {

    private QueryRunner queryRunner;

    private QueryEventData queryEventData;

    private static final Logger logger = LoggerFactory.getLogger(MyddlOperate.class);

    public MyddlOperate(QueryEventData queryEventData) {
        this.queryRunner = new QueryRunner();
        this.queryEventData = queryEventData;
    }

    @Override
    public void execute(List<ConfigBean> configBean) {
        String sql = queryEventData.getSql();
        if ("BEGIN".equalsIgnoreCase(sql)) {
            return;
        }
        Connection connection = null;
        try {
            connection = configBean.get(0).getTargetDs().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            int update = queryRunner.update(connection, sql);
            logger.debug(" DDL 操作 = {}", update);
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
