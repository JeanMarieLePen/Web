<template>
    <div>
        <div class="container">
            <label class='label'>Korisnicko ime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite korisnicko ime..."  v-model="newCustomer.username">
            <label class='label'>Ime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite vase ime..."  v-model="newCustomer.name">
            <label class='label'>Prezime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite vase prezime..."  v-model="newCustomer.lastname">
            <label class='label'>Lozinka:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite lozinku..."  v-model="newCustomer.password">
            
            
            <label style="padding:5px;" class='label'>Pol</label>
            <span class="col-xl-3 col-md-6 mb-1">
                <select style="padding:5px;" v-model="newCustomer.gender">
                    <option disabled value="">Odabir pola</option>
                    <option v-bind:key="polTemp.naziv" v-for="polTemp in polovi">{{polTemp.naziv}}</option>
                </select>
            </span>
            
            <label style="padding:5px;" class='label'>Datum rodjenja:</label>
            
            <vuejsDatepicker placeholder="Odaberite datum rodjenja" v-model="newCustomer.dateOfBirth">
            </vuejsDatepicker>
            
            <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
            <div v-if='messages.errorResponse' class="alert alert-success" v-html="messages.errorResponse"></div>
            <button class="btn btn-success" v-on:click='getCustomerTest()'>Potvrdi</button>
        </div>
    </div>
</template>

<script>
import dataService from '../services/DataService'
import Datepicker from 'vuejs-datepicker'
export default {
    name:'customer-new',
    data(){
        return{
            newCustomer:{
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
    watch:{
        'newCustomer.dateOfBirth' : function(val, oldVal){
            console.log(this.newCustomer.dateOfBirth);
            this.newCustomer.dateOfBirth = this.newCustomer.dateOfBirth.toString().substring(0, 10);
        }
    },
    methods:{
        formatDate(){
            Console.log("USAO U FORMATDATE")
            this.newCustomer.dateOfBirth = this.newCustomer.dateOfBirth.substring(0,10);
        },
        createCustomer:function(){
            console.log(JSON.stringify(this.newCustomer));
            console.log("AAAA")
            console.log(this.newCustomer);
            dataService.addCustomer(this.newCustomer).then(response => {
                console.log("Stigao odgovor sa beka: " + response);
            }).catch(error => {
                if(error.response.status === 500 || error.response.status === 404){
                        this.messages.errorResponse= `<h4>We had some server errors, please try again later!</h4>`;
                        setTimeout(() => this.messages.errorResponse='', 5000);
                }
            })
        },
        addChoosenGender:function(pickedGender){
            this.newCustomer.gender = pickedGender.naziv;
        },
        
        
        
    },
    components:{
        vuejsDatepicker:Datepicker,
    },
    
}
</script>


<style scoped>


</style>