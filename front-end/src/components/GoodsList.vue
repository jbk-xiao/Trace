<template>
<el-row>

<!--el-button class="el-icon-finished" @click="AddBatch()">保存</el-button-->
    <el-button class="el-icon-refresh"  @click="setCurrent()">刷新</el-button>

  <el-table
    :data="tableData"
    
    stripe
    style="width: 60%"
    max-height="300">
    
    <el-table-column
      
      sortable
      prop="product"
      label="商品"
      width="400">
    </el-table-column>
    
    <el-table-column
      sortable
      prop="code"
      label="编码"
      width="320">
    </el-table-column>
    <el-table-column
      
      label="操作"
      >
      <template slot-scope="scope">
        <!--el-popconfirm @="deleteRow(scope.$index, tableData)"
        title="确定删除吗？"
        -->
        <el-button 
          @click.native.prevent="deleteRow(scope.$index, tableData)"
          type="text"
          size="small">
          移除
        </el-button>
        <!--/el-popconfirm-->
      </template>
    </el-table-column>
    
  </el-table>

  <el-form :inline="true" :model="formInline" class="demo-form-inline">
  <el-form-item label="商品">
    <el-input v-model="formInline.name" placeholder="商品名称"></el-input>
  </el-form-item>
  <el-form-item label="编码">
    <el-input v-model="formInline.code" placeholder="商品编号"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button class="el-icon-circle-plus" @click="handleAddItem()">添加</el-button>
  </el-form-item>
</el-form>
</el-row>


  
</template>

<script>
  import axios from 'axios'
  export default {
    props: ['info'],
    inject:['reload'],
    methods: {
      show(info) {
                // this.list = info
                //console.log(info)
                var data = []
                var gdata = []
                for (let i = 0; i < info.length; i++) {
                    data.push({
                            value: info[i].product,
                            label: info[i].product
                        }),
                    gdata.push({
                        
                        product: info[i].product,
                        code: info[i].code
                    })
                }
                //console.log(gdata)
                this.tableData = gdata
            },
      async deleteRow(index, rows) {
       //console.log(rows[index].product)
       let res = await axios.get(
                    "http://121.46.19.26:8511/deleteProduct/520102000400793/"+rows[index].product
                );
        this.re=res.data
        alert(this.re)
        
        rows.splice(index, 1);
        //alert(this.re)
      },
      
　　async handleAddItem() {
      let res = await axios.get(
                    "http://121.46.19.26:8511/addProduct/520102000400793/"+this.formInline.name+"/"
                    +this.formInline.code
                );
        this.re=res.data
        alert(this.re)
},
    setCurrent() {
      this.reload()
    }
    },
    
    
    mounted() {
      //console.log(this.info)
            setTimeout(() => {
                this.show(this.info)
            }, 500);
        },
    data() {
      return {
        value: '',
        tableData: [],
        re:'',
        formInline: {
          name: '',
          code: ''
        }
      }
    }
  }
</script>

<style>
.el-row{
  margin-top: 20px;
}
.el-button--text{
  color: #255946;
}
.el-button--text:hover{
  color: #f7c35f;
}
.el-button--text:active{
  color: #f7c35f;
}
.el-table{
  margin: auto;
    width: 800;
}
.el-button:hover{
  background-color: #255946;
  color: #f7c35f;
}
.el-button:active{
  background-color: #255946;
  color: #f7c35f;
}
.el-form{
  margin-top: 20px;
}
.el-input{
  border-color: #255946;
  border-width: 2px;
  border-radius: 0%;
}
.el-input__inner{
  border-color: #255946;
  border-width: 2px;
  background-color:#FAFAFA;
}
.el-input__inner:focus-within{
  border-color: #f7c35f;
}
</style>