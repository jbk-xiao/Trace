<template>
    <div>
        <div id="tqmain" ref="chart"></div>
    </div>
</template>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
    import {graph} from '../assets/js/RelationChart2.js'
    export default {
        props: ['jsonObj'],
        created() {
            this.$nextTick(() => {
                //console.log(this.jsonObj)
                setTimeout(() => {
                        this.graph(this.jsonObj)
                        //this.graph()
                    }, 1500)
                
            })
        },
        methods: {
            graph
        }
    }
</script>

<style scoped>
    #tqmain {
        width: 80%;
        height: 800px;
        border: 1px solid #EBEEF5;
        margin: 0 auto;
        margin-top: 30px;
    }
</style>