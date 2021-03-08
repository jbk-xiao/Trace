<template>
    <div>
        
            <div class="charti" id="maini" ref="chart"></div>
    </div>
</template>

<script>
    export default {
        name: 'eCharts',
        props: ['info'],
        data() {
            return {
                // list: []
            }
        },
        mounted() {
            setTimeout(() => {
                //console.log(this.info)
                this.draw(this.info)
            }, 1000);
        },
        methods: {
            draw(info) {
                var myChart = this.$echarts.init(document.getElementById('maini'))
                //console.log(info.date)
                var option;
                var data = [];
                for (let i = 0; i < info.index.length; i++) {
                    data.push({
                        value:info.index[i]
                    })
                }

var date = [];

for (let i = 0; i < info.date.length; i++) {
                    date.push({
                        value:info.date[i]
                    })
                }

option = {
    tooltip: {
        trigger: 'axis',
        position: function (pt) {
            return [pt[0], '10%'];
        }
    },
    title: {
        left: 'center',
        subtext:'蓝色线为搜索指数，黄色线为预测'
    },
    toolbox: {
        feature: {
            
            restore: {},
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: date
    },
    yAxis: {
        type: 'value',
        name:'搜索指数',
        boundaryGap: [0, '100%']
    },
    dataZoom: [{
        type: 'inside',
        start: 0,
        end: 100
    }, {
        start: 0,
        end: 100
    }],
    visualMap: {
        show: false,
        dimension: 0,
        pieces: [{
            lte: 73,
            color: '#5470C6',
            areaStyle: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgb(128, 178, 228)'
                }, {
                    offset: 1,
                    color: 'rgb(84,112,198)'
                }])
            }
        },{
            gt:73,
            lte:74,
            color:'#f7c35f',
            areaStyle: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgb(246, 245, 242)'
                }, {
                    offset: 1,
                    color: '#f7c35f'
                }])
            },
        },
         {
            gt: 74,
            color: '#f7c35f',
            areaStyle: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgb(246, 245, 242)'
                }, {
                    offset: 1,
                    color: '#f7c35f'
                }])
            },
        }]
    },
    series: [
        {
            name: '搜索指数',
            type: 'line',
            symbol: 'none',
            sampling: 'lttb',
            itemStyle: {
                color: 'rgb(84,112,198)'
            },
            /*areaStyle: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgb(128, 178, 228)'
                }, {
                    offset: 1,
                    color: 'rgb(84,112,198)'
                }])
            },*/
            data: data
        }
    ]
};

option && myChart.setOption(option);
window.onresize = this.myEchart.resize;
            }
        }
    }
</script>

<style>
    .charti {
        width: 100%;
        height: 400px;
        margin: 50px auto;
        margin-top: 95px;
        border: 1px solid rgb(246, 245, 242);
        box-shadow: 8px 8px 8px rgba(0, 0, 0, .2)
    }
    
</style>