package com.sun.swffsp.service.base;

import com.sun.swffsp.dto.admin.result.FieldErrorsResult;
import com.sun.swffsp.dto.admin.result.base.Response;
import com.sun.swffsp.dto.core.base.BaseDto;
import com.sun.swffsp.jpa.base.BaseRepository;
import com.sun.swffsp.utils.ReflexUtils;
import com.sun.swffsp.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 通用业务
 *
 * @author sun
 * @date 2019/2/27 11:47
 */
public abstract class BaseService<T extends BaseDto> {

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
        return Response.success("成功删除" + i + "条数据");
    }

    /**
     * 保存<br>
     * id为空时,添加对象到数据库<br>
     * id不为空时,修改对象不为空的字段到数据库
     *
     * @param entity
     * @return
     * @throws ReflectiveOperationException
     */
    protected Response saveNotNull(T entity) throws ReflectiveOperationException {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!isAdd(entity)) {
            T query = baseRepository.findById(entity.getId()).get();
            //把需要修改的实体中不为null的字段的值合并到查询出的实体
            entity = (T) ReflexUtils.mergeNotNull(entity.getClass(), query, entity);
        } else {
            //新增默认状态为1
            entity.setStatus(BaseDto.STATUS_NORMAL);
            entity.setCreatedBy(currentUser);
        }
        entity.setModifiedBy(currentUser);
        baseRepository.save(entity);
        return Response.success("保存成功");
    }

    /**
     * 检查并保存对象保存<br>
     * id为空时,添加对象到数据库<br>
     * id不为空时,修改对象不为空的字段到数据库
     *
     * @param entity
     * @return
     * @throws ReflectiveOperationException
     */
    protected Response validateAndSaveNotNull(T entity) throws ReflectiveOperationException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations;
        if (isAdd(entity)) {
            //新增
            violations = validator.validate(entity, BaseDto.Add.class);
        } else {
            //校验值不为空的字段
            violations = validator.validate(entity, BaseDto.Update.class);
        }
        if (!violations.isEmpty()) {
            //封装错误消息
            List<FieldErrorsResult> errors = new ArrayList<>();
            violations.forEach(violation -> {
                addError(errors, violation.getPropertyPath().toString(), violation.getMessage());
            });
            return Response.fail("保存失败").data(errors);
        }
        //验证通过
        return saveNotNull(entity);
    }

    /**
     * 如果字段在errors中存在则将message加入到对象的对象中<br>
     * 不存在则新建对象添加到errors中
     *
     * @param errors
     * @param field
     * @param message
     */
    private void addError(List<FieldErrorsResult> errors, String field, String message) {
        AtomicBoolean flag = new AtomicBoolean(true);
        errors.forEach(error -> {
            if (error.getField().equals(field)) {
                error.addMessage(message);
                flag.set(false);
            }
        });
        if (flag.get()) {
            FieldErrorsResult error = new FieldErrorsResult();
            error.setField(field);
            error.addMessage(message);
            errors.add(error);
        }
    }

    /**
     * 根据实体id判断是否是新增数据<br>
     * 新增数据id为空
     *
     * @param entity
     * @return
     */
    public boolean isAdd(T entity) {
        return StringUtils.isEmpty(entity.getId());
    }

}
