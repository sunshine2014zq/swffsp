/**
 * Created by sun on 2019/3/25 11:17
 */
(function ($) {
    $.extend({
        vueValidator: {
            //定义每一种校验规则的校验方法
            methods: {},
            //定义每一种校验规则对应的message
            messages: {},
            //为插件添加校验方法
            addMethod: function (name, method, message) {
                $.vueValidator.methods[name] = method;
                $.vueValidator.messages[name] = message;
            },
            //插件参数设置
            setting: {
                scope: "", //校验范围
                valObject: {},//需要验证的数据对象-vue 绑定的对象
                rules: {},//校验规则
                messages: {}, //自定义消息
                errors: {}, //如覆盖了showSuccess,showFail,messageReset三个方法则该字段不需要传入
                selector: "input",//默认验证元素
                event: "focusout",//验证事件
                others: [{
                    // select:"",
                    // event:""
                }],
                //校验成功操作--用户可以自定义覆盖该操作
                showSuccess: function (target) {
                    var ok_icon = '<svg class="icon" aria-hidden="true"><use xlink:href="#icon-ok"></use></svg>';
                    $(target).parents(".row").find(".message-icon").html(ok_icon);
                },
                //校验失败操作-用户可以自定义覆盖该方法
                showFail: function (target, name, message) {
                    var fail_icon = '<svg class="icon" aria-hidden="true"><use xlink:href="#icon-icon--jinggao"></use></svg>';
                    $(target).parents(".row").find(".message-icon").html(fail_icon);
                    this.errors[name + "Err"] = message;
                },
                //消息复位方法-用户可以自定义覆盖该方法
                messageReset: function (target, name) {
                    $(target).parents(".row").find(".message-icon").html("");
                    this.errors[name + "Err"] = "";
                }
            },
            validation: function (target) {
                var name = $(target).attr("name");
                //清除旧的消息
                this.setting.messageReset(target,name);
                var rules = this.setting.rules[name];
                if (!rules) {
                    return true;
                }
                for (var rule in rules) {
                    var method = $.vueValidator.methods[rule];
                    if (!method) {
                        console.warn("规则错误", rule);
                        continue;
                    }
                    var ruleVal = rules[rule];
                    //值为false的规则不校验
                    if (ruleVal instanceof Boolean && !ruleVal) {
                        continue;
                    }
                    var result = method.call(this, this.setting.valObject[name], ruleVal);
                    if (!result) {
                        this.setting.showFail(target, name, this.getMessage(name, rule, ruleVal));
                        return false;
                    }
                }
                this.setting.showSuccess(target, name);
                return true;
            },
            getMessage: function (name, rule, val) {
                var message;
                var sm = $.vueValidator.setting.messages;
                if (this.isBlank(sm) || this.isBlank(sm[name]) || this.isBlank(sm[name][rule])) {
                    message = $.vueValidator.messages[rule];
                } else {
                    message = sm[name][rule];
                }
                if (message.startWith("format")) {
                    return this.paramInstall(message.split("format:")[1], val);
                }
                return message;
            },
            isBlank: function (val) {
                return undefined == val || '' == val || null == val;
            },
            paramInstall: function (str, params) {
                var paramArray = [];
                if (params instanceof Object) {
                    $.each(params, function (key, val) {
                        paramArray.push(val);
                    })
                } else {
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
            },
            /**
             * 循环校验
             * @param names 不需要校验的字段数组
             * @returns {*|boolean}
             */
            validationAll:function(names){
                var tags = $($.vueValidator.setting.scope).find($.vueValidator.setting.selector);
                names = (names == undefined ? [] : names);
                var flag = this.validations(tags,names);
                for (var index in $.vueValidator.setting.others) {
                    var other = $.vueValidator.setting.others[index];
                    var tags = $($.vueValidator.setting.scope).find(other.selector);
                    var result = this.validations(tags,names);
                    if(!result) flag = false;
                }
                return flag;
            },
            validations: function(tags,names){
                var flag = true;
                if(tags != undefined) {
                    for (var i = 0; i< tags.length; i++) {
                        if($.inArray($(tags[i]).attr("name"),names) > -1){
                            continue;
                        }
                        var result = this.validation($(tags[i]));
                        if(!result) flag = false;
                    }
                }
                return flag;
            }
        }
    });

    $.extend($.fn, {
        validate: function (options) {
            //设置参数
            $.vueValidator.setting.scope = this.selector;
            $.vueValidator.setting.rules = options.rules;
            $.vueValidator.setting.messages = options.messages;
            if (options.selector) {
                $.vueValidator.setting.selector = options.selector;
            }
            if (options.event) {
                $.vueValidator.setting.event = options.event;
            }
            if (options.others && $.isArray(options.others) && options.others.length > 0) {
                $.vueValidator.setting.others = options.others;
            }
            if (options.valObject) {
                $.vueValidator.setting.valObject = options.valObject;
            }
            if (options.errors) {
                $.vueValidator.setting.errors = options.errors;
            }
            if (options.showSuccess && $.isFunction(options.showSuccess)) {
                $.vueValidator.setting.showSuccess = options.showSuccess;
            }
            if (options.showFail && $.isFunction(options.showFail)) {
                $.vueValidator.setting.showFail = options.showFail;
            }
            if (options.messageReset && $.isFunction(options.messageReset)) {
                $.vueValidator.setting.messageReset = options.messageReset;
            }
            //绑定默认事件
            this.find($.vueValidator.setting.selector).on($.vueValidator.setting.event, function (event) {
                $.vueValidator.validation(event.target);
            });
            //绑定自定义事件
            var others = $.vueValidator.setting.others;
            for (var i = 0; i < others.length; i++) {
                this.find(others[i].selector).on(others[i].event, function (event) {
                    $.vueValidator.validation(event.target);
                });
            }
        }
    });


})(jQuery);