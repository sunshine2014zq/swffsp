/**
 * 用户管理-表单验证
 * Created by sun on 2019/3/11 17:05
 */

var validate = {
    obj: {},
    errors: {},
    rules: {
        username: {
            required: true,
            isUsername: true,
            length: {
                min: 3,
                max: 10
            }
        },
        nickName: {
            stringCheck: true,
            length: {
                min: 1,
                max: 10
            }
        },
        password: {
            required: true,
            isRightfulString: true,
            length: {
                min: 6,
                max: 16
            }
        },
        rePassword: {
            required: true,
            equalTo: "password"
        },
        phoneNum: {
            isMobile: true
        },
        email: {
            email: true
        },
        roles: {
            required: true
        }
    },
    messages: {
        rePassword: {
            equalTo: "两次输入的密码不一致"
        }
    },
    others: [{selector: "select", event: "change"}]
}