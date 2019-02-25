/**
 * Created by sun on 2019/2/21 15:38
 */



// 全局变量
var contextPath = "/swffsp"
var getUserUrl = contextPath +"/user/info";

// 业务方法处理
var service = {

}
var menu = new Vue({
    el: "#vue-menu",
    // 内部参数定义
    data: {
        menus:[],
        contextPath:contextPath
    },
    // vue加载初始化函数
    mounted: function () {
        baseUtils.post(new Vue(), getUserUrl, {}, function (response) {
            console.log(response.body)
            $(".username").html(response.body.username)
            menu.$data.menus = response.body.menus
            console.log(menu.$data.menus)
        })

    },
    methods: {
        // vue element 范围内触发事件处理
        fold:function (index,e) {
            var t=e.currentTarget;
            $('.menu_dropdown dl dd').not($('.menu_dropdown dl dd').eq(index)).slideUp('fast');
            $('.menu_dropdown dt').not($('.menu_dropdown dt').eq(index)).removeClass('selected');
            if($(t).siblings('dd').css('display')=="block"){
                $(t).removeClass('selected');
            }else{
                $(t).addClass('selected');
            }
            $(t).siblings('dd').slideToggle('fast');
        }
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
