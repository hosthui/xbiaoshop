let vm = new Vue({
    el: '#app',
    data: {
        searchMsg:{},
        searchPageInfo:{},
        bargoods:{}
    },
    methods: {
        findSearch: function (page=1) {
            axios({
                url: `goods/findsearch/${page}`,
                method: "get",
                params:{message:this.searchMsg}
            }).then(response => {
                this.searchPageInfo=response.data.data
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
        },
        clearSession:function () {
            debugger;
            sessionStorage.clear();
        }
    },
    created:function () {
        this.searchMsg=JSON.parse(sessionStorage.getItem("searchmsg"));
        this.findSearch();
        this.findBars();
    }
});