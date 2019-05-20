package com.sun.swffsp.dto.admin.param.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询条件
 *
 * @author sun
 * @date 2019/2/26 10:17
 */
@Data
public class PageQuery implements Serializable {

    /**
     * 页面
     */
    private Integer page;

    /**
     * 每页数据条数
     */
    private Integer size;

    public Integer getPage() {
        return (page > 0) ? (page - 1) : 0;
    }
}
