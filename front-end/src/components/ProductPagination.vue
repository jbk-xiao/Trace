<template>
  <div class="pagination">
    
    <el-pagination
      
      ref="changepage"
      :page-size="20"
      :pager-count="11"
      layout="prev, pager, next"
      :total="20*pagecount"
      :current-page="this.page"
      @current-change="currentChange"
      @prev-click="prevpage"
      @next-click="nextpage"
    ></el-pagination>
  </div>
</template>

<style>
.pagination{
    
    width: 380px;
    margin: 0 auto;
}
.el-pagination.is-background{
  font: 200;
  font-size: 20px;
  margin-top: 20px;
}
.el-pagination.is-background .el-pager.active {
     background-color: #f7c35f;
     color: #FFF;
}

.el-pager li{
    background:#f7c35f;
    font: 20px;
}
.el-pager li #text{
  font: 20px;
}
.el-pager li.active {
  color:  #f7c35f;
  cursor: default;
  background-color: #255946;
}
.el-pager li.number.active{
  background: #255946;
}

.el-pager li.number:hover{
  color:#255946;
}
.el-pagination.is-background button:hover{
  color: #255946;
}
.el-pagination.is-background .el-pager li:not(.disabled):hover {
  color: #255946;
}
.el-pagination.is-background .el-pager.number{
  font-size: 20px;
}
</style>

<script>
export default {
  props: ['pagecount'],
  data() {
    return {
      page: parseInt(this.$route.query.page),
    };
  },
  methods: {
    //给sessionStorage存值
setContextData: function(key, value) { 
	if(typeof value == "string"){
		sessionStorage.setItem(key, value);
	}else{
		sessionStorage.setItem(key, JSON.stringify(value));
	}
},
// 从sessionStorage取值
getContextData: function(key){
	const str = sessionStorage.getItem(key);
	if( typeof str == "string" ){
		try{
			return JSON.parse(str);
		}catch(e) {
			return str;
		}
	}
	return;
},

    currentChange(val) {
      this.page = val;
      //alert(val);
      this.setContextData("currentPage",this.page)
      this.$parent.point(val);
    },
    prevpage(val) {
      this.page = val;
      //console.log(val);
      this.$parent.point(val);
    },
    nextpage(val) {
      this.page = val;
      // console.log(val);
      this.$parent.point(val);
    },
  },

};
</script>

