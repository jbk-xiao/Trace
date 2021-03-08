<template>
    <div>
        <ToTop></ToTop>
        <Header></Header>

        <section class="page-header">
            <div class="page-header__bg"
                style="background-image: url(../../public/images/backgrounds/page-header-bg-1-1.jpg);"></div>
            <!-- /.page-header__bg -->
            <div class="container">
                <h2 style="margin-top:70px">实时监控视频</h2>
            </div><!-- /.container -->
        </section><!-- /.page-header -->
        <div class="video">
            <p>{{this.$route.query.process}}</p>
            <Video></Video>
        </div>

        <Footer></Footer>
    </div>
</template>

<style scoped>
    .video {
        width: 800px;
        height: 600px;
        margin: 30px auto;
    }
</style>

<script>
    // @ is an alias to /src
    import Header from '@/components/Header.vue'
    import Footer from '@/components/Footer.vue'
    import Video from '@/components/Video.vue'
    import ToTop from '@/components/ToTop.vue'
    export default {
        name: 'RealTimeVideo',
        components: {
            Header,
            Footer,
            ToTop,
            Video
        },
    }
</script>