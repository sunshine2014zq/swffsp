/**
 * Created by sun on 2019/2/15 16:24
 * use jquery-1.9.1
 * use layer-2.4
 */
//操作成功
var SUCCESS = "success";

var baseUtils = {
    /**
     * 判断对象的value是否为空--item : jquery选择器
     * @param item
     * @returns {boolean}
     */
    isEmptyValue: function (item) {
        if (item.val() == '' || item.val() == undefined) {
            return true;
        }
        return false;
    },
    /**
     * 判断是否为空"",null,undefined
     * @param val
     */
    isEmpty: function (val) {
        if (val == undefined || val == null || val == '') {
            return true;
        }
        return false;
    },

    /**
     * 消息提示框
     * @param message 为空提示：消息解析错误
     * @param icon 1:成功 2:错误 3：疑问 4：锁定 5：哭脸，6：笑脸 7：警告
     * @param time 时间/毫秒
     */
    tip: function (message, icon, time) {
        if (message) {
            layer.msg(message, {icon: icon, time: time});
        }
    },

    /**
     * 请求弹框提示
     * @param message
     * @param code
     * @param time
     */
    reqTip: function (message, code, time) {
        var icon = code == SUCCESS ? 1 : 2;
        this.tip(message, icon, time);
    },

    /**
     *
     * 包含加载遮罩层的post请求 -回调省略时会根据状态tip-message
     * @param vue vue对象
     * @param url 请求地址
     * @param data 请求参数
     * @param successCallBack 请求成功回到函数
     * @param failCallBack 失败回调
     */
    post: function (vue, url, data, successCallBack, failCallBack) {
        var loading = layer.load(0, {shade: [0.5, '#949494']}); //0代表加载的风格，支持0-2
        vue.$http.post(url, data).then(function (response) {
            layer.close(loading);
            baseUtils.reqTip(response.body.message, response.body.code, 1500);
            if (response.body.code == SUCCESS) {
                if ($.isFunction(successCallBack)) {
                    //data:数据
                    successCallBack(response.body.data);
                }
            } else {
                if ($.isFunction(failCallBack)) {
                    //code:错误码,data:数据
                    failCallBack(response.body.code, response.body.data)
                }
            }
        }, function () {
            layer.close(loading);
            baseUtils.tip("系统繁忙!请稍后重试", 2, 1500);
        });
    },

    /**
     * layer弹框
     * @param type 弹框类型 1页面层(content为请求内容) ; 2iframe层(content为请求URL)
     * @param title 标题
     * @param content 内容
     * @param w 宽
     * @param h 高
     * @param closeCallback 弹框关闭回调
     * @returns {*} index
     */
    layer_show: function (type, title, content, w, h, closeCallback) {
        if (title == null || title == '') {
            title = false;
        }
        if (content == null || content == '') {
            content = 'empty content'
        }
        if (w == null || w == '') {
            w = 800;
        }
        if (h == null || h == '') {
            h = ($(window).height() - 50);
        }
        if (type == null || type == '') {
            type = 1;
        }
        var index = layer.open({
            type: type,
            area: [w + 'px', h + 'px'],
            fix: false, //不固定
            maxmin: true,
            shade: 0.4,
            title: title,
            content: content,
            end: closeCallback
        });
        return index;
    },
    /**
     * 将第二个对象的值赋值给第一个对象<br>
     * 只赋值第一个对象有的属性
     * @param obj
     * @param from
     */
    copyValue: function (obj, from) {
        $.each(obj, function (key, val) {
            if (from[key]) {
                obj[key] = from[key];
            }
        })
    },
    /**
     * 清除对象所有属性的值,不删除属性
     * @param obj
     */
    clearValues: function (obj) {
        $.each(obj, function (key, val) {
            if (obj[key].length != undefined) {
                //字符串和数组
                if ($.isArray(obj[key])) {
                    obj[key] = [];
                } else {
                    obj[key] = "";
                }
            } else {
                obj[key] = {};
            }
        })
    },

    /**
     * 将对象数组array中移除属性名是attrName,值不在attrValue中的对象返回。
     * @param array
     * @param attrName
     * @param attrValue 可以是数组
     */
    removeObjectByAttr: function (array, attrName, attrValue) {
        var result = [];
        for (var i = 0; i < array.length; i++) {
            if ($.isArray(attrValue)) {
                if ($.inArray(array[i][attrName], attrValue) < 0) {
                    result.push(array[i])
                }
            } else {
                if (array[i][attrName] != attrValue) {
                    result.push(array[i])
                }
            }
        }
        return result;
    },

    /**
     * 加载分页插件
     * @param elem 放置分页控件的元素ID,注意：不要带#
     * @param total 数据总条数
     * @param listUrl 分页接口地址
     * @param vue vue对象
     * @param vueData 数据<br>
     *     --vueData.query-分解接口查询条件<br>
     *     ----vueData.query.page/size 页码，每页数据<br>
     *     --vueData.pageInfo 分页接口返回的数据<br>
     */
    laypage: function (elem, listUrl, vue) {
        var vueData = vue.$data;
        layui.use('laypage', function () {
            var laypage = layui.laypage;
            laypage.render({
                elem: elem,
                curr: vueData.query.page,
                count: vueData.pageInfo.total,
                layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip'],
                jump: function (obj, first) {
                    vueData.query.page = obj.curr;
                    vueData.query.size = obj.limit;
                    if (!first) {
                        baseUtils.post(vue, listUrl, vueData.query, function (data) {
                            vueData.pageInfo = data;
                        });
                    }
                }
            });
        });
    },

    full: function (s) {
        return s < 10 ? '0' + s : s;
    },
    now: function () {
        var myDate = new Date();
        var year = myDate.getFullYear();        //获取当前年
        var month = myDate.getMonth() + 1;   //获取当前月
        var date = myDate.getDate();            //获取当前日
        var h = myDate.getHours();              //获取当前小时数(0-23)
        var m = myDate.getMinutes();          //获取当前分钟数(0-59)
        var s = myDate.getSeconds();
        return year + '-' + this.full(month) + "-" + this.full(date) + " " + this.full(h) + ':' + this.full(m) + ":" + this.full(s);
    }
}


/**
 * jQuery库扩展--序列化为对象
 * @returns {Object}
 */
jQuery.prototype.serializeObject = function () {
    var obj = new Object();
    $.each(this.serializeArray(), function (index, param) {
        if (!(param.name in obj)) {
            obj[param.name] = param.value;
        }
    });
    return obj;
}
String.prototype.endWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length) {
        return false;
    }
    if (this.substring(this.length - str.length)) {
        return true;
    } else {
        return false;
    }
    return true;
};

String.prototype.startWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length) {
        return false;
    }
    if (this.substr(0, str.length) == str) {
        return true;
    } else {
        return false;
    }
    return true;
};

Array.prototype.insert = function (index, item) {
    this.splice(index, 0, item);
};