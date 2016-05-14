package com.clear.dao.pojo;

import java.util.Date;

public class TPlan {
    private Long id;

    private Long adId;

    private String planName;

    private Integer maxExposure;

    private Integer maxFrequency;

    private Long createUserid;

    private Date createTime;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public Integer getMaxExposure() {
        return maxExposure;
    }

    public void setMaxExposure(Integer maxExposure) {
        this.maxExposure = maxExposure;
    }

    public Integer getMaxFrequency() {
        return maxFrequency;
    }

    public void setMaxFrequency(Integer maxFrequency) {
        this.maxFrequency = maxFrequency;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
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