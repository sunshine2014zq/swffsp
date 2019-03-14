package com.sun.swffsp.dto.core.base;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 底层实体：数据库映射实体通用字段
 *
 * @author sun
 * @date 2019/2/20 14:48
 */
@MappedSuperclass
@Data
public abstract class BaseDto implements Serializable {

    /**
     * 主键-UUID
     */
    @Id
    @GeneratedValue(generator = "system_uuid")
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
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

    /**
     * 正常状态
     */
    public static final Integer STATUS_NORMAL = 1;
    /**
     * 无效状态-列表可见
     */
    public static final Integer STATUS_INVALID = 0;
    /**
     * 已删除状态-列表不可见<br>
     * 该状态数据任何时候不计入查询等其他操作,除了恢复数据时
     */
    public static final Integer STATUS_DELETED = -1;

    @Version
    @Column(name = "version")
    private Long version;
}