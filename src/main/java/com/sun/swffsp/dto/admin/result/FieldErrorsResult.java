package com.sun.swffsp.dto.admin.result;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加/修改 字段错误
 *
 * @author sun
 * @date 2019/3/15 14:31
 */
@Data
public class FieldErrorsResult implements Serializable {

    /**
     * 字段
     */
    private String field;
    /**
     * 错误消息
     */
    private List<String> messages = new ArrayList<>();

    public FieldErrorsResult(String field, String message) {
        this.field = field;
        addMessage(message);
    }

    public FieldErrorsResult(String field, List<String> messages) {
        this.field = field;
        this.messages = messages;
    }

    public FieldErrorsResult(String field) {
        this.field = field;
    }

    /**
     * 将消息加入到消息集合
     *
     * @param message
     */
    public void addMessage(String message) {
        messages.add(message);
    }

}
