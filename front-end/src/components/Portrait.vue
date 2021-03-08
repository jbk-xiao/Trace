<template>
    <div>
        <div id="list">
			<ul v-for="item in name" :key="item" @click="changedata(item)">
				<li><button style="border-radius: 5px;">{{item}}</button></li>
			</ul>
		</div>
    <div class="chartp" id="mainp" ref="chartp">
        
    </div>
    </div>
</template>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

<script>
export default {
    name: 'eCharts',
    props: ['info'],
    data() {
            return {
                list: [],
                name:['年龄分布','性别分布']
            }
        },
        mounted() {
            setTimeout(() => {
                this.draw(this.info)
            }, 1000);
            /*$(".tabToggle").click(function () {
                var _this=this
                if ($(this).val() == 1) {
                    setTimeout(() => {
                        _this.draw()
                    }, 1000);//第二个tab图表初始化
                    console.log($(this).val());
                }
            });*/
        },
        methods:{
            changedata(item){
				//console.log(item);
				if(item=='年龄分布'){
					
                    this.draw(this.info)
				};
				if(item=='性别分布'){
					
                    this.drawsex(this.info)
				}
				
			},
            draw(info){
                var myChart = this.$echarts.init(document.getElementById('mainp'))
                
                var option;
                var arate = [];
                for (let i = 0; i < info.age_distribution.data.length; i++) {
                    arate.push(info.age_distribution.data[i].all_rate)
                }
                var wrate = [];
                for (let i = 0; i < info.age_distribution.data.length; i++) {
                    wrate.push(info.age_distribution.data[i].word_rate)
                }
                var tgi = [];
                for (let i = 0; i < info.age_distribution.data.length; i++) {
                    tgi.push(info.age_distribution.data[i].tgi)
                }
                var time=info.age_distribution.period

var colors = ['#5470C6', '#f7c35f', '#255946'];

option = {
    color: colors,
    title:{
        left: 'left',
        subtext: '统计时间:'+time,
        subtextStyle:{
            align:'left'
        }
    }, 
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross'
        }
    },
    
    toolbox: {
        feature: {
            dataView: {show: true, readOnly: false},
            restore: {show: true}
        },
        left:'right'
    },
    legend: {
        data: ['搜索年龄分布', '全网年龄分布', 'TGI'],
        top:"8%"
    },
    xAxis: [
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            data: ['≤19', '20~29', '30~39', '40~49', '≥50']
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '年龄分布',
            min: 0,
            max: 50,
            position: 'right',
            axisLine: {
                show: true,
                lineStyle: {
                    color: colors[0]
                }
            },
            axisLabel: {
                formatter: '{value} %'
            }
        },
        {
            type: 'value',
            
            min: 0,
            max: 50,
            position: 'right',
            offset: 80,
            axisLine: {
                show: false,
                
            },
            axisLabel: {
                formatter: '{value} %'
            }
        },
        {
            type: 'value',
            name: 'TGI',
            min: 0,
            max: 150,
            position: 'left',
            axisLine: {
                show: true,
                lineStyle: {
                    color: colors[2]
                }
            },
            axisLabel: {
                formatter: '{value} '
            }
        }
    ],
    series: [
        {
            name: '搜索年龄分布',
            type: 'bar',
            data: wrate
        },
        {
            name: '全网年龄分布',
            type: 'bar',
            yAxisIndex: 1,
            data: arate
        },
        {
            name: 'TGI',
            type: 'line',
            yAxisIndex: 2,
            data: tgi
        }
    ]
};

option && myChart.setOption(option);
window.onresize = myChart.resize;
            },
            drawsex(info){
var colors = ['#5470C6', '#f7c35f', '#255946'];

var myChart = this.$echarts.init(document.getElementById('mainp'))
var arate = [];
                for (let i = 0; i < info.sex_distribution.data.length; i++) {
                    arate.push(info.sex_distribution.data[i].all_rate)
                }
                var wrate = [];
                for (let i = 0; i < info.sex_distribution.data.length; i++) {
                    wrate.push(info.sex_distribution.data[i].word_rate)
                }
                var tgi = [];
                for (let i = 0; i < info.sex_distribution.data.length; i++) {
                    tgi.push(info.sex_distribution.data[i].tgi)
                }
                //var time=info.sex_distribution.period
//window.onresize = myChart2.resize; 
//var option2;
var option = {
    color: colors,

    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross'
        }
    },
    
    toolbox: {
        feature: {
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            magicType:{show:true}
        }
    },
    legend: {
        data: ['搜索性别分布', '全网性别分布', 'TGL']
    },
    xAxis: [
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            data: ['女', '男']
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '性别分布',
            min: 0,
            max: 80,
            position: 'right',
            axisLine: {
                show: true,
                lineStyle: {
                    color: colors[0]
                }
            },
            axisLabel: {
                formatter: '{value} %'
            }
        },
        {
            type: 'value',
            
            min: 0,
            max: 80,
            position: 'right',
            offset: 80,
            axisLine: {
                show: false,
                
            },
            axisLabel: {
                formatter: '{value} %'
            }
        },
        {
            type: 'value',
            name: 'TGL',
            min: 0,
            max: 150,
            position: 'left',
            axisLine: {
                show: true,
                lineStyle: {
                    color: colors[2]
                }
            },
            axisLabel: {
                formatter: '{value} '
            }
        }
    ],
    series: [
        {
            name: '搜索性别分布',
            type: 'bar',
            data: wrate
        },
        {
            name: '全网性别分布',
            type: 'bar',
            yAxisIndex: 1,
            data: arate
        },
        {
            name: 'TGL',
            type: 'line',
            yAxisIndex: 2,
            data: tgi
        }
    ]
};

option && myChart.setOption(option);
window.onresize = myChart.resize;
            },
        }
}
</script>

<style>
    .chartp {
        width: 100%;
        height: 400px;
        margin: 20px auto;
        border: 1px solid rgb(246, 245, 242);
        box-shadow: 8px 8px 8px rgba(0, 0, 0, .2)
    }
    #list{
        width: 180px;
        height: 50px;
        margin: 0 auto;
    }
    #list ul{
        width: 85px;
        list-style: none;
        
        height: 44px;
        margin: 0;
        display: inline;
    }
    #list li{
        
        display: inline;
        line-height: 40px;
    float:left;
    margin-left: 5px;
    }
    #list button{
        
        width: 85px;
        background-color: #f7c35f;
        display: block;
        margin: 0 auto;
    }
</style>