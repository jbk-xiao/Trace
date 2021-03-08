import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import * as echarts from 'echarts';//此处引入echarts
import BaiduMap from 'vue-baidu-map'
import 'echarts/dist/extension/bmap.js'
import VueAMap from 'vue-amap'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import 'jquery'
//import 'echarts-gl'
//import "echarts-wordcloud/dist/echarts-wordcloud.js";
//import "echarts-wordcloud/dist/echarts-wordcloud.min.js";

require('echarts-wordcloud');

//import chinaJson from 'echarts/map/json/china.json';//此处引入中国地图json
Vue.use(axios)
Vue.use(ElementUI)

Vue.prototype.$echarts = echarts
Vue.use(BaiduMap, {
  // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key /
  ak: 'BeXrLRHFruh6pjiXNq3LachO72AjHsAe'
  })
//echarts.registerMap('china', chinaJson);
//var behavior = echarts.init(document.getElementById('main'));
// vue-video-player
import VideoPlayer from 'vue-video-player'
require('video.js/dist/video-js.css')
require('vue-video-player/src/custom-theme.css')
import 'videojs-contrib-hls' //单是 RTMP 的话不需要第三方库，如果是 HLS 的话需要引入videojs-contrib-hls，看具体情况而定。
import './plugins/wyz-echarts/wyz-echarts.js'
Vue.use(VideoPlayer);

Vue.use(VueAMap)
VueAMap.initAMapApiLoader({
  key: '2b01c527483499451f85e8b0e1010727',
  plugin: [
    "AMap.Autocomplete",// 输入提示插件 
    "AMap.PlaceSearch",// POI搜索插件
    "AMap.Scale",// 右下角缩略图插件 比例尺
    "AMap.OverView",// 地图鹰眼插件
    "AMap.ToolBar",// 地图工具条
    "AMap.MapType",// 类别切换控件，实现默认图层与卫星图、实施交通图层之间切换的控制
    "AMap.PolyEditor",// 编辑 折线多，边形
    "AMap.CircleEditor",// 圆形编辑器插件
    "AMap.Geolocation", // 定位控件，用来获取和展示用户主机所在的经纬度位置
    "AMap.Geocoder",// 地理编码与逆地理编码服务，用于地址描述与坐标间的相互转换
    "AMap.AMapUI",// UI组件
  ]
})

Vue.config.productionTip = false

new Vue({
  router,
  
  render: h => h(App),
}).$mount('#app')


//window.ace = require('ace-builds')
//window.define = ace.define