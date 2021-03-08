<template>
    <div>

        <footer class="site-footer">
            <img src="../../public/images/icons/footer-bg-icon-1.png" class="site-footer__shape-1" alt="">
            <img src="../../public/images/icons/footer-bg-icon-2.png" class="site-footer__shape-2" alt="">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12 col-md-6 col-lg-6 col-xl-4">
                        <div class="footer-widget">
                            <a href="index.html" class="footer-widget__Logo">
                                <img src="../../public/images/logo-light.png" width="153" alt="">
                            </a>
                            
                        </div><!-- /.footer-widget -->
                    </div><!-- /.col-sm-12 col-md-6 col-lg-4 -->
                    <div class="col-sm-12 col-md-6 col-lg-6 col-xl-4">
                        <div class="footer-widget footer-widget__links-widget">
                            <h3 class="footer-widget__title">Links</h3><!-- /.footer-widget__title -->
                            <ul class="list-unstyled footer-widget__links">
                                <li><router-link :to="'/Style'">信息示例</router-link></li>
                                <li><router-link :to="'/Products'">产品搜索</router-link></li>
                                <li><router-link :to="'/MSearch'">竞争图谱</router-link></li>
                                <li><router-link :to="'/Contact'">联系我们</router-link></li>
                            </ul><!-- /.list-unstyled -->
                        </div><!-- /.footer-widget -->
                    </div><!-- /.col-sm-12 col-md-6 col-lg-2 -->
                    <!--div class="col-sm-12 col-md-6 col-lg-6 col-xl-3">
                        <div class="footer-widget">
                            <h3 class="footer-widget__title">News</h3>
                            <ul class="list-unstyled footer-widget__post">
                                <li>
                                    <img src="../../public/images/resources/footer-post-1.png" alt="">
                                    <div class="footer-widget__post-content">
                                        <span>Nov 16, 2020</span>
                                        <h4><a href="blog-details.html">How to grow vagetables in the forms</a></h4>
                                    </div>
                                </li>
                                <li>
                                    <img src="../../public/images/resources/footer-post-2.png" alt="">
                                    <div class="footer-widget__post-content">
                                        <span>Nov 16, 2020</span>
                                        <h4><a href="blog-details.html">How to grow vagetables in the forms</a></h4>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div--><!-- /.col-sm-12 col-md-6 col-lg-3 -->
                    <div class="col-sm-12 col-md-6 col-lg-6 col-xl-4">
                        <h3 class="footer-widget__title">Contact</h3><!-- /.footer-widget__title -->
                        <ul class="list-unstyled footer-widget__contact">
                            <li>
                                <i class="agrikon-icon-telephone"></i>
                                <a>XXX XXX XXXX</a>
                            </li>
                            <li>
                                <i class="agrikon-icon-email"></i>
                                <a>XXX@mail.cn</a>
                            </li>
                            <li>
                                <i class="agrikon-icon-pin"></i>
                                <a>China</a>
                            </li>
                        </ul><!-- /.list-unstyled -->
                    </div><!-- /.col-sm-12 col-md-6 col-lg-3 -->
                </div><!-- /.row -->
            </div><!-- /.container -->
        </footer><!-- /.site-footer -->
        <div class="bottom-footer">
            <div class="container">
                <p>© Copyright 2021 by TEAM SOS</p>
                
            </div><!-- /.container -->
        </div><!-- /.bottom-footer -->

    </div>
</template>

<script>
    export default {}
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

<style scoped>
    .site-footer::before {
        background-image: none;
    }
    .blog-details__image img {
        width: 100%;
    }
    
</style>