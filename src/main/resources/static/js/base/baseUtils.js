/**
 * Created by sun on 2019/2/15 16:24
 * use jquery-1.9.1
 * use layer-2.4
 */

var baseUtils = {
    /**
     * 判断对象的value是否为空--item : jquery选择器
     * @param item
     * @returns {boolean}
     */
    isEmpty : function (item) {
        if (item.val() == '' || item.val() == undefined) {
            return true;
        }
        return false;
    },

    /**
     * 消息提示框
     * @param msg 为空提示：消息解析错误
     * @param icon 1:成功 2:错误 3：疑问 4：锁定 5：哭脸，6：笑脸 7：警告
     * @param time 时间/毫秒
     */
    tip: function (msg, icon, time) {
        if(msg){
            layer.msg(msg, {icon: icon, time: time});
        }else {
            layer.msg("消息解析错误", {icon: 2, time: time});
        }
    },

    reqTip: function(msg,status,time){
        var icon = status ? 1 : 2;
        this.tip(msg,icon,time);
    },

    /**
     * 包含加载遮罩层的post请求
     * @param vue vue对象
     * @param url 请求地址
     * @param data 请求参数
     * @param successCallBack 请求成功回到函数
     * @param failCallBack 如未传入回调函数,请求失败提示“系统繁忙!请稍后重试”
     */
    post: function (vue, url, data, successCallBack, failCallBack) {
        var loading = layer.load(0, {shade: [0.5, '#949494']}); //0代表加载的风格，支持0-2
        vue.$http.post(url, data).then(function (response) {
            layer.close(loading);
            //回调
            successCallBack(response);
        }, function (response) {
            console.log(response)
            layer.close(loading);
            if($.isFunction(failCallBack)){
                //回调
                failCallBack(response)
            }else {
                baseUtils.tip("系统繁忙!请稍后重试",2,1500);
            }
        });
    },

    /**
     * 包含加载遮罩层的get请求
     * @param vue vue对象
     * @param url 请求地址
     * @param successCallBack 请求成功回到函数
     * @param failCallBack 如未传入回调函数,请求失败提示“系统繁忙!请稍后重试”
     */
    get: function (vue, url, successCallBack, failCallBack) {
        var loading = layer.load(0, {shade: [0.5, '#949494']}, "#addPanel"); //0代表加载的风格，支持0-2
        vue.get(url).then(function (response) {
            //回调
            successCallBack(response);
            layer.close(loading);
        }, function (response) {
            console.log(response)
            layer.close(loading);
            if($.isFunction(failCallBack)){
                //回调
                failCallBack(response)
            }else {
                baseUtils.tip("系统繁忙!请稍后重试",2,1500);
            }
        });
    },
    /**
     * layer弹框
     * @param type 弹框类型 1页面层(content为请求内容) ; 2iframe层(content为请求URL)
     * @param title 标题
     * @param content 内容
     * @param w 宽
     * @param h 高
     */
    layer_show: function (type, title, content, w, h,closeCallback) {
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
        layer.open({
            type: type,
            area: [w + 'px', h + 'px'],
            fix: false, //不固定
            maxmin: true,
            shade: 0.4,
            title: title,
            content: content,
            end: closeCallback
        });
    },
    /**
     * 将第二个对象的值赋值给第一个对象<br>
     * 只赋值第一个对象有的属性
     * @param obj
     * @param from
     */
    copyValue: function (obj,from) {
        $.each(obj,function (key, val) {
            if(from[key]){
                obj[key] = from[key];
            }
        })
    },
    /**
     * 清除对象所有属性的值,不删除属性
     * @param obj
     */
    clearValues: function (obj) {
        $.each(obj,function (key, val) {
            obj[key] = '';
        })
    }
}


/**
 * jQuery库扩展--序列化为对象
 * @returns {Object}
 */
jQuery.prototype.serializeObject=function(){
    var obj=new Object();
    $.each(this.serializeArray(),function(index,param){
        if(!(param.name in obj)){
            obj[param.name]=param.value;
        }
    });
    return obj;
}
String.prototype.endWith = function(str){
    if(str==null || str=="" || this.length == 0 ||str.length > this.length){
        return false;
    }
    if(this.substring(this.length - str.length)){
        return true;
    }else{
        return false;
    }
    return true;
};

String.prototype.startWith = function(str){
    if(str == null || str== "" || this.length== 0 || str.length > this.length){
        return false;
    }
    if(this.substr(0,str.length) == str){
        return true;
    }else{
        return false;
    }
    return true;
};