let vm = new Vue({
    el: '#app',  //选中整个main
    data: {
        user: {},
        repassword: "",
        flag: false,
        isflag:[false,false,false]
    },
    methods: {
        checkEmail: function (event) {
            if (this.user.email != "") {
                axios({
                    url: `login/checkemail`,
                    method: "get",
                    params:{email:this.user.email}
                }).then(response => {
                    if (response.data.flag) {
                        layer.msg(response.data.message)
                        this.isflag[0] = false
                    } else {
                        this.isflag[0] = true
                    }
                })
            }
        },
        checkUsername: function (event) {
            if (this.user.username != "") {
                axios({
                    url: `login/checkusername`,
                    method: "get",
                    params:{userName:this.user.userName}
                }).then(response => {
                    if (response.data.flag) {
                        layer.msg(response.data.message)
                        this.isflag[1] = false
                    } else {
                        this.isflag[1] = true
                    }
                })
            }
        },
        checkPassword: function () {
            if (this.user.password==""||this.user.password==undefined){
                layer.msg("请输入密码")
                this.isflag[2] = false
                return;
            }
            if (this.repassword == this.user.password) {
                this.isflag[2] = true
            } else {
                if (this.repassword == "") {
                    layer.msg("请再次输入密码")
                } else {
                    layer.msg("两次密码输入不一致")
                }
                this.isflag[2] = false
            }
        },
        addUser: function () {
            if (Object.keys(this.user).length != 3) {
                layer.msg("请填写完整信息")
                this.flag = false
                return;
            } else {
                for (let userKey = 0; userKey <this.user.length; userKey++) {
                    if (this.user[userKey] == "") {
                        layer.msg("请填写完整信息")
                        this.flag = false
                        return;
                    }
                }
                this.flag = true
            }
            for (let i = 0; i < this.isflag.length; i++) {
                if (this.isflag[i]==false){
                    this.flag=false
                }
            }
            if (this.flag) {
                axios({
                    url: "login/register",
                    method: "post",
                    data: this.user
                }).then(response => {
                    layer.msg(response.data.message)
                    setTimeout(function () {
                        window.location.href = "login.html";
                    }, 1000);
                }).catch(error => {
                    layer.msg(error.message)
                })
            } else {
                layer.msg("信息有误")
            }
        }
    }
});