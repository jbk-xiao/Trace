//import $ from 'jquery'
function graph(ret){
//var myChart = echarts.init(document.getElementById('lgmmain'));
var tqmain = this.$refs.chart;
    let myChart = this.$echarts.init(tqmain)
var option;
myChart.showLoading();
//$.get('/data/brand.json', function (graph) {
    myChart.hideLoading();
    ret.nodes.forEach(function (node) {
        node.symbolSize = 20;
    });
    option = {
        title: {
            text: '关系图',
            top: 'bottom',
            left: 'right'
        },
        tooltip: {},
        legend: [{
            // selectedMode: 'single',
            data: ret.categories.map(function (a) {
                return a.name;
            })
        }],
        animation: false,
        series: [
            {
                name: '品牌竞品关系图',
                type: 'graph',
                layout: 'force',
                
                data: ret.nodes,
                links: ret.links,
                categories: ret.categories,
                roam: true,
                label: {
                    show: false
                },
                force: {
                    repulsion: 80,
                    edgeLength: 2,
                    layoutAnimation :true,
                    gravity:0.5
                },
                focusNodeAdjacency: true,
                draggable:true
            }
        ]
    };

    myChart.setOption(option, true);
//});
option && myChart.setOption(option);
}

export{
    graph
}