package com.clear.dao.impl;

import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chengweisen on 2014/11/1.
 */
public class MyBasic1 {


    public static void main(String[] args) {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/masterbasic";
        String user = "root";
        String password = "1qaz2wsx";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, password);
            getPrimaryKeys("bs_goodscode_rela", conn);
            /*if (!conn.isClosed()) {
                String sql = "insert into a_carrydown_latch values(?,?,?)";
                QueryRunner run = new QueryRunner();
                Object[] params = {"dsxza", "0000-00-00 :00:0000", "1"};
                run.insert(conn, sql, new ArrayListHandler(), params);
            }*/
        } catch (Exception e) {

            System.out.println(e);
        }
    }

    /**
     * 读取表的唯一值（primary key,union key-联合唯一索引）
     * @param table
     * @param conn
     * @return
     */
    public static String getPrimaryKeys(String table, Connection conn) {
        String keys = "";
        ResultSet rs = null;
        try {
            rs = conn.getMetaData().getIndexInfo(null, null, table, true, true);
            while (rs.next()) {// 注意：结果集中存在表的很多信息，字段名称在第 4 列
                System.out.println(rs.getString("INDEX_NAME") + " - " + rs.getString("COLUMN_NAME") + " - " + rs.getString("NON_UNIQUE") + " - " + rs.getString("TYPE"));
            }
            if (!keys.equals("")) { // 去掉最后面的逗号
                keys = keys.substring(0, keys.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return keys;
    }

}
