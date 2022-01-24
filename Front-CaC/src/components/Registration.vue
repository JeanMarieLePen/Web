<template>
    <div style="width:50%">
        <div style="margin-left:30px;margin-top:20px;" class="container">
            <form>
                <div class="form-group row">
                    <label class="col-sm-2;col-form-label">Korisnicko ime:</label>
                    <input type="text"  class="form-control" placeholder="Unesite korisnicko ime..." v-model="newCustomer.username">
                </div>
                <div class="form-group row">
                    <label class="col-sm-2;col-form-label">Ime:</label>
                    <input type="text" class="form-control" placeholder="Unesite Vase ime..." v-model="newCustomer.name">
                </div>
                <div class="form-group row">
                    <label class="col-sm-2;col-form-label">Prezime:</label>
                    <input type="text"  class="form-control" placeholder="Unesite Vase prezime..." v-model="newCustomer.lastname">
                </div>
                <div class="form-group row">
                    <label class="col-sm-2;col-form-label">Lozinka:</label>
                    <input type="password"  class="form-control" placeholder="Unesite Vasu lozinku..." v-model="newCustomer.password">
                </div>
                <div class="form-group row">
                    <label class="col-sm-2;col-form-label">Pol:</label>
                    <span  class="col-sm-2 col-form-label;form-control">
                        <select class="form-control" style="padding:5px;" v-model="newCustomer.gender">
                            <option disabled value="">Odabir pola</option>
                            <option v-bind:key="polTemp.naziv" v-for="polTemp in polovi">{{polTemp.naziv}}</option>
                        </select>
                    </span>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2;col-form-label">Datum rodjenja:</label>
                    <vuejsDatepicker input-class="width:100%" class="form-control" placeholder="Odaberite datum rodjenja" v-model="newCustomer.dateOfBirth"></vuejsDatepicker>
                </div>
                <div class="text-center">
                    <button class="btn btn-success" v-on:click='createCustomer()'>Potvrdi</button>
                    <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
                    <div v-if='messages.errorResponse' class="alert alert-danger" v-html="messages.errorResponse"></div>
                </div>
                </form>
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
        'newCustomer.dateOfBirth' : function(val){
            console.log(this.newCustomer.dateOfBirth);
            this.newCustomer.dateOfBirth = this.newCustomer.dateOfBirth.toString().substring(4, 15);
        }
    },
    methods:{
        formatDate(){
            Console.log("USAO U FORMATDATE")
            this.newCustomer.dateOfBirth = this.newCustomer.dateOfBirth.substring(4,15);
        },
        createCustomer:function(){
            console.log(JSON.stringify(this.newCustomer));
            console.log("AAAA")
            console.log(this.newCustomer);
            dataService.addCustomer(this.newCustomer).then(response => {
                console.log("Stigao odgovor sa beka: " + response.data);
                console.log("Stigao odgovor sa beka: " + response.data.stringify);
                if(!response.data){
                    this.messages.errorResponse= `<h4>Korisnik sa tim korisnickim imenom vec postoji. Izaberite drugo korisnicko ime.</h4>`;
                    setTimeout(()=>this.messages.errorResponse='',3000);
                }else{
                    this.messages.successResponse=`<h4>Uspesno ste se registrovali. Bicete prebaceni na login stranicu. </h4>`
                    setTimeout(()=>this.messages.successResponse='',3000);
                    setTimeout(()=>this.$router.push('/login'), 3000);
                }
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
        // addCustomer(){
        //     dataService.addCustomer(this.newCustomer).then(response =>{
        //         console.log("Kreiran je korisnik: " + response.data.username);
        //         if(!response.data){
        //             errorResponse
        //         }
        //     })
        // }
        
        
        
    },
    components:{
        vuejsDatepicker:Datepicker,
    },
    created(){
        if(JSON.parse(localStorage.getItem('token')) != null){
            this.$router.push(`/login`);
        }else{
            
        }
    }
}
</script>


<style scoped>


</style>