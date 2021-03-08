<template>
    <div>
        <div class="page-wrapper">
            <ToTop></ToTop>
            <Header></Header>

            <section class="page-header">
                <div class="page-header__bg"
                    style="background-image: url(../../public/images/backgrounds/page-header-bg-1-1.jpg);"></div>
                <!-- /.page-header__bg -->
                <div class="container">
                    <h2 style="margin-top:70px">营销分析</h2>
                </div><!-- /.container -->
            </section><!-- /.page-header -->

            <section class="project-details">
                <div class="block-title">
                    <p>Company Information</p>
                    <h3>公司信息</h3>
                </div><br>

                <Info :info='cinfo' :key="keyn"></Info>
            </section><!-- /.project-details -->

            <section class="project-details">
                <div class="block-title">
                    <p>relative news</p>
                    <h3>相关新闻</h3>
                </div>
                <news :ninfo='news' :key="keyn"></news>
            </section>

            <template>
                <div ref="menu">
                    <div class="menu" :class="{'is_top' : isTop}">
            <section class="project-details" id="top">

                <div class="container project-details__list">
                    <span id="guide">视图分析导航</span>
                    <ul class="list-unstyled project-details__list">
                        <li>
                            <span><a href="#search">相关关键词搜索分析</a>
                            </span>

                        </li>
                        <li>
                            <span><a href="#product">商品评价与销量分析</a>
                            </span>

                        </li>
                        <li>
                            <span><a href="#comp">竞争性分析</a>
                            </span>

                        </li>
                    </ul>
                </div><!-- /.container -->
            </section>                        
                    </div>
                    <div :class="{'placeholder' : placeholder}"></div>
                </div>
            </template>



            <section class="project-details">
                <div class="block-title">
                    <div class="block-title__image"></div>
                    <p id="search">search Analysis</p>
                    <h3>相关关键词搜索分析</h3>
                </div>
                <div id="namelist">
                    <ul v-for="item in name" :key="item" @click="changedata(item)">
                        <li><button style="border-radius: 5px;">{{item}}</button></li>
                    </ul>
                </div>
                <el-row :gutter="3">
                    <el-col :span="8" class="col-sm-12 col-md-6 col-lg-4">
                        <section class="project-details">
                            <div class="block-title">
                                <p>Portrait of The Crowd</p>
                                <h3>关键词搜索人群画像</h3>
                            </div>
                            <Portrait :info='pinfo' :key="keynum"></Portrait>
                        </section>
                    </el-col>
                    <el-col :span="8" class="col-sm-12 col-md-6 col-lg-4">
                        <section class="project-details">
                            <div class="block-title">
                                <p>Search Index</p>
                                <h3>搜索指数</h3>
                            </div>
                            <SearchIndex :info='iinfo' :key="keynum"></SearchIndex>
                        </section>
                    </el-col>
                    <el-col :span="8" class="col-sm-12 col-md-6 col-lg-4">
                        <section class="project-details">
                            <div class="list">
                                <div class="block-title">
                                    <p>Search Index Distribution</p>
                                    <h3>搜索指数地区分布</h3>
                                </div>
                            </div>
                            <Region :info='dinfo' :key="keynum"></Region>
                        </section>
                    </el-col>
                </el-row>

                <section class="project-details">
                    <div class="list">
                        <div class="block-title">
                            <p>Search popularity</p>
                            <h3>搜索词热度分析</h3>
                        </div>
                        <Scatter :info='qinfo' :key="keynum"></Scatter>
                    </div>

                </section>
            </section>

            <section class="project-details">
                <div class="block-title">
                    <div class="block-title__image"></div>
                    <p id="product">product Analysis</p>
                    <h3>商品评价与销量分析</h3>
                </div>
                <el-row :gutter="3">
                    
                    <el-col :span="12" class="col-sm-12 col-md-6 col-lg-6">
                        <Rose :info='commentInfo' :key="keyn"></Rose>
                    </el-col>
                    <el-col :span="12" class="col-sm-12 col-md-6 col-lg-6">
                        <Sales :info='sales' :key="keyn"></Sales>
                    </el-col>
                </el-row>
            </section>

                        <section class="project-details">
                            <div class="block-title">
                                <p>comment analysis</p>
                                <h3>商品评价观点挖掘</h3>
                            </div>
                            <TreeMap :info='treeinfo' :key="keyn"></TreeMap>
                        </section>

            <section class="project-details">
                <div class="block-title">
                    <div class="block-title__image"></div>
                    <p id="comp">Competitive Analysis</p>
                    <h3>竞争性分析</h3>
                </div>

                <CompetitiveList></CompetitiveList>

                <el-row :gutter="3">
                    <el-col :span="8" class="col-sm-12 col-md-6 col-lg-4">
                        <Radar :info='info' :key="keyn"></Radar>
                    </el-col>
                    <el-col :span="8" class="col-sm-12 col-md-6 col-lg-4">
                        <section class="project-details">
                            <div class="block-title">
                                <p>Companies Map</p>
                                <h3>竞争公司分布地图</h3>
                            </div><br>
                            <Geo></Geo>
                        </section>
                    </el-col>
                    <el-col :span="8" class="col-sm-12 col-md-6 col-lg-4">
                        <section class="project-details">
                            <div class="block-title">
                                <p>3D Analysis</p>
                                <h3>竞品三维分析图</h3>
                            </div>
                            <DGraph :info='tinfo' :key="keyn"></DGraph>
                        </section>
                    </el-col>
                </el-row>

                <section class="project-details">
                    <div class="block-title">
                        <p>Competitive Products</p>
                        <h3>竞品关系图</h3>
                    </div><br>
                    <BrandRelation :jsonObj="rinfo" :key="keyn"></BrandRelation>
                </section>
            </section>


            <Footer></Footer>

        </div><!-- /.page-wrapper -->

    </div>
</template>

<script src="../assets/js/Radar.js"></script>
<script>
    // @ is an alias to /src
    import axios from 'axios'
    /*import Geo from '@/components/Geo2.vue'
    import Header from '@/components/Header.vue'
    import Footer from '@/components/Footer.vue'
    import ToTop from '@/components/ToTop.vue'
    import CompetitiveList from '@/components/CompetitiveCompanies2.vue'
    import Radar from '@/components/Radar.vue'
    import Info from '@/components/CompanyInfo.vue'
    import Rose from '@/components/Rose.vue'
    import BrandRelation from '@/components/BrandRelation.vue'
    import Region from '@/components/Region.vue'
    import Portrait from '@/components/Portrait.vue'
    import SearchIndex from '@/components/SearchIndex.vue'*/
    export default {
        name: 'CPs',
        components: {
            Header: () => import('@/components/Header.vue'),
            Geo: () => import('@/components/Geo2.vue'),
            Footer: () => import('@/components/Footer.vue'),
            ToTop: () => import('@/components/ToTop.vue'),
            CompetitiveList: () => import('@/components/CompetitiveCompanies2.vue'),
            Radar: () => import('@/components/Radar.vue'),
            Info: () => import('@/components/CompanyInfo.vue'),
            Rose: () => import('@/components/Rose.vue'),
            BrandRelation: () => import('@/components/BrandRelation.vue'),
            Region: () => import('@/components/Region.vue'),
            Portrait: () => import('@/components/Portrait.vue'),
            SearchIndex: () => import('@/components/SearchIndex.vue'),
            Scatter: () => import('@/components/Scatter.vue'),
            Sales: () => import('@/components/Sales.vue'),
            DGraph: () => import('@/components/DGraph.vue'),
            news: () => import('@/components/news.vue'),
            TreeMap: () => import('@/components/TreeMap.vue'),
        },
        data() {
            return {
                name: ['泰奇八宝粥', '银鹭', '达利园', '娃哈哈八宝粥', '五芳斋', '方家铺子', '禾煜'],
                info: {},//雷达图
                cinfo: {},//公司信息
                rinfo: {},
                commentInfo: {},//玫瑰图
                sales: {}, //销量
                qinfo: {}, //关联搜索指数
                iinfo: {}, //搜索指数
                pinfo: {}, //人群画像数据
                dinfo: {}, //地区搜索指数
                news: {}, //新闻
                tinfo: {}, //3d图数据
                treeinfo:{},//树图数据
                keynum: 0,
                keyn:0,
                isTop: false,
                placeholder: false
            };
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
            changedata(item) {
                console.log(item);
                if (item == '泰奇八宝粥') {

                    this.getInfo()
                } else {

                    this.getInfo1(item)
                    this.loading()
                }

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
            async getInfo() {
                let res = await axios.get(
                    "http://121.46.19.26:8511/getCompet/440108400003939"
                );
                let commentInfo = await axios.get("http://121.46.19.26:8511/getCommentStatistic/7552427")//玫瑰图数据
                let saleInfo = await axios.get("http://121.46.19.26:8511/getPredict/" + "泰奇食品") //销量+预测数据
                let qpInfo = await axios.get("http://121.46.19.26:8511/getRelateSearch/" + "八宝粥") //关联搜索气泡图数据
                let siInfo = await axios.get("http://121.46.19.26:8511/getIndexPredict/" + "泰奇") //搜索指数数据
                let ageInfo = await axios.get("http://121.46.19.26:8511/getAgeDistribution/" + "八宝粥") //搜索指数数据
                let regInfo = await axios.get("http://121.46.19.26:8511/getProvinceIndex/" + "八宝粥") //地区搜索指数数据
                let newsInfo = await axios.get("http://121.46.19.26:8511/getNews/" + "泰奇食品") //新闻数据
                let threeInfo = await axios.get("http://121.46.19.26:8511/get3dScore/7552427") //3d图数据
                let treeInfo = await axios.get("http://121.46.19.26:8511/getEmotionAnalysis/"+"泰奇") //树图数据                
                let {
                    data
                } = await axios.get(
                    "http://121.46.19.26:8511/getGraphByBrand/" +
                    "泰奇");
                let dataa = res.data                
                this.info = dataa.scoreList
                this.cinfo = dataa.companyInfo
                this.tinfo = threeInfo.data
                this.sales = saleInfo.data
                this.commentInfo = commentInfo
                this.qinfo = qpInfo.data
                this.iinfo = siInfo.data
                this.pinfo = ageInfo.data
                this.dinfo = regInfo.data
                this.news = newsInfo.data
                
                this.treeinfo = treeInfo.data
                this.rinfo = data;
                console.log(this.tinfo)
                /*console.log(this.commentInfo)
                console.log(this.pinfo)
                console.log(this.dinfo)
                console.log(this.iinfo)
                console.log(this.sales)
                console.log(this.news)
                
                console.log(this.treeinfo)
                console.log(dataa)                
                console.log(this.rinfo)                
                console.log(this.info)
                console.log(this.cinfo)*/
                this.keynum += 1
                this.keyn+=1
            },
            async getInfo1(item) {
                let qpInfo = await axios.get("http://121.46.19.26:8511/getRelateSearch/" + item) //关联搜索气泡图数据
                let siInfo = await axios.get("http://121.46.19.26:8511/getIndexPredict/" + item) //搜索指数数据
                let ageInfo = await axios.get("http://121.46.19.26:8511/getAgeDistribution/" + item) //搜索指数数据
                let regInfo = await axios.get("http://121.46.19.26:8511/getProvinceIndex/" + item) //地区搜索指数数据
                this.qinfo = qpInfo.data
                this.iinfo = siInfo.data
                this.pinfo = ageInfo.data
                this.dinfo = regInfo.data
                //console.log(this.qinfo)
                //this.$forceUpdate()
                this.keynum += 1
            }
        },
        created() {
            console.log('mounted()');
            //setTimeout(() => {
            this.getInfo()
            
            //}, 500);
            this.loading()
        },
        mounted() {
    this.$nextTick(() => {
      this.topMenu();
    });
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
        watch: {
            '$route'(to, from) {
                this.getInfo1()
            }
        }
    }
</script>

<style scoped>
    .blog-details__image img {
        width: 100%;
    }

    .block-title {
        width: 100;
        margin: 0 auto;
        text-align: center;
    }

    .el-row {
        width: 100%;
        margin: 0 auto;
    }

    .list {
        width: 100;
        margin: 0 auto;
    }

    .project-details__list {
        width: 90%;
        margin: 0 auto;
    }

    .project-details__list li {
        width: 100%;
        margin: 0 auto;
    }

    .project-details__list li span{
        width: 100%;
        margin: 0 auto;
    }

    .project-details__list li span a {
        color: var(--thm-black);
    }

    .block-title__image {
        left: 30%;
        width: 24px;
        margin: 0 auto;
        margin-bottom: 5px;
    }

    .project-details {
        padding-bottom: 20px;
        padding-top: 30px;
        
    }

    .list-unstyled.project-details__list{
        width: 90%;
    }

    #namelist {
        height: 50px;
        width: 800px;
        margin: 0 auto;
    }

    #namelist ul {
        width: 105px;
        list-style: none;

        height: 44px;
        margin: 0 auto;
        display: inline;
    }

    #namelist li {
        width: 105px;
        margin: 0 auto;
        display: inline;
        line-height: 40px;
        float: left;
        margin-left: 5px;
    }

    #namelist button {

        width: 105px;
        background-color: #f7c35f;
        display: block;
        margin: 0 auto;
        border-radius: 5px;
    }

    .block-title h3 {
        margin-bottom: 20px;
    }

    #guide {
        width: 190px;
        margin: 0 auto;
        font-size: 30px;
        font-weight: bold;
        color: rgb(37, 89, 70);
    }
    #top {
        width: 80%;
        margin: 0 auto;
    }
</style>
<style>
    .create-isLoading .el-loading-text {
        color: #f7c35f;
        font-size: 18px;
    }

    .create-isLoading i {
        color: #f7c35f;
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
  top: 0;
  z-index: 999;
}
.placeholder {
  height: 50px;
}
</style>