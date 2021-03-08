<template>
    <div>
        <div id="lgmmain" ref="chart"></div>
    </div>
</template>

<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
    
    import {graph} from '../assets/js/RelationChart1.js'
    export default {
        props: ['jsonObj'],
        created() {
            this.$nextTick(() => {
                
                setTimeout(() => {
                        this.graph(this.jsonObj)
                        //this.graph()
                    }, 1500)
                //console.log(this.jsonObj)
            })
        },
        methods: {
            graph,
            
            //alert(jsonObj)
        }
    }
</script>


<style scoped>
    #lgmmain {
        width: 80%px;
        height: 800px;
        border: 1px solid #EBEEF5;
        margin: 0 auto;
        margin-top: 30px;
    }
</style>