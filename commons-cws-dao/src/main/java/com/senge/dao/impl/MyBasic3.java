package com.clear.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chengweisen on 2014/11/1.
 */
public class MyBasic3 {


    public static void main(String[] args) {
        /*String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/masterbasic";
        String user = "root";
        String password = "1qaz2wsx";
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            if (!conn.isClosed()) {
                String sql = "insert into a_carrydown_latch value (?,?,?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                for (int i = 0; i < 1000; i++) {
                    statement.setInt(1, i);
                    statement.setString(2, DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                    statement.setInt(3, i);
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/

        List<Object> ids = new ArrayList<Object>();
        ids.add("a");
        ids.add("b");
        ids.add("c");
        ids.add("a");
        System.out.println(Arrays.toString(ids.toArray()));

    }

    public void toStringTest() {
        List<Object> ids = new ArrayList<Object>();
        ids.add("a");
        ids.add("b");
        ids.add("c");
        ids.add("a");
        System.out.println(Arrays.deepToString(ids.toArray()));

    }

}
