package com.clear.dao.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class TAdPosition {
    private Long id;

    private String positionName;

    private Long appId;

    private String types;

    private Integer width;

    private Integer height;

    private BigDecimal size;

    private Integer status;

    private String isExternalLink;

    private String interactiveMode;

    private String adStyle;

    private Date modifyTime;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName == null ? null : positionName.trim();
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types == null ? null : types.trim();
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIsExternalLink() {
        return isExternalLink;
    }

    public void setIsExternalLink(String isExternalLink) {
        this.isExternalLink = isExternalLink == null ? null : isExternalLink.trim();
    }

    public String getInteractiveMode() {
        return interactiveMode;
    }

    public void setInteractiveMode(String interactiveMode) {
        this.interactiveMode = interactiveMode == null ? null : interactiveMode.trim();
    }

    public String getAdStyle() {
        return adStyle;
    }

    public void setAdStyle(String adStyle) {
        this.adStyle = adStyle == null ? null : adStyle.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}