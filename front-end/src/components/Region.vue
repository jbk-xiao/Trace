<template>
    <div>
        <div class="chartr" id="mainr" ref="myEchart"></div>
    </div>
</template>

<script>
import * as echarts from 'echarts';
import "../../public/js/china.js"
export default {
    name: 'eCharts',
    props: ['info'],
    mounted() {
            setTimeout(() => {
                this.draw(this.info)
            }, 1000);
        },
    methods:{
        draw(info){
            this.myEchart = echarts.init(this.$refs.myEchart);
            
            //console.log(info.data)
            var data = [];
                for (let i = 0; i < info.data.length; i++) {
                    data.push({
                        value: info.data[i].value,
                        name: info.data[i].name
                    })
                }
              var time=info.period
            let option = {
              title:{
        left: 'left',
        subtext: '统计时间:'+time,
        subtextStyle:{
            align:'left'
        }
    }, 
        tooltip: {
            trigger: 'item',
            formatter: '{b}<br/>搜索指数：{c}'
        },
        geo: {
          map: "china",
          roam: false,// 一定要关闭拖拽
          zoom: 1.23,
          center: [105, 36], // 调整地图位置
          label: {
            normal: {
              show: false, //关闭省份名展示
              fontSize: "10",
              color: "rgba(0,0,0,0.7)"
            },
            emphasis: {
              show: false
            }
          },
          /*itemStyle: {
            normal: {
              areaColor: "#fff",
              borderColor: "#389dff",
              borderWidth: 1, //设置外层边框
              shadowBlur: 5,
              shadowOffsetY: 8,
              shadowOffsetX: 0,
              shadowColor: "#01012a"
            },
            emphasis: {
              areaColor: "#fff",
              shadowOffsetX: 0,
              shadowOffsetY: 0,
              shadowBlur: 5,
              borderWidth: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)"
            }
          }*/
        },
        visualMap: {
            min: 100,
            max: 2000,
            text: ['High', 'Low'],
            realtime: false,
            calculable: true,
            inRange: {
                color: ['rgb(246, 245, 242)', '#f7c35f']
            },
            outOfRange: {
                    color: ['rgb(246, 245, 242)']
                },
            controller: {
                inRange: {
                    color: ['rgb(246, 245, 242)','#f7c35f']
                },
                outOfRange: {
                    color: ['rgb(246, 245, 242)']
                }
            }
        },
        series: [
          {
            type: "map",
            map: "china",
            roam: false,
            data:data,
            zoom: 1.23,
            center: [105, 36],
            // geoIndex: 1,
            // aspectScale: 0.75, //长宽比
            showLegendSymbol: false, // 存在legend时显示
            label: {
              normal: {
                show: false
              },
              emphasis: {
                show: false,
                textStyle: {
                  color: "#fff"
                }
              }
            },
            itemStyle: {
              normal: {
                areaColor: "rgb(246, 245, 242)",
                borderColor: "#f7c35f",
                borderWidth: 0.5
              },
              emphasis: {
                areaColor: "#f7c35f",
                shadowOffsetX: 0,
                shadowOffsetY: 0,
                shadowBlur: 2,
                borderWidth: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)"
              }
            }
          }
        ]
      };
this.myEchart.setOption(option);
window.onresize = this.myEchart.resize;

        }
    }
}
</script>

<style>
    .chartr {
        width: 100%;
        height: 400px;
        margin: 50px auto;
        margin-top: 95px;
        border: 1px solid rgb(246, 245, 242);
        box-shadow: 8px 8px 8px rgba(0, 0, 0, .2)
    }
    .block-title{
    width: 100;
    margin: 0 auto;
    text-align:center;
  }
</style>