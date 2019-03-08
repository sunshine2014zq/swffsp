/*****************************************************************
 * $ Validate扩展验证方法  (linjq)    
 * Modified by guojunhui
 * Date modified:01/01/2017  
*****************************************************************/
$(function(){
    // 判断整数value是否等于0 
    $.vf_validate.addMethod("isIntEqZero", function(value) {
         value=parseInt(value);      
         return isEmpty(value) || value==0;       
    }, "整数必须为0"); 
      
    // 判断整数value是否大于0
    $.vf_validate.addMethod("isIntGtZero", function(value) {
         value=parseInt(value);      
         return isEmpty(value) || value>0;       
    }, "整数必须大于0"); 
      
    // 判断整数value是否大于或等于0
    $.vf_validate.addMethod("isIntGteZero", function(value) {
         value=parseInt(value);      
         return isEmpty(value) || value>=0;       
    }, "整数必须大于或等于0");   
    
    // 判断整数value是否不等于0 
    $.vf_validate.addMethod("isIntNEqZero", function(value) {
         value=parseInt(value);      
         return isEmpty(value) || value!=0;       
    }, "整数必须不等于0");  
    
    // 判断整数value是否小于0 
    $.vf_validate.addMethod("isIntLtZero", function(value) {
         value=parseInt(value);      
         return isEmpty(value) || value<0;       
    }, "整数必须小于0");  
    
    // 判断整数value是否小于或等于0 
    $.vf_validate.addMethod("isIntLteZero", function(value) {
         value=parseInt(value);      
         return isEmpty(value) || value<=0;       
    }, "整数必须小于或等于0");  
    
    // 判断浮点数value是否等于0 
    $.vf_validate.addMethod("isFloatEqZero", function(value) {
         value=parseFloat(value);      
         return isEmpty(value) || value==0;       
    }, "浮点数必须为0"); 
      
    // 判断浮点数value是否大于0
    $.vf_validate.addMethod("isFloatGtZero", function(value) {
         value=parseFloat(value);      
         return isEmpty(value) || value>0;       
    }, "浮点数必须大于0"); 
      
    // 判断浮点数value是否大于或等于0
    $.vf_validate.addMethod("isFloatGteZero", function(value) {
         value=parseFloat(value);      
         return isEmpty(value) || value>=0;       
    }, "浮点数必须大于或等于0");   
    
    // 判断浮点数value是否不等于0 
    $.vf_validate.addMethod("isFloatNEqZero", function(value) {
         value=parseFloat(value);      
         return isEmpty(value) || value!=0;       
    }, "浮点数必须不等于0");  
    
    // 判断浮点数value是否小于0 
    $.vf_validate.addMethod("isFloatLtZero", function(value) {
         value=parseFloat(value);      
         return isEmpty(value) || value<0;       
    }, "浮点数必须小于0");  
    
    // 判断浮点数value是否小于或等于0 
    $.vf_validate.addMethod("isFloatLteZero", function(value) {
         value=parseFloat(value);      
         return isEmpty(value) || value<=0;       
    }, "浮点数必须小于或等于0");  
    
    // 判断浮点型  
    $.vf_validate.addMethod("isFloat", function(value) {
         return isEmpty(value) || /^[-\+]?\d+(\.\d+)?$/.test(value);       
    }, "只能包含数字、小数点等字符"); 
     
    // 匹配integer
    $.vf_validate.addMethod("isInteger", function(value) {
         return isEmpty(value) || (/^[-\+]?\d+$/.test(value) && parseInt(value)>=0);       
    }, "匹配integer");  
     
    // 判断数值类型，包括整数和浮点数
    $.vf_validate.addMethod("isNumber", function(value) {
         return isEmpty(value) || /^[-\+]?\d+$/.test(value) || /^[-\+]?\d+(\.\d+)?$/.test(value);       
    }, "匹配数值类型，包括整数和浮点数");  
    
    // 只能输入[0-9]数字
    $.vf_validate.addMethod("isDigits", function(value) {
         return isEmpty(value) || /^\d+$/.test(value);       
    }, "只能输入0-9数字");  

     // 手机号码验证    
    $.vf_validate.addMethod("isMobile", function(value) {
      var length = value.length;    
      return isEmpty(value) || (length == 11 && /^(((13[0-9]{1})|(15[0-35-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));    
    }, "手机号码格式不正确。");

    // 电话号码验证    
    $.vf_validate.addMethod("isPhone", function(value) {
      var tel = /^(\d{3,4}-?)?\d{7,9}$/g;    
      return isEmpty(value) || (tel.test(value));    
    }, "电话号码格式不正确");

    // 联系电话(手机/电话皆可)验证   
    $.vf_validate.addMethod("isTel", function(value,element) {
        var length = value.length;   
        var mobile = /^(((13[0-9]{1})|(15[0-35-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        var tel = /^(\d{3,4}-?)?\d{7,9}$/g;       
        return isEmpty(value) || tel.test(value) || (length==11 && mobile.test(value));   
    }, "请输入正确手机号码或电话号码"); 
 
     // 匹配qq      
    $.vf_validate.addMethod("isQq", function(value) {
         return isEmpty(value) || /^[1-9]\d{4,12}$/;       
    }, "QQ号码不合法");   
 
     // 邮政编码验证    
    $.vf_validate.addMethod("isZipCode", function(value) {
      var zip = /^[0-9]{6}$/;    
      return isEmpty(value) || (zip.test(value));    
    }, "邮政编码不正确");  
    
    // 匹配密码，以字母开头，长度在6-16之间，只能包含字符、数字和下划线。      
    $.vf_validate.addMethod("isPwd", function(value) {
         return isEmpty(value) || /^[a-zA-Z]\\w{6,16}$/.test(value);       
    }, "以字母开头，长度在6-12之间，只能包含字符、数字和下划线。");  
    
    // 身份证号码验证
    $.vf_validate.addMethod("isIdCardNo", function(value) {
      //var idCard = /^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/;   
      return isEmpty(value) || isIdCardNo(value);    
    }, "身份证号码不正确"); 

    // IP地址验证   
    $.vf_validate.addMethod("ip", function(value) {
      return isEmpty(value) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);    
    }, "请填写正确的IP地址");
   
    // 字符验证，只能包含中文、英文、数字、下划线等字符。    
    $.vf_validate.addMethod("stringCheck", function(value) {
         return isEmpty(value) || /^[a-zA-Z0-9\u4e00-\u9fa5-_]+$/.test(value);       
    }, "只能包含中文、英文、数字、下划线等字符");   
   
    // 匹配english  
    $.vf_validate.addMethod("isEnglish", function(value) {
         return isEmpty(value) || /^[A-Za-z]+$/.test(value);       
    }, "只能输入英文");
    
    // 匹配汉字  
    $.vf_validate.addMethod("isChinese", function(value) {
         return isEmpty(value) || /^[\u4e00-\u9fa5]+$/.test(value);       
    }, "只能输入汉字");   
    
    // 匹配中文(包括汉字和字符) 
    $.vf_validate.addMethod("isChineseChar", function(value) {
         return isEmpty(value) || /^[\u0391-\uFFE5]+$/.test(value);       
    }, "只能输入中文字符(包括汉字和字符) ");
      
    // 判断是否为合法字符(a-zA-Z0-9-_)
    $.vf_validate.addMethod("isRightfulString", function(value) {
         return isEmpty(value) || /^[A-Za-z0-9_-]+$/.test(value);       
    }, "含有(A-Za-z-_)之外的非法字符");
    
    // 判断是否包含中英文特殊字符，除英文"-_"字符外
    $.vf_validate.addMethod("isContainsSpecialChar", function(value) {
         var reg = RegExp(/[(\ )(\`)(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\+)(\=)(\|)(\{)(\})(\')(\:)(\;)(\')(',)(\[)(\])(\.)(\<)(\>)(\/)(\?)(\~)(\！)(\@)(\#)(\￥)(\%)(\…)(\&)(\*)(\（)(\）)(\—)(\+)(\|)(\{)(\})(\【)(\】)(\‘)(\；)(\：)(\”)(\“)(\’)(\。)(\，)(\、)(\？)]+/);   
         return isEmpty(value) || !reg.test(value);       
    }, "含有中英文特殊字符");
	
	//车牌号校验
	$.vf_validate.addMethod("isPlateNo", function(value) {
		var reg = /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
		return isEmpty(value) || (reg.test(value));
	},"请输入正确车牌号");
	//不能为空
	$.vf_validate.addMethod("required",function (value) {
	    return !isEmpty(value);
    },"这是必填字段")
    //邮箱格式 -jquery-validate
    $.vf_validate.addMethod("email",function (value) {
        var reg = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/
        return isEmpty(value) || reg.test(value);
    },"请输入有效的电子邮件地址")
    //url格式 -jquery-validate
    $.vf_validate.addMethod("url",function (value) {
        var reg = /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$/i;
        return isEmpty(value) || reg.test(value);
    },"请输入有效的网址")
    //data -jquery-validate
    $.vf_validate.addMethod("data",function (value) {
        return isEmpty(value) || !/Invalid|NaN/.test( new Date( value ).toString() );
    },"请输入有效的日期")
    // dateISO-jquery-validate
    $.vf_validate.addMethod("dateISO", function (value) {
        return this.optional(element) || /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/.test(value);
    }, "请输入有效的日期 (YYYY-MM-DD)")
    // number-jquery-validate
    $.vf_validate.addMethod("number", function (value) {
        return this.optional(element) || /^(?:-?\d+|-?\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(value);
    }, "请输入有效的数字")
    // digits-jquery-validate
    $.vf_validate.addMethod("digits", function (value) {
        return this.optional(element) || /^\d+$/.test(value);
    }, "只能输入数字")
    //信用卡 creditcard-jquery-validate
    $.vf_validate.addMethod("creditcard",function (value) {
        if(isEmpty(value)){
            return true;
        }
        if ( /[^0-9 \-]+/.test( value ) ) {
            return false;
        }
        var nCheck = 0,
            nDigit = 0,
            bEven = false,
            n, cDigit;

        value = value.replace( /\D/g, "" );

        // Basing min and max length on
        // http://developer.ean.com/general_info/Valid_Credit_Card_Types
        if ( value.length < 13 || value.length > 19 ) {
            return false;
        }
        for ( n = value.length - 1; n >= 0; n--) {
            cDigit = value.charAt( n );
            nDigit = parseInt( cDigit, 10 );
            if ( bEven ) {
                if ( ( nDigit *= 2 ) > 9 ) {
                    nDigit -= 9;
                }
            }
            nCheck += nDigit;
            bEven = !bEven;
        }
        return ( nCheck % 10 ) === 0;
    },"请输入有效的信用卡号码")
    //长度 length(minLength,maxLength)
    $.vf_validate.addMethod("length",function (value, params) {
        var length = value.length;
        return isEmpty(value) || ( length >= params[0] && length <= params[1] );
    },"format:请输入长度在 {0} 到 {1} 之间的字符串")
    //值范围 range(minValue,maxValue)
    $.vf_validate.addMethod("range",function (value, params) {
        return isEmpty(value) || ( value >= params[0] && value <= params[1] );
    },"format:请输入范围在 {0} 到 {1} 之间的数值")
    //相等 equal(eqValue)
    $.vf_validate.addMethod("equalTo",function (value, eqName) {
        var eqVal = $("input[name='"+ eqName+"']").val();
        return isEmpty(value) || value ==eqVal;
    },"两次输入不一致")
    $.vf_validate.addMethod("isUsername",function (value) {
        return isEmpty(value) || /^[a-zA-Z][a-zA-Z0-9]*$/.test(value);
    },"以字母开头，只能包含英文字母，数字")

});

/**
 * NaN,''
 * @param val
 * @returns {boolean}
 */
function isEmpty(val) {
    return NaN == val || '' == val;
}

//身份证号码的验证规则
function isIdCardNo(num){ 
　   //if (isNaN(num)) {alert("输入的不是数字！"); return false;} 
　　 var len = num.length, re; 
　　 if (len == 15) 
　　 re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{2})(\w)$/); 
　　 else if (len == 18) 
　　 re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/); 
　　 else {
		//alert("输入的数字位数不对。"); 
		return false;
	} 
　　 var a = num.match(re); 
　　 if (a != null) 
　　 { 
　　 if (len==15) 
　　 { 
　　 var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]); 
　　 var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
　　 } 
　　 else 
　　 { 
　　 var D = new Date(a[3]+"/"+a[4]+"/"+a[5]); 
　　 var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
　　 } 
　　 if (!B) {
		//alert("输入的身份证号 "+ a[0] +" 里出生日期不对。"); 
		return false;
	} 
　　 } 
　　 if(!re.test(num)){
		//alert("身份证最后一位只能是数字和字母。");
		return false;
	}
　　 return true; 
}

//jquery-validation
var validate = {
    remote: function(value, element, param ) {
        if ( this.optional( element ) ) {
            return "dependency-mismatch";
        }

        var previous = this.previousValue( element ),
            validator, data;

        if (!this.settings.messages[ element.name ] ) {
            this.settings.messages[ element.name ] = {};
        }
        previous.originalMessage = this.settings.messages[ element.name ].remote;
        this.settings.messages[ element.name ].remote = previous.message;

        param = typeof param === "string" && { url: param } || param;

        if ( previous.old === value ) {
            return previous.valid;
        }

        previous.old = value;
        validator = this;
        this.startRequest( element );
        data = {};
        data[ element.name ] = value;
        $.ajax( $.extend( true, {
            mode: "abort",
            port: "validate" + element.name,
            dataType: "json",
            data: data,
            context: validator.currentForm,
            success: function( response ) {
                var valid = response === true || response === "true",
                    errors, message, submitted;

                validator.settings.messages[ element.name ].remote = previous.originalMessage;
                if ( valid ) {
                    submitted = validator.formSubmitted;
                    validator.prepareElement( element );
                    validator.formSubmitted = submitted;
                    validator.successList.push( element );
                    delete validator.invalid[ element.name ];
                    validator.showErrors();
                } else {
                    errors = {};
                    message = response || validator.defaultMessage( element, "remote" );
                    errors[ element.name ] = previous.message = $.isFunction( message ) ? message( value ) : message;
                    validator.invalid[ element.name ] = true;
                    validator.showErrors( errors );
                }
                previous.valid = valid;
                validator.stopRequest( element, valid );
            }
        }, param ) );
        return "pending";
    }
}


