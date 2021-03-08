<template>
    <div>
        <section class="project-details">
            
                <div class="block-title">
                    <p>Product Comment Dimension Radar</p>
                    <h3>竞品评价维度雷达图</h3>
                </div>

            <div class="chart1" id="main" ref="chart"></div>

        </section><!-- /.project-details -->
    </div>
</template>

<script>
    export default {
        name: 'eCharts',
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
                //console.log(info)
                var myChart = this.$echarts.init(document.getElementById('main'))
                
                var list = [];
                var series = [];

                for(let i = 0; i < info.length; i++){
                // info.forEach(function (e) {
                    list.push(info[i].brand);
                    series.push({
                        name: '评价维度',
                        type: 'radar',
                        symbol: 'none',
                        lineStyle: {
                            width: 1
                        },
                        emphasis: {
                            areaStyle: {
                                color: 'rgba(0,250,0,0.3)'
                            }
                        },
                        data: [{
                            value: [
                                info[i].package_score * 10,
                                info[i].price_score*10,
                                info[i].logistics_score*10,
                                info[i].taste_score*10,
                                info[i].service_score*10,
                            ],
                            name: info[i].brand
                        }]
                    })
                // });
                }

                // 绘制图表
                myChart.setOption({
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        type: 'scroll',
                        bottom: 10,
                        data: list
                    },
                    visualMap: {
                        top: 'middle',
                        right: 10,
                        color: ['red', 'yellow'],
                        calculable: true,
                        min: 0,
                        max: 10
                    },
                    radar: {
                        indicator: [{
                                text: '包装',
                                max: 10
                            },
                            {
                                text: '价格',
                                max: 10
                            },
                            {
                                text: '物流',
                                max: 10
                            },
                            {
                                text: '味道',
                                max: 10
                            },
                            {
                                text: '服务',
                                max: 10
                            }
                        ]
                    },
                    series: series
                });
                window.onresize = myChart.resize;
            }
        }
    };
</script>

<style>
    .chart1 {
        width: 100%;
        height: 400px;
        margin: 50px auto;
        border: 1px solid rgb(246, 245, 242);
        box-shadow: 8px 8px 8px rgba(0, 0, 0, .2)
    }
    .block-title{
    width: 100;
    margin: 0 auto;
    text-align:center;
  }
  .project-details{
      padding-bottom: 20px;
      padding-top: 30px;
  }
  .block-title h3{
        margin-bottom: 20px;
    }
</style>