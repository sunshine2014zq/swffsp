/**
 * Created by sun on 2019/2/21 16:47
 */

// 全局变量
var contextPath = "/swffsp"
var xxxUrl = contextPath +"/user/getUserInfo";

// 本页面业务方法
var service = {
    init:function () {
        //do some things
    }
    ,check:function () {

    }

}

//×××模块
var header = new Vue({
    el: "#header",
    // 内部参数定义
    data: {
         data1:""
        ,data2:{}
        ,data3:[]
    },
    // 页面加载初始化函数
    mounted: function () {
        service.init();
    },
    methods: {
        // vue element 范围内触发事件处理
        save:function () {
            service.check()
        }
        ,update:function () {

        }
        ,delete:function () {

        }
    }
});