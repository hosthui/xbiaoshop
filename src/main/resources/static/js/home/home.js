let vm = new Vue({
    el: '#app',
    data: {
        newgoods: {},
        bargoods: {}
    },
    methods: {
        findNews: function () {
            axios({
                url: "home/selectnew",
                method: "get"
            }).then(response => {
                if (response.data.flag) {
                    this.newgoods = response.data.data
                } else {
                    layer.msg(response.data.message)
                    // this.refresh()
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

        }
    },
    created: function () {
        this.findNews();
        this.findBars();
    }
});