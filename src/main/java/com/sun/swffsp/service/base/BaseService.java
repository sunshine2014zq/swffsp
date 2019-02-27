package com.sun.swffsp.service.base;

import com.sun.swffsp.dto.resp.ResponseFields;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用业务
 *
 * @author sun
 * @date 2019/2/27 11:47
 */
public class BaseService {

    /**
     * 修改成功返回
     * @return
     */
    protected Map modifiedSuccess(){
        Map map = new HashMap();
        map.put(ResponseFields.Modified.STATUS,true);
        map.put(ResponseFields.Modified.MESSAGE,"修改成功");
        return map;
    }
}
