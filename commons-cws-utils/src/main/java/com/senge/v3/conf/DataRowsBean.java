package com.clear.v3.conf;

/**
 * Created by chengweisen on 2014/12/30.
 */
public class DataRowsBean {

    private String columnsName;

    private Integer columnsIndex;

    private Object columnsData;

    private String key;

    public String getColumnsName() {
        return columnsName;
    }

    public void setColumnsName(String columnsName) {
        this.columnsName = columnsName;
    }

    public Integer getColumnsIndex() {
        return columnsIndex;
    }

    public void setColumnsIndex(Integer columnsIndex) {
        this.columnsIndex = columnsIndex;
    }

    public Object getColumnsData() {
        return columnsData;
    }

    public void setColumnsData(Object columnsData) {
        this.columnsData = columnsData;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
