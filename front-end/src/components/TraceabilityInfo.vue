<template>
    <div>
        <el-select v-model="value" filterable placeholder="请选择溯源产品" class="select">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
        </el-select>
        <br><br>
        <el-table :data="tableData" height="400" width="80%">
            <el-table-column prop="id" label="溯源码" width="260">
            </el-table-column>
            <el-table-column prop="foodName" label="商品名称" width="200">
            </el-table-column>
            <el-table-column prop="specification" label="规格" width="120">
            </el-table-column>
            <el-table-column prop="category" label="类别" width="180">
            </el-table-column>
            <el-table-column prop="latestProcess" label="最新流程">
            </el-table-column>
            <el-table-column prop="time" label="时间" width="260">
            </el-table-column>
        </el-table>
        <br>
        <router-link :to="'/Index'"></router-link>
        <el-button type="primary" icon="el-icon-edit" class="editbtn">
            <router-link :to="{name:'TInfoEntry',params:{ft:value,cid:cid}}" id="enter">新溯源信息录入</router-link>
        </el-button>>

        <br><br>
        <div class="pag">
            <el-pagination background layout="prev, pager, next" :total="10">
            </el-pagination>
        </div>

    </div>
</template>

<script>
    import axios from 'axios'
    export default {
        data() {
            return {
                options: [],
                list: '',
                value: '',
                tableData: [],
            }
        },
        props: ['pname', 'url','cid'],
        mounted() {
            setTimeout(() => {
                this.show(this.pname)
            }, 500);
            setTimeout(() => {
                this.getTdata(this.value)
            }, 600);
        },
        watch: {
            value: function () {
                this.getTdata(this.value)
            }
        },
        methods: {
            show(pname) {
                var data = []
                for (let i = 0; i < pname.length; i++) {
                    data.push({
                        value: pname[i].product,
                        label: pname[i].product
                    })
                }
                this.options = data
            },
            async getTdata(value) {
                // console.log(value)
                var tdata = []
                let tinfo = await axios.get(
                    this.url+value+"/1")
                let info = tinfo.data
                for (let i = 1; i < info.length; i++) {
                    tdata.push({
                        id: info[i].id,
                        foodName: info[i].foodName,
                        specification: info[i].specification,
                        category: info[i].category,
                        latestProcess: info[i].latestProcess,
                        time: info[i].time
                    })
                }
                this.tableData = tdata
            }
        }
    }
</script>

<style>
    div .select .el-input__inner {
        width: 260px;
        cursor: pointer;
        -webkit-appearance: none;
        background-color: #FFF;
        background-image: none;
        border-radius: 4px;
        border: 1px solid #046915;
        border-color: #255946;
        box-sizing: border-box;
        color: #606266;
        display: inline-block;
        font-size: inherit;
        height: 40px;
        line-height: 40px;
        outline: 0;
        padding: 0 15px;
        transition: border-color .2s cubic-bezier(.645, .045, .355, 1);
    }

    .el-select.select.is-focus {
        border-color: #255946;
    }

    div .select .el-input__suffix {
        right: 10px;
        transition: all .3s;
    }

    div .select .el-input__prefix,
    .el-input__suffix {
        position: absolute;
        top: 2px;
        -webkit-transition: all .3s;
        height: 100%;
        color: #C0C4CC;
        text-align: center;
    }

    div .select .el-select .el-input .el-select__caret {
        color: #C0C4CC;
        font-size: 14px;
        transition: transform .3s;
        transform: rotateZ(180deg);
        cursor: pointer;
    }

    div .select .el-input__icon {
        height: 100%;
        width: 25px;
        text-align: center;
        transition: all .3s;
        line-height: 40px;
    }

    .pag .el-pagination.is-background .el-pager li:not(.disabled).active {
        background-color: #406915 !important;
        color: #FFF;
    }

    .el-pager li:not(.disabled):hover {
        color: #255946;
    }

    .el-select-dropdown__item.selected {
        color: #255946;
    }

    .el-input.el-input--suffix:focus-within {
        border-color: #255946;
    }

    .editbtn {
        background-color: white;
        border-color: #046915;
        color: #049615;
    }

    .el-button.el-button--primary {
        background-color: white;
        border-color: #255946;
        color: #255946;
    }

    .el-button.el-button--default {
        background-color: white;
        border-color: #255946;
        color: #255946;
    }

    .el-button.el-button--default:hover {
        background-color: #255946;
        color: #f7c35f;
    }

    .el-button.el-button--primary:hover {
        background-color: #255946;
        color: #f7c35f;
    }

    .editbtn {
        background-color: #255946;
        color: #f7c35f;
    }

    .el-drawer.rtl {
        max-width: 100%;
    }

    .demo-ruleForm {
        margin: 20px auto;
    }

    .el-textarea {
        border-color: #255946;
        border-width: 2px;
        border-radius: 0%;
    }

    .el-textarea__inner {
        border-color: #255946;
        border-width: 2px;
        background-color: #FAFAFA;
    }
    #enter{
        color: #255946;
    }
    #enter:hover{
        color: #f7c35f;
    }
</style>