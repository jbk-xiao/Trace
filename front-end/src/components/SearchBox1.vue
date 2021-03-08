<template>
  <!-- 检索框 -->
<div >
  <div class="blog-sidebar__search">
                                <form class="form" >
                                    <el-input type="text" placeholder="请输入溯源码"
                                    v-model="input"
                                    @keyup.enter.native="trans"
                                    clearable @clear="setValueNull"
                                    ></el-input>
                                    <button type="submit" v-bind:disabled="dis" @click="trans" id="searchclick" @click.prevent="trans"><i class="agrikon-icon-magnifying-glass"></i></button>
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
.form{
  width: 100%;
  height: 60px;
  margin: 0 auto;
}
.search-content{
  margin-top:-40px;
  padding:0;
  width:60%;
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
  padding-bottom: 15px;
  padding-top: 0px;
  padding-left: 10px;
  padding-right: 30px;
  border: none;
  border-radius: 28px;
  outline: none;
  float:left;
  margin:0;
  text-align: left;
}
.blog-sidebar__search{
  width: 100%;
  height: 180px;
}
form.form{
  height: 60px;
}
.el-input--suffix{
  width: 70%;
  margin-top: 0px;
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
  margin-top: 0px;
}
</style>

<script>
//import $ from 'jquery'
import axios from 'axios'
export default {
  name: "SearchBox1",
  data() {
    return {
      input: "",
      list:[],
      returnlist:[],
      dis:true,
      
    };
  },

  methods: {
    /*async realtimeSearch() {
      let {data}  = await axios.get(
        'http://121.46.19.26:8511/getOrigin/'+this.input
      );
      this.list=data;
       console.log(this.list);
    },*/

    // 发送检索请求
    async trans() {
      let {data}  = await axios.get(
        'http://121.46.19.26:8511/getOrigin/'+this.input
      );
      this.list=data;
       //console.log(this.list);
      if(this.input=== ''){
        /*$(".btn btn-primary").addClass("huise")
        // 将鼠标设置为不可点击状态
        document.getElementById('searchclick').style.cursor = 'not-allowed'*/
        this.dis=true;
      }
      else if(this.list[0].category==undefined){
      setTimeout(() => {
        //this.$router.push("/Origin" + "?query=" + this.input);
        //this.$router.push({ name:'Home',params:{ type:1 } })
        this.$router.push({ name:'Style'});
      }, 200);
      }
      else{
        setTimeout(() => {
        //this.$router.push("/Origin" + "?query=" + this.input);
        //this.$router.push({ name:'Home',params:{ type:1 } })
        this.$router.push("/Origin"+ "?query=" + this.input);
      }, 100);
      this.returnlist=[];
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
      }
    },
    setValueNull(){
      this.dis=true;
    },
    /*async transFromRealtime(item){
      await this.$router.push("/Origin" + "?query=" + item);
    },*/
    /*hideIcon(){
      let dom = '';
        dom = document.getElementsByClassName("el-icon-search")
      this.$nextTick(() => {
        dom.style.display="none";
        })
    }*/
  },
 mounted(){
    //this.realtimeSearch();
  },
  
  watch: {
    input () {
      if (this.timer) {
        clearTimeout(this.timer)
      }
      if (this.input!='')
      {
        //this.hideIcon();
        this.dis=false;
        /*$(".btn btn-primary").removeClass("huise") //移除不可点击状态
        document.getElementById('searchclick').style.cursor = 'pointer'*/
      }
      //alert(this.list)
      this.timer = setTimeout(() => {
        
        for (let i in this.list) {
           
            if (this.input==this.list[i].id ) {
              this.returnlist.push(this.list[i])
            }
        }
        
      }, 100)
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