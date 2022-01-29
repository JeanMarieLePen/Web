<template>
<div id="home">
    <div class="container">
        <h1 style="margin-top:10px;color:#35424a;">Welcome <span id='titleEffect'>{{user.username}}</span>!</h1>
        <hr style='background:#35424a;height:1px;'>
    </div>

    <!-- <div style='font-size:25px;'>
        <label>User</label>
        <switches v-model="mode"  theme="bulma" color="green"></switches>
        
        <label>Agent</label>
    </div> -->

    <!-- Page Content -->
    <!-- <div class="container" v-show=!mode> -->
    <div class="container">
        <div class="row"> 

            <!-- Profile-->
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0 shadow">
                    <router-link style='text-decoration: none;color:#35424a;' to="/profile" class="nav-link" exact>
                        <img src="../assets/dadada.png" class="card-img-top" alt="...">
                        <div class="card-body text-center">
                            <h5 class="card-title mb-0">Moj Profil</h5>
                        </div>
                    </router-link>
                </div>
            </div>

            <div v-show='isAdmin' class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0 shadow">
                    <router-link style='text-decoration: none;color:#35424a;' to="/newManager" class="nav-link"
                        exact>
                        <img src="../assets/manager.jpg" class="card-img-top" alt="...">
                        <div class="card-body text-center">
                            <h5 class="card-title mb-0">Kreiranje novog menadžera</h5>
                            
                        </div>
                    </router-link>
                </div>
            </div>
            <div v-show='isAdmin' class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0 shadow">
                    <router-link style='text-decoration: none;color:#35424a;' to="/newRestaurant" class="nav-link"
                        exact>
                        <img src="../assets/restau.jpg" class="card-img-top" alt="...">
                        <div class="card-body text-center">
                            <h5 class="card-title mb-0">Dodaj novi restoran</h5>
                            
                        </div>
                    </router-link>
                </div>
            </div>
            <div v-show='isAdmin' class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0 shadow">
                    <router-link style='text-decoration: none;color:#35424a;' to="/newDeliveryMan" class="nav-link"
                        exact>
                        <img src="../assets/delivery.png" class="card-img-top" alt="...">
                        <div class="card-body text-center">
                            <h5 class="card-title mb-0">Dodaj novog dostavljača</h5>
                           
                        </div>
                    </router-link>
                </div>
            </div>
            <div v-show="isCustomer" class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0 shadow">
                    <router-link style='text-decoration: none;color:#35424a;' to="/searchRestaurants" class="nav-link"
                        exact>
                        <img src="../assets/search.png" class="card-img-top" alt="...">
                        <div class="card-body text-center">
                            <h5 class="card-title mb-0">Pretraga restorana</h5>
                           
                        </div>
                    </router-link>
                </div>
            </div>
            
            
            <div v-show="isManager" class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0 shadow">
                    <router-link style='text-decoration: none;color:#35424a;' to="/newProduct" class="nav-link"
                        exact>
                        <img src="../assets/search.png" class="card-img-top" alt="...">
                        <div class="card-body text-center">
                            <h5 class="card-title mb-0">Kreiraj artikal</h5>
                           
                        </div>
                    </router-link>
                </div>
            </div>

            
            <div v-show="isManager" class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0 shadow">
                    <router-link style='text-decoration: none;color:#35424a;' to="/moderation" class="nav-link"
                        exact>
                        <img src="../assets/search.png" class="card-img-top" alt="...">
                        <div class="card-body text-center">
                            <h5 class="card-title mb-0">Moderacija komentara</h5>
                        </div>
                    </router-link>
                </div>
            </div>

            <div v-show='isCustomer' class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0 shadow">
                    <router-link style='text-decoration: none;color:#35424a;' to="/ordersCustomer" class="nav-link"
                        exact>
                        <img src="../assets/orderrr.png" class="card-img-top" alt="...">
                        <div class="card-body text-center">
                            <h5 class="card-title mb-0">Pregledajte svoje porudzbine</h5>
                            
                        </div>
                    </router-link>
                </div>
            </div>

            <div v-show="isManager" class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0 shadow">
                    <router-link style='text-decoration: none;color:#35424a;' to="/ordersManager" class="nav-link"
                        exact>
                        <img src="../assets/orderrr.png" class="card-img-top" alt="...">
                        <div class="card-body text-center">
                            <h5 class="card-title mb-0">Pregledajte sve porudzbine za vas restoran</h5>
                           
                        </div>
                    </router-link>
                </div>
            
            </div>

             <div v-show="isDeliveryMan" class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0 shadow">
                    <router-link style='text-decoration: none;color:#35424a;' to="/ordersDeliveryMan" class="nav-link"
                        exact>
                        <img src="../assets/orderrr.png" class="card-img-top" alt="...">
                        <div class="card-body text-center">
                            <h5 class="card-title mb-0">Pregledajte porudzbine za koje ste zaduzeni</h5>
                           
                        </div>
                    </router-link>
                </div>
            
            </div>
        </div><!-- /.row -->
    </div>

    
</div>
</template>

<script>
import axios from 'axios'
// import Switches from 'vue-switches'

export default {
    components:{
        // Switches
    },
    data(){
        return{
            user:{
                username:''
            },
            mode: false,
            isAdmin: false,
            isCustomer: true,
            isDeliveryMan: false,
            isManager: false,
            role: ''
        }
    },
    methods:{
        parseJwt:function(token) {
            var base64Url = token.split('.')[1];
            var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));

            return JSON.parse(jsonPayload);
        }
    },
    created(){

       // let parsToken = this.parseJwt(localStorage.getItem('token'));
        //console.log("ISPIS TOKENA:" + JSON.stringify(parsToken))
        //localStorage.setItem('parsToken', JSON.stringify(parsToken));
        let temp = JSON.parse(localStorage.getItem('token'));
        if(temp.role ==='admin')
        {
            this.isAdmin=true;
            this.role='admin';
        }
        if(temp.role==='manager')
        {
            this.isManager=true;
            this.role='manager';
        }
        if(temp.role==='customer')
        {
            this.isCustomer=true;
            this.role='customer';
        }
        if(temp.role==='deliveryman')
        {
            this.isDeliveryMan=true;
            this.role='deliveryman';
        }       
    },
}
</script>

<style scoped>

#titleEffect{
  color:gold;
  font-weight: bold;
}


</style>