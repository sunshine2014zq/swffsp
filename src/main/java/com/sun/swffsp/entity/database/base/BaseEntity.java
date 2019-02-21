package com.sun.swffsp.entity.database.base;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体基础字段
 *
 * @author sun
 * @date 2019/2/20 14:48
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    /**
     * 主键-UUID
     */
    @Id
    @GeneratedValue
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 修改时间
     */
    @UpdateTimestamp
    @Column(name = "modified_time")
    private Date modifiedTime;

    /**
     * 创建者
     */
    @Column(name = "create_by", length = 32, nullable = false)
    private String createBy;

    /**
     * 修改人
     */
    @Column(name = "modified_by", length = 32)
    private String modifiedBy;

    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;

    @Version
    @Column(name = "version")
    private long version;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}