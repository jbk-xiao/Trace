<template>
  <section class="page-header" style="height:410px">
    <div class="page-header__bg" style="background-color: 255946;"></div>
    <!-- /.page-header__bg -->
    <div class="container">

      <h2>竞争图谱搜索</h2>
      <el-tabs :stretch="true" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="品类搜索" name="first">
          <search-boxM1 class="center search-box1" />
          <div class="suggestion">
                <span class="title">Try:</span>
                <span class="content">
                  <div
                    v-for="(suggestion, index) in search_suggestion1"
                    :key="index" @click="loading"
                  >
                    <router-link
                      :to="{ path: '/MappingK', query: { query: suggestion } }"
                      tag="a"
                      >{{ suggestion }}</router-link
                    >
                  </div>
                </span>
              </div>
        </el-tab-pane>
        <el-tab-pane label="品牌搜索" name="second">
          <search-boxM2 class="center search-box2" />
          <div class="suggestion">
                <span class="title">Try:</span>
                <span class="content">
                  <div
                    v-for="(suggestion, index) in search_suggestion2"
                    :key="index" @click="loading"
                  >
                    <router-link
                      :to="{ path: '/MappingB', query: { query: suggestion } }"
                      tag="a"
                      >{{ suggestion }}</router-link
                    >
                  </div>
                </span>
              </div>
        </el-tab-pane>
      </el-tabs>
    </div><!-- /.container -->
  </section>
</template>

<style>
  .container.h2 {
    margin-bottom: 60px;
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

  .el-tabs__nav-wrap.is-top {
    width: 100%;
    margin: 0 auto;
  }

  .el-tabs__item {
    font-size: 1.1em;
    font-weight: bold;
    color: #ffffff;
  }

  .el-tabs__item.is-active {
    color: #f7c35f;
    border-bottom-color: #f7c35f;
  }

  .el-tabs__item.is-top.is-active {
    color: #f7c35f;
    border-bottom-color: #f7c35f;
  }

  .el-tabs__item:hover {
    color: #f7c35f;
  }

  .el-tabs__item.is-top:hover {
    color: #f7c35f;
  }

  .el-tabs__active-bar {
    background-color: #f7c35f;
  }

.el-tabs__active-bar.is-top{
    width: 100%;
  }
  .search-box1 {
    width: 80%;
    margin: 0 auto;
    height: 80px;
  }

  .search-box2 {
    width: 80%;
    margin: 0 auto;
    height: 80px;
  }

 .suggestion {
  font-size: 1em;
  margin: 0;
  color: #ffffff;
  
}
.suggestion .title {
  display: inline-block;
  margin: 0 1em 0 0.5em;
  font-weight: 700;
}
.suggestion .content div {
  display: inline;
  font-weight: 700;
}
.suggestion a {
  display: inline-block;
  text-decoration: none;
  text-transform: capitalize;
  margin: 0 0.5em;
  color: #ffffff;
}
.suggestion a:hover {
  text-decoration: underline;
  color: #ffd808;
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
  import SearchBoxM1 from "./SearchBoxM1.vue";
  import SearchBoxM2 from "./SearchBoxM2.vue";
  export default {
    data() {
      return {
        search_suggestion1: ["冷冻食品"],
        search_suggestion2: ["湾仔码头","泰奇","陶华碧老干妈"],
        activeName: 'first'
      }
    },
    components: {
      "search-boxM1": SearchBoxM1,
      "search-boxM2": SearchBoxM2,
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