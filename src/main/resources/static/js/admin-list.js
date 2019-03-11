/**
 * Created by sun on 2019/2/26 10:28
 */

// 全局变量
var contextPath = "/swffsp"
var listUrl = contextPath +"/security/userList";
var saveUrl = contextPath + "/security/save";
var delUrl = contextPath + "/security/deleteUsers";
var rolesUrl = contextPath + "/security/roles";


// 本页面业务方法
var service = {
    userPaging: function (vue,query) {
        baseUtils.post(vue, listUrl, query, function (response) {
            userVue.$data.pageInfo = response.body;
            console.log(userVue.$data)
        })
    }
    , save:function (vue,user,successCallback) {
        baseUtils.post(vue,saveUrl,user,function (response) {
            //返回数据相关处理
            console.log(response)
            if(response.body.status){
                successCallback(response.body.msg);//html组件相关处理
            }
        });
    }
    ,del:function (vue, ids,successCallback) {
        baseUtils.post(vue,delUrl,ids,function (response) {
            console.log(response)
            baseUtils.reqTip(response.body.msg,response.body.status,1500);
            if(response.body.status){
                successCallback()
            }
        })
    }
    ,loadRoles: function (vue) {
        baseUtils.post(vue,rolesUrl,{},function (response) {
            console.log(response.body)
            userEdit.$data.roles = response.body;
        })
    }
}

//用户列表模块
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
    methods:{
        // vue element 范围内触发事件处理
        search: function () {
            service.userPaging(this, this.$data.query);
        }
        //修改状态
        ,statusModified: function (event, id, status) {
            var vue = this;
            var msg = status == 1 ?  "确定启用吗?" :"确定停用吗?";
            layer.confirm(msg, function (index) {
                //此处请求后台程序，下方是成功后的前台处理……
                var user = {"id":id,"status":status};
                service.save(vue,user,function (msg) {
                    $(event.target).parents("tr").find(".btn-status").toggle();
                    $(event.target).parents("tr").find(".label-status").toggle();
                    layer.close(index);
                    baseUtils.tip(msg,1,1500)
                })
            });
        }
        //删除
        ,admin_del:function (event,id) {
            var vue = this;
            var ids = [];
            ids.push(id);
            layer.confirm('确认要删除吗？',function(index){
                service.del(vue,ids,function () {
                    $(event.target).parents("tr").remove();
                })
            });
        }
        //批量删除
        ,batch_del:function () {
            var vue = this;
            layer.confirm('确认要删除吗？',function(index){
                service.del(vue,userVue.$data.ids,function () {
                    //显示处理
                    $(userVue.$data.pageInfo.content).each(function (index,user) {
                        //隐藏多选框选中的行
                        $("input[name='ids']").each(function (index, el) {
                            if(el.checked){
                                $(el).parents("tr").remove();
                            }
                        })
                    })
                })
            });
        }
        /*管理员-编辑*/
        ,admin_edit :function (title,w,h,index){
            //加载角色信息
            service.loadRoles(this);
            baseUtils.layer_show(1,title,$(".admin-edit"),w,h,function () {
                baseUtils.clearValues(userEdit.$data.user);
                $(".input-message .message").html("");
                $(".input-message .message-icon").html("");
            });
            if(index != undefined){
                //修改
                var current = userVue.$data.pageInfo.content[index];
                baseUtils.copyValue(userEdit.$data.user,current);
                $("input[type='password']").parents(".row").hide();
            }else{
                //添加
                $("input[type='password']").parents(".row").show();
            }
        }
    }
});

/**
 * 用户添加或修改
 */
var userEdit = new Vue({
    el: ".admin-edit",
    // 内部参数定义
    data: {
        user:{
            id:""
            ,username:""
            ,nickName:""
            ,password:""
            ,passwordRepeat:""
            ,phoneNum:""
            ,email:""
            ,roles:""
        },
        roles:[],
    },
    // 页面加载初始化函数
    mounted: function () {
        $('#form-admin-edit').validate(validate);
    },
    methods: {
        save: function () {
            $.vf_validate.each();
        }
    }
});