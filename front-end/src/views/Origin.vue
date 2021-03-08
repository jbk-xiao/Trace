<template>
    <div>
        <ToTop></ToTop>
        <Header></Header>

        <section class="project-details">
            <div class="container">
                <div class="bottom-content">
                    <div class="row"  v-for="item in info" :key="item.id">
                        <div class="col-lg-6">
                            <h3>{{item.foodName}}</h3>
                            <p>{{item.company_info.proj_desc}}</p>
                            <ul class="list-unstyled project-details__check-list">
                                <li>
                                    <i class="fa fa-check-circle"></i>
                                    溯源码：{{item.id}}
                                </li>
                                <li>
                                    <i class="fa fa-check-circle"></i>
                                    产品类别：{{item.category}}
                                </li>
                                <li>
                                    <i class="fa fa-check-circle"></i>
                                    产品规格：{{item.specification}}
                                </li>
                                <li>
                                    <i class="fa fa-check-circle"></i>
                                    品牌名称：{{item.company_info.proj_name}}
                                </li>
                                <li>
                                    <i class="fa fa-check-circle"></i>
                                    公司名称：{{item.company_info.company_name}}
                                </li>
                                <li>
                                    <i class="fa fa-check-circle"></i>
                                    公司地址：{{item.company_info.address}}
                                </li>
                                <li>
                                    <i class="fa fa-check-circle"></i>
                                    联系方式：{{item.company_info.phone_num}}
                                </li>
                                
                            </ul>
                        </div>
                        <div class="col-lg-6">
                            <div class="project-details__images">
                                
                                <img :src="imgsrc" alt="" width="80%">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <div class="projects-one project-page">
            <div class="container process">

                <div class="block-title text-center">
                    <div class="block-title__image"></div><!-- /.block-title__image -->
                    <br>
                    <h3>生产过程</h3>
                </div><!-- /.block-title -->

                <Process></Process>

                <!--div class="block-title text-center">
                    <div class="block-title__image"></div>
                    <br>
                    <h3>产品追溯</h3>
                </div>
                <div>
                <PathG :jsonObj="location"></PathG>
                
                </div-->
            </div><!-- /.container -->
        </div><!-- /.projects-one -->

        <Footer></Footer>
    </div>
</template>
<script type="text/javascript" src="echarts/extension/bmap/bmap.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=3.0&ak=BeXrLRHFruh6pjiXNq3LachO72AjHsAe"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

<script src="../../public/js/theme.js"></script>
<script src="../../public/js/bootstrap-select.min.js"></script>
<script src="../../public/js/bootstrap.bundle.min.js"></script>
<script src="../../public/js/circle-progress.min.js"></script>

<script src="../../public/js/swiper.min.js"></script>
<script src="../../public/js/jarallax.min.js"></script>
<script src="../../public/js/jarallax.min.js.map"></script>
<script src="../../public/js/jquery.ajaxchimp.min.js"></script>
<!-- SENDMAIL -->
<script src="../../public/js/jquery.appear.min.js"></script>
<script src="../../public/js/jquery.magnific-popup.min.js"></script>
<script src="../../public/js/jquery.validate.min.js"></script>
<script>
    import axios from 'axios'
    import Header from '@/components/Header.vue'
    import Footer from '@/components/Footer.vue'
    // import Video from '@/components/Video.vue'
    import Process from '@/components/Process.vue'
    import ToTop from '@/components/ToTop.vue'
    //import PathG from '@/components/Path.vue'
    //import BMap from 'BMap'
    export default {
        name: 'Origin',
        components: {
            Header,
            Footer,
            ToTop,
            // Video
            Process,
            //PathG
        },
        data() {
            return {
                info: {},
                query:decodeURI(this.$route.query.query),
                imgsrc:''
                //location: {},
                //process:{},
            };
        },
        methods: {
            async point() {
                // let { data } = await this.$get(
                //   "http://222.200.184.74:8288/ForeSee/allInfo/" + this.stockCode
                // );
                let {
                    data
                } = await axios.get("http://121.46.19.26:8511/getOrigin/"+this.query);
                this.info = data;
                //console.log(this.info)
                this.imgsrc=this.info[0].company_info.img_url
                //console.log(this.imgsrc)
                
            },
            
        },
        mounted() {
            this.point();
            //this.getpath();
        },
        //created(){
        //    this.getpath();
        //}
    }
</script>

<style scoped>
    .process {
        width: 80%;
    }
    .block-title.text-center{
        margin-top: 30px;
    }
    
</style>