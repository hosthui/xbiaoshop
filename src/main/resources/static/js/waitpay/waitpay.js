let vm = new Vue({
    el: '#app',
    data: {
        orders:{},
        bargoods:{}
    },
    methods: {
        findOrders: function () {
            axios({
                url: "order/findorders",
                method: "get",
            }).then(response => {
                if (response.data.flag) {
                    this.orders = response.data.data
                } else {
                    layer.msg(response.data.message)
                    // this.refresh()
                }
            }).catch(error => {
                layer.msg(error)
                // this.refresh()
            })
        },
        delOrder: function (oId) {
            layer.confirm('您是否删除？', {
                btn: ['确定', '取消'] //按钮
            }, () => {
                axios({
                    url: `order/delorder/${oId}`,
                    method: "put",
                }).then(response => {
                    this.findOrders()
                    layer.msg(response.data.message)
                }).catch(error => {
                    layer.msg(error)
                    // this.refresh()
                })
            }, () => {
            });

        },
        pay: function (oId) {
            axios({
                url: `order/pay/${oId}`,
                method: "put",
            }).then(response => {
                layer.msg(response.data.message)
                this.findOrders()
            }).catch(error => {
                layer.msg(error)
                // this.refresh()
            })
        },
        payAll: function () {
            axios({
                url: `order/payAll`,
                method: "put"
            }).then(response => {
                layer.msg(response.data.message)
                this.findOrders()
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

        }
    },
    computed:{
        countmoney:{
            get: function ()
            {
                let count=0;
                for (let i = 0; i < this.orders.length; i++) {
                    count=count+this.orders[i].gNum*this.orders[i].price
                }

                return count
            }
        }
    },
    created:function () {
        this.findOrders();
        this.findBars();
    }
});