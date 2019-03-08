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
                if(rules && $.isArray(rules)){
                    //循环执行校验规则
                    for (var i = 0;i<rules.length ; i++){
                        var rule = rules[i];
                        var ruleName = [];
                        //解析规则参数
                        if(rule.indexOf("(") > -1 && rule.indexOf(")") > -1 ){
                            var params;
                            var s1 = rule.split("(");
                            if(s1.length == 2) {
                                ruleName = s1[0];
                                var s2 = s1[1].split(")");
                                if(s2.length == 2) {
                                    params = s2[0].split(",");
                                }
                            }
                        }else {
                            ruleName = rule;
                        }
                        var method = $.vf_validate.methods[ruleName];
                        if(undefined == method) {
                            console.log("错误规则" + rule);
                            continue;
                        }
                        var result = method.call(this,value,params);
                        if(!result){
                            var message;
                            if(this.isUndefinedEmpty(validate.messages) || this.isUndefinedEmpty(validate.messages[name]) ||
                                this.isUndefinedEmpty(validate.messages[name][ruleName])){
                                message = $.vf_validate.messages[ruleName];
                            }else {
                                message = validate.messages[name][ruleName];
                            }
                            if(message.startWith("format")){
                                message = this.paramInstall(message.split("format:")[1],params);
                            }
                            //错误提示
                            $("input[name='"+name+"']").parents(".row").find(".message-icon").html(fail_icon);
                            $("input[name='"+name+"']").parents(".row").find(".message").html(message);
                            return;
                        }
                    }
                }
                //所有规则验证通过
                $("input[name='"+name+"']").parents(".row").find(".message-icon").html(ok_icon);
            },
            isUndefinedEmpty: function(val) {
                return undefined == val || '' == val;
            },
            paramInstall: function (str, params) {
                var result = "";
                var s1 = str.split("{");
                result += s1[0];
                for (var i = 1; i < s1.length; i++) {
                    var s2 = s1[i].split("}");
                    result += params[s2[0]] + s2[1]
                }
                return result;
            }
        }
    });


})(jQuery);
