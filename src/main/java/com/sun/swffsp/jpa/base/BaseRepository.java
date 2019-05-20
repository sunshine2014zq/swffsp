package com.sun.swffsp.jpa.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * JPA-Repository基类接口
 *
 * @author sun
 * @date 2019/2/28 11:03
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, String>, JpaSpecificationExecutor<T>, Serializable {

    /**
     * 批量删除 - 软删除
     *
     * @param tableName
     * @param ids
     * @return
     */
    @Transactional
    public int softDelete(String tableName, List ids);

}
