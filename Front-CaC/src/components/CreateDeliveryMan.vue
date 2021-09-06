<template>
    <div>
        <div class="container">
            <label class='label'>Korisnicko ime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite korisnicko ime..."  v-model="newCustomer.username">
            <label class='label'>Ime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite ime dostavljaca..."  v-model="newCustomer.name">
            <label class='label'>Prezime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite  prezime dostavljaca..."  v-model="newCustomer.lastname">
            <label class='label'>Pol:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite pol dostavljaca..."  v-model="newCustomer.password">
            <label class='label'>Datum rodjenja:</label>
            <!-- <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite datum rodjenja dostavljaca..."  v-model="newCustomer.password"> -->
            <vuejsDatepicker placeholder="Odaberite datum rodjenja" v-model="newManager.dateOfBirth"></vuejsDatepicker>
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
            }
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
    }
}
</script>


<style scoped>


</style>