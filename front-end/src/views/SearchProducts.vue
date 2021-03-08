<template>
    <div>
        <div class="page-wrapper">
            <ToTop></ToTop>
            <Header></Header>
            
            <section class="project-details">
                <div class="block-title">
                    <div class="block-title__image"></div>
                    <p id="product">product search</p>
                    <h3>商品检索</h3>
                </div>
            </section>
            <div id="search">
                                <search-box2></search-box2>
                            </div>
 
    <section class="service-two service-two__home-two">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12 col-md-6 col-lg-3" v-for="item in info.slice(1)" :key="item.skuId">
                        <div class="service-two__card">
                            <div class="service-two__card-image">
                                <img :src="item.img_url" alt="">
                            </div><!-- /.service-two__card-image -->
                            <div class="service-two__card-content">
                                <p>价格：{{item.price}}</p>
                                <p>
                                  <a :href="item.goods_url" id="name">
                                  {{item.title}}</a>
                                </p>
                                <p>产品类型：{{item.kind}}</p>
                                <p>店铺：{{item.shop}}</p>
                            </div><!-- /.service-two__card-content -->
                        </div><!-- /.service-two__card -->
                    </div><!-- /.col-sm-12 col-md-6 col-lg-3 -->
                   
                </div><!-- /.row -->
            </div><!-- /.container -->
        </section>
            <ProductPagination :pagecount="pagecount" ref="fy"></ProductPagination>
            <Footer></Footer>

        </div><!-- /.page-wrapper -->

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

<style lang="less">

    .main-menu{
      margin-bottom: 30px;
    }
    .search-box{
      width: 500px;
      margin: 0 auto;
      
      height: 60px;
      text-align: center;
    }
    .blog-sidebar__search{
      height: 150px;
      
      margin: 0 auto;
      margin-bottom: 0;
    }

    .el-input--suffix{
  width: 300px;
  margin: 0 auto;
  display: inline-block;
}
    .service-two.service-two__home-two{
      margin-top: 0px;
      padding-top: 0px;
      padding-bottom: 5px;
    }
    .service-two__card-content {
      text-align: center;
      padding-left: 8px;
      padding-right: 8px;
      height: 308px;
      display: table;
    }
    .service-two__card-image{
      height: 300px;
      background: #fff;
      position: relative;
      display: table-cell;
      vertical-align: middle;
      width: 100%;
    }
    .service-two__card-image.img{
      position: absolute;
      top: 0;
      right: 0;
      left: 0;
      bottom: 0;
      margin: auto;
      width: 100%;
    }
    .service-two__home-two {
      padding-top: 0px;
    }
    .service-two__card-content #name {
     
      color: #f7c35f;
    }
    .service-two__card-content #name:hover {
     
      color: #255946;
    }
    .el-paper{
      width: 565px;
      margin: 0 auto;
    }
    .pagination{
      width: 660px;
      margin: 0 auto;
      margin-top: 20px;
      margin-bottom: 20px;
    }
    .el-pagination{
      width: 510px;
      margin: 0 auto;
    }
    .block-title{
    width: 100;
    margin: 0 auto;
    text-align:center;
  }
  .block-title__image{
      left: 30%;
      width: 24px;
      margin: 0 auto;
      margin-bottom: 5px;
  }
  
</style>

<style scoped>
#search{
      width: 30%;
      margin: 0 auto;
    }
    @media screen and (max-width: 550px){
    #search{
      width: 90%;
      margin: 0 auto;
    }
  }
  @media screen and (max-width: 1024px) and (min-width: 550px){
    #search{
      width: 40%;
      margin: 0 auto;
    }
  }
</style>

    <script src="../../public/js/jquery-3.5.1.min.js"></script>
    <script src="../../public/js/bootstrap.bundle.min.js"></script>
    <script src="../../public/js/swiper.min.js"></script>
    <script src="../../public/js/jquery.ajaxchimp.min.js"></script>
    <script src="../../public/js/jquery.magnific-popup.min.js"></script>
    <script src="../../public/js/jquery.validate.min.js"></script>
    <script src="../../public/js/bootstrap-select.min.js"></script>
    <script src="../../public/js/wow.js"></script>
    <script src="../../public/js/odometer.min.js"></script>
    <script src="../../public/js/jquery.appear.min.js"></script>
    <script src="../../public/js/jarallax.min.js"></script>
    <script src="../../public/js/circle-progress.min.js"></script>
    <script src="../../public/js/wNumb.min.js"></script>
    <script src="../../public/js/nouislider.min.js"></script>

    <!-- template js -->
    <script src="../../public/js/theme.js"></script>

    <script rel="apple-touch-icon" sizes="180x180" src="../../public/images/favicons/apple-touch-icon.png"></script>
    <script rel="icon" type="image/png" sizes="32x32" src="../../public/images/favicons/favicon-32x32.png"></script>
    <script rel="icon" type="image/png" sizes="16x16" src="../../public/images/favicons/favicon-16x16.png"></script>
    <script rel="manifest" src="../../public/images/favicons/site.webmanifest"></script>
    
    <script rel="preconnect" src="https://fonts.gstatic.com"></script>
    <script src="https://fonts.googleapis.com/css2?family=Handlee&family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet"></script>

<script>
    // @ is an alias to /src
    import Header from '@/components/Header.vue'
    import Footer from '@/components/Footer.vue'
    import ToTop from '@/components/ToTop.vue'
    import SearchBox2 from "@/components/SearchBox2.vue";
    import axios from 'axios'
    import ProductPagination from '@/components/ProductPagination.vue'
  export default {
    name: 'Products',
        components: {
            Header,
            Footer,
            ToTop,
            "search-box2": SearchBox2,
            ProductPagination,
            
        },
    data() {
      return {
        info: {},
        //title: {},
        query:decodeURI(this.$route.query.query),
        page: parseInt(this.$route.query.page),
      };
    },
    
    methods: {
      getContextData: function(key){
	const str = sessionStorage.getItem(key);
	if( typeof str == "string" ){
		try{
			return JSON.parse(str);
		}catch(e) {
			return str;
		}
	}
	return;
},
      async point(page) {
        this.page = page;
        //alert(page);
        if(page!=1)
      {
        this.$router.replace({
        path: this.$route.path,
        query: { ...this.$route.query, page },
      });
      }
        
        let { data } = await axios.get(
          "http://121.46.19.26:8511/getCommodity/" +
          this.query +
          "/" +
          page);
        this.info = data;
        this.pagecount=this.info[0].pageCount;
        //this.$forceUpdate();
        //alert(this.pagecount);
        //this.title = info.title
      },
      
    },
    mounted() {
      this.point(this.page);
    },
    created(){
    this.page=this.getContextData("currentPage")||1;
  },
  watch:{
    $route: {
      handler: function(route) {
        const q = route.query
        if (q) {
          this.query=q.query;
          this.page=parseInt(q.page);
          this.point(this.page);
          //this.$refs.fy.currentChange(this.page);
          //this.$refs.fy.internalCurrentPage = 1
        }
        
      },
      immediate: true
    },
    
  }
  };
</script>
