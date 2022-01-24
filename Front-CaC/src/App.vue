<template>
  <div id="app">
    <app-nav></app-nav>
    <router-view></router-view>
    <app-footer></app-footer>
  </div>
</template>




<script>


import Footer from './components/Footer'
import Navigation from './components/Navigation'
import axios from "axios"


export default {
  name: 'app',
  components: {
    'app-footer':Footer,
    'app-nav':Navigation
  },
  data () {
    return {
      retrievedToken:null,
    }
  },
  methods:{
    retrieveTokenFromLocalStorage(){
      if(localStorage.getItem('token')){
        this.retrievedToken = JSON.parse(localStorage.getItem('token'));
        axios.defaults.headers.common['Authorization'] = 'Bearer' + this.retrievedToken.accessToken;
      }
    }
  }
}
</script>

<style>

</style>
