/**
 * Created by sun on 2019/2/15 16:03
 */

//登录
var loginUrl = "/swffsp/login";
var indexUrl = "/swffsp/index.html";

$(".loginBtn").click(function () {
    //空验证-账户
    if(baseUtils.isEmptyValue($("input[name='username']"))){
        baseUtils.tip("账号不能为空",2,2000);
        return;
    }
    var formData = $(".loginForm").serializeObject();
    baseUtils.post(new Vue(),loginUrl,formData,function () {
        setTimeout(function () {
            window.location.href = indexUrl
        },400)
    })
});
