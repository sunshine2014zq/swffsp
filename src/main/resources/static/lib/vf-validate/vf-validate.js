/**
 * vue form validate plug
 * Created by sun on 2019/3/4 18:17
 */
(function ($) {
    $.extend({
        vf_validate :{
            methods: {

            },
            addMethod: function( name, method, message ) {
                $.vf_validate.methods[ name ] = method;
                $.vf_validate.messages[ name ] = (message !== undefined ? message : $.vf_validate.messages[ name ]);
            },
            messages: {

            },
            validate:function (name,obj,validate) {
                $("input[name='"+name+"']").parents(".row").find(".message").html("")
                var value = obj[name];
                var rules = validate.rules[name];
                var fail_icon = '<svg class="icon" aria-hidden="true"><use xlink:href="#icon-icon--jinggao"></use></svg>';
                var ok_icon = '<svg class="icon" aria-hidden="true"><use xlink:href="#icon-ok"></use></svg>';
                //保存插件对象
                var self = this;
                //先填充通过图标，校验不通过时失败图标覆盖该图标
                $("input[name='"+name+"']").parents(".row").find(".message-icon").html(ok_icon);
                // 循环执行校验规则
                $.each(rules,function (key,val) {
                    var method = $.vf_validate.methods[key];
                    if(undefined == method) {
                        console.log("错误规则" + key);
                    }
                    //值为false的规则不校验
                    if(val instanceof Boolean && !val){
                        //跳出本次循环 相当于continue
                        return true;
                    }
                    var result = method.call(this,value,val);
                    if(!result){
                        var message;
                        if(self.isUndefinedEmpty(validate.messages) || self.isUndefinedEmpty(validate.messages[name]) ||
                            self.isUndefinedEmpty(validate.messages[name][key])){
                            message = $.vf_validate.messages[key];
                        }else {
                            message = validate.messages[name][key];
                        }
                        if(message.startWith("format")){
                            message = self.paramInstall(message.split("format:")[1],val);
                        }
                        //错误提示
                        $("input[name='"+name+"']").parents(".row").find(".message-icon").html(fail_icon);
                        $("input[name='"+name+"']").parents(".row").find(".message").html(message);
                        return false;
                    }
                });
            },
            isUndefinedEmpty: function(val) {
                return undefined == val || '' == val;
            },
            paramInstall: function (str, params) {
                var paramArray = [];
                if(params instanceof Object){
                    $.each(params,function (key, val) {
                        paramArray.push(val);
                    })
                }else {
                    paramArray.push(params)
                }
                var result = "";
                var s1 = str.split("{");
                result += s1[0];
                for (var i = 1; i < s1.length; i++) {
                    var s2 = s1[i].split("}");
                    result += paramArray[s2[0]] + s2[1]
                }
                return result;
            }
        }
    });


})(jQuery);
