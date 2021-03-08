// var echarts = require('echarts')
//import $ from 'jquery'
function graph(ret){
//var myChart = echarts.init(document.getElementById('lgmmain'));
var lgmmain = this.$refs.chart;
    let myChart = this.$echarts.init(lgmmain)
var option;
//var key='\''+ret+'\'';
myChart.showLoading();
//$.get('http://121.46.19.26:8511/getGraphByKind/'+ret, function (graph) {
    myChart.hideLoading();
    ret.nodesMap.forEach(function (node) {
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
            data: ret.categoriesMap.map(function (a) {
                return a.name;
            })
        }],
        animation: false,
        series: [
            {
                name: '竞品关系图',
                type: 'graph',
                layout: 'force',
                
                data: ret.nodesMap,
                links: ret.linksMap,
                categories: ret.categoriesMap,
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