<template>
    <section class="service-details">
        <div class="container">
            <el-row :gutter="1">
                <el-col :span="12" v-for="item in list" :key=item._id class="col-sm-12 col-md-6 col-lg-6">
                    <ul class="service-details__list list-unstyled">
                        <li>
                            <i class="agrikon-icon-right-arrow"></i>
                            <a :href="item.news_url" target="_blank">
                                <strong>{{item.news_title}}</strong></a>
                        </li>
                    </ul>
                </el-col>
                <el-col :span="12" v-for="item2 in list2" :key=item2._id class="col-sm-12 col-md-6 col-lg-6">
                    <ul class="service-details__list list-unstyled">
                        <li>
                            <i class="agrikon-icon-right-arrow"></i>
                            <a :href="item2.news_url" target="_blank">
                                <strong>{{item2.news_title}}</strong></a>
                        </li>
                    </ul>
                </el-col>
            </el-row>
        </div>
    </section>
</template>

<script>
    export default {
        name: 'news',
        props: ['ninfo'],
        data() {
            return {
                list: [],
                list2: []
            }
        },
        mounted() {
            setTimeout(() => {
                this.show(this.ninfo)
            }, 1500);
        },
        methods: {
            show(info) {
                // console.log(info.data[2])
                //news_title,news_url
                console.log(info[1])
                this.list = info.slice(0, 4)
                this.list2 = info.slice(5, 9)
            }
        }
    }
</script>

<style>
    .service-details {
        padding-top: 0px;
        padding-bottom: 0px;
    }

    .service-details__list.list-unstyled {
        margin: 0 auto;
        width: 500px;
    }

    .service-details__list.list-unstyled li {
        left: 10%;
        padding-right: 5px;
        margin-bottom: 20px;

    }

    .service-details__list.list-unstyled strong {
        width: 400px;
        margin: auto;
        overflow: hidden;
        line-height: 60px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
</style>