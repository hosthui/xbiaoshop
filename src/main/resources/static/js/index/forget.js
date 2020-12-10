let vm = new Vue({
    el: '#app',
    data: {
        email:"",
        username:"",
        password:"",
        code:"",
        user:{}
    },
    methods: {
        reset: function () {
            if (this.code==""||this.code==undefined){
                layer.msg("验证码不能为空")
                return;
            }
            axios({
                url: `login/reset/${this.code}`,
                method: "put",
                data: this.user
            }).then(response => {
                if (response.data.flag) {//如果返回success是true
                    layer.msg(response.data.message);
                    setTimeout(function () {
                        window.location.href = "login.html";
                    }, 1000);
                }
                //提示

            }).catch(error => {
                layer.msg(error.message);
            });
        }
        ,
        sendCode: function () {
            axios({
                url: "login/send",
                method: "get",
                params: {'email':this.email,'username':this.username}
            }).then(response => {
                if (response.data.flag) {
                    debugger
                    layer.msg(response.data.message)
                    this.user.email=this.email
                    this.user.userName=this.username
                    this.user.password=this.password
                } else {
                    //失败提示
                    layer.msg(response.data.message);
                }
            }).catch(error => {
                layer.msg(error.message);//异常提示
            })
        }
    }
})