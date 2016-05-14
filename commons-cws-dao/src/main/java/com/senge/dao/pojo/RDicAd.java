package com.clear.dao.pojo;

import java.util.Date;

public class RDicAd {
    private Integer id;

    private String adForm;

    private String adFormat;

    private Date createTime;

    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdForm() {
        return adForm;
    }

    public void setAdForm(String adForm) {
        this.adForm = adForm == null ? null : adForm.trim();
    }

    public String getAdFormat() {
        return adFormat;
    }

    public void setAdFormat(String adFormat) {
        this.adFormat = adFormat == null ? null : adFormat.trim();
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