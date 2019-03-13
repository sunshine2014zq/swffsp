package com.sun.swffsp.dto.req.base;

import lombok.Data;

/**
 * 分页请求通用参数
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
