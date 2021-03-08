import Vue from 'vue'
import VueRouter from 'vue-router'
/*import Products from '../views/SearchProducts.vue'
import Origin from '../views/Origin.vue'*/
import LgmCPs from '../views/LgmCompetitiveProducts.vue'
/*import TqCPs from '../views/TqCompetitiveProducts.vue'
import Style from '../views/Style.vue'
import Contact from '../views/Contact.vue'*/

Vue.use(VueRouter)

let router = new VueRouter({
routes :[
  {
    path: '/',
    name: 'Index',
    component: () => import(/* webpackChunkName: "Index" */ '@/views/Index'),
    meta: { title: "首页" }
  },
  {
    path: '/Index',
    name: 'Index',
    component: () => import(/* webpackChunkName: "Index" */ '@/views/Index'),
    meta: { title: "首页" }
  },
  {
    path: '/Origin',
    name: 'Origin',
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/Origin'),
    meta: { title: "产品溯源" }
  },
  {
    path: '/LgmCPs',
    name: 'LgmCPs',
    component: LgmCPs,
    props: true,
    //component: () => import(/* webpackChunkName: "Index" */ '@/views/LgmCompetitiveProducts'),
    meta: { title: "老干妈" }
  },
  {
    path: '/TqCPs',
    name: 'TqCPs',
    //component: TqCPs
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/TqCompetitiveProducts'),
    meta: { title: "泰奇" }
  },
  {
    path: '/Products',
    name: 'Products',
    //component: Products
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/SearchProducts'),
    meta: { title: "产品搜索" }
  },
  {
    path: '/RealTimeVideo',
    name: 'RealTimeVideo',
    //component: Products
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/RealTimeVideo'),
    meta: { title: "实时视频" }
  },
  {
    path: '/Style',
    name: 'Style',
    //component: Style
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/Style'),
    meta: { title: "信息实例" }
  },
  {
    path: '/Contact',
    name: 'Contact',
    //component: Contact
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/Contact'),
    meta: { title: "联系我们" }
  },
  {
    path: '/MSearch',
    name: 'MSearch',
    //component: Contact
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/MSearch'),
    meta: { title: "知识图谱搜索" }
  },
  {
    path: '/MappingK',
    name: 'MappingK',
    //component: Contact
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/MappingK'),
    meta: { title: "品类知识图谱" }
  },
  {
    path: '/MappingB',
    name: 'MappingB',
    //component: Contact
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/MappingB'),
    meta: { title: "品牌知识图谱" }
  },
  {
    path: '/Detail',
    name: 'Detail',
    //component: Contact
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/Detail'),
    meta: { title: "老干妈详细信息" }
  },
  {
    path: '/Detail2',
    name: 'Detail2',
    //component: Contact
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/Detail2'),
    meta: { title: "泰奇详细信息" }
  },
  {
    path: '/TInfoEntry',
    name: 'TInfoEntry',
    //component: Contact
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/TraceabilityInfoEntry'),
    meta: { title: "溯源信息录入" }
  },
  {
    path: '/AddProcess/:id',
    name: 'AddProcess',
    //component: Contact
    props: true,
    component: () => import(/* webpackChunkName: "Index" */ '@/views/AddProcess'),
    meta: { title: "流程信息录入" }
  }
],
scrollBehavior() {
    return { x: 0, y: 0 };
},
})

router.beforeEach((to, from, next) => {
  // to and from are both route objects. must call `next`.
  if (to.meta.title)
      document.title = to.meta.title
  next()
})


export default router
