package com.sun.swffsp.service.base;

import com.sun.swffsp.dto.db.base.BaseEntity;
import com.sun.swffsp.dto.resp.Response;
import com.sun.swffsp.jpa.base.BaseRepository;
import com.sun.swffsp.utils.ReflexUtils;
import com.sun.swffsp.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用业务
 *
 * @author sun
 * @date 2019/2/27 11:47
 */
public abstract class BaseService<T> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private BaseRepository<T> baseRepository;

    public void setBaseRepository(BaseRepository<T> baseRepository) {
        this.baseRepository = baseRepository;
    }

    /**
     * 初始化父类的baseRepository属性<br>
     * 请在子类中调用super.setBaseRepository(*)初始化<br>
     */
    @PostConstruct
    public abstract void initBaseRepository();

    /**
     * 软删除 支持批量
     *
     * @param ids
     * @return
     */
    protected Response softDelete(List ids) {
        int i = baseRepository.softDelete("user", ids);
        return Response.ok("成功删除" + i + "条数据");
    }

    /**
     * 保存<br>
     * id为空时,添加对象到数据库<br>
     * id不为空时,修改对象不为空的字段到数据库
     * @param entity
     * @return
     * @throws ReflectiveOperationException
     */
    protected Response saveNotNull(T entity) throws ReflectiveOperationException {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        String id = (String) ReflexUtils.getFieldValue(entity.getClass(), "id", entity);
        if (!StringUtils.isEmpty(id)) {
            T query = baseRepository.findById(id).get();
            //把需要修改的实体中不为null的字段的值合并到查询出的实体
            entity = (T) ReflexUtils.mergeNotNull(entity.getClass(), query, entity);
        } else {
            //新增默认状态为1
            ReflexUtils.setFieldValue(entity.getClass(),entity,"status",BaseEntity.STATUS_NORMAL);
            ReflexUtils.setFieldValue(entity.getClass(),entity,"createBy",currentUser);
        }
        ReflexUtils.setFieldValue(entity.getClass(),entity,"modifiedBy",currentUser);
        baseRepository.save(entity);
        return Response.ok("保存成功");
    }

    /**
     * 检查并保存对象保存<br>
     * id为空时,添加对象到数据库<br>
     * id不为空时,修改对象不为空的字段到数据库
     * @param entity
     * @return
     * @throws ReflectiveOperationException
     */
    protected Response checkAndSaveNotNull(T entity) throws ReflectiveOperationException {
        Map fieldMap = new HashMap();
        if(check(entity,fieldMap)){
            return saveNotNull(entity);
        }
        return Response.fail("保存失败").data(fieldMap);
    }

    /**
     * 检查需要新增的数据正确性
     *
     * @return
     */
    protected abstract boolean check(T entity, Map fieldErr);

}
