<template>
    <div>
        <div class="page-wrapper">
            <ToTop></ToTop>
            <Header></Header>

            <div class="entryForm">

                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm"
                    style="width: 50%;text-align: center;">
                    <div class="title">> 溯源信息录入</div>
                    <el-form-item label="品类及规格" prop="foodType">
                        <el-input v-model="ruleForm.foodType" v-text="value"></el-input>
                    </el-form-item>
                    <el-form-item label="主公司ID" prop="com">
                        <el-input v-model="ruleForm.com" v-text="ruleForm.com"></el-input>
                    </el-form-item>
                    <el-form-item label="总流程数" prop="processCount">
                        <el-input v-model="ruleForm.processCount"></el-input>
                    </el-form-item>
                    <el-form-item label="流程名称" prop="name">
                        <el-input type="textarea" v-model="ruleForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="负责人" prop="master">
                        <el-input v-model="ruleForm.master"></el-input>
                    </el-form-item>
                    <el-form-item label="城市" prop="location">
                        <el-input v-model="ruleForm.location"></el-input>
                    </el-form-item>
                    <el-form-item style="text-align: right;">
                        <el-button type="primary" @click="submitForm(ruleForm)">确定</el-button>&nbsp;&nbsp;
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                    <img :src="qrCodeUrl" alt="">
                </el-form>
            </div>

            <Footer></Footer>

        </div><!-- /.page-wrapper -->

    </div>
</template>

<script>
    import axios from 'axios'
    import qs from 'qs'

    export default {
        components: {
            Header: () => import('@/components/Header.vue'),
            Footer: () => import('@/components/Footer.vue'),
            ToTop: () => import('@/components/ToTop.vue'),
        },
        data() {
            return {
                value: '',
                qrCodeUrl:'',
                ruleForm: {
                    sid: '',
                    name: '',
                    foodType: '',
                    com: '',
                    processCount: '',
                    master: '',
                    location: ''
                },

                rules: {
                    processCount: [{
                            required: true,
                            message: '请输入流程总数',
                            trigger: 'blur'
                        },
                        {
                            min: 1,
                            max: 100,
                            message: '流程总数为正',
                            trigger: 'blur'
                        }
                    ],
                    name: [{
                        required: true,
                        message: '请输入名称',
                        trigger: 'blur'
                    }],
                    master: [{
                        required: true,
                        message: '请输入负责人',
                        trigger: 'blur'
                    }],
                    location: [{
                        required: true,
                        message: '请输入城市',
                        trigger: 'blur'
                    }]
                }
            }
        },
        methods: {
            loading() {
                this.ruleForm.com = this.$route.params.cid;
                this.value= this.$route.params.ft;
                this.ruleForm.foodType = this.value
                console.log(this.ruleForm)
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
            submitForm(formName) {
                // foodType=油辣椒酱-275g-辣椒酱&com=520102000400793&processCount=4&name=菜籽油产地&master=张三&location=遵义
                console.log(formName)
                // this.$refs[formName].validate((valid) => {
                //     if (valid) {
                axios.request({
                    method: 'post',
                    url: 'http://121.46.19.26:8511/addFirstProcess/',
                    params: {
                        foodType: formName.foodType,
                        com: formName.com,
                        processCount: formName.processCount,
                        name: formName.name,
                        master: formName.master,
                        location: formName.location
                    },
                    paramsSerializer: function (params) {
                        console.log(params)
                        // arrayFormat可以格式化数组参数
                        return qs.stringify(params, {
                            arrayFormat: 'brackets'
                        })
                    }
                }).then(res => {
                    this.qrCodeUrl=res.data.qrCode
                    console.log(this.qrCodeUrl);

                })
                //     alert('信息录入成功!');
                this.resetForm('formName')
                // } else {
                //     alert('信息录入失败!!');
                //     return false;
                // }
                // });
            },
            resetForm(formName) {
                this.$nextTick(() => {
                    this.$refs[formName].resetFields();
                })
            },

        },
        created() {
            this.loading();
        }
    }
</script>

<style scoped>
    .title {
        width: 200px;
        color: #255946;
        margin-top: 20px;
        margin-bottom: 20px;
        text-align: left;
        font-size: 24px;
        font-weight: 700;
    }
</style>