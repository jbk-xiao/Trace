<template>
  <section class="page-header">
    <div class="page-header__bg" style="background-color: 255946;"></div>
    <!-- /.page-header__bg -->
    <div class="container">
 
      <el-tabs :stretch="true" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="产品溯源" name="first">
          <search-box1 class="center search-box1" />
          <div class="suggestion">
                <span class="title">示例溯源码:</span>
                <span class="content">
                  <div
                    v-for="(suggestion, index) in search_suggestion1"
                    :key="index" @click="loading"
                  >
                    <router-link
                      :to="{ path: '/Origin', query: { query: suggestion } }"
                      tag="a"
                      >{{ suggestion }}</router-link
                    >
                  </div>
                </span>
              </div>
        </el-tab-pane>
        <el-tab-pane label="产品搜索" name="second">
          <search-box2 class="center search-box2" />
        </el-tab-pane>
      </el-tabs>
    </div><!-- /.container -->
  </section>
</template>

<style>
  .container.h2 {
    margin-bottom: 60px;
  }
  .page-header{
    height: 350px;
  }
  .el-tabs__content {
    height: 200px;
  }

  .el-tabs.el-tabs--top{
    width: 50%;
    margin: 0 auto;
  }
  .el-tabs__header {
    margin-left: auto;
    margin-right: auto;
    margin-top: 60px;
    width: 80%;
  }


  .el-tabs__nav-scroll {
    width: 100%;
    margin: 0 auto;
    margin-left: 0;
    margin-right: 0;
  }

  .el-tabs__nav-wrap.is-top {
    width: 100%;
    margin: 0 auto;
  }

  .el-tabs__item {
    font-size: 24px;
    font-weight: bold;
    color: #ffffff;
  }

  
  .el-tabs__item.is-active {
    color: #f7c35f;
    border-bottom-color: #f7c35f;
  }

  .el-tabs__item:hover {
    color: #f7c35f;
  }

  .el-tabs__active-bar {
    background-color: #f7c35f;
    width: 100%;
  }
  .el-tabs__active-bar.is-top{
    width: 100%;
  }
  .search-box1 {
    width: 80%;
    margin: 0 auto;
  }

  .search-box2 {
    width: 80%;
    margin: 0 auto;
  }
  @media screen and (max-width: 550px){
  .el-tabs.el-tabs--top{
    width: 100%;
    margin: 0 auto;
  }
  .el-tabs__header,.search-box1,.search-box2{
    width: 100%;
  }
}
</style>

<style src="../../public/css/bootstrap.min.css"></style>
<style src="../../public/css/fontawesome-all.min.css"></style>
<style src="../../public/css/swiper.min.css"></style>
<style src="../../public/css/animate.min.css"></style>
<style src="../../public/css/odometer.min.css"></style>
<style src="../../public/css/jarallax.css"></style>
<style src="../../public/css/magnific-popup.css"></style>
<style src="../../public/css/bootstrap-select.min.css"></style>
<style src="../../public/css/agrikon-icons.css"></style>
<style src="../../public/css/nouislider.min.css"></style>
<style src="../../public/css/nouislider.pips.css"></style>
<style src="../../public/css/main.css"></style>

<script>
  import SearchBox1 from "./SearchBox1.vue";
  import SearchBox2 from "./SearchBox2.vue";
  export default {
    data() {
      return {
        activeName: 'first',
        search_suggestion1: ["16119701634150000"],
      }
    },
    components: {
      "search-box1": SearchBox1,
      "search-box2": SearchBox2,
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      },
      loading(){
                const loading = this.$loading({
                    lock: true,
                    customClass: 'create-isLoading',
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                setTimeout(() => {
                    loading.close();
                }, 1000);
            },
    }
  };
</script>