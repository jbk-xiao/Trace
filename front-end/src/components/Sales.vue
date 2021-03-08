<template>
    <div>
        <section class="project-details">
            
                <div class="block-title">
                    <p>Sales & Prediction</p>
                    <h3>商品销量及预测</h3>
                </div>
            <div class="charts" id="mains" ref="chart"></div>
        </section>
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
                //console.log(this.info.sale)
                this.draw(this.info)
            }, 1000);
        },
        methods: {
            draw(info) {
                var myChart = this.$echarts.init(document.getElementById('mains'))
                
                var option;
                var data = [];
                for (let i = 0; i < info.sale.length; i++) {
                    data.push({
                        value:info.sale[i]
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
        text: '销量及预测',
        subtext:'绿色线为销量，红色线为预测'
    },
    toolbox: {
        feature: {
            
            restore: {},
            
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: date
    },
    yAxis: {
        type: 'value',
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
            lte: 36,
            color: 'green'
        },{
            gt:36,
            lte:37,
            color:'red'
        },
         {
            gt: 37,
            color: 'red'
        }]
    },
    series: [
        {
            name: '销量',
            type: 'line',
            symbol: 'none',
            sampling: 'lttb',
            itemStyle: {
                color: 'rgb(84,112,198)'
            },
            
            data: data
        }
    ]
};

option && myChart.setOption(option);
window.onresize = myChart.resize;
            }
        }
    }
</script>

<style>
    .charts {
        width: 90%;
        height: 400px;
        margin: 50px auto;
        
        border: 1px solid rgb(246, 245, 242);
        box-shadow: 8px 8px 8px rgba(0, 0, 0, .2)
    }
    .project-details{
      padding-bottom: 20px;
      padding-top: 30px;
  }
  .block-title h3{
        margin-bottom: 20px;
    }
</style>