<template>
    <div style="width:50%">
        <div class="container">
            <table class="table" id="table_newdeliveryman">
                <tbody>
                    <tr>
                        <td>
                            Korisnicko ime:
                        </td>
                        <td>
                            <input type="text" placeholder="Unesite korisnicko ime..."  v-model="newDeliveryMan.username">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Ime:
                        </td>
                        <td>
                            <input type="text" placeholder="Unesite ime..."  v-model="newDeliveryMan.name">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Prezime:
                        </td>
                        <td>
                            <input type="text" placeholder="Unesite prezime..."  v-model="newDeliveryMan.lastname">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Lozinka:
                        </td>
                        <td>
                            <input type="password" placeholder="Unesite lozinku..." v-model="newDeliveryMan.password">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Pol:
                        </td>
                        <td>
                            <span >
                                <select style="width:100%" v-model="newDeliveryMan.gender">
                                    <option disabled value="">Odabir pola</option>
                                    <option v-bind:key="polTemp.naziv" v-for="polTemp in polovi">{{polTemp.naziv}}</option>
                                </select>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Datum rodjenja:
                        </td>
                        <td>
                            <vuejsDatepicker v-model="selectedDate" style="padding:5px;" placeholder="Odaberite datum rodjenja" ></vuejsDatepicker>
                        </td>
                    </tr>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="2">
                            <div class="text-center">
                                <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
                                <div v-if='messages.errorResponse' class="alert alert-error" v-html="messages.errorResponse"></div>
                                <button class="btn btn-success" v-on:click='createDeliveryMan()'>Potvrdi</button>
                            </div>
                            
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
        
    </div>
</template>

<script>
import dataService from '../services/DataService'
import Datepicker from 'vuejs-datepicker'

export default {
    components:{
        vuejsDatepicker:Datepicker
    },
    watch:{
        'selectedDate' : function(val, oldVal){
            console.log('Datum pre: ' + this.newDeliveryMan.dateOfBirth);
             this.newDeliveryMan.dateOfBirth = this.selectedDate.toString().substring(4, 15);
            console.log('Datum posle: ' + this.newDeliveryMan.dateOfBirth);
        }
    },
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
            selectedDate: '',

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
#table_newdeliveryman td:first-child{
    font-weight: 700;
    width:30%;
    
}
#table_newdeliveryman tr:nth-child(even){
    background-color: lightgray;
    font-weight: 600;
}
#table_newdeliveryman td:last-child{
    width:100%;
}
#table_newdeliveryman{
    border-style: solid;
}
input{
    width: 100%;
}

</style>