<template>
    <div>
        <div class="page-wrapper">
            <ToTop></ToTop>
            <Header></Header>
            
            <div class="blog-author">
                            <div class="about-one__box">
                                <i class="agrikon-icon-customer"></i>
                            </div><!-- /.blog-author__image -->
                            <div class="blog-author__content">
                                <h3>导师：Df Lee</h3>
                                <p></p>
                            </div><!-- /.blog-author__content -->
                        </div>
            <div class="blog-author">
                            <div class="about-one__box">
                                <i class="agrikon-icon-farmer"></i>
                            </div><!-- /.blog-author__image -->
                            <div class="blog-author__content">
                                <h3>组长：Clivia-Han</h3>
                                <p>Major in back-end.</p>
                            </div><!-- /.blog-author__content -->
                        </div>
            <div class="blog-author">
                            <div class="about-one__box">
                                <i class="agrikon-icon-farmer"></i>
                            </div><!-- /.blog-author__image -->
                            <div class="blog-author__content">
                                <h3>组员：jbk-xiao</h3>
                                <p>Major in back-end.</p>
                            </div><!-- /.blog-author__content -->
                        </div>
            <div class="blog-author">
                            <div class="about-one__box">
                                <i class="agrikon-icon-farmer"></i>
                            </div><!-- /.blog-author__image -->
                            <div class="blog-author__content">
                                <h3>组员：Jh Peng</h3>
                                <p>Major in font-end.</p>
                            </div><!-- /.blog-author__content -->
                        </div>
            <div class="blog-author">
                            <div class="about-one__box">
                                <i class="agrikon-icon-farmer"></i>
                            </div><!-- /.blog-author__image -->
                            <div class="blog-author__content">
                                <h3>组员：JyChan22</h3>
                                <p>Major in font-end.</p>
                            </div><!-- /.blog-author__content -->
                        </div>
            <div class="blog-author">
                            <div class="about-one__box">
                                <i class="agrikon-icon-farmer"></i>
                            </div><!-- /.blog-author__image -->
                            <div class="blog-author__content">
                                <h3>组员：sakura1379</h3>
                                <p>Major in back-end.</p>
                            </div><!-- /.blog-author__content -->
                        </div>
            <Footer></Footer>

        </div><!-- /.page-wrapper -->

    </div>
</template>

<script>
    // @ is an alias to /src
    import Header from '@/components/Header.vue'
    import Footer from '@/components/Footer.vue'
    //import SearchBox1 from "@/components/SearchBox1.vue";
    import ToTop from '@/components/ToTop.vue'
    export default {
        name: 'Origin',
        components: {
            Header,
            Footer,
            ToTop
            //"search-box1": SearchBox1
        }
    }
</script>

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
.blog-author{
    width: 70%;
    margin: 0 auto;
    margin-bottom: 50px;
}
</style>