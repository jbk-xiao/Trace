<template>
    <div>
        <section class="project-details">
            
                <div class="block-title">
                    <p>Product Comment graph</p>
                    <h3>商品评论满意度分析</h3>
                </div>

            <div class="chart2" id="main2" ref="chart"></div>

        </section><!-- /.project-details -->
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
            //console.log(this.info.data)
            setTimeout(() => {
                this.draw(this.info)
            }, 1000);
        },
        methods: {
            draw(info) {
                var myChart = this.$echarts.init(document.getElementById('main2'))
                
                var data = [];
                for (let i = 0; i < info.data.length; i++) {
                    data.push({
                        value: info.data[i].value,
                        name: info.data[i].name
                    })
                }
                // 绘制图表
                myChart.setOption({
                        tooltip: {
                            trigger: 'item'
                        },
                        legend: {
                            top: '5%',
                            left: 'center'
                        },
                        series: [{
                            name: '访问来源',
                            type: 'pie',
                            radius: ['40%', '70%'],
                            avoidLabelOverlap: false,
                            itemStyle: {
                                borderRadius: 15,
                                borderColor: '#fff',
                                borderWidth: 2
                            },
                            label: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                label: {
                                    show: true,
                                    fontSize: '40',
                                    fontWeight: 'bold'
                                }
                            },
                            labelLine: {
                                show: false
                            },
                            data: data
                        }]
                        });
window.onresize = myChart.resize;
                    }
                }
            };
</script>

<style>
    .chart2 {
        width: 90%;
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