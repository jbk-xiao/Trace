<template>
    <div>
        <div class="container">
            <div class="row" >
                <div class="col-lg-6">
                    <div class="about-two__images">
                        <img :src="list.img_url" alt="" width="100%" class="clogo">
                        <!-- <img src="assets/images/resources/about-2-1.jpg" alt=""> -->
                    </div><!-- /.about-two__images -->
                </div><!-- /.col-lg-6 -->
                <div class="col-lg-6">
                    <div class="about-two__content">
                        <div class="block-title">
                            <div class="block-title__image"></div><!-- /.block-title__image -->
                            <p>Our introduction</p>
                            <h3>{{list.proj_name}}</h3>
                        </div><!-- /.block-title -->
                        <div class="about-two__summery">
                            <p id="intro">{{list.proj_desc}}</p>
                        </div><!-- /.about-two__summery -->
                        <div class="row">
                            <!-- <div class="col-md-8"> -->
                                <ul class="list-unstyled about-two__list">
                                    <li>
                                        <i class="fa fa-check-circle"></i>
                                        成立时间：{{list.es_time}}
                                    </li>
                                    <li>
                                        <i class="fa fa-check-circle"></i>
                                        公司名称：{{list.company_name}}
                                    </li>
                                    <li>
                                        <i class="fa fa-check-circle"></i>
                                        详细地址：{{list.address}}
                                    </li>
                                    <li>
                                        <i class="fa fa-check-circle"></i>
                                        联系电话：{{list.phone_num}}
                                    </li>
                                </ul><!-- /.list-unstyled about-two__list -->
                            <!-- </div> -->
                            <!-- /.col-md-6 -->
                            
                        </div><!-- /.row -->
                    </div><!-- /.about-two__content -->
                </div><!-- /.col-lg-6 -->
            </div><!-- /.row -->
        </div><!-- /.container -->
    </div>
</template>

<script>
    export default {
        name: 'eCharts',
        props: ['info'],
        data() {
            return {
                list: []
            }
        },
        mounted() {
            setTimeout(() => {
                this.show(this.info)
            }, 1000);
        },
        methods: {
            show(info) {
                 //console.log(info)
                this.list=info
            }
        }
    };
</script>

<style>
    .clogo {
        box-shadow: 6px 6px 6px rgba(0,0,0,.3)
    }
    .about-two__summery{
        margin-bottom: 0;
    }
    #intro{
        margin-top: 40px;
    }
</style>