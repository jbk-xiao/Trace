<template>
    <div>
        <div class="page-wrapper">
            <ToTop></ToTop>
            <Header></Header>

            <div class="entryForm">

                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm"
                    style="width: 50%;text-align: center;">
                    <div class="title">> 后续流程录入</div>
                    <el-form-item label="溯源码" prop="sid">
                        <el-input v-model="ruleForm.sid" v-text="ruleForm.sid"></el-input>
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
                    <el-form-item>
                        <el-button type="primary" @click="submitForm1(ruleForm)">确定</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </el-form>

                <!-- <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm"
                    style="width: 50%;text-align: center;">
                    <div class="title">> 工序信息录入</div>
                    <el-form-item label="溯源码" prop="sid">
                        <el-input v-model="ruleForm.sid"></el-input>
                    </el-form-item>
                    <el-form-item label="工序名称" prop="name">
                        <el-input type="textarea" v-model="ruleForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="负责人" prop="master">
                        <el-input v-model="ruleForm.master"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm2(ruleForm)">确定</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </el-form> -->

            </div>

            <Footer></Footer>

        </div><!-- /.page-wrapper -->

    </div>
</template>

<script>
    import axios from 'axios'
    import qs from 'qs'

    export default {
        name:'AddProcess',
        components: {
            Header: () => import('@/components/Header.vue'),
            Footer: () => import('@/components/Footer.vue'),
            ToTop: () => import('@/components/ToTop.vue'),
        },
        data() {
            return {
                value: '',
                qrCodeUrl: '',
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
                this.ruleForm.sid = this.$route.params.id
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
            resetForm(formName) {
                this.$nextTick(() => {
                    this.$refs[formName].resetFields();
                })
            },
            submitForm1(formName) {
                //id=6921804700221210301221819&name=工厂 &master=李四&location=贵阳
                this.ruleForm.foodType = this.value
                // this.$refs[formName].validate((valid) => {
                //     if (valid) {
                axios.request({
                    method: 'post',
                    url: 'http://121.46.19.26:8511/addFirstProcess/',
                    params: {
                        id: formName.sid,
                        name: formName.name,
                        master: formName.master,
                        location: formName.location
                    },
                    paramsSerializer: function (params) {
                        // arrayFormat可以格式化数组参数
                        return qs.stringify(params, {
                            arrayFormat: 'brackets'
                        })
                    }
                })
                // alert('信息录入成功!');
                this.resetForm(formName)
                //     } else {
                //         alert('信息录入失败!!');
                //         return false;
                //     }
                // });
            },
            submitForm2(formName) {
                // id=6921804700221210301221819&name=炒制&master=李子谦
                this.ruleForm.foodType = this.value
                // this.$refs[formName].validate((valid) => {
                //     if (valid) {
                axios.request({
                    method: 'post',
                    url: 'http://121.46.19.26:8511/addFirstProcess/',
                    params: {
                        id: formName.sid,
                        name: formName.name,
                        master: formName.master
                    },
                    paramsSerializer: function (params) {
                        // arrayFormat可以格式化数组参数
                        return qs.stringify(params, {
                            arrayFormat: 'brackets'
                        })
                    }
                })
                // alert('信息录入成功!');
                this.resetForm(formName)
                //     } else {
                //         alert('信息录入失败!!');
                //         return false;
                //     }
                // });
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