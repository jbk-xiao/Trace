<template>
    <div>
        <div class="process">
            <el-collapse @change="handleChange">
                <el-collapse-item :title="item.name" :name="item.name" v-for="item in info" :key="item.name">
                    <img title="双击查看具体过程监控图片" width="600px" :src="item.picture" alt="" v-on:dblclick="a=true,b=false"
                        v-if="b">
                        
                    <div v-if="a" v-on:dblclick="b=true,a=false">
                        <el-carousel :interval="4000" type="card" height="300px">
                            <el-carousel-item v-for="i in item.procedure " :key="i.name">
                              <h6 class="medium">{{ i.name }} （负责人：{{i.master}}）</h6>
                              <img :src="i.picture" alt="" >
                            </el-carousel-item>
                        </el-carousel> 
                        <!-- <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
                            <el-tab-pane v-for="i in item.procedure" :key="i.name" :label="i.name" :name="i.name">
                                {{i.master}}--{{i.time}} <br>
                                <img  width="600px" :src="item.picture" alt="" >
                            </el-tab-pane>
                        </el-tabs> -->
                    </div>
                    <p>总负责人：{{item.master}}</p>
                    <p @click="routerTo(item.process)" class="realtimevideo">点击查看实时视频</p>

                </el-collapse-item>
            </el-collapse>
        </div>

        <div class="block-title text-center">
                    <div class="block-title__image"></div><!-- /.block-title__image -->
                    <br>
                    <h3>产品追溯</h3>
                </div>
                <div>
                <PathG :jsonObj="info"></PathG>
                <!--PathG></PathG-->
                </div>
        </div><!-- /.container -->
        
</template>
<script>
    import axios from 'axios'
    import PathG from '@/components/Path.vue'
    // import Video from '@/components/Video.vue'
    export default {
        // props: ["geo"],
        data() {
            return {
                info: {},
                tabPosition: 'left',
                a: false,
                b: true,
                activeName: 'second',
                query:decodeURI(this.$route.query.query),
            };
        },
        methods: {
            routerTo(process) {
                this.$router.push({
                    path: '/RealTimeVideo',
                    query: {
                        process: process
                    }
                });
                //console.log(process)
            },
            handleChange(val) {
                console.log(val);
            },
            async point() {
                let { data } = await axios.get(
                  "http://121.46.19.26:8511/getOrigin/"+this.query
                );
                // let {
                //     data
                // } = await axios.get("/data/process.json");
                this.info = data[0].process;
                //console.log(data)
            },
        },
        mounted() {
            this.point();
        },
        components: {
            // Video
            PathG
        }
    };
</script>

<style>
    .process .el-collapse .el-collapse-item .el-collapse-item__header {
        font-size: 20px;
        color: rgb(37, 89, 70);
        font-weight: 700;
    }
    .el-tabs__item.is-top.is-active{
        color: #255946;
        font-weight: bolder;
    }
    .el-tabs__item.is-top:hover{
        color: #255946;
        font-weight: bolder;
    }
    .realtimevideo {
        cursor: pointer;
        color:#f7c35f;
    }
</style>