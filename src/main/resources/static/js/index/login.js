let vm = new Vue({
    el: '#app',
    data: {
        username:"",
        password: "",
        code:"",
        rem:"",
        render:"kaptcha/render"
    },
    methods: {
        refresh:function () {
            this.render="kaptcha/render?"+new Date()
        },
        loginIn: function () {
            axios({
                url:"login/loginIn",
                method:"post",
                data:{username:this.username,password:this.password,code:this.code,rem:this.rem}
            }).then(response=>{
                let result=response.data.data
                if (response.data.flag){
                    let userjson = JSON.stringify(result);
                    localStorage.setItem("loginsession",userjson);
                    window.location.href="main/toHome"
                }else {
                    layer.msg(response.data.message)
                    this.refresh()
                }
            }).catch(error=>{
                layer.msg(error)
                this.refresh()
            })
        }
    }
});