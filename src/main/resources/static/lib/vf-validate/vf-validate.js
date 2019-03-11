/**
 * vue form validate plug
 * Created by sun on 2019/3/4 18:17
 */
(function ($) {
    $.extend({
        vf_validate :{
            //定义每一种校验规则的校验方法
            methods: {
            },
            //为插件添加校验方法
            addMethod: function( name, method, message ) {
                $.vf_validate.methods[ name ] = method;
                $.vf_validate.messages[ name ] = (message !== undefined ? message : $.vf_validate.messages[ name ]);
            },
            //定义每一种校验规则对应的message
            messages: {

            },
            //定义表单具体需要校验的规则，以及自定义的方法
            setting:{
                box:"",
                obj:{},//需要验证的数据对象-vue 绑定的对象
                rules:{},
                messages:{},
                selector:"input",//默认验证元素
                event:"focusout",//
                others:[{
                    // select:"",
                    // event:""
                }],
            },
            validate:function (tag) {
                //清空上次提示信息
                tag.parents(".row").find(".message").html("")
                var name = tag.attr("name");
                var value = $.vf_validate.setting.obj[name];
                var rules = $.vf_validate.setting.rules[name];
                var fail_icon = '<svg class="icon" aria-hidden="true"><use xlink:href="#icon-icon--jinggao"></use></svg>';
                var ok_icon = '<svg class="icon" aria-hidden="true"><use xlink:href="#icon-ok"></use></svg>';
                //保存插件对象
                var self = this;
                //先填充通过图标，校验不通过时失败图标覆盖该图标
                tag.parents(".row").find(".message-icon").html(ok_icon);
                // 循环执行校验规则
                if(rules){
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
                            var cm = $.vf_validate.setting.messages;
                            if(self.isUndefinedEmpty(cm) || self.isUndefinedEmpty(cm[name]) ||
                                self.isUndefinedEmpty(cm[name][key])){
                                message = $.vf_validate.messages[key];
                            }else {
                                message = validate.messages[name][key];
                            }
                            if(message.startWith("format")){
                                message = self.paramInstall(message.split("format:")[1],val);
                            }
                            //错误提示
                            tag.parents(".row").find(".message-icon").html(fail_icon);
                            tag.parents(".row").find(".message").html(message);
                            return false;
                        }
                    });
                }
            },
            each:function(){
                var self = this;
                var tags = $($.vf_validate.setting.box).find($.vf_validate.setting.selector);
                this.eachValidate(tags);
                $.each($.vf_validate.setting.others,function (val) {
                    var tags = $($.vf_validate.setting.box).find(val.selector);
                    self.eachValidate(tags);
                })
            },
            eachValidate: function(tags){
                if(tags != undefined) {
                    for (var i = 0; i< tags.length; i++) {
                        this.validate($(tags[i]));
                    }
                }
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

    $.extend($.fn,{
        validate: function (options) {
            //设置参数
            $.vf_validate.setting.box = this.selector;
            $.vf_validate.setting.rules = options.rules;
            $.vf_validate.setting.messages = options.messages;
            if(options.selector){
                $.vf_validate.setting.selector = options.selector;
            }
            if(options.event){
                $.vf_validate.setting.event = options.event;
            }
            if(options.others && $.isArray(options.others) && options.others.length > 0){
                $.vf_validate.setting.others = options.others;
            }
            if(options.obj){
                $.vf_validate.setting.obj = options.obj;
            }
            //绑定默认事件
            this.find($.vf_validate.setting.selector).on($.vf_validate.setting.event,function (event) {
                $.vf_validate.validate($(event.target));
            });
            //绑定自定义事件
            var others = $.vf_validate.setting.others;
            for (var i = 0; i < others.length; i++) {
                this.find(others[i].selector).on(others[i].event,function (event) {
                    $.vf_validate.validate($(event.target));
                });
            }
        }
    });
})(jQuery);
