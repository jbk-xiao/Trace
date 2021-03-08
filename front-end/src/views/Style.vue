<template>
  <div>
    <div class="page-wrapper">
      <ToTop></ToTop>
      <Header></Header>
      <div class="blog-sidebar__search">
        <form class="form">
          <el-input type="text" placeholder="请输入溯源码" v-model="input" @keyup.enter.native="search" clearable
            @clear="setValueNull"></el-input>
          <button type="submit" v-bind:disabled="dis" @click="trans" id="searchclick" @click.prevent="btnClick"><i
              class="agrikon-icon-magnifying-glass"></i></button>
        </form>
      </div>
      <section class="feature-two">
        <div class="container">
          <div class="row">
            <div class="col-md-12 col-lg-4">
              <a href="#product">
                <div class="feature-two__box">
                  <i class="agrikon-icon-tractor-1"></i>
                  <p>Feature 01</p>
                  <h3>产品 <br>
                    信息</h3>
                </div><!-- /.feature-two__box -->
              </a>
            </div><!-- /.col-md-12 col-lg-4 -->
            <div class="col-md-12 col-lg-4">
              <a href="#company">
                <div class="feature-two__box">
                  <i class="agrikon-icon-agriculture"></i>
                  <p>Feature 02</p>
                  <h3>公司 <br>
                    信息</h3>
                </div><!-- /.feature-two__box -->
              </a>
            </div><!-- /.col-md-12 col-lg-4 -->
            <div class="col-md-12 col-lg-4">
              <a href="#trace">
                <div class="feature-two__box">
                  <i class="agrikon-icon-farmer"></i>
                  <p>Feature 03</p>
                  <h3>溯源 <br>
                    信息</h3>
                </div><!-- /.feature-two__box -->
              </a>
            </div><!-- /.col-md-12 col-lg-4 -->
          </div><!-- /.row -->
        </div><!-- /.container -->
      </section>

      <div class="blog-sidebar__posts" id="product">
        <h3>产品信息</h3>
        <ul>
          <li>
            <h4 class="fa fa-check-circle">溯源码</h4>
          </li>
          <li>
            <h4 class="fa fa-check-circle">产品类别</h4>
          </li>
          <li>
            <h4 class="fa fa-check-circle">产品规格</h4>
          </li>
          <li>
            <h4 class="fa fa-check-circle">品牌名称</h4>
          </li>          
        </ul>
      </div>

      <div class="blog-sidebar__posts" id="company">
        <h3>公司信息</h3>
        <ul>
          <li>

            <h4 class="fa fa-check-circle">公司名称</h4>
          </li>
          <li>
            <h4 class="fa fa-check-circle">地址</h4>
          </li>
          <li>
            <h4 class="fa fa-check-circle">联系方式</h4>
          </li>
          <li>
            <h4 class="fa fa-check-circle">品牌logo</h4>
          </li>
        </ul>
      </div>

      <div class="blog-sidebar__posts" id="trace">
        <h3>溯源信息</h3>
        <ul>
          <li>
            <h4 class="fa fa-check-circle">生产过程（图片、视频）</h4>
          </li>
          <li>
            <h4 class="fa fa-check-circle">流程负责人</h4>
          </li>
          <li>
            <h4 class="fa fa-check-circle">流通信息</h4>
          </li>

        </ul>
      </div>
      <Footer></Footer>

    </div><!-- /.page-wrapper -->

  </div>
</template>

<style>
  .main-menu {
    margin-bottom: 60px;
  }

  .blog-sidebar__search {
    
    margin: 0 auto;
    height: 180px;
    text-align: center;
    margin-bottom: 30px;
  }

  .el-input--suffix {
    width: 280px;
    margin: 0 auto;
    display: inline-block;
  }

  .feature-two {
    /* padding-top: 100px; */
    margin-top: 30px;
    margin-bottom: 30px;
  }

  .blog-sidebar__posts {
    width: 800px;
    margin: 0 auto;
    margin-bottom: 50px;
  }

  .blog-sidebar__posts.li {
    padding: 1;
  }
  .el-input__inner {
  height: 54px;
  width: 80%;
  padding-bottom: 15px;
  padding-top: 0px;
  padding-left: 30px;
  padding-right: 30px;
  border: none;
  border-radius: 28px;
  outline: none;
  margin: 0;
  
}
.el-input--suffix{
  width: 60%;
  margin-top: 0px;
  padding-top: 0;
  padding-bottom: 0;
  
}
.el-input__icon.el-icon-circle-close.el-input__clear{
  color: #fff;

}
.el-input .el-input__inner{
  margin-bottom: 0;
  margin-top: 0px;
}
.el-input ::-webkit-input-placeholder {
  color: #fff;
}
</style>
<style scoped>
.blog-sidebar__search{
      width: 30%;
    }
    @media screen and (max-width: 550px){
    .blog-sidebar__search{
      width: 90%;
    }
  }
  @media screen and (max-width: 1024px) and (min-width: 550px){
    .blog-sidebar__search{
      width: 40%;
    }
  }
</style>
<script>
  // @ is an alias to /src
  import Header from '@/components/Header.vue'
  import Footer from '@/components/Footer.vue'
  //import SearchBox1 from "@/components/SearchBox1.vue";
  import ToTop from '@/components/ToTop.vue'
  import axios from 'axios'
  export default {
    name: 'Origin',
    components: {
      Header,
      Footer,
      ToTop
      //"search-box1": SearchBox1
    },
    data() {
      return {
        input: "",
        list: [],
        returnlist: [],
        dis: true,
      };
    },
    methods: {
      //async realtimeSearch() {
        //let {
          //data
        //} = await axios.get(
          //'/data/id_test.json'
        //);
        //this.list = data;
        // console.log(this.list);
     // },

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
  }
</script>