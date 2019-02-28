package com.sun.swffsp.jpa.base;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author sun
 * @date 2019/2/28 18:07
 */
public class BaseRepositoryImpl <T> extends SimpleJpaRepository<T,String> implements BaseRepository<T> {
    private EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public BaseRepositoryImpl(Class domainClass, EntityManager em) {
        super(domainClass, em);
    }

    public int softDelete(String tableName, List ids){
        StringBuffer sb = new StringBuffer().append("update ").append(tableName)
                .append(" set status = -1 where id in (");
        ids.forEach(id->{
            sb.append(id).append(",");
        });
        String sql = sb.substring(0, sb.length() - 1) + ")";
        return entityManager.createNativeQuery(sql).executeUpdate();
    }

}