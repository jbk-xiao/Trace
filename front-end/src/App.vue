<template>
  <div id="app">
    <router-view v-if="isRouterAlive"></router-view>
    
  </div>
</template>

<script>
export default {
  name: 'app',
  //提供一个依赖
  provide(){
    return {
      reload:this.reload
    }
  },
  data(){
    return {
      isRouterAlive:true
    }
  },
  //声明reload方法，控制router-view的显示或隐藏，从而控制页面的再次加载
  methods:{
    reload(){
      this.isRouterAlive = false;
      this.$nextTick(function(){
        this.isRouterAlive=true
      })
    }
  },
  components: {

  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
}
#app {
  height: 100%;
}

</style>
