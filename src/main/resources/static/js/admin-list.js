/**
 * Created by sun on 2019/2/26 10:28
 */

// 全局变量
var contextPath = "/swffsp";
var modelPath = "/user";
var listUrl = contextPath + modelPath + "/list";
var saveUrl = contextPath + modelPath + "/save";
var delUrl = contextPath + modelPath + "/delete";
var rolesUrl = contextPath + modelPath + "/roles";

// 本页面业务方法
var service = {
    /**
     * 分页插件加载
     */
    laypageLoad: function (vue) {
        baseUtils.laypage("layPage",listUrl, vue);
    },
    /**
     * 初始化页面数据,并加载分页插件
     * @param vue 列表所在的vue对象
     */
    list: function (vue) {
        var vueData = vue.$data;
        baseUtils.post(vue, listUrl, vueData.query, function (data) {
            vueData.pageInfo = data;
            service.laypageLoad(vue);
        });
    },
    /**
     * 删除
     */
    del: function () {
        var ids = userVue.$data.ids;
        baseUtils.post(userVue, delUrl, ids, function () {
            var pageInfo = userVue.$data.pageInfo;
            //移除已删除的数据
            pageInfo.content = baseUtils.removeObjectByAttr(pageInfo.content, "id", ids);
            pageInfo.total -= ids.length; //改变总记录条数
            userVue.$data.ids = []; //多选框复位
            service.laypageLoad(userVue); //重新加载分页插件
        })
    },
    showSuccess: function (target) {
        var ok_icon = '<svg class="icon" aria-hidden="true"><use xlink:href="#icon-ok"></use></svg>';
        $(target).parents(".row").find(".message-icon").html(ok_icon);
    },
    showFail: function (target, name, message) {
        var fail_icon = '<svg class="icon" aria-hidden="true"><use xlink:href="#icon-icon--jinggao"></use></svg>';
        $(target).parents(".row").find(".message-icon").html(fail_icon);
        userEdit.$data.errors[name + "Err"] = message;
    },
    messageReset: function (target, name) {
        $(target).parents(".row").find(".message-icon").html("");
        userEdit.$data.errors[name + "Err"] = "";
    }
}

//用户列表模块
var userVue = new Vue({
    el: ".page-container",
    data: {
        pageInfo: {}, //页面数据
        //查询条件
        query: {
            usernameKey: "",
            page: 1,
            size: 10
        },
        ids: [], //复选框
        edit: "" //添加，修改弹窗index
    },
    mounted: function () {
        // 页面初始化
        service.list(this)
    },
    methods:{
        search: function () {
            service.list(this)
        },
        //修改状态
        statusToggle: function (event, id, status) {
            var vue = this;
            var msg = status == 1 ? "确定启用吗?" : "确定停用吗?";
            layer.confirm(msg, function (index) {
                //此处请求后台程序，下方是成功后的前台处理……
                var user = {"id": id, "status": status};
                baseUtils.post(vue, saveUrl, user, function () {
                    $(event.target).parents("tr").find(".btn-status").toggle();
                    $(event.target).parents("tr").find(".label-status").toggle();
                    layer.close(index);
                })
            });
        },
        //删除
        admin_del: function (id) {
            var vue = this;
            userVue.$data.ids.push(id);
            layer.confirm('确认要删除吗？', function () {
                service.del(vue, userVue.$data.ids)
            });
        },
        //批量删除
        batch_del: function () {
            var vue = this;
            layer.confirm('确认要删除吗？', function (index) {
                service.del(vue, userVue.$data.ids);
            });
        },
        /*管理员-编辑*/
        admin_edit: function (title, w, h, index) {
            userVue.$data.edit = baseUtils.layer_show(1, title, $(".admin-edit"), w, h, function () {
                //编辑框关闭回调
                baseUtils.clearValues(userEdit.$data.user);  //清空输入框数据
                baseUtils.clearValues(userEdit.$data.errors);  //清空错误提示
                $(".input-message .message-icon").html("");  //清空验证图标
            });
            if (index != undefined) {
                //修改
                var current = userVue.$data.pageInfo.content[index];
                baseUtils.copyValue(userEdit.$data.user, current);//加载数据到表单作为默认值
                userEdit.$data.index = index;
                $("input[type='password']").parents(".row").hide(); //修改页面不能修改密码
            } else {
                //添加
                $("input[type='password']").parents(".row").show(); //添加页面-密码
            }
        }
        //methods...
    }
});

/**
 * 用户添加或修改
 */
var userEdit = new Vue({
    el: ".admin-edit",
    data: {
        user: { //表单
            id: "",
            username: "",
            nickName: "",
            password: "",
            rePassword: "",
            phoneNum: "",
            email: "",
            roles: []
        },
        errors: { //错误提示
            usernameErr: "",
            nickNameErr: "",
            passwordErr: "",
            rePasswordErr: "",
            phoneNumErr: "",
            emailErr: "",
            rolesErr: ""
        },
        roles: [],
        index: "" //修改的第几条记录
    },
    mounted: function () {
        //初始化
        validate.valObject = this.$data.user;
        validate.showFail = service.showFail;
        validate.showSuccess = service.showSuccess;
        validate.messageReset = service.messageReset;
        $('#form-admin-edit').validate(validate); //开启数据校验
        //加载角色信息
        baseUtils.post(this, rolesUrl, {}, function (data) {
            userEdit.$data.roles = data;
        })
    },
    methods: {
        save: function () {
            //不需要校验字段
            var names = userEdit.$data.user.id == "" ? [] : ["password", "rePassword"];
            var result = $.vueValidator.validationAll(names); //检查整个表单数据
            if (result) {
                //表单校验通过
                baseUtils.post(this, saveUrl, this.$data.user, function (data) {
                    //更改列表显示数据
                    if (userEdit.$data.user.id != "") {
                        //修改成功-修改内容同步到列表
                        var current = userVue.$data.pageInfo.content[userEdit.$data.index];
                        baseUtils.copyValue(current, userEdit.$data.user);
                    } else {
                        //添加时
                        var user = $.extend(true, {}, userEdit.$data.user);
                        user.id = data;
                        user.status = 1;
                        user.createdTime = baseUtils.now();
                        //添加的数据同步到列表
                        userVue.$data.pageInfo.content.insert(0, user);
                        userVue.$data.pageInfo.total += 1;
                        service.laypageLoad(userVue); //重新加载分页控件
                    }
                    layer.close(userVue.$data.edit)
                }, function (code, data) {
                    //显示服务端校验的错误信息
                    $.each(data, function (index, el) {
                        var msg = "";
                        $.each(el.messages, function (index, message) {
                            msg += (message + "<br>")
                        });
                        userEdit.$data.errors[el.field + "Err"] = msg;
                    })
                })
            }
        }
        //methods...
    }
});