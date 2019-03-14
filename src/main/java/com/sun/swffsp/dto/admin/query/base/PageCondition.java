package com.sun.swffsp.dto.admin.query.base;

import lombok.Data;

/**
 * 分页查询条件
 *
 * @author sun
 * @date 2019/2/26 10:17
 */
@Data
public class PageCondition {

    /**
     * 页面
     */
    private Integer page;

    /**
     * 每页数据条数
     */
    private Integer size;

}
