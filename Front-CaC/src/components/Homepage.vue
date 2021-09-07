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
                        <img src="../assets/logo.png" class="card-img-top" alt="...">
                        <div class="card-body text-center">
                            <h5 class="card-title mb-0">Profil</h5>
                            <div class="card-text text-black-50">Pogledajte i izmenite vaše lične podatke na profilu...</div>
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
            mode: false
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
        let parsToken = this.parseJwt(localStorage.getItem('token'));
        localStorage.setItem('parsToken', JSON.stringify(parsToken));
        this.user.username = parsToken.username;
    },
}
</script>

<style scoped>

#titleEffect{
  color:gold;
  font-weight: bold;
}


</style>