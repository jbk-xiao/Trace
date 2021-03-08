<template>
    <div>
        <div id="main"></div>
    </div>
</template>

<script>
    import {graph} from '../assets/js/GraphPath.js'
    import 'echarts/extension/bmap/bmap'
    //import { MP } from '../assets/js/map.js'
    //import axios from 'axios'
    export default {
        name:'PathG',
        props: ['jsonObj'],
        /*data() {
            return {
                //info: {},
                location: {},
                //process:{},
            };
        },*/
        mounted() {
            this.$nextTick(() => {
　　            //MP("BeXrLRHFruh6pjiXNq3LachO72AjHsAe").then(() => {
   　　             setTimeout(() => {
                        this.graph(this.jsonObj)
                        //this.graph()
                    }, 1000)
   　//})
})
            
        },
        methods: {
            graph,
            
            /*async getpath(){
                let{data}= await axios.get("http://121.46.19.26:8511/getOrigin/16119701634150000");
                if(data){
                this.location=data[0].process;
                }
                //this.process=this.location.process;
                //alert(this.location)
                console(data)
            }*/
        },
        /*created(){
            this.getpath();
        }*/
    }
</script>


<style scoped>
    #main {
        width: 100%;
        height: 600px;
        border: 1px solid #EBEEF5;
        margin: 0 auto;
        margin-top: 30px;
    }
</style>