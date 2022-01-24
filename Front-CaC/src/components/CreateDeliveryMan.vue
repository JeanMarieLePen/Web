<template>
    <div>
        <div class="container">
            <label class='label'>Korisnicko ime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite korisnicko ime..."  v-model="newDeliveryMan.username">
            <label class='label'>Ime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite ime dostavljaca..."  v-model="newDeliveryMan.name">
            <label class='label'>Prezime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite  prezime dostavljaca..."  v-model="newDeliveryMan.lastname">
            <label class='label'>Lozinka:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite pol dostavljaca..."  v-model="newDeliveryMan.password">
            <label style="padding:5px;" class='label'>Pol</label>
            <span class="col-xl-3 col-md-6 mb-1">
                <select style="padding:5px;" v-model="newDeliveryMan.gender">
                    <option disabled value="">Odabir pola</option>
                    <option v-bind:key="polTemp.naziv" v-for="polTemp in polovi">{{polTemp.naziv}}</option>
                </select>
            </span>
            
            <label style="padding:5px;" class='label'>Datum rodjenja:</label>
            <vuejsDatepicker style="padding:5px;" placeholder="Odaberite datum rodjenja" v-model="newDeliveryMan.dateOfBirth">
            </vuejsDatepicker>
            
            
            <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
            <div v-if='messages.errorResponse' class="alert alert-success" v-html="messages.errorResponse"></div>
            <button class="btn btn-success" v-on:click='createDeliveryMan()'>Potvrdi</button>
        </div>
    </div>
</template>

<script>
import dataService from '../services/DataService'
import Datepicker from 'vuejs-datepicker'

export default {
    data(){
        return{
            newDeliveryMan:{
                username:'',
                name:'',
                lastname:'',
                password:'',
                gender:'',
                dateOfBirth:''              
            },
            messages:{
                successResponse:'',
                errorResponse:'',
            },
            polovi:[
                {naziv:'musko'},
                {naziv:'zensko'}
            ]
        }
    },
    methods:{
        createDeliveryMan:function(){
            console.log("Kreiranje dostavljaca: " + this.newDeliveryMan.username);
            dataService.addDeliveryMan(this.newDeliveryMan).then(response => {
                console.log("Stigao odgovor sa beka: " + response);
            }).catch(error => {
                if(error.response.status === 500 || error.response.status === 404){
                        this.messages.errorResponse= `<h4>We had some server errors, please try again later!</h4>`;
                        setTimeout(() => this.messages.errorResponse='', 5000);
                }
            })
        }
    },
    created(){
        // if(JSON.parse(localStorage.getItem('token')) == null){
        //     this.$router.push(`/login`);
        // }else{

        // }
      }
}

</script>


<style scoped>


</style>