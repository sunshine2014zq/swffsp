package com.sun.swffsp.service.base;


import com.alibaba.druid.util.StringUtils;
import org.aspectj.weaver.ast.Var;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Predicate查询工具
 *
 * @author sun
 * @date 2019/3/1 16:22
 */
public class PredicateUtils {

    private Root root;
    private CriteriaQuery query;
    private CriteriaBuilder criteriaBuilder;
    private List<Predicate> predicateList = new ArrayList<>();

    public PredicateUtils(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        this.root = root;
        this.query = query;
        this.criteriaBuilder = criteriaBuilder;
    }

    /**
     * like查询
     *
     * @param name    字段名-java实体
     * @param pattern like表达式
     * @return
     */
    public Predicate like(String name, String pattern) {
        if (null != pattern && !StringUtils.isEmpty(name)) {
            return criteriaBuilder.like(root.get(name), pattern);
        }
        return null;
    }

    /**
     * in查询
     *
     * @param name   字段名-java实体
     * @param values 值
     * @return
     */
    public Predicate in(String name, Object... values) {
        return root.get(name).in(values);
    }

    /**
     * not in 查询
     *
     * @param name   字段名-java实体
     * @param values 值
     * @return
     */
    public Predicate notIn(String name, Object... values) {
        return criteriaBuilder.not(root.get(name).in(values));
    }

    /**
     * 等值查询
     * @param name
     * @param val
     * @return
     */
    public Predicate equal(String name,Object val){
        return criteriaBuilder.equal(root.get(name),val);
    }

    /**
     * 连接Predicate
     *
     * @param predicate
     * @return
     */
    public PredicateUtils and(Predicate predicate) {
        if (null != predicate) {
            predicateList.add(predicate);
        }
        return this;
    }



    /**
     * 将所有已连接的Predicate装配成新的Predicate
     *
     * @return
     */
    public Predicate getPredicate() {
        Predicate[] predicates = new Predicate[predicateList.size()];
        return criteriaBuilder.and(predicateList.toArray(predicates));
    }

}
