package com.clear.dao.pojo;

import java.util.Date;

public class TScheduling {
    private Long id;

    private Long strId;

    private Long creId;

    private Long areaId;

    private Date date;

    private Integer status;

    private String time;

    private Date createTime;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStrId() {
        return strId;
    }

    public void setStrId(Long strId) {
        this.strId = strId;
    }

    public Long getCreId() {
        return creId;
    }

    public void setCreId(Long creId) {
        this.creId = creId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}