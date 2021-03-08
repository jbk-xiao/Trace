// var echarts = require('echarts')
//import $ from 'jquery'
function graph(ret){
//var myChart = echarts.init(document.getElementById('lgmmain'));
var lgmmain = this.$refs.chart;
    let myChart = this.$echarts.init(lgmmain)
    
var option;
//console.log(ret)
//var key='\''+ret+'\'';
myChart.showLoading();
//$.get('http://121.46.19.26:8511/getGraphByKind/'+ret, function (graph) {
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
        animation: true,
        animationDuration: 1500,
        animationEasingUpdate: 'quinticInOut',
        series: [
            {
                name: '竞品关系图',
                type: 'graph',
                layout: 'force',
                
                data: ret.nodes,
                links: ret.links,
                categories: ret.categories,
                roam: true,
                label: {
                    show: false
                },
                lineStyle: {
                    color: 'source',
                    curveness: 0.3
                },
                emphasis: {
                    focus: 'adjacency',
                    lineStyle: {
                        width: 10
                    }
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
window.onresize = myChart.resize;
}

export{
    graph
}