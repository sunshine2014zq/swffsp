package com.sun.swffsp.entity.base;

import javax.persistence.Column;
import java.util.Date;

/**
 * 实体基础字段
 *
 * @author sun
 * @date 2019/2/20 14:48
 */
public abstract class BaseEntity {

    @Column(name="create_time")
    private Date createTime;

    @Column(name="modified_time")
    private Date modifiedTime;

    @Column(name="create_by")
    private String createBy;

    @Column(name="modified_by")
    private String modifiedBy;

    @Column(name="status")
    private Integer status;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}