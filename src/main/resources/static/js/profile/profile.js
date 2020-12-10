let vm = new Vue({
    el: '#app',
    data: {
        user: {},
        upuser:{},
        uppassword:{},
        isflag:[false],
        collectPageInfo:{},
        address:"",
        addressmsg:{},
        isdef:"0",
        addresses:"",
        countcar:"",
        countorder:"",
        payorders:{},
        putorders:{}
    },
    methods: {
        uploadPic: function () {
            let formData = new FormData();
            formData.append("pic",document.getElementById("picFile").files[0])
            axios({
                url: "profile/uploadpic",
                method: "post",
                data:formData,
                contentType:"multipart/form-data"
            }).then(response => {
                layer.msg(response.data.message)
                if (response.data.flag) {
                    this.user=response.data.data
                    let userjson = JSON.stringify(response.data.data);
                    localStorage.setItem("loginsession",userjson);
                } else {

                    // this.refresh()
                }
            }).catch(error => {
                layer.msg(error)
                // this.refresh()
            })
        },
        doUpdate: function () {
            axios({
                url: "profile/doupdate",
                method: "put",
                data:this.upuser
            }).then(response => {
                layer.msg(response.data.message)
                if (response.data.flag) {
                    this.user=response.data.data
                    let userjson = JSON.stringify(response.data.data);
                    localStorage.setItem("loginsession",userjson);
                } else {
                    this.upuser=JSON.parse(localStorage.getItem("loginsession"))
                    layer.msg(response.data.message)
                    // this.refresh()
                }
            }).catch(error => {
                layer.msg(error)
                // this.refresh()
            })
        },
        doUpPassword: function () {
            if (this.uppassword.password==""||this.uppassword.password==undefined){
                layer.msg("请输入原密码")
                return;
            }
            let flag=true
            for (let i = 0; i < this.isflag.length; i++) {
                if (this.isflag[i]==false){
                    flag=false
                }
            }
            if (flag) {
                axios({
                    url: "profile/doUpPassword",
                    method: "put",
                    data: this.uppassword
                }).then(response => {
                    layer.msg(response.data.message)
                    this.uppassword={}
                }).catch(error => {
                    layer.msg(error.message)
                })
            } else {
                layer.msg("信息有误")
            }

        },
        checkPassword: function () {
            if (this.uppassword.newpassword==""||this.uppassword.newpassword==undefined){
                layer.msg("请输入密码")
                this.isflag[0] = false
                return;
            }
            if (this.uppassword.repassword == this.uppassword.newpassword) {
                this.isflag[0] = true
            } else {
                if (this.uppassword.repassword == ""||this.uppassword.repassword==undefined) {
                    layer.msg("请再次输入密码")
                } else {
                    layer.msg("两次密码输入不一致")
                }
                this.isflag[0] = false
            }
        },
        findCollect:function (page=1) {
            axios({
                url: `profile/findcollect/${page}`,
                method: "get",
            }).then(response => {
                this.collectPageInfo=response.data.data
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        toDetail: function (data) {
            let JSONdata = JSON.stringify(data)
            sessionStorage.setItem("goodsmsg", JSONdata);
            window.location.href = "goods/detail"
        },
        addAddress:function () {
         let address= this.address.province + this.address.city + this.address.district + this.address.street + this.address.streetNumber;
         this.addressmsg.address=address;
            axios({
                url: `profile/addAddress/${this.isdef}`,
                method: "post",
                data: this.addressmsg
            }).then(response => {
                layer.msg(response.data.message)
                this.addressmsg={}
                if (this.isdef=='1'){
                    this.user=response.data.data
                    let userjson = JSON.stringify(response.data.data);
                    localStorage.setItem("loginsession",userjson);
                    this.isdef='0'
                }
                this.findAddress()
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        findAddress:function () {
            axios({
                url: `profile/findAddress`,
                method: "get"
            }).then(response => {
                this.addresses=response.data.data
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        delAddress:function (aId) {
            axios({
                url: `profile/delAddress/${aId}`,
                method: "put"
            }).then(response => {
                layer.msg(response.data.message)
                this.findAddress()
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        setDef:function (aId) {
            axios({
                url: `profile/resetdef/${aId}`,
                method: "put"
            }).then(response => {
                this.user=response.data.data
                let userjson = JSON.stringify(response.data.data);
                localStorage.setItem("loginsession",userjson);
                layer.msg(response.data.message)
                this.findAddress()
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        countCar:function () {
            axios({
                url: `profile/countcar`,
                method: "get"
            }).then(response => {
                this.countcar=response.data.data
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        countOrder:function () {
            axios({
                url: `profile/countorder`,
                method: "get"
            }).then(response => {
                this.countorder=response.data.data
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        findPayOrder:function () {
            axios({
                url: `profile/findpayOrder`,
                method: "get"
            }).then(response => {
                this.payorders=response.data.data
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        findPutOrder:function () {
            axios({
                url: `profile/findputorder`,
                method: "get"
            }).then(response => {
                this.putorders=response.data.data
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        confirm:function (oId) {
            axios({
                url: `profile/confirmreceipt/${oId}`,
                method: "put"
            }).then(response => {
                layer.msg(response.data.message)
                this.findPayOrder()
                this.findPutOrder()
            }).catch(error => {
                layer.msg(error.message)
            })
        }
    },
    created: function () {
        this.user=JSON.parse(localStorage.getItem("loginsession"))
        this.upuser=JSON.parse(localStorage.getItem("loginsession"))
        this.findCollect();
        this.findAddress();
        this.countCar();
        this.countOrder();
        this.findPayOrder();
        this.findPutOrder();
    }
});