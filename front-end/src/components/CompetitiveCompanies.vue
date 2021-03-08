<template>
  <div class="content">
    <section class="project-details">
      <div class="block-title">
                    <p>Competitive Products List</p>
                    <h3>竞品列表</h3>
                </div>
    </section><!-- /.project-details -->

    <el-collapse accordion>
      <el-collapse-item>
        <template slot="title"><span id="title">展开/折叠</span></template>
    <section class="service-two service-two__home-two">
      <div class="container">

        <!--div class="row" v-if="a">
          <div class="col-sm-12 col-md-6 col-lg-3" v-for="item in geo" :key="item.proj_name">
            <div class="service-two__card">
              
              
              <div class="service-two__card-content">
                <p id="name">{{item.proj_name}}</p>
                <p>{{item.company_name}}</p>

              </div>
            </div>
          </div>

        </div-->

        <div class="row">
          <div class="col-sm-12 col-md-6 col-lg-3" v-for="item in pro" :key="item.pname">
            <div class="service-two__card">
              <div class="service-two__card-image">
                <img :src="item.img_url" alt="">
              </div><!-- /.service-two__card-image -->
              <div class="service-two__card-content" id="proCard">
                <p>
                  <a :href="item.detail_url" id="name">
                    {{item.pname}}</a>
                </p>
                <p>
                  <a :href="item.brand_url" id="link">
                    {{item.brand}}</a>
                </p>
                <p>价格：{{item.price}}</p>
                <p>评论数：{{item.commentCount}}</p>
                <p>好评率：{{item.goodRate}}%</p>
              </div><!-- /.service-two__card-content -->
            </div><!-- /.service-two__card -->
          </div><!-- /.col-sm-12 col-md-6 col-lg-3 -->
        </div><!-- /.row -->

      </div><!-- /.container -->
    </section><!-- /.service-two -->
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<style scoped>
  .text-center {
    margin-bottom: 100px;
  }
  .service-two__card-content {
    text-align: center;
    padding: 20px 8px;
    height: 110px;
    width: 100%;
  }

  .service-two__card-content #name {
    font-weight: 700;
    font-size: 16px;
    color: rgb(37, 89, 70);
  }

  .service-two__home-two {
    padding-top: 50px;
  }

  #proCard {
    text-align: left;
    height: 200px;
    padding: 10px 30px;
    width: 100%;
  }

  #proCard #name {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
  }
  .block-title{
    width: 100;
    margin: 0 auto;
    text-align:center;
  }
</style>

<script>
  import axios from 'axios'
  export default {
    data() {
      return {
        a: true,
        b: false,
        geo: {},
        pro: {}
      };
    },
    methods: {
      async point() {
        let {
          data
        } = await axios.get("http://121.46.19.26:8511/getCompet/520102000400793");
        // let {
        //   data
        // } = await axios.get("data/520102000400793.json");
        this.geo = data.compet_geoList;
        this.pro = data.compet_jdetails;
      },
    },
    mounted() {
      this.point();
    },
  };
</script>
<style>
    .project-details{
      padding-bottom: 20px;
      padding-top: 30px;
  }
.el-collapse{
  margin-bottom: 50px;
}
.el-collapse-item__header{
  height: 80px;
  font-size: 20px;
  color:rgb(37, 89, 70);
  font-weight: bolder;
  display: flex;
}
#title{
  width:100px;
  margin: 0 auto;
  margin-right: 0;
}
.el-collapse-item__arrow.el-icon-arrow-right{
  width: 20px;
  margin: 0 auto;
  margin-left: 0;
}
#link{
  color: #f7c35f;
}
</style>