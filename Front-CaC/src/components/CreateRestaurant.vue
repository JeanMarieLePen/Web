<template>
    <div>
        <div class="container">
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
                type:''
            },
            // newRestaurant:{
            //     name:'',
            //     type:'',
            //     menuItems:null,
            //     opened:true,
            //     lokacija:null,
            //     images:null,
            //     comments:null,
            //     manager:null,
            //     ocena:0
            // },
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
            }
            
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
        getManagers:function(){
            console.log("GET FREE MANAGERS")
            dataService.getFreeManagers().then(response => {
                this.freeManagers = response.data;
                console.log("STIGLO SA BEKA:" + this.freeManagers[1].lastname);
            })
        },
        getUsernames:function(){
            dataService.getAllManagers().then(response => {
                this.allManagers = response.data;
            })
        },
        createRestaurant:function(){
            console.log("NA BEK SE SALJE OBJEKAT newRestaurant: " + JSON.stringify(this.newRestaurant));
            dataService.addRestaurant(this.newRestaurant).then(response =>{
            });
        },
        uploadImage:function(e){
            const reader = new FileReader();
            let image = e.target.files;
            console.log(image);
            reader.readAsDataURL(image);
            reader.onload = () => {
                this.newRestaurant.images.push(reader.result);
            }

        }
    },
    created(){
        this.getManagers();
        
    }
    
}
</script>