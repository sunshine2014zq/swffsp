/**
 * Created by sun on 2019/2/15 16:24
 * use jquery-1.9.1
 * use layer-2.4
 */

var baseUtils = {
    //判断对象的value是否为空--item : 使用jquery选择器
    isEmpty : function (item) {
        if (item.val() == '' || item.val() == undefined) {
            return true;
        }
        return false;
    },
    //1:成功 2:错误 3：疑问 4：锁定 5：哭脸，6：笑脸 7：警告
    tip: function (msg, icon, time) {
        if(msg){
            layer.msg(msg, {icon: icon, time: time});
        }
    },

    /**
     * 包含加载遮罩层的post请求
     * failCallBack 为空时提示“系统繁忙!请稍后重试”,否则执行回调failCallBack
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