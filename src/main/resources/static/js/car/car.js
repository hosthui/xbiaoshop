let vm = new Vue({
    el: '#app',
    data: {
        cars:{},
        oldNum:[],
        bargoods:{}
    },
    methods: {
        findCars: function () {
            axios({
                url: "car/findcars",
                method: "get",
            }).then(response => {
                if (response.data.flag) {
                    this.cars = response.data.data
                } else {
                    layer.msg(response.data.message)
                    // this.refresh()
                }
            }).catch(error => {
                layer.msg(error)
                // this.refresh()
            })
        },
        delCar:function (gId) {
            layer.confirm('您是否删除？', {
                btn: ['确定','取消'] //按钮
            }, ()=>{
                axios({
                    url: `car/delcar/${gId}`,
                    method: "put",
                }).then(response => {
                    this.findCars()
                    layer.msg(response.data.message)
                }).catch(error => {
                    layer.msg(error)
                    // this.refresh()
                })
            }, ()=>{
            });

        },
        settle:function (gId,gNum) {
            axios({
                url: `car/settle/${gId}/${gNum}`,
                method: "post",
            }).then(response => {
                if (response.data.flag) {
                    window.location.href="waitPay.html"
                }

            }).catch(error => {
                layer.msg(error)
                // this.refresh()
            })
        },
        settleAll:function () {
            axios({
                url: `car/settleAll`,
                method: "post",
                data:this.cars
            }).then(response => {
                if (response.data.flag) {
                    window.location.href="waitPay.html"
                }
            }).catch(error => {
                layer.msg(error)
                // this.refresh()
            })
        },
        findBars: function () {
            axios({
                url: "home/selectbargain",
                method: "get"
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
        toDetail: function (data) {
            let JSONdata = JSON.stringify(data)
            sessionStorage.setItem("goodsmsg", JSONdata);
            window.location.href = "goods/detail"

        },
        chnum:function (num,index) {
            if (this.cars[index].gNum==1&&num==-1){
                //删除
                this.delCar(this.cars[index].goodsId)
                return;
            }
            this.cars[index].gNum=this.cars[index].gNum+num
        }
    },
    computed:{
        countmoney:{
            get: function ()
            {
                let count=0;
                for (let i = 0; i < this.cars.length; i++) {
                    count=count+this.cars[i].gNum*this.cars[i].price
                }

                return count
            }
        }
    },
    created:function () {
        this.findCars();
        this.findBars();
    }
});