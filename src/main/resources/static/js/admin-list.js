/**
 * Created by sun on 2019/2/26 10:28
 */

// 全局变量
var contextPath = "/swffsp";
var modelPath = "/user";
var listUrl = contextPath + modelPath +"/list";
var saveUrl = contextPath + modelPath + "/save";
var delUrl = contextPath + modelPath + "/delete";
var rolesUrl = contextPath + modelPath + "/roles";


// 本页面业务方法
var service = {
    laypage:function (total,vue,vuedata) {
        layui.use('laypage', function(){
            var laypage = layui.laypage;
            laypage.render({
                elem: 'layPage'
                ,count: total
                ,layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
                ,jump: function(obj, first){
                    console.log(obj)
                    vuedata.query.page = obj.curr;
                    vuedata.query.size = obj.limit;
                    if(!first){
                        baseUtils.post(vue, listUrl, vuedata.query,function (data) {
                            vuedata.pageInfo = data;
                        });
                    }
                }
            });
        });
    },
    getList:function (vue,vuedata) {
        baseUtils.post(vue, listUrl, vuedata.query,function (data) {
            vuedata.pageInfo = data;
            service.laypage(data.total,vue,vuedata);
        });
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
            ,size:10

        }
        ,ids:[]
        ,edit:""
    },
    // 页面加载初始化函数
    mounted: function () {
        service.getList(this,this.$data)
        // service.list(this,this.$data);
    },
    methods:{
        // vue element 范围内触发事件处理
        search: function () {
            service.getList(this,this.$data)
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
                baseUtils.post(vue,delUrl,userVue.$data.ids,function () {
                    //显示处理
                    $(userVue.$data.pageInfo.content).each(function () {
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
                //编辑框关闭回调
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
        errors:{
            usernameErr:""
            ,nickNameErr:""
            ,passwordErr:""
            ,passwordRepeatErr:""
            ,phoneNumErr:""
            ,emailErr:""
            ,rolesErr:""
        },
        roles:[],
        index:""//修改的第几条记录
    },
    // 页面加载初始化函数
    mounted: function () {
        validate.obj = this.$data.user;
        validate.errors = this.$data.errors;
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
                baseUtils.post(this,saveUrl,this.$data.user,function (data) {
                    //更改列表显示数据
                    if(userEdit.$data.user.id != ""){
                        //修改成功-更新显示数据
                        var current = userVue.$data.pageInfo.content[userEdit.$data.index];
                        baseUtils.copyValue(current, userEdit.$data.user)
                    }else {
                        //添加时
                        var user = $.extend(true, {}, userEdit.$data.user);
                        user.id = data;
                        user.status = 1;
                        user.createdTime = baseUtils.now();
                        userVue.$data.pageInfo.content.insert(0,user);
                        console.log(userVue.$data.pageInfo.content)
                    }
                    layer.close(userVue.$data.edit)
                },function (code, data) {
                    $.each(data,function (index,el) {
                        var msg = "";
                        $.each(el.messages,function (index,message) {
                            msg += (message+"<br>")
                        })
                        userEdit.$data.errors[el.field + "Err"] = msg;
                    })
                })
            }
        }
        //methods...
    }
});


//
// laypage.render({
//     elem: 'layPage'
//     ,count: 100
//     ,layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
//     ,jump: function(obj){
//         console.log(obj)
//     }
// });