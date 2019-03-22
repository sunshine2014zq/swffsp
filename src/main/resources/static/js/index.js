/**
 * Created by sun on 2019/2/21 15:38
 */

// 全局变量
var contextPath = "/swffsp"
var userInfoUrl = contextPath +"/user/info";

// 业务方法处理
var service = {

}
var vue = new Vue({
    el: "#body",
    // 内部参数定义
    data: {
        menus: [],
        contextPath: contextPath,
        pwd: {
            oldPwd: "",
            newPwd: "",
            rePwd: ""
        },
        errors: {
            oldPwdErr: "",
            newPwdErr: "",
            rePwdErr: ""
        }
    },
    // vue加载初始化函数
    mounted: function () {
        baseUtils.post(new Vue(), userInfoUrl, {}, function (data) {
            var name = baseUtils.isEmpty(data.nickName) ? data.username : data.nickName;
            $(".username").html(name)
            vue.$data.menus = data.menus
        })
    },
    methods: {
        fold: function (index, e) {
            var t = e.currentTarget;
            $('.menu_dropdown dl dd').not($('.menu_dropdown dl dd').eq(index)).slideUp('fast');
            $('.menu_dropdown dt').not($('.menu_dropdown dt').eq(index)).removeClass('selected');
            if ($(t).siblings('dd').css('display') == "block") {
                $(t).removeClass('selected');
            } else {
                $(t).addClass('selected');
            }
            $(t).siblings('dd').slideToggle('fast');
        },
        pwdChange: function () {
            baseUtils.layer_show(1, "修改密码", $(".pwd"), 500, 300, function () {
                //修改密码弹框关闭
                baseUtils.clearValues(vue.$data.pwd);
                baseUtils.clearValues(vue.$data.errors);
            });
            $("#form-change-password").validate({
                obj: vue.$data.pwd,
                errors: vue.$data.errors,
                rules: {
                    oldPwd: {
                        required: true
                    },
                    newPwd: {
                        required: true,
                        isRightfulString: true,
                        length: {
                            min: 6,
                            max: 16
                        },
                        notEqualTo: "oldPwd"
                    },
                    rePwd: {
                        required: true,
                        equalTo: "newPwd"
                    }
                },
                messages: {
                    newPwd: {
                        notEqualTo: "新密码不能与旧密码相同"
                    },
                    rePwd: {
                        equalTo: "两次输入的密码不一致"
                    }
                }
            });

        },
        savePwd: function () {
            var result = $.vf_validate.each([]);
            if(result) {
                //通过校验
            }
        }
        // methods...
    }
});

/*个人信息*/
function myselfinfo() {
    layer.open({
        type: 1,
        area: ['300px', '200px'],
        fix: false, //不固定
        maxmin: true,
        shade: 0.4,
        title: '查看信息',
        content: '<div>管理员信息</div>'
    });
}

/*资讯-添加*/
function article_add(title, url) {
    var index = layer.open({
        type: 2,
        title: title,
        content: url
    });
    layer.full(index);
}

/*图片-添加*/
function picture_add(title, url) {
    var index = layer.open({
        type: 2,
        title: title,
        content: url
    });
    layer.full(index);
}

/*产品-添加*/
function product_add(title, url) {
    var index = layer.open({
        type: 2,
        title: title,
        content: url
    });
    layer.full(index);
}

/*用户-添加*/
function member_add(title, url, w, h) {
    layer_show(title, url, w, h);
}
