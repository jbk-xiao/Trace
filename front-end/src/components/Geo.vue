<template>
  <div class="content">
    <div class="amap-wrapper">
      <el-amap
        class="amap-box"
        :zoom="zoom"
        :center="center"
        :mapStyle="mapStyle"
      >
        <el-amap-marker
          v-for="(marker, index) in markers"
          :position="marker.position"
          :key="index"
          :events="marker.events"
        ></el-amap-marker>
        <el-amap-info-window
          v-if="window"
          :position="window.position"
          :visible="window.visible"
          :content="window.content"
          :offset="window.offset"
        ></el-amap-info-window>
      </el-amap>
    </div>
  </div>
</template>

<style scoped>
.amap-wrapper {
  width: 80%;
  height: 400px;
  margin: 0 auto;
  margin-bottom: 30px;
}
.amap-wrapper {
  box-shadow: 10px 10px 10px rgba(0,0,0,.5)
}
.amap-box{
  width: 80%;
}
</style>

<script>
import axios from 'axios'
export default {
  // props: ["geo"],
  data() {
    return {
      markers_data: {},
      //center, zoom, mapStyle, windows, window, marker, markers是amap固定的参数，不能随意修改名字
      center: [121.539693, 31.126667], //地图中心点坐标
      zoom: 3, //初始化地图显示层级
      mapStyle: "normal", //设置地图样式, 还有dark等模式
      windows: [], //所有信息窗体的数组
      window: "", //一个信息窗体
      markers: [], //所有地点标志的数组
      geo: {},
    };
  },
  methods: {
    async point() {
      
      let { data } = await axios.get("http://121.46.19.26:8511/getCompet/520102000400793");
      //console.log(data)
      this.geo = data.compet_geoList;
      //console.log(this.geo)
      // this.markers_data = data.geo;
      // this.geo=this.markers_data;
      // console.log(data.geo.lng)
      // console.log(data);

      let windows = [];
      let markers = [];
      let that = this;
      //将获取到的raw data解析为amap可以读取的format
      //position, events等都是amap固定的，不能擅自修改
      this.geo.forEach((item, i) => {

      markers.push({
        position: [this.geo[i].lng, this.geo[i].lat],
        events: {
          click() {
            that.windows.forEach((window) => {
              window.visible = false; //关闭窗体
            });
            that.window = that.windows[i];
            that.$nextTick(() => {
              that.window.visible = true; //点击点坐标，出现信息窗体
            });
          },
        },
      });
      windows.push({
        position: [this.geo[i].lng, this.geo[i].lat],
        content:
          "<div>" + this.geo[i].proj_name +"<br>"+ this.geo[i].company_name+"</div>", //内容
        offset: [0, -40], //窗体偏移
        visible: false, //初始是否显示
      });
      // });
      //  加位置点
      this.markers = markers;
      //   加弹窗
      this.windows = windows;
    })
    },
  },
  mounted() {
    this.point();
  },
};
</script>