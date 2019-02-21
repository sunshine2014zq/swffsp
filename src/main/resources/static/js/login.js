/**
 * Created by sun on 2019/2/15 16:03
 */

//登录
var loginUrl = "/swffsp/login";
var indexUrl = "/swffsp/index.html";

$(".loginBtn").click(function () {
    //空验证-账户
    if(baseUtils.isEmpty($("input[name='username']"))){
        baseUtils.tip("账号不能为空",2,2000);
        return;
    }
    var formData = $(".loginForm").serializeObject();
    baseUtils.post(new Vue(),loginUrl,formData,function (response) {
        console.log(response)
        if(response.body.status){
            window.location.href = indexUrl;
            baseUtils.tip(response.body.msg,1,1500);
        }else {
            if(response.body.msg){
                baseUtils.tip(response.body.msg,2,1500);
            }else {
                //没有返回正确的JSON结果
                baseUtils.tip("系统繁忙!请稍后重试!",2,1500);
            }

        }
    })
});
