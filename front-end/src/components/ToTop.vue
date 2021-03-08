<template>
    <a href="javascript:void(0)" class="cd-top cd-is-visible cd-fade-out" @click="backtop">Top</a>
</template>

<style scoped>
.cd-top {
  display: inline-block;
  height: 40px;
  width: 40px;
  position: fixed;
  bottom: 50px;
  right: 10px;
  z-index: 999;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  text-indent: 100%;
  white-space: nowrap;
  background: #f7c35f;
  visibility: hidden;
  opacity: 0;
  -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
  filter: alpha(opacity=0);
  -webkit-transition: opacity .3s 0s, visibility 0s .3s;
  -moz-transition: opacity .3s 0s, visibility 0s .3s;
  transition: opacity .3s 0s, visibility 0s .3s;
  -webkit-border-radius: 20px;
  -moz-border-radius: 20px;
  -ms-border-radius: 20px;
  border-radius: 20px; }
  .cd-top:hover {
    opacity: 1;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=100)";
    filter: alpha(opacity=100);
    -webkit-transition: opacity .3s 0s, visibility 0s 0s;
    -moz-transition: opacity .3s 0s, visibility 0s 0s;
    transition: opacity .3s 0s, visibility 0s 0s; }
  .cd-top.cd-is-visible {
    visibility: visible;
    opacity: 1;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=100)";
    filter: alpha(opacity=100); }
  .cd-top.cd-fade-out {
    opacity: 0.5;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=50)";
    filter: alpha(opacity=50);
    color: #fff; }
</style>

<script>
export default {
    mounted() {
       window.addEventListener("scroll",this.showbtn,true);
    },
    methods: {
        // 显示回到顶部按钮
       showbtn(){
          let that = this;
          let scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
          that.scrollTop = scrollTop
       },
      /**
        * 回到顶部功能实现过程：
        * 1. 获取页面当前距离顶部的滚动距离（虽然IE不常用了，但还是需要考虑一下兼容性的）
        * 2. 计算出每次向上移动的距离，用负的滚动距离除以5，因为滚动的距离是一个正数，想向上移动就是做一个减法
        * 3. 用当前距离加上计算出的距离，然后赋值给当前距离，就可以达到向上移动的效果
        * 4. 最后记得在移动到顶部时，清除定时器
      */
      backtop(){
          var timer = setInterval(function(){
            let osTop = document.documentElement.scrollTop || document.body.scrollTop;
            let ispeed = Math.floor(-osTop / 5); 
            document.documentElement.scrollTop = document.body.scrollTop = osTop + ispeed;
            this.isTop = true;
            if(osTop === 0){
              clearInterval(timer);
            }
          },30)
      }
    }
}
</script>