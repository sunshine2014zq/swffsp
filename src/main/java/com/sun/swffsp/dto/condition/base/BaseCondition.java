package com.sun.swffsp.dto.condition.base;

/**
 * 通用查询条件
 *
 * @author sun
 * @date 2019/2/26 10:17
 */
public class BaseCondition {

    /**
     * 页面
     */
    private Integer page;

    /**
     * 每页数据条数
     */
    private Integer size;

    public Integer getPage() {
        return page-1;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
