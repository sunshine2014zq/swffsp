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
                valObject: {},//需要验证的数据对象-vue 绑定的对象
                rules: {},//校验规则
                messages: {}, //自定义消息
                selector: "input",//默认验证元素
                event: "focusout",//验证事件
                others: [{
                    // select:"",
                    // event:""
                }],
                //校验成功操作-默认无操作-用户自定义覆盖该操作
                showSuccess: function (name) {
                },
                //校验失败操作-用户自定义覆盖该方法
                showFail: function (name, message) {
                    alert(message);
                },
                //消息复位方法-用户自定义覆盖该方法
                messageReset: function (name) {
                }
            },
            validation: function (name) {
                //清除旧的消息
                this.setting.messageReset(name);
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
                        this.setting.showFail(name, this.getMessage(name, rule, ruleVal));
                        return false;
                    }
                }
                this.setting.showSuccess(name);
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
            }
        }
    });

    $.extend($.fn, {
        validate: function (options) {
            //设置参数
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
                $.vueValidator.validation($(event.target).attr("name"));
            });
            //绑定自定义事件
            var others = $.vueValidator.setting.others;
            for (var i = 0; i < others.length; i++) {
                this.find(others[i].selector).on(others[i].event, function (event) {
                    $.vueValidator.validation($(event.target).attr("name"));
                });
            }
        }
    });


})(jQuery);