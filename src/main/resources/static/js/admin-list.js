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
        ,edit:""
    },
    // 页面加载初始化函数
    mounted: function () {
        baseUtils.post(this, listUrl, this.$data.query,function (data) {
            userVue.$data.pageInfo = data;
        });
    },
    methods:{
        // vue element 范围内触发事件处理
        search: function () {
            baseUtils.post(this,listUrl,this.$data.query,function (data) {
                userVue.$data.pageInfo = data;
            });
        }
        //修改状态
        ,statusModified: function (event, id, status) {
            var vue = this;
            var msg = status == 1 ?  "确定启用吗?" :"确定停用吗?";
            layer.confirm(msg, function (index) {
                //此处请求后台程序，下方是成功后的前台处理……
                var user = {"id":id,"status":status};
                baseUtils.post(vue,saveUrl,user,function () {
                    $(event.target).parents("tr").find(".btn-status").toggle();
                    $(event.target).parents("tr").find(".label-status").toggle();
                    layer.close(index);
                })
            });
        }
        //删除
        ,admin_del:function (event,id) {
            var vue = this;
            var ids = [];
            ids.push(id);
            layer.confirm('确认要删除吗？',function(index){
                baseUtils.post(vue,delUrl,ids,function () {
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
            userVue.$data.edit = baseUtils.layer_show(1,title,$(".admin-edit"),w,h,function () {
                baseUtils.clearValues(userEdit.$data.user);
                $(".input-message .message").html("");
                $(".input-message .message-icon").html("");
            });
            if(index != undefined){
                //修改
                var current = userVue.$data.pageInfo.content[index];
                baseUtils.copyValue(userEdit.$data.user,current);
                userEdit.$data.index = index;
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
            ,roles:[]
        },
        roles:[],
        index:""//修改的第几条记录
    },
    // 页面加载初始化函数
    mounted: function () {
        validate.obj = this.$data.user
        $('#form-admin-edit').validate(validate);
        //加载角色信息
        baseUtils.post(this,rolesUrl,{},function (data) {
            userEdit.$data.roles = data;
        })
    },
    methods: {
        save: function () {
            var names = userEdit.$data.user.id == "" ? [] : ["password","passwordRepeat"];
            var result = $.vf_validate.each(names);
            if(result){
                console.log(this.$data.user)
                baseUtils.post(this,saveUrl,this.$data.user,function () {
                    //更改列表显示数据
                    if(userEdit.$data.user.id != ""){
                        //修改成功-更新显示数据
                        baseUtils.copyValue(userVue.$data.pageInfo.content[userEdit.$data.index]
                            ,userEdit.$data.user)
                    }else {
                        //添加时
                        var user = $.extend(true, {}, userEdit.$data.user);
                        user.status = 1;
                        user.createdTime = baseUtils.now();
                        userVue.$data.pageInfo.content.insert(0,user);
                        console.log(userVue.$data.pageInfo.content)
                    }
                    layer.close(userVue.$data.edit)
                })
            }
        }
    }
});