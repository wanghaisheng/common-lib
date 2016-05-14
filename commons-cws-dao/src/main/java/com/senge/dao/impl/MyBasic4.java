package com.clear.dao.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chengweisen on 2014/11/1.
 */
public class MyBasic4 {

    private static QueryRunner queryRunner;

    public MyBasic4() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/admp");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setMaxActive(80);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setMinIdle(5);
        this.queryRunner = new QueryRunner(druidDataSource);
    }

    public static void main(String[] args) {
        new MyBasic4();
        /*try {
            List<Tarea> tareaList = queryRunner.query("select * from t_area", new BeanListHandler<Tarea>(Tarea.class));
            for (int i = 0; i < tareaList.size(); i++) {
                System.out.println(tareaList.get(i).getArea_name());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        getJson();

        getJsonTree();
    }

    public static void getJson() {
        String filePath = "d://cityinfo";
        try {
            List<String> lineCity = FileUtils.readLines(new File(filePath), "utf-8");
            for (String str : lineCity) {
                LocDomain locDomain = JSON.parseObject(str, LocDomain.class);

                // insert
                insertLocDomain(locDomain);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void insertLocDomain(LocDomain locDomain) {
        try {

            Object[] insert1 = null;

            Object[] insert2 = null;

            Object[] insert3 = null;

            List<Object> param = null;

            // 1
            String province1 = locDomain.getA().getRegeocode().getAddressComponent().getProvince();
            Tarea tarea1 = exitsLocDoamin(province1);
            if (tarea1 == null) {
                if (String.valueOf(province1).equals("[]")) {
                    return;
                } else {
                    param = new ArrayList<Object>();
                    param.add(province1);
                    param.add(0);
                    param.add(province1);
                    param.add(1);
                    param.add(0);
                    param.add(new Timestamp(new Date().getTime()));
                    param.add(new Timestamp(new Date().getTime()));
                    insert1 = queryRunner.insert("insert into t_area(area_name,parent_id,full_name,level,status,create_time,modify_time) values (?,?,?,?,?,?,?)",
                            new ArrayHandler(), param.toArray());
                }
            } else {
                insert1 = new Object[]{tarea1.getId()};
            }

            // 2
            String city = locDomain.getA().getRegeocode().getAddressComponent().getCity();
            Tarea tarea2 = exitsLocDoamin(city);
            if (tarea2 == null) {
                if (String.valueOf(city).equals("[]")) {
                    insert2 = insert1;
                } else {
                    param = new ArrayList<Object>();
                    param.add(city);
                    param.add(insert1[0]);
                    param.add(province1 + city);
                    param.add(2);
                    param.add(0);
                    param.add(new Timestamp(new Date().getTime()));
                    param.add(new Timestamp(new Date().getTime()));
                    insert2 = queryRunner.insert("insert into t_area(area_name,parent_id,full_name,level,status,create_time,modify_time) values (?,?,?,?,?,?,?)",
                            new ArrayHandler(), param.toArray());

                }
            } else {
                insert2 = new Object[]{tarea2.getId()};
            }

            // 3
            String district = locDomain.getA().getRegeocode().getAddressComponent().getDistrict();
            if (exitsLocDoamin(district) == null) {
                if (String.valueOf(district).equals("[]")) {
                    return;
                } else {
                    param = new ArrayList<Object>();
                    param.add(district);
                    if (insert2 == null) {
                        insert2 = insert1;
                    }
                    param.add(insert2[0]);
                    if (city.equals("[]")) {
                        city = "";
                    }
                    param.add(province1 + city + district);
                    param.add(3);
                    param.add(0);
                    param.add(new Timestamp(new Date().getTime()));
                    param.add(new Timestamp(new Date().getTime()));
                    insert3 = queryRunner.insert("insert into t_area(area_name,parent_id,full_name,level,status,create_time,modify_time) values (?,?,?,?,?,?,?)",
                            new ArrayHandler(), param.toArray());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Tarea exitsLocDoamin(String areaName) {
        Tarea query = null;
        try {
            query = queryRunner.query("select * from t_area where area_name = '" + areaName + "'", new BeanHandler<Tarea>(Tarea.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }


    private static void getJsonTree() {
        String sql = "select t.ID,t.area_name name,t.parent_id `pId` from t_area t";
        try {
            List<Area> query = queryRunner.query(sql, new BeanListHandler<Area>(Area.class));
            System.out.println(JSON.toJSON(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
