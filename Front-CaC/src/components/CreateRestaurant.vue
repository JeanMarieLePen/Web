<template>
    <div>
        <div class="container">

            <div style="margin-top: 20px" v-if="messages.errorName" class="alert alert-danger" v-html="messages.errorName"></div>
            <div style="margin-top: 20px" v-if="messages.errorType" class="alert alert-danger" v-html="messages.errorType"></div>
            <div style="margin-top: 20px" v-if="messages.errorLogo" class="alert alert-danger" v-html="messages.errorLogo"></div>
            <div style="margin-top: 20px" v-if="messages.errorManager" class="alert alert-danger" v-html="messages.errorManager"></div>

            <label class='label'>Naziv restorana:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unesite naziv restorana..."  v-model="newRestaurant.name">
            <label class='label'>Tip restorana:</label>
            <select style="padding:5px;" v-model="newRestaurant.type">
                <option disabled value="">Tip restorana</option>
                <option  v-bind:key="tipRestorana.naziv"  v-for="tipRestorana in tipoviRestorana">{{tipRestorana.naziv}}</option> 
            </select><br>
             <!-- <label class='label'>Lokacija restorana:</label> -->
            <!-- <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Odaberite tip ime menadzera..."  v-model="newRestaurant.tip"> -->
            <!-- <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unesite  lokaciju restorana..."  v-model="newRestaurant.lokacija"> -->
            <label class='label'>Logo:</label>
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0">
                    <span>
                        <label>Images</label>
                        <input type="file" name="file" class="filestyle" multiple @change="uploadImage"/>
                    </span>
            </div>
        </div>


            <!-- <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unesite pol menadzera..."  v-model="newRestaurant.logo"> -->
            
            <label class='label'>Menadzer:</label>
            <select  @change="addChoosenManager()" style="padding:5px;" v-model="newRestaurant.manager">
                <option disabled value="">Odabir slobodnih menadzera</option>
                <option v-bind:key="freeManager.username" v-for="freeManager in freeManagers">{{freeManager.username}}</option>
            </select>
            <!-- <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Odaberite slobodnog menadzera..."  v-model="newRestaurant.manager"> -->
            <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
            <div v-if='messages.errorResponse' class="alert alert-success" v-html="messages.errorResponse"></div>
            <button class="btn btn-success" v-on:click='createRestaurant()'>Potvrdi</button>
        </div>
    </div>
</template>


<script>

import dataService from '../services/DataService'

export default {
    data(){
        return {
            newRestaurant:{
                name:'',
                type:'',
                menuItems:null,
                opened:'',
                lokacija:null,
                images:[],
                comments:null,
                manager:'',
                ocena:''
            },
            tipoviRestorana:[
                {
                    naziv:'porodicni'
                },
                {
                    naziv: 'fastfood'
                },
                {
                    naziv: 'caffee'
                },
                {
                    naziv: 'poslasticarnica'
                }
            ],
            freeManagers:[],
            allManagers:[],
            messages:{
                successResponse:'',
                errorResponse:'',
                errorName:'',
                errorType:'',
                errorLogo:'',
                errorManager:''
            },
            restaurantsWithManager:[],
            
            
        }
            
    },
    methods:{
        addChoosenType:function(tipRestorana){
            this.newRestaurant.type = tipRestorana;
        },
        addChoosenManager:function(){
            for(let i = 0; i < this.freeManagers.length; i++){
                if(this.newRestaurant.manager == this.freeManagers[i].username){
                    this.newRestaurant.manager = this.freeManagers[i];
                    break;
                }
            }
            console.log("Za menadzera je postavljen: " + this.newRestaurant.manager.username);
        },
        getRestaurantsWithManagment:function(){
            dataService.getRestaurantsWithManager().then(response => {
                //dobijamo listu svih restorana koji imaju menadzera
                this.restaurantsWithManager = response.data;
                
                console.log("Restorani sa menadzerom: " + this.restaurantsWithManager.length);
                console.log("USERNAME JEDNOG OD MENADZERA: " + this.restaurantsWithManager[0].manager.name);
            })
        },
        getManagers:function(){
            console.log("GET ALL MANAGERS")
            dataService.getFreeManagers().then(response => {
                this.freeManagers = response.data;
                console.log("USERNAME JEDNOG OD MENADZERA 2: " + this.freeManagers[0].username);
                console.log("STIGLO SA BEKA:" + this.freeManagers[1].lastname);
                this.separateFreeManagers();
            })
        },
        separateFreeManagers(){
            //u tempManagers su privremeno smesteni SVI menadzeri iz baze
            // console.log("AAAAAAAAA")
            let tempManagers = this.freeManagers;
            this.freeManagers = [];
            // console.log("BROJ ELEMENATA U tempManagers: " + tempManagers.length);
            let menadzerImaZaduzenje = false;
            for(let i = 0; i < tempManagers.length; i++){
                for(let j = 0; j < this.restaurantsWithManager.length; j++){
                    if(tempManagers[i].username === this.restaurantsWithManager[j].manager.username){
                        console.log('uslo u if')
                        menadzerImaZaduzenje = true;
                        
                    }
                }
                
                if(menadzerImaZaduzenje === false){
                    // console.log("Ustanovljeno da menadzer: " + tempManagers[i].username + " ne radi u restoranu")
                    // console.log("DODAT JEDAN")
                    this.freeManagers.push(tempManagers[i]);
                }else{
                    menadzerImaZaduzenje = false;
                }
            }


        },
        getUsernames:function(){
            dataService.getAllManagers().then(response => {
                this.allManagers = response.data;
            })
        },
        createRestaurant:function(){
            if (this.newRestaurant.name == "") {
                this.messages.errorName = `<h4>Polje naziv restorana ne moze biti prazno!</h4>`;
                setTimeout(() => this.messages.errorName = '', 3000);
            }
            else if (this.newRestaurant.type == "") {
                this.messages.errorType = `<h4>Polje tip restorana ne moze biti prazno!</h4>`;
                setTimeout(() => this.messages.errorType = '', 3000);
            }
            else if (this.newRestaurant.images.length == 0) {
                this.messages.errorLogo= `<h4>Morate izabrati logo restorana!</h4>`;
                setTimeout(() => this.messages.errorLogo = '', 3000);
            }
            else if (this.newRestaurant.manager === "") {
                this.messages.errorManager = `<h4>Morate izabrati menadzera restorana!</h4>`;
                setTimeout(() => this.messages.errorManager = '', 3000);
            }

            else{
                console.log("NA BEK SE SALJE OBJEKAT newRestaurant: " + JSON.stringify(this.newRestaurant));
                dataService.addRestaurant(this.newRestaurant).then(response =>{
                    alert("Uspesno ste dodali restoran.");
                    this.$router.push("/home");
                });
            }

            
        },
        uploadImage:function(e){
            const reader = new FileReader();
            let image = e.target.files[0];
            console.log(image);
            reader.readAsDataURL(image);
            reader.onload = () => {
                console.log("uslo u onload")
                this.newRestaurant.images.push(reader.result);
                console.log("Broj slika: " + this.newRestaurant.images.length)
            }
            
        }
    },
    created(){
        if(JSON.parse(localStorage.getItem('token')) == null){
            this.$router.push(`/login`);
        }else{
            this.getRestaurantsWithManagment();
            this.getManagers();
        }
        
    }
    
}
</script>