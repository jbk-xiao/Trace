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
                    <h2 style="margin-top:70px">竞争图谱</h2>
                </div><!-- /.container -->
            </section>

            <section class="project-details">
                <div class="text-center more-btn__box">
                    <span class="thm-btn">品牌竞争图谱</span>
                </div><br>
            <BrandRelation :jsonObj="info"></BrandRelation>
            </section>

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

<script>
    // @ is an alias to /src
    import Header from '@/components/Header.vue'
    import Footer from '@/components/Footer.vue'
    import ToTop from '@/components/ToTop.vue'
    import BrandRelation from '@/components/BrandRelation.vue'
    import axios from 'axios'

  export default {
    name: 'Products',
        components: {
            Header,
            Footer,
            ToTop,
            BrandRelation
        },
    data() {
      return {
        info: {},
        //title: {},
        query:decodeURI(this.$route.query.query),
        key:"",
      };
    },
    methods:{
        async point() {
        let { data } = await axios.get(
          "http://121.46.19.26:8511/getGraphByBrand/" +
          this.query );
        this.info = data;
        this.key = this.query;
        //alert(this.key);
        }
    },
    mounted() {
      this.point();
    },
  };
</script>