let vm = new Vue({
    el: '#app',
    data: {
        bargoods: {},
        goods:{},
        iscoll:"",
        num:1,
        goodsimgs:[]
    },
    methods: {
        findBars: function () {
            axios({
                url: "goods/selectbargain",
                method: "get",
                params:{"typeid":this.goods.typeId}
            }).then(response => {
                if (response.data.flag) {
                    this.bargoods = response.data.data
                } else {
                    layer.msg(response.data.message)
                    // this.refresh()
                }
            }).catch(error => {
                layer.msg(error)
                // this.refresh()
            })
        },
        Collect:function () {
            axios({
                url: `goods/collect/${this.goods.goodsId}`,
                method: "post",
            }).then(response => {
                this.iscoll=!this.iscoll
                layer.msg(response.data.message)
            }).catch(error => {
                layer.msg(error)
                // this.refresh()
            })
        },
        upLook:function () {
            axios({
                url: `goods/uplook/${this.goods.goodsId}`,
                method: "put",
            }).then(response => {
                this.iscoll=response.data.data
                this.goods.look=this.goods.look+1;
                let userjson = JSON.stringify(this.goods);
                sessionStorage.setItem("goodsmsg",userjson);
            }).catch(error => {
                layer.msg(error)
                // this.refresh()
            })
        },
        addCar:function () {
            axios({
                url: `goods/addcar`,
                method: "post",
                data:{"gId":this.goods.goodsId,"gNum":this.num}
            }).then(response => {
                layer.msg(response.data.message)
            }).catch(error => {
                layer.msg(error)
                // this.refresh()
            })
        },
        chnum:function (num) {
            if (this.num==1&&num==-1){

                return;
            }
            this.num=this.num+num
        },
        toDetail: function (data) {
            let JSONdata = JSON.stringify(data)
            sessionStorage.setItem("goodsmsg", JSONdata);
            window.location.href = "goods/detail"

        }
    },
    computed:{
        countmoney:{
            get: function ()
            {
                return this.num*this.goods.price
            }
        }
    },
    created:function () {
        this.goods=JSON.parse(sessionStorage.getItem("goodsmsg"));
        this.findBars();
        this.goodsimgs=this.goods.goodsPhotos.split(",")
    },
    mounted:function () {
        this.upLook();
    }
});