<template>
    <div>
        <div class="page-wrapper">
            <ToTop></ToTop>
            <Header></Header>

            <search></search>

        <template>
                <div ref="menu">
                    <div class="menu" :class="{'is_top' : isTop}">
            <section class="project-details" id="top">
            <nav class="main-menu">
                <div class="container" style="width:100%">
                    
                    <ul class="main-menu__list">
                        
                        <li style="padding-top:0;margin: 0px 20px 0px 20px;" id="li1">
                            <a href="#overall" style="font-size:22px;font:bolder">总览</a>
                        </li>
                        <li class="dropdown" style="padding-top:0;margin: 0px 20px 0px 20px;" id="li2"><a href="#about" style="font-size:22px;font:bolder">项目</a>
                            
                        </li>
                        <li class="dropdown" style="padding-top:0;margin: 0px 20px 0px 20px;" id="li3">
                            <a href="#service" style="font-size:22px;font:bolder">服务</a>
                            
                        </li>
                        <li class="dropdown" style="padding-top:0;margin: 0px 20px 0px 20px;" id="li4"><a href="#example" style="font-size:22px;font:bolder">示例</a>
                            
                        </li>
                        <li class="dropdown" style="padding-top:0;margin: 0px 20px 0px 20px;" id="li4"><a href="#tech" style="font-size:22px;font:bolder">技术</a>
                            
                        </li>
                    </ul>
                </div><!-- /.container -->
            </nav>
            </section>                        
                    </div>
                    <div :class="{'placeholder' : placeholder}"></div>
                </div>
            </template>
            <div class="block" id="overall">
            <section class="about-one">
            <img src="../../public/images/icons/about-bg-icon-1-1.png" class="about-one__bg-shape-1" alt="">
            <div class="container">
                <div class="row">
                    <div class="col-lg-5">
                        <div class="about-one__images">
                            <img src="../../public/images/resources/about-1-1.jpg" alt="">
                            <img src="../../public/images/resources/about-1-2.jpg" alt="">
                            <div class="about-one__count wow fadeInLeft" data-wow-duration="1500ms">
                                <h5>众多中小企业</h5>
                                <h5>的选择</h5>
                            </div><!-- /.about-one__count -->
                        </div><!-- /.about-one__images -->
                    </div><!-- /.col-lg-6 -->
                    <div class="col-lg-7">
                        <div class="about-one__content">
                            <div class="block-title text-left">
                                <div class="block-title__image"></div><!-- /.block-title__image -->
                                <p>Welcome to agricon</p>
                                <h3>Better Agriculture for
                                    Better Future</h3>
                            </div><!-- /.block-title -->
                            <div class="about-one__tagline">
                                <p>We provide professional marketing & product source tracing service for you</p>
                            </div><!-- /.about-one__tagline -->
                            <div class="about-one__summery">
                                <p>为您提供专业的营销与溯源服务。</p>
                            </div><!-- /.about-one__summery -->
                            <div class="about-one__icon-row">
                                <div class="row">
                                    <div class="col-lg-6">
                                        <div class="about-one__box">
                                            <i class="agrikon-icon-farmer"></i>
                                            <h4><router-link :to="'/Style'">查看溯源信息<br>示例</router-link></h4>
                                        </div><!-- /.about-one__box -->
                                    </div><!-- /.col-lg-6 -->
                                    <div class="col-lg-6">
                                        <div class="about-one__box">
                                            <i class="agrikon-icon-agriculture"></i>
                                            <h4><router-link :to="'/LgmCPs'">查看营销分析<br>示例</router-link></h4>
                                        </div><!-- /.about-one__box -->
                                    </div><!-- /.col-lg-6 -->
                                </div><!-- /.row -->
                            </div><!-- /.about-one__icon-row -->
                            <button @click="trans" class="thm-btn">Discover More</button><!-- /.thm-btn -->
                        </div><!-- /.about-one__content -->
                    </div><!-- /.col-lg-6 -->
                </div><!-- /.row -->
            </div><!-- /.container -->
        </section>
            </div>

            <div class="projects-one project-one__home-one" id="about">
            <div class="container" id="container">
                <div class="block-title text-center">
                    <div class="block-title__image"></div><!-- /.block-title__image -->
                    <p>about us</p>
                    <h3>项目介绍</h3>
                </div><!-- /.block-title --><br>
                <div class="thm-swiper__slider swiper-container" data-swiper-options='{"spaceBetween": 0, "slidesPerView": 1, "loop": true, "slidesPerGroup": 1, "pagination": {
            "el": "#projects-one__swiper-pagination",
            "type": "bullets",
            "clickable": true
        },
        "breakpoints": {
            "0": {
                "spaceBetween": 0,
                "slidesPerView": 1,
                "slidesPerGroup": 1
            },
            "640": {
                "spaceBetween": 30,
                "slidesPerView": 2,
                "slidesPerGroup": 2
            },
            "992": {
                "spaceBetween": 30,
                "slidesPerView": 2,
                "slidesPerGroup": 2
            }
        }}'>
                    <div class="swiper-wrapper" id="sw">
                        <div class="swiper-slide">
                            <div class="projects-one__single">
                                <h3><span class="odometer" id="sp1">生产透明，打造信誉</span></h3>
                                <img src="../../public/images/1.png" alt="" id="img1">
                                <div class="projects-one__content">
                                    <h3 id="h31">软硬件串联多媒体数据，区块链上锁生产信息，不可篡改的溯源信息打造可信品牌</h3>
                                </div><!-- /.projects-one__content -->
                            </div><!-- /.projects-one__single -->
                        </div><!-- /.swiper-slide -->
                        <div class="swiper-slide">
                            <div class="projects-one__single">
                                <h3><span class="odometer" id="sp2">一物双码，快捷溯源</span></h3>
                                <img src="../../public/images/2.png" alt="" id="img2">
                                <div class="projects-one__content">
                                    <h3 id="h32">信息录入与信息追溯分开两码，溯源高效快速</h3>
                                </div><!-- /.projects-one__content -->
                            </div><!-- /.projects-one__single -->
                        </div><!-- /.swiper-slide -->
                        <div class="swiper-slide">
                            <div class="projects-one__single">
                                <h3><span class="odometer" id="sp3">销量分析，精准预测</span></h3>
                                <img src="../../public/images/3.png" alt="" id="img3">
                                <div class="projects-one__content">
                                    <h3 id="h33">ARIMA算法精准预测市场需求、销量走势</h3>
                                </div><!-- /.projects-one__content -->
                            </div><!-- /.projects-one__single -->
                        </div><!-- /.swiper-slide -->
                    </div><!-- /.swiper-wrapper -->
                    <div class="swiper-wrapper" id="sw2">
                        <div class="swiper-slide">
                            <div class="projects-one__single">
                                <h3><span class="odometer" id="sp4">售后追踪，NLP评估</span></h3>
                                <img src="../../public/images/4.png" alt="" id="img4">
                                <div class="projects-one__content">
                                    <h3 id="h34">追踪顾客售后评价，多种NLP模型评估用户体验，助力经营顾客资产</h3>
                                    <!-- /.projects-one__button -->
                                </div><!-- /.projects-one__content -->
                            </div><!-- /.projects-one__single -->
                        </div><!-- /.swiper-slide -->
                        <div class="swiper-slide">
                            <div class="projects-one__single">
                                <h3><span class="odometer" id="sp5">智能搜索，一览竞品</span></h3>
                                <img src="../../public/images/5.png" alt="" id="img5">
                                <div class="projects-one__content">
                                    <h3 id="h35">高性能框架+高效数据库管理，赋能海量多源数据查询</h3>
                                </div><!-- /.projects-one__content -->
                            </div><!-- /.projects-one__single -->
                        </div><!-- /.swiper-slide -->
                        <div class="swiper-slide">
                            <div class="projects-one__single">
                                <h3><span class="odometer" id="sp6">多维评分，查漏补缺</span></h3>
                                <img src="../../public/images/6.png" alt="" id="img6">
                                <div class="projects-one__content">
                                    <h3 id="h36">深度学习支持评论分类分析，多维评分纵览优劣益弊</h3>
                                </div><!-- /.projects-one__content -->
                            </div><!-- /.projects-one__single -->
                        </div><!-- /.swiper-slide -->
                    </div>
                </div><!-- /.swiper-container -->
            </div><!-- /.container -->
        </div><!-- /.projects-one -->


        <section class="service-two service-two__home-two" id="service">
            <!-- /. -->
            <div class="container">
                <div class="block-title text-center">
                    <div class="block-title__image"></div><!-- /.block-title__image -->
                    <p>Our Services list</p>
                    <h3>What We're Offering</h3>
                </div><!-- /.block-title -->
                
            </div><!-- /.container -->
        </section>
                <section class="gray-boxed-wrapper home-one__boxed">
            <section class="funfact-one">
                <div class="container">
                    <div class="row no-gutters">
                        <div class="col-sm-12 col-md-6 col-lg-4">
                            <div class="funfact-one__single">
                                <i class="agrikon-icon-planting"></i>
                                <h3><span class="odometer">产品溯源</span></h3>
                                <p>追溯生产源头</p>
                            </div><!-- /.funfact-one__single -->
                        </div><!-- /.col-sm-12 col-md-6 col-lg-3 -->
                        <div class="col-sm-12 col-md-6 col-lg-4">
                            <div class="funfact-one__single">
                                <i class="agrikon-icon-shopping-cart"></i>
                                <h3><span class="odometer">产品搜索</span></h3>
                                <p>搜索相关产品</p>
                            </div><!-- /.funfact-one__single -->
                        </div><!-- /.col-sm-12 col-md-6 col-lg-3 -->
                        <div class="col-sm-12 col-md-6 col-lg-4">
                            <div class="funfact-one__single">
                                <i class="agrikon-icon-customer"></i>
                                <h3><span class="odometer">营销分析</span></h3>
                                <p>竞品画像横向对比</p>
                            </div><!-- /.funfact-one__single -->
                        </div><!-- /.col-sm-12 col-md-6 col-lg-3 -->
                    </div><!-- /.row -->
                </div><!-- /.container -->
            </section><!-- /.funfact-one -->
        </section>
        <section class="gray-boxed-wrapper home-one__boxed">
            <section class="funfact-one">
                <div class="container">
                    <div class="row no-gutters">
                        <div class="col-sm-12 col-md-6 col-lg-4">
                            <div class="funfact-one__single">
                                <i class="agrikon-icon-organic-food"></i>
                                <h3><span class="odometer">信息录入</span></h3>
                                <p>登记商品及溯源信息</p>
                            </div><!-- /.funfact-one__single -->
                        </div><!-- /.col-sm-12 col-md-6 col-lg-3 -->
                        <div class="col-sm-12 col-md-6 col-lg-4">
                            <div class="funfact-one__single">
                                <i class="agrikon-icon-investment"></i>
                                <h3><span class="odometer">商业洞察</span></h3>
                                <p>销量走向预测与评价情感分析</p>
                            </div><!-- /.funfact-one__single -->
                        </div><!-- /.col-sm-12 col-md-6 col-lg-3 -->
                        <div class="col-sm-12 col-md-6 col-lg-4">
                            <div class="funfact-one__single">
                                <i class="agrikon-icon-vegetable"></i>
                                <h3><span class="odometer">竞争图谱</span></h3>
                                <p>品牌、品类图谱搜索</p>
                            </div><!-- /.funfact-one__single -->
                        </div><!-- /.col-sm-12 col-md-6 col-lg-3 -->
                    </div><!-- /.row -->
                </div><!-- /.container -->
            </section><!-- /.funfact-one -->
        </section><br>

        <div class="block-title text-center" id="example">
                    <div class="block-title__image"></div><!-- /.block-title__image -->
                    <p>examples</p>
                    <h3>项目示例</h3>
        </div><br>
        <section class="service-one">
            <div class="container">
                
                <div class="row">
                    <div class="col-md-12 col-lg-3">
                            <div class="blog-card">
                                <div class="blog-card__image">
                                    <img src="../../public/images/sy.jpg" alt="Best Way to Do Eco and Agriculture">
                                    <router-link :to="'/Origin?query=16119701634150000'"></router-link>
                                </div><!-- /.blog-card__image -->
                                <div class="blog-card__content">
                                    
                                    <div class="blog-card__meta">
                                        <i class="far fa-user-circle"></i> 产品溯源模块
                                        
                                    </div><!-- /.blog-card__meta -->
                                    <h3><a>产品生产流程信息</a></h3>
                                    <router-link :to="'/Origin?query=16119701634150000'" class="thm-btn">See More</router-link><!-- /.thm-btn -->
                                </div><!-- /.blog-card__content -->
                            </div><!-- /.blog-card -->
                        </div><!-- /.col-md-12 col-lg-4 -->
                        <div class="col-md-12 col-lg-3">
                            <div class="blog-card">
                                <div class="blog-card__image">
                                    <img src="../../public/images/comp.jpg" alt="Leverage agile frameworks to provide">
                                    <router-link :to="'/LgmCPs'"></router-link>
                                </div><!-- /.blog-card__image -->
                                <div class="blog-card__content">
                                    
                                    <div class="blog-card__meta">
                                        <i class="far fa-user-circle"></i> 竞品分析模块
                                        
                                    </div><!-- /.blog-card__meta -->
                                    <h3><a>人群画像/竞争性分析</a></h3>
                                    <router-link :to="'/LgmCPs'" class="thm-btn">See More</router-link><!-- /.thm-btn -->
                                </div><!-- /.blog-card__content -->
                            </div><!-- /.blog-card -->
                        </div><!-- /.col-md-12 col-lg-4 -->
                        <div class="col-md-12 col-lg-3">
                            <div class="blog-card">
                                <div class="blog-card__image">
                                    <img src="../../public/images/analysis.jpg">
                                    <router-link :to="'/LgmCPs'"></router-link>
                                </div><!-- /.blog-card__image -->
                                <div class="blog-card__content">
                                    
                                    <div class="blog-card__meta">
                                        <i class="far fa-user-circle"></i> 商业分析模块
                                        
                                    </div><!-- /.blog-card__meta -->
                                    <h3><a>销量与评价分析预测</a></h3>
                                    <router-link :to="'/LgmCPs'" class="thm-btn">See More</router-link><!-- /.thm-btn -->
                                </div><!-- /.blog-card__content -->
                            </div><!-- /.blog-card -->
                        </div>
                        <div class="col-md-12 col-lg-3">
                            <div class="blog-card">
                                <div class="blog-card__image">
                                    <img src="../../public/images/management.jpg" alt="Organically grow the holistic world view">
                                    <router-link :to="'/Detail'"></router-link>
                                </div><!-- /.blog-card__image -->
                                <div class="blog-card__content">
                                    
                                    <div class="blog-card__meta">
                                        <i class="far fa-user-circle"></i> 信息管理模块
                                        
                                    </div><!-- /.blog-card__meta -->
                                    <h3><a>商品和溯源信息录入与管理</a></h3>
                                    <router-link :to="'/Detail'" class="thm-btn">See More</router-link>
                                </div><!-- /.blog-card__content -->
                            </div><!-- /.blog-card -->
                        </div><!-- /.col-md-12 col-lg-4 -->
                </div><!-- /.row -->
            </div><!-- /.container -->
        </section><!-- /.service-one -->

            <section class="project-details" id="tech">
                    <div class="block-title text-center">
                        <div class="block-title__image"></div>
                        <p>Technology</p>
                        <h3>技术栈</h3>
                    </div>
        <template>
  <div class="block">
    
    <el-carousel :height="bannerHeight+'px'" :interval="1500">
      <el-carousel-item>
        <img src="../../public/images/vue.jpg" alt="" @load="imgLoad" style="width: 100%">
        
      </el-carousel-item>
      <el-carousel-item>
      <img src="../../public/images/echarts.jpg" alt="" @load="imgLoad" style="width: 100%">
      </el-carousel-item>
      <el-carousel-item>
      <img src="../../public/images/springboot.jpg" alt="" @load="imgLoad" style="width: 100%">
      </el-carousel-item>
      <el-carousel-item>
      <img src="../../public/images/redis.jpg" alt="" @load="imgLoad" style="width: 100%">
      </el-carousel-item>
      <el-carousel-item>
      <img src="../../public/images/mysql.jpg" alt="" @load="imgLoad" style="width: 100%">
      </el-carousel-item>
      <el-carousel-item>
      <img src="../../public/images/mongodb.jpg" alt="" @load="imgLoad" style="width: 100%">
      </el-carousel-item>
      <el-carousel-item>
      <img src="../../public/images/hf.jpg" alt="" @load="imgLoad" style="width: 100%">
      </el-carousel-item>
      <el-carousel-item>
      <img src="../../public/images/py.png" alt="" @load="imgLoad" style="width: 100%">
      </el-carousel-item>
      <el-carousel-item>
      <img src="../../public/images/berry.jpg" alt="" @load="imgLoad" style="width: 100%">
      </el-carousel-item>
    </el-carousel>
  </div>
  
        </template>
        </section>      
            <br>
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
    
    <script src="../../public/js/jquery-3.5.1.min.js"></script>
    <script src="https://code.jquery.com/jquery.smoove.min.js"></script>
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
    import search from '@/components/search.vue'
    import ToTop from '@/components/ToTop.vue'
    export default {
        name: 'Index',
        components: {
            Header,
            Footer,
            search,
            ToTop
        },
        data() {
            return {
                bannerHeight:"",
                isTop: false,
                placeholder: false
            }
        },
        methods: {
        topMenu() { //保持在顶部
                var menu = this.$refs.menu;
                window.addEventListener("scroll", () => {
                // 由于使用递归offsetTop,parentElement,offsetParent等方法都会遇到准确定位父元素的问题，所以使用如下方法
                if (menu.getBoundingClientRect().top <= 0) {
                this.isTop = true;
                this.placeholder = true;
                } else {
                this.isTop = false;
                this.placeholder = false;
                }
                });
            },
        trans(){
            setTimeout(() => {
        //this.$router.push("/Origin" + "?query=" + this.input);
        this.$router.push("/MSearch");
      }, 200);
      },
      imgLoad(){ //走马灯图片自适应
                this.$nextTick(()=>{
                    this.bannerHeight=this.$refs.bannerHeight[0].height;
                    console.log(this.$refs.bannerHeight[0].height);
                     // document.getElementsByClassName拿到的是数组并非某一个对象
                    var testH=document.getElementById("test-div");
                    testH.style.height= this.bannerHeight+"px";
                })
            }
        },
        mounted(){
            this.imgLoad();
            this.$nextTick(() => {
                this.topMenu();
            });
            window.addEventListener('resize',() => {
                this.bannerHeight=this.$refs.bannerHeight[0].height;
                this.imgLoad();
            },false)
        },
        activated() {
    this.$nextTick(() => {
      var menu = this.$refs.menu;
      if (menu.getBoundingClientRect().top <= 0) {
        this.isTop = true;
        this.placeholder = true;
      } else {
        this.isTop = false;
        this.placeholder = false;
      }
    });
  },
    }
    
</script>

<style>
.service-two.service-two__home-two{
    margin-bottom: 0px;
    margin-top: 80px;
}
.block-title.text-center{
    margin-bottom:15px;
    margin-top: 20px;
}
.about-one__tagline{
    margin-top: 20px;
}
.el-carousel__container{
    width:40%;
    margin: 0 auto;
}
.el-carousel__item{
    width:100%;
    height: 100%;
    display: flex;
    justify-content: center;
}
.el-carousel__item.is-animating.img{
    width:100%;
    min-width: 100%;
    max-width: 100%;
  }
  #sp1{
      font-family: fantasy;
  }
  .thm-swiper__slider.swiper-container{
      height: 80%;
      width: 90%;
      margin: 0 auto;
      margin-bottom: 10px;
  }
  .projects-one__single{
      height: 100%;
      width: 100%;
      margin: 0 auto;
  }
  @media screen and (max-width: 550px){
      #sw{
          width: 100%;
          display: block;
      }
      #sw2{
          width: 100%;
          display: block;
      }
      .swiper-slide{
        position: relative;
        margin-left: 90px;
        display: block;
      }
      .projects-one__content{
      height: 60%;
      width: 80%;
      margin: 0 auto;
      margin-top: 10px;
  }
  #h31{
      height: 90%;      
  }
  #h32{
      height: 90%;      
  }
  #h33{
      height: 90%;      
  }
  #h34{
      height: 90%;      
  }
  #h35{
      height: 90%;      
  }
  #h36{
      height: 90%;      
  }
  #sp1,
  #sp2,
  #sp3,
  #sp4,
  #sp5,
  #sp6{
      font-size: 28px;
      margin-left: 0px;
  }
  #img1,#img2,#img3,#img4,#img5,#img6{
      
      margin-left: 70px;
  }
  #li1,#li2,#li3,#li4{
      width: 20%;
      overflow:hidden;
      margin: 0 10px 0 10px !important
  }
  .projects-one__single h3{
      text-align: center;
  }
  .main-menu__list{
      width:100%;
  }
  }
  @media screen and (max-width: 1200px) and (min-width: 550px){
      #sw{
          width: 100%;
          display: block;
      }
      #sw2{
          width: 100%;
          display: block;
      }
      .swiper-slide{
        position: relative;
        margin-left: 90px;
        display: block;
      }
      .projects-one__content{
      height: 60%;
      margin-top: 10px;
  }
  #h31{
      height: 90%;      
  }
  #h32{
      height: 90%;      
  }
  #h33{
      height: 90%;      
  }
  #h34{
      height: 90%;      
  }
  #h35{
      height: 90%;      
  }
  #h36{
      height: 90%;      
  }
  .projects-one__single h3{
      text-align: center;
  }
  #img1,#img2,#img3,#img4,#img5,#img6{
      width: 55%;
      margin-left: 190px;
  }
  }
  .projects-one__single img{
      height: 80%;
      width: 55%;
      margin: 0 auto;
      margin-left: 80px;
  }
  .swiper-wrapper{
      width: 40%;
      height: 50%;
      margin: 0 auto;
      margin-left: -90px;
      margin-bottom: 40px;
      word-wrap:break-word;
    word-break:break-all;
    
  }
  #projects-one__swiper-pagination{
      height: 50px;
  }
  .projects-one__content{
      height: 95%;
  }
  #h31,#h32,#h33,#h34,#h35,#h36{
      width: 90% i !important;
      margin: 0 auto;
  }
  .projects-one.project-one__home-one{
      height: auto;
      padding: 0;
  }
  #container{
      height:100%;
  }
#top{
    padding: 0;
    width: 100%;
}

.projects-one__single h3 span {
  font-family: inherit !important;
  margin: 0;
  font-size: 38px;
  line-height: 1;
  font-family: var(--special-font) !important;
  color: var(--thm-black);
  margin-top: 0px;
  margin-bottom: 20px;
  font-weight: bold;
  margin-left: 30px;
}
</style>
<style scoped>
.menu {
  width: 100%;
  height: 180px;
  text-align: center;
  display: flex;
}
</style>

<style>
.is_top {
  position: fixed;
  width: 100%;
  margin: 0 auto;
  top: 0;
  z-index: 999;
}
.placeholder {
  height: 50px;
}
</style>