import Vue from 'vue'

import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'

import App from './App.vue'
import VueRouter from 'vue-router'
import Routes from './routes'
import * as VueGoogleMaps from 'vue2-google-maps'
// import Cors from 'cors'


export const bus = new Vue();

Vue.use(VueRouter)
Vue.use(VueGoogleMaps, {
  load:{
    // key:'AIzaSyC1mmG4iL9qR_o0G03QY8F9l60CMZKXm10',
    key:'',
    libraries: 'places'
  },
})
// Vue.use(Cors)
Vue.config.productionTip = false;

const router = new VueRouter({
  mode:'history',
  routes:Routes
});

new Vue({
  render: h => h(App),
  router:router
}).$mount('#app')
