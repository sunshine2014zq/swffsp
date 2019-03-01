package com.sun.swffsp.service.base;

import com.sun.swffsp.dto.resp.ResponseFields;
import com.sun.swffsp.jpa.base.BaseRepository;
import com.sun.swffsp.utils.ReflexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * 检查数据并添加到数据库<br>
     * 请实现check()方法检查数据
     *
     * @param entity
     */
    protected Map checkAndAdd(T entity) throws ReflectiveOperationException {
        //检查新增的数据
        Map fieldErr = new HashMap();
        if (check(entity, fieldErr)) {
            //id必须为空
            String id = (String) ReflexUtils.getFieldValue(entity.getClass(), "id", entity);
            if (null != id) {
                throw new RuntimeException("新增方法：id必须为null");
            }
            //保存数据到数据库
            baseRepository.save(entity);
            return responseMap(true, "添加成功");
        }
        return responseMap(false, "添加的数据有误", fieldErr);
    }

    /**
     * 软删除 支持批量
     *
     * @param ids
     * @return
     */
    protected Map softDelete(List ids) {
        int i = baseRepository.softDelete("user", ids);
        return responseMap(true, "成功删除" + i + "条数据");
    }

    /**
     * 检查并修改不为空的字段
     *
     * @return
     */
    protected Map checkAndModifiedNotNull(T entity) throws ReflectiveOperationException {
        //检查需要修改的数据
        Map fieldErr = new HashMap();
        if (checkNotNullFields(entity, fieldErr)) {
            //id必须为空
            String id = (String) ReflexUtils.getFieldValue(entity.getClass(), "id", entity);
            if (null == id) {
                throw new RuntimeException("修改方法：id不能为null");
            }
            T query = baseRepository.findById(id).get();
            //把需要修改的实体中不为null的字段的值合并到查询出的实体
            ReflexUtils.mergeNotNull(entity.getClass(), query, entity);
            baseRepository.save(query);
        }
        return responseMap(false, "修改的数据有误", fieldErr);
    }

    /**
     * 响应结果集
     *
     * @param result 请求结果 成功/失败
     * @param msg    消息
     * @return
     */
    protected Map responseMap(boolean result, String msg) {
        Map map = new HashMap();
        map.put(ResponseFields.CRUD.STATUS, result);
        map.put(ResponseFields.CRUD.MESSAGE, msg);
        return map;
    }

    /**
     * 响应结果集
     *
     * @param result   请求结果 成功/失败
     * @param msg      消息
     * @param fieldErr 字段错误消息
     * @return
     */
    protected Map responseMap(boolean result, String msg, Map fieldErr) {
        Map map = responseMap(result, msg);
        map.put(ResponseFields.CRUD.FIELD_ERROR, fieldErr);
        return map;
    }


    /**
     * 检查需要新增的数据正确性
     *
     * @return
     */
    protected abstract boolean check(T entity, Map fieldErr);

    /**
     * 检查不为空的字段
     *
     * @param entity
     * @param fieldErr
     * @return
     */
    protected abstract boolean checkNotNullFields(T entity, Map fieldErr);

}
