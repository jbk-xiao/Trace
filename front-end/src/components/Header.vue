<template>
  <div>

    <header class="main-header">
      <div class="topbar">
        <div class="container">
          <div class="topbar__left">
            <div class="topbar__social">
              <a href="#" class="fab fa-twitter"></a>
            </div>
            <p>Welcome to Intelligent Marketing Analysis Platform by Team SOS</p>
          </div><!-- /.topbar__left -->
          <div class="topbar__right">
            <router-link :to="'/Contact'"><i class="agrikon-icon-email"></i>Contact us</router-link>
            
          </div><!-- /.topbar__right -->
        </div><!-- /.container -->
      </div><!-- /.topbar -->
      <nav class="main-menu">
        <div class="container">
          <div class="logo-box">
            <a href="index.html" aria-label="logo image"><img src="../../public/images/logo-dark.png" width="153"
                alt=""></a>
            <!--el-button @click="drawer = true" type="primary" class="fa fa-bars mobile-nav__toggler"></el-button-->
          </div><!-- /.logo-box -->
          <ul class="main-menu__list">
            <li>
              <router-link :to="'/Index'">首页</router-link>
            </li>
            <li>
              <router-link :to="'/Style'">信息示例</router-link>
            </li>
            <li>
              <router-link :to="'/MSearch'">竞争图谱</router-link>
            </li>

            <li class="dropdown">营销分析
              <ul>
                <li>
                  <router-link :to="'/LgmCPs'">老干妈</router-link>
                </li>
                <li>
                  <router-link :to="'/TqCPs'">泰奇食品</router-link>
                </li>
              </ul>
            </li>
            <li class="dropdown">信息管理
              
              <ul>
                <li>
                  <router-link :to="'/Detail'">老干妈</router-link>
                </li>
                <li>
                  <router-link :to="'/Detail2'">泰奇食品</router-link>
                </li>
              </ul>
            </li>
          </ul>

          <div class="main-header__info">
            <el-popover trigger="click">
              <div class="blog-sidebar__search">
                <form class="form">
                  <el-input type="text" placeholder="请输入溯源码" v-model="input" @keyup.enter.native="search" clearable
                    @clear="setValueNull"></el-input>
                  <button type="submit" v-bind:disabled="dis" @click="trans" id="searchclick"
                    @click.prevent="btnClick"><i class="agrikon-icon-magnifying-glass"></i></button>
                </form>
              </div>
              <button slot="reference"><i class="agrikon-icon-magnifying-glass"></i></button>
            </el-popover>
            <!--a href="#" class="search-toggler main-header__search-btn"><i
                                    class="agrikon-icon-magnifying-glass"></i></a-->
            <router-link :to="'/Products'" class="main-header__cart-btn"><i class="agrikon-icon-shopping-cart"></i>
            </router-link>
            <router-link :to="'/Contact'" class="main-header__info-phone">
              <i class="agrikon-icon-email"></i>
              <span class="main-header__info-phone-content">
                <span class="main-header__info-phone-text"></span>
                <span class="main-header__info-phone-title">开发团队</span>
              </span><!-- /.main-header__info-phone-content -->
            </router-link><!-- /.main-header__info-phone -->
          </div><!-- /.main-header__info -->
        </div><!-- /.container -->
      </nav>
      <!-- /.main-menu -->
    </header><!-- /.main-header -->

  </div>
</template>

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

<style scoped>
  .main-menu::after {
    background-image: none;
  }

  .blog-details__image img {
    width: 100%;
  }

  .el-popover__reference {
    border-style: none;
    background: #fff;
    height: 38px;
    width: 38px;
  }

  .agrikon-icon-magnifying-glass {
    width: 23px;
    height: 23px;
  }

  .blog-sidebar__search {
    height: 70px;
    width: 300px;
    margin-bottom: 0;
  }

  .el-input--suffix {
    width: 200px;
    float: left;
    height: 70px;
    padding-bottom: 0;
    padding-top: 0;
    margin-top: 0;
  }

  .el-input.el-input__inner {
    margin: 0;
    margin-top: 0;
    padding-bottom: 0;
    padding-top: 0;
  }

  .el-input__icon.el-icon-circle-close.el-input__clear {
    color: #fff;
    height: 90px;
  }

  .form{
  height: 60px;
}

</style>

<script>
  //import $ from 'jquery'
  import axios from 'axios'
  export default {

    data() {
      return {
        input: "",
        list: [],
        returnlist: [],
        dis: true,
        //drawer: false,
        //direction: 'ltr',
      };
    },

    methods: {
      // 发送检索请求
      async trans() {
        let {data}  = await axios.get(
        'http://121.46.19.26:8511/getOrigin/'+this.input
      );
      this.list=data;
        if (this.input === '') {
          /*$(".btn btn-primary").addClass("huise")
          // 将鼠标设置为不可点击状态
          document.getElementById('searchclick').style.cursor = 'not-allowed'*/
          this.dis = true;
        } else if (this.list[0].category==undefined) {
          setTimeout(() => {
            //this.$router.push("/Origin" + "?query=" + this.input);
            //this.$router.push({ name:'Home',params:{ type:1 } })
            this.$router.push({
              name: 'Style'
            });
          }, 200);
        } else {
          setTimeout(() => {
            //this.$router.push("/Origin" + "?query=" + this.input);
            //this.$router.push({ name:'Home',params:{ type:1 } })
            this.$router.push("/Origin"+ "?query=" + this.input);
          }, 100);
          this.returnlist = [];
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
        }
      },
      setValueNull() {
        this.dis = true;
      },
      /*async transFromRealtime(item){
        await this.$router.push("/Origin" + "?query=" + item);
      },*/
      /*hideIcon(){
        let dom = '';
          dom = document.getElementsByClassName("el-icon-search")
        this.$nextTick(() => {
          dom.style.display="none";
          })
      }*/
    },
    mounted() {
      //this.realtimeSearch();
    },

    watch: {
      input() {
        if (this.timer) {
          clearTimeout(this.timer)
        }
        if (this.input != '') {
          //this.hideIcon();
          this.dis = false;
          /*$(".btn btn-primary").removeClass("huise") //移除不可点击状态
          document.getElementById('searchclick').style.cursor = 'pointer'*/
        }
        this.timer = setTimeout(() => {

          for (let i in this.list) {

            if (this.input == this.list[i].id) {
              this.returnlist.push(this.list[i])
            }
          }

        }, 100)
      }
    }
  };
</script>