/**
 * Created by sun on 2019/2/26 10:28
 */

// 全局变量
var contextPath = "/swffsp"
var listUrl = contextPath +"/user/list";
var modifiedUrl = contextPath + "/user/modified";

// 本页面业务方法
var service = {
    userPaging: function (vue,query) {
        baseUtils.post(vue, listUrl, query, function (response) {
            userVue.$data.pageInfo = response.body;
            console.log(userVue.$data)
        })
    }
    , modified:function (vue,user,successCallback) {
        baseUtils.post(vue,modifiedUrl,user,function (response) {
            //返回数据相关处理
            console.log(response)
            if(response.body.status){
                successCallback(response.body.msg);//html组件相关处理
            }
        });
    }

}

//×××模块
var userVue = new Vue({
    el: ".page-container",
    // 内部参数定义
    data: {
        pageInfo:{}
        ,query:{
            usernameKey:""
            ,page:1
            ,size:15
        }
        ,ids:[]
    },
    // 页面加载初始化函数
    mounted: function () {
        service.userPaging(this,this.$data.query);
    },
    methods: {
        // vue element 范围内触发事件处理
        search: function () {
            service.userPaging(this, this.$data.query);
        }
        ,statusModified: function (event, id, status) {
            var vue = this;
            var msg = status == 1 ?  "确定启用吗?" :"确定停用吗?";
            layer.confirm(msg, function (index) {
                //此处请求后台程序，下方是成功后的前台处理……
                var user = {"id":id,"status":status};
                service.modified(vue,user,function (msg) {
                    $(".btn-status").toggle();
                    $(".label-status").toggle();
                    layer.close(index);
                    baseUtils.tip(msg,1,1000)
                })
            });
        }
    }
});



/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*管理员-增加*/
function admin_add(title,url,w,h){
    layer_show(title,url,w,h);
}
/*管理员-删除*/
function admin_del(obj,id){
    layer.confirm('确认要删除吗？',function(index){
        $.ajax({
            type: 'POST',
            url: '',
            dataType: 'json',
            success: function(data){
                $(obj).parents("tr").remove();
                layer.msg('已删除!',{icon:1,time:1000});
            },
            error:function(data) {
                console.log(data.msg);
            },
        });
    });
}

/*管理员-编辑*/
function admin_edit(title,url,id,w,h){
    layer_show(title,url,w,h);
}
/*管理员-停用*/
function admin_stop(obj,id){
    layer.confirm('确认要停用吗？',function(index){
        //此处请求后台程序，下方是成功后的前台处理……

        $(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_start(this,id)" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
        $(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">已禁用</span>');
        $(obj).remove();
        layer.msg('已停用!',{icon: 5,time:1000});
    });
}

/*管理员-启用*/
// function admin_start(obj,id){
//     layer.confirm('确认要启用吗？',function(index){
//         //此处请求后台程序，下方是成功后的前台处理……
//
//
//         $(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_stop(this,id)" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
//         $(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
//         $(obj).remove();
//         layer.msg('已启用!', {icon: 6,time:1000});
//     });
// }