let vm = new Vue({
    el: '#app',
    data: {
        hotPageInfo:{},
        bargoods:{}
    },
    methods: {
        findHot: function (page=1) {
            axios({
                url: `goods/findhot/${page}`,
                method: "get",
            }).then(response => {
                this.hotPageInfo=response.data.data
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
        }
    },
    created:function () {
        this.findHot();
        this.findBars();
    }
});