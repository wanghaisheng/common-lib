package com.clear.v2.conf;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by chengweisen on 2015/1/22.
 */
public class ConfigBean {

    /* 同步任务名 */
    private String subName;

    /* 数据库名 */
    private String database;

    /* 同步类型 */
    private int subType;

    /* 用户名 */
    private String username;

    /* 密码 */
    private String password;

    /* url */
    private String url;

    /* 源库 */
    private DataSource sourceDs;

    /* 目标库 */
    private DataSource targetDs;

    /* 当前正在同步表的结构 */
    private List<DataRowsBean> curTableInfo;

    /* 要同步的表 */
    private String[] tables;

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DataSource getSourceDs() {
        return sourceDs;
    }

    public void setSourceDs(DataSource sourceDs) {
        this.sourceDs = sourceDs;
    }

    public DataSource getTargetDs() {
        return targetDs;
    }

    public void setTargetDs(DataSource targetDs) {
        this.targetDs = targetDs;
    }

    public List<DataRowsBean> getCurTableInfo() {
        return curTableInfo;
    }

    public void setCurTableInfo(List<DataRowsBean> curTableInfo) {
        this.curTableInfo = curTableInfo;
    }

    public String[] getTables() {
        return tables;
    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }
}
