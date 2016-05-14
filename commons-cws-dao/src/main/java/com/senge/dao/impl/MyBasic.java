package com.clear.dao.impl;

/**
 * Created by chengweisen on 2014/11/1.
 */
public class MyBasic {


    public static void main(String[] args) {
        /*String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/report2";
        String user = "root";
        String password = "1qaz2wsx";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, password);
            if (!conn.isClosed()) {
                String sql = "select * from bs_itembase";
                System.out.println("Succeeded connecting to the Database!");
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                if (rs.next()) {
                    // 得到ResultSetMetaData
                    ResultSetMetaData rsmd = rs.getMetaData();
                    System.out.println(rsmd.getColumnCount());
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        Hashtable hst = new Hashtable();
                        // 把字段名放入Name
                        String name = String.valueOf(rsmd.getColumnLabel(i));
                        hst.put("Name", name);
                        // 把字段类型放入Type
                        String type = String.valueOf(rsmd.getColumnType(i));
                        hst.put("Type", type);
                        System.out.println(hst.get("Name") + "  " + hst.get("Type"));
                    }
                }
            }
        } catch (Exception e) {

            System.out.println("出现异常");
        }*/
    }

}
