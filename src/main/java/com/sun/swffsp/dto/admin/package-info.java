/**
 * 后台管理系统所用到的数据通道对象<br>
 * 规则：<br>
 * (1) 查询条件多于2个字段建议创建实体对象,建议以condition结尾<br>
 * (2) 请求响应的结果若无法用dto.core包中的对象及通用对象表述时，
 * 可以单独创建对象。以result结尾。<br>
 */
package com.sun.swffsp.dto.admin;