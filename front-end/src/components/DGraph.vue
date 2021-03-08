<template>
    <div>

            <div class="chartd" id="maind" ref="chart"></div>
    </div>
</template>

<script>
//import $ from 'jquery'

//import 'echarts-gl/dist/echarts-gl';
//import 'echarts-gl';
import * as echarts from 'echarts';
require('echarts-gl')
    export default {
        name:'eCharts',
        props: ['info'],
        data() {
            return {
                list: []
            }
        },
        mounted() {
            setTimeout(() => {
                
                this.draw(this.info)
            }, 1000);
        },
        methods: {
            draw(info) {
                
                var myChart = echarts.init(document.getElementById('maind'))
                
                var option;
                //var ROOT_PATH = 'https://cdn.jsdelivr.net/gh/apache/echarts-website@asf-site/examples';
                var data = info;
                //$.get(ROOT_PATH + '/data/asset/data/life-expectancy-table.json', function (data) {
                    console.log(data)
    var symbolSize = 20;
    option = {
        tooltip: {
            show:true,
            trigger: 'item',
        },
        grid3D: {},
        xAxis3D: {
            name: "评论分数",
            type: 'value',

        },
        yAxis3D: {
            name: "评论数",
            type: 'value',

        },
        zAxis3D: {
            name: "价格",
            type: 'value',
        },
        dataset: {
            dimensions: [
                'brand',
                'comment_score',
                'count',
                'sku_id',

               {name: 'price', type: 'ordinal'}
            ],
            source: data
        },
        series: [
            {
                type: 'scatter3D',
                symbolSize: symbolSize,
                encode: {
                    x: 1,
                    y: 2,
                    z: 4,
                    
                    tooltip: [0, 1, 2, 3, 4]
                }
            }
        ]
    };

    myChart.setOption(option);


option && myChart.setOption(option);
window.onresize = myChart.resize;
            }
        }
    }
</script>

<style>
    .chartd {
        width: 100%;
        height: 400px;
        margin: 50px auto;
        
        border: 1px solid rgb(246, 245, 242);
        box-shadow: 8px 8px 8px rgba(0, 0, 0, .2)
    }
    
</style>