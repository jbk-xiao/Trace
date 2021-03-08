<template>
    <div>
        <div class="page-wrapper">
            <ToTop></ToTop>
            <Header></Header>

            <ComInfo :info='cinfo' class="info"></ComInfo>

        
            <div class="list">
                <div class="block-title">
                    <p>GOODSLIST</p>
                    <h3>商品列表</h3>
                </div><!-- /.block-title -->
                <GoodsList :info='goodinfo'></GoodsList>
            </div>

            <div class="list">
                <div class="block-title">
                    <p>Traceability information</p>
                    <h3>溯源信息列表</h3>
                </div><!-- /.block-title -->
                <Trace :pname='goodinfo' url='http://121.46.19.26:8511/getAllTraceInfo/520102000400793/' cid='520102000400793'></Trace>
            </div>

            <Footer></Footer>

        </div><!-- /.page-wrapper -->

    </div>
</template>

<script>
    // @ is an alias to /src
    import axios from 'axios'
    /*import Header from '@/components/Header.vue'
    import Footer from '@/components/Footer.vue'
    import ToTop from '@/components/ToTop.vue'
    import GoodsList from '@/components/GoodsList.vue'
    import ComInfo from '@/components/CompanyInfo.vue'
    import Trace from '@/components/TraceabilityInfo.vue'*/

    export default {
        name: 'Detail',
        components: {
            Header:() => import('@/components/Header.vue'),
            Footer:() => import('@/components/Footer.vue'),
            ToTop:() => import('@/components/ToTop.vue'),
            GoodsList:() => import('@/components/GoodsList.vue'),
            ComInfo:() => import('@/components/CompanyInfo.vue'),
            Trace:() => import('@/components/TraceabilityInfo.vue')
        },
        data() {
            return {
                cinfo:{},
                goodinfo:{},
                // traceinfo:{},
                productinfo:{}
            };
        },
        methods: {
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
                    "http://121.46.19.26:8511/getCompet/520102000400793"
                );
                this.cinfo = res.data.companyInfo

                let ginfo = await axios.get("http://121.46.19.26:8511/getProducts/520102000400793")
                this.goodinfo=ginfo.data

                // let tinfo = await axios.get("data/goods_list0.1.json")
                // this.traceinfo = tinfo.data

                //let pinfo = await axios.get("http://121.46.19.26:8511/getProducts/520102000400793")
                //this.productinfo = pinfo.data

                //  console.log(this.goodinfo)
                // console.log(this.productinfo)
                
            },
        },
        created() {
            // console.log('mounted()');
            //setTimeout(() => {
                this.getInfo();
                this.loading();
            //}, 100);
        }
    }
</script>

<style scoped>
    .list {
    width: 80%;
    margin: 30px auto;
    margin-bottom: 80px;
    text-align: center;
  }
  .info {
      margin: 50px auto;
  }
</style>