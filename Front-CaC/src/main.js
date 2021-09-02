import Vue from 'vue'

import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'

import App from './App.vue'
import VueRouter from 'vue-router'
import Routes from './routes'
// import Cors from 'cors'


export const bus = new Vue();

Vue.use(VueRouter)
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
