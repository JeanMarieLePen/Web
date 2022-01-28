<template>
<div id="user-profile">
    <div>
        <div class="container" id='page-title'>
            <h1 style="margin-top:10px;color:#35424a;">Profil <span id='titleEffect'>Usera</span></h1>
            <hr style='background:#35424a;height:1px;'>
        </div>
        <div class="container">
            <form>
                <ul id="services" class="list-group">
                <!-- Osoba -->
                    <li class="list-group-item" >
                        <h5 class="header5">Korisnicko ime</h5>
                        <h4 v-show="isDeliveryMan">{{profileDeliveryMan.username}}</h4>
                        <h4 v-show="isManager">{{profileManager.username}}</h4>
                        <h4 v-show="isAdmin">{{profileAdmin.username}}</h4>
                        <h4 v-show="isCustomer">{{profileCustomer.username}}</h4>
                    </li>
                    <li class="list-group-item" >
                        <h5 class="header5">Ime</h5>
                        <h4 v-show="isDeliveryMan">{{profileDeliveryMan.name}}</h4>
                        <h4 v-show="isManager">{{profileManager.name}}</h4>
                        <h4 v-show="isAdmin">{{profileAdmin.name}}</h4>
                        <h4 v-show="isCustomer">{{profileCustomer.name}}</h4>
                    </li>

                    <li class="list-group-item" >
                        <h5 class="header5">Prezime</h5>
                        <h4 v-show="isDeliveryMan">{{profileDeliveryMan.lastname}}</h4>
                        <h4 v-show="isManager">{{profileManager.lastname}}</h4>
                        <h4 v-show="isAdmin">{{profileAdmin.lastname}}</h4>
                        <h4 v-show="isCustomer">{{profileCustomer.lastname}}</h4>
                    </li>

                    <li class="list-group-item" >
                        <h5 class="header5">Pol</h5>
                        <h4 v-show="isDeliveryMan">{{profileDeliveryMan.gender}}</h4>
                        <h4 v-show="isManager">{{profileManager.gender}}</h4>
                        <h4 v-show="isAdmin">{{profileAdmin.gender}}</h4>
                        <h4 v-show="isCustomer">{{profileCustomer.gender}}</h4>
                    </li>

                    <li class="list-group-item" >
                        <h5 class="header5">Datum rodjenja</h5>
                        <h4 v-show="isDeliveryMan">{{profileDeliveryMan.dateOfBirth}}</h4>
                        <h4 v-show="isManager">{{profileManager.dateOfBirth}}</h4>
                        <h4 v-show="isAdmin">{{profileAdmin.dateOfBirth}}</h4>
                        <h4 v-show="isCustomer">{{profileCustomer.dateOfBirth}}</h4>
                    </li>
                </ul>

                    <div id='buttonUpdate'>
                    <button v-show="isDeliveryMan" type='button' class="btn btn-lg btn-success marg" v-on:click='updateUser(profileDeliveryMan.username)'> Update</button> 
                    <button v-show="isAdmin" type='button' class="btn btn-lg btn-success marg" v-on:click='updateUser(profileAdmin.username)'> Update</button> 
                    <button v-show="isManager" type='button' class="btn btn-lg btn-success marg" v-on:click='updateUser(profileManager.username)'> Update</button> 
                    <button v-show="isCustomer" type='button' class="btn btn-lg btn-success marg" v-on:click='updateUser(profileCustomer.username)'> Update</button> 
                </div>
            </form>
        </div>
    </div>
</div>
</template>
<script>

import dataService from '../services/DataService'
export default {
    data(){
        return{
            //ako je dostavljac
            profileDeliveryMan:{
                id:'',
                username:'',
                name:'',
                lastname:'',
                password:'',
                gender:'',
                dateOfBirth:'',
                listOfActiveOrders:[],
                listOfAllOrders:[],
            },
            //ako je menadzer
            profileManager: {
                id:'',
                username:'',
                name:'',
                lastname:'',
                password:'',
                gender:'',
                dateOfBirth:'',
                restaurant:''
            },
            //ako je Kupac
            profileCustomer:{
                id:'',
                username:'',
                name:'',
                lastname:'',
                password:'',
                gender:'',
                dateOfBirth:'',
                listOfAllOrders:null,
                cart:null,
                numberOfPoints:0,
                typeOfCustomer:''
            },
            //ako je admin
            profileAdmin:{
                id:'',
                username:'',
                name:'',
                lastname:'',
                password:'',
                gender:'',
                dateOfBirth:''
            },

            //utvrdjivanje uloge Usera
            isDeliveryMan:false,
            isAdmin:false,
            isManager:false,
            isCustomer:false
        }
    },
    methods:{
        getUserProfileData(id){
            let parsToken = JSON.parse(localStorage.getItem('token'));
            if(parsToken.role === "admin"){
                dataService.getAdmin(id).then(response => {
                    this.profileAdmin = response.data;
                })
            }
            if(parsToken.role === "delivery_man"){
                dataService.getDeliveryMan(id).then(response => {
                    
                    this.profileDeliveryMan = response.data;
                })
            }
            if(parsToken.role === "manager"){
                dataService.getManager(id).then(response => {
                    this.profileManager = response.data;
                })
            }
            if(parsToken.role === "customer"){
                dataService.getCustomer(id).then(response => {
                    this.profileCustomer = response.data;
                })
            }
        },
        updateUser(id){ 
            this.$router.push(`/profile/${id}/update`);
        },
        
    },
    computed:{
        // id() {
        //     return this.$route.params.id; //preuzimam id usera na cijoj sam stranici za prikaz profila
        // },
    },

    created(){
        if(JSON.parse(localStorage.getItem('token')) == null){
            this.$router.push(`/login`);
        }else{
            let parsToken = JSON.parse(localStorage.getItem('token'));
            console.log("TOKEN PROCITAN IZ LOCALSTORAGE-a: " + localStorage.getItem('token'));
            if(parsToken.role === "admin"){
                this.isAdmin = true;
            }
            if(parsToken.role === "delivery_man"){
                this.isDeliveryMan = true;
            }
            if(parsToken.role === "manager"){
                this.isManager = true;
            }
            if(parsToken.role === "customer"){
                this.isCustomer = true;
            }

            this.getUserProfileData(parsToken.username);
        }
    },

}
</script>

<style scoped>

#titleEffect{
  color:gold;
  font-weight: bold;
}

#user-profile ul#services li{
    list-style: none;
    padding:15px;
    border-bottom: gray solid 1px;
    border-radius: 15px;
    margin-bottom: 3px; 
    background-color:#fff;
    width:90%;
}

#user-profile#buttonUpdate{
  float:left;
}

.header5{
    color:gold;
    font-weight: bold;
}

.marg{
    margin-top:15px;
}

</style>