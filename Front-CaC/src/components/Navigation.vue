<template>
  <div id="navigation">
  <nav class="navbar navbar-expand-lg navbar-dark static-top">
      <div class="container">
        <a class="navbar-brand" href="/"><h1><span id='titleEffect'>Cash n' Curry</span> App</h1></a>
        <!-- <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button> -->
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
            
            <li v-if='loggedIn==false' class="nav-item active">
              <router-link to='/' class="nav-link" exact> Home
                <span class="sr-only">(current)</span>
              </router-link>
            </li>
             <li v-if='loggedIn==true' class="nav-item active">
              <router-link to='/home' class="nav-link" exact> Home
                <span class="sr-only">(current)</span>
              </router-link>
            </li>
            <li class="nav-item">
              <router-link to='/about' class="nav-link" exact>About</router-link>
            </li>
          </ul>
          
          <router-link to='/login' class="nav-link" exact> <button v-show="loggedIn==false" class="btn" id='btnLogin'>Log In</button> </router-link>
          
          <button  class="btn" v-show="loggedIn==true" id='btnLogout' v-on:click='logOut()'>Log Out</button> 
          

          <router-link to='/shoppingCart' class="nav-link" exact v-if='loggedIn==true' > 
            <span>{{numberOfItems}}</span>
            <button style="background" class="btn btn-primary">
               <!-- <img src="/src/assets/shopping-cart.png" style="float:left;margin-right:0.5em; height:20px;width:40px;"> -->
              <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
                <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
              </svg>
              
              </button> 

          </router-link>
          <!-- <div class="nav__cart">
            <button>
              <i class="fas fa-shopping-cart"></i>
              
            </button>
            <span class="total-quantity">0</span>
            <div class="cart-dropdown">
              <ul class="cart-dropdown__list">
                <li>Product</li>
              </ul>
            </div>
          </div> -->

          <!-- <router-link to='/searchRestaurants' class="nav-link" exact>
            <button class="btn" id="btnLogin">Kreiraj korisnika</button>
          </router-link> -->
        </div>
      </div>
    </nav> 

  </div>
</template>

<script> 
import axios from 'axios'
import { bus } from '../main'; 

export default {
  name: 'Navigation',
  components: {
  },
  mounted() {
    window.addEventListener('shoppingList-changed', (event) => {
      this.lista = event.detail.storage;
    });
  },

  data:function(){
    return{
      loggedIn: localStorage.getItem('token') ? true : false,
      // brojStavki : 0,
      lista : [],
    }
  },
  computed:{
    numberOfItems(){
      // var lista = JSON.parse(localStorage.getItem('shoppingList')) || []
      // console.log('broj u listi: ' + lista.length)
      // return lista.length;
      let tempLista = JSON.parse(this.lista);
      let listaTemp = [];
      for(let i = 0; i < tempLista.length; i++){
        for(let j = 0; j < tempLista[i].listOfProducts.length; j++){
          listaTemp.push(tempLista[i].listOfProducts[j]);
        }
      }
      let brojStavki = listaTemp.length;
      // this.brojStavki = lista.length;
      console.log("broj artikala: " + this.brojStavki)
      return brojStavki;
    }
  },
  methods:{
    onOver() {
        this.$refs.dropdown.visible = true;
      },
      onLeave() {
        this.$refs.dropdown.visible = false;
      },
    logOut:function(){
      if(confirm('Da li ste sigurni da se zelite izlogovati?')){
        if(localStorage.getItem('token')){
          localStorage.removeItem('token');
          localStorage.removeItem('parsToken');
          axios.defaults.headers.common['Authorization'] = undefined;
  
          this.loggedIn = false;  //logoutovan je treba da pise log in dugme
          this.$router.push('/');
        }
      }
    },
  },
  created(){
    //Prilikom created osluskujem da li je doslo do eventa login i ako jeste kazem da je loginova;
     bus.$on('loggedIn',(data)=>{
       this.loggedIn = data;
     });
  }
  
}
</script>

<style scoped>
#navigation{
  background-color:#35424a;
  color:#fff;
  padding-top:30px;
  min-height: 70px;
  border-bottom:gold 3px solid;
} 
/* Ostaviti za sada ako ne obrisati */
ul{
  list-style-type: none;
  text-align: center;
  margin:0;
}
li{
  display:inline-block;
  margin: 0;
}

a{
  color:#fff;
  text-decoration: none;
  padding:6px 8px;
  border-radius: 10px;
}

#btnLogin{
  color:#fff;
  border:none;
  padding: 0 20px;
}
#btnLogin{
  height:38px;
  background-color:gold;
  color:#fff;
  border:none;
  padding: 0 20px;
}

#btnLogin:hover{
  background-color:#cccccc;
  color:#fff;
  font-weight: bold;
}

#btnLogout{
  height:38px;
  background-color:gold;
  color:#fff;
  border:none;
  padding: 0 20px;
}

#btnLogout:hover{
  background-color:#cccccc;
  color:#fff;
  font-weight: bold;
}

#titleEffect{
  /* color:#9400D3;FFD300 */
  color:gold;
  font-weight: bold;
}

.router-link-active{
  border-bottom: #fff solid 1px;
  font-weight: bold;
}
</style>
