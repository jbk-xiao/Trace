<template>
  <!-- 检索框 -->
<div >
<div class="blog-sidebar__search">
                                <form class="form">
                                    <el-input type="text" placeholder="请输入品牌名称"
                                    v-model="input"
                                    @keyup.enter.native="search"
                                    clearable @clear="setValueNull"
                                    ></el-input>
                                    <button type="submit" v-bind:disabled="dis" @click="trans" id="searchclick" @click.prevent="btnClick"><i class="agrikon-icon-magnifying-glass"></i></button>
                                </form>
                            </div>
  <!-- 实时检索下拉菜单 >
  <div class="search-content" ref="search" v-show="input" style="background:white">
        <ul>
          <li class="search-item " v-for="item in returnlist" :key="item.id" @click="transFromRealtime(item)" >
              <p>{{ item.company_name }}</p>
          </li>
          <li class="search-item nodata" v-show="hasNoData">没有找到匹配数据</li>
        </ul>
  </div-->
</div>
</template>

<style>
.search-box{
  margin-bottom: 0;
  margin-top: 8px;
  position: relative;
  height: 100px;
}

.search-content{
  margin-top:-40px;
  padding:0;
  width:280px;
  margin-left: 224px;
  /* text-align: left; */
  /* border-top: 1px solid #eee; */
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  overflow: hidden;
  position: absolute;
  z-index: 9999;


}
.search-content ul{
  margin-left: 0px;
}
.search-content .search-item{
  margin-left: 0;
  height: 40px;
  /* text-align: left; */
  display:flex;
  vertical-align: middle;
  align-items: center;
  align-content: center;
  border-top: 1px solid #eee;
}

.search-content .search-item p{
  margin-top: 30px;
  margin-left: 10px;
  font-size: 12px;
}
.banner .wrap-caption .search-content .search-item p {
  color: #606266;
}

.search-item:hover{
  cursor: pointer;
}

.search-item.nodata{
  margin-left: 10px;
  border-bottom: none;
}
#searchclick.btn.btn-primary{
  background-color: #f7c35f;
    font-size: 18px;
  font-weight: bold;
  margin-top: 0%;
  padding: 10px;
}
#searchclick.btn.btn-primary:hover{
  background-color: #255946;
}
 .el-input__inner {
  height: 54px;
  width: 80%;
  padding: 10px;
  border: none;
  border-radius: 28px;
  outline: none;
  text-align: left;
  
}
.blog-sidebar__search{
  width: 100%;
  height: 180px;
}
.form{
  height: 70px;
}
.el-input--suffix{
  width: 70%;
  margin-top: 0px;
  padding-top: 0;
  padding-bottom: 0;
  text-align: left;
}
.el-input__icon.el-icon-circle-close.el-input__clear{
  color: #fff;

}
.btn {
  font-size: 14px;
  color: #ffffff;
  
  border-style: none;
  min-width: 150px;
  height: 54px;
  font-family: "Ubuntu", sans-serif;
  -webkit-border-radius: 0;
  -moz-border-radius: 0;
  -ms-border-radius: 0;
  border-radius: 8;
  font-size: 16px;
  -webkit-box-shadow: 0px 5px 30px rgba(0, 0, 0, 0.1);
  -moz-box-shadow: 0px 5px 30px rgba(0, 0, 0, 0.1);
  box-shadow: 0px 5px 30px rgba(0, 0, 0, 0.1);
  margin: 0; 
  text-align:center;
  overflow:hidden;
}
.btn-primary {
  background-color: #f7c35f;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  border-radius: 28px;
  margin: 0;
}
.btn-primary:hover {
  background-color: #255946;
  color: #f7c35f;
}
.el-input ::-webkit-input-placeholder {
  color: #fff;
}
.el-input .el-input__inner{
  margin-bottom: 0;
  margin-top: 10px;
}
</style>
<script>
//import $ from 'jquery'
export default {
  name: "SearchBoxM2",
  data() {
    return {
      input: "",
      list:[],
      returnlist:[],
      dis:true,
    };
  },
  computed: {
    hasNoData () {
      return !this.returnlist.length
    },
    },
  methods: {
    

    // 发送检索请求
    trans() {
      if(this.input=== ''){
        /*$(".btn btn-primary").addClass("huise")
        // 将鼠标设置为不可点击状态
        document.getElementById('searchclick').style.cursor = 'not-allowed'*/
        this.dis=true;
      }
      else{
      sessionStorage.clear();
      this.$forceUpdate();
      setTimeout(() => {
        this.$router.push("/MappingB" + "?query=" + this.input);
        //this.$router.push("/Products" + "?query=" + this.input + "&page=1" );
        //this.$router.push({ name:'Products'});
      }, 500);
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
      //this.$forceUpdate();
      }
    },
    setValueNull(){
      this.dis=true;
    },
    /*async transFromRealtime(item){
      await this.$router.push("/Origin" + "?query=" + item);
    },
    hideIcon(){
      let dom = '';
        dom = document.getElementsByClassName("el-icon-search")
      this.$nextTick(() => {
        dom.style.display="none";
        })
    }*/
  },
 
  
  watch: {
    input () {
      
      if (this.input!='')
      {
        //this.hideIcon();
        this.dis=false;
        /*$(".btn btn-primary").removeClass("huise") //移除不可点击状态
        document.getElementById('searchclick').style.cursor = 'pointer'*/
      }
      
    }
  }
};
</script>
<style lang="scss">
.create-isLoading {
.el-loading-text{
  color:#f7c35f;
  font-size: 18px;
}
i {
  color:#f7c35f;
}
 }
</style>