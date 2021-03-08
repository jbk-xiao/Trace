<template>
    <div>
        <div class="page-wrapper">
            <ToTop></ToTop>
            <Header></Header>
            
            <searchM></searchM>

        <section class="call-to-action jarallax" data-jarallax data-speed="0.3" data-imgPosition="-80% 50%">
            <img class="call-to-action__bg jarallax-img" src="../../public/images/backgrounds/cta-1-bg-1.jpg" alt="parallax-image" />
            <!-- /.call-to-action__bg -->
            <div class="container">
                <div class="call-to-action__content">
                    <i class="call-to-action__icon agrikon-icon-agriculture-2"></i>
                    <h3>提供全面的品牌、品类
                        竞争图谱分析</h3>
                </div><!-- /.call-to-action__content -->
                
            </div><!-- /.container -->
        </section><!-- /.call-to-action -->
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

<style>
.call-to-action__content{
    width: 70%;
    margin: auto;
}

.call-to-action__content h3{
    width:95%;
}
</style>

<script>
    // @ is an alias to /src
    import Header from '@/components/Header.vue'
    import Footer from '@/components/Footer.vue'
    import ToTop from '@/components/ToTop.vue'
    import searchM from '@/components/searchM.vue'
    //import axios from 'axios'

  export default {
    name: 'Products',
        components: {
            Header,
            Footer,
            ToTop,
            searchM
        }
  };
</script>