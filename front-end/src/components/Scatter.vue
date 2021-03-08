<template>
    <div>
        
            <div class="chartc" id="mainc" ref="chart"></div>
    </div>
</template>

<script>
    export default {
        name: 'eCharts',
        props: ['info'],
        data() {
            return {
                
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
                //console.log(info.data[1])
                var myChart = this.$echarts.init(document.getElementById('mainc'))
                
                var data = [];
                for (let i = 0; i < info.data.length; i++) {
                    data.push([info.data[i].ratio,info.data[i].pv,info.data[i].word])
                }
                //console.log(data)
                var time=info.period
                // 绘制图表
                myChart.setOption({
    title: {
        left: 'center',
        text: '绿色：搜索趋势下降 红色：搜索趋势上升',
        subtext:'统计时间'+time,
        subtextStyle:{
            align:'right'
        }
    },                    
    tooltip: {
            trigger: 'item',
            formatter: function (obj) {
            var value = obj.value;
            return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 15px;padding-bottom: 7px;margin-bottom: 7px">'
                + '搜索词：'
                + value[2]
                + '</div>'
                + '热度：' + value[1] + '<br>'
                + '搜索趋势：' + value[0] + '<br>';
        }
        },
        visualMap: {
        show: false,
        dimension: 0,
        pieces: [{
            lte: 1,
            color: 'green'
        },{
            gt:1,
            lte:2,
            color:'red'
        },
         {
            gt: 2,
            color: 'red'
        }]
    },
    singleAxis: [{
        left: 70,
        top:80,
        bottom:10,
        type: 'value',
        boundaryGap: true,
        min:0,
        max:3,
        height:15+'%',
        axisTick:{
            show:true,
        },
        splitLine:{
            show:false,
        }
    }],
    series: [{
        
        coordinateSystem: 'singleAxis',
        type: 'scatter',
        data:data,
        symbolSize: function (dataItem) {
            return Math.sqrt(dataItem[1])/3 ;
        }
    }],
                        
                        });
window.onresize = myChart.resize;
                    }
                }
            };
</script>

<style>
    .chartc {
        width: 80%;
        height: 180px;
        margin: 50px auto;
        border: 1px solid rgb(246, 245, 242);
        box-shadow: 8px 8px 8px rgba(0, 0, 0, .2)
    }
    .block-title{
    width: 100;
    margin: 0 auto;
    text-align:center;
  }
</style>