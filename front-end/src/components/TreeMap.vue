<template>
    <div>
        
            <div class="chartt" id="maint" ref="chart"></div>
    </div>
</template>
<script>
//import $ from 'jquery'
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
        methods:{
            draw(info){
                var myChart = this.$echarts.init(document.getElementById('maint'))
                
                //var ROOT_PATH = 'https://cdn.jsdelivr.net/gh/apache/echarts-website@asf-site/examples';
//var app = {};

var option;

myChart.showLoading();

//$.get(ROOT_PATH + '/data/asset/data/disk.tree.json', function (diskData) {
    myChart.hideLoading();

    /*function colorMappingChange(value) {
        var levelOption = getLevelOption(value);
        chart.setOption({
            series: [{
                levels: levelOption
            }]
        });
    }*/

    //var formatUtil = this.$echarts.format;

    function getLevelOption() {
        return [
            /*{
                itemStyle: {
                    borderColor: '#f7c35f',
                    borderWidth: 1,
                    gapWidth: 0
                },
                upperLabel: {
                    show: false
                }
            },*/
            {
                itemStyle: {
                    borderColor: '#f7c35f',
                    borderWidth: 6,
                    gapWidth: 6
                },
                emphasis: {
                    itemStyle: {
                        borderColor: '#f7c35f'
                    }
                }
            },
            {
                colorSaturation: [0.35, 0.5],
                itemStyle: {
                    borderWidth: 6,
                    gapWidth: 6,
                    borderColorSaturation: 0.6
                }
            }
        ];
    }

    myChart.setOption(option = {

        title: {
            subtext: '评价维度按色块划分'+'\n'+'点击方块放大查看详情',
            left: 'center',
            top:0
        },

        tooltip: {
            formatter: function (info) {
                //var value = info.value;
                var name = info.name;
                var val = info.value[1];
                return '<div class="tooltip-title">' + '关键词：'+name +'<br>'+'情感极性：'+val+ '</div>';
            }
        },
        //visualDimension: 2,
        visualMap: {
        //show: false,
        min: 0,
            max: 1,
            dimension: 1,
            text: ['正面', '负面'],
            realtime: false,
            calculable: true,
            inRange: {
                color: ['green', 'red']
            },
            
            
    },
        series: [
            {
                name: '评价维度',
                type: 'treemap',
                visibleMin: 2000,
                label: {
                    show: true,
                    formatter: '{b}'
                },
                upperLabel: {
                    show: true,
                    height: 28
                },
                itemStyle: {
                    borderColor: '#f7c35f'
                },
                levels: getLevelOption(),
                data: info
            }
        ]
    });
//});

option && myChart.setOption(option);
window.onresize = myChart.resize;
            }
        }
}
</script>

<style>
    .chartt {
        width: 70%;
        height: 600px;
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