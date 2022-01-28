<template>
    <div style="width:50%">
        <div style="margin-left:30px;">
            <h2>Registracija novog korisnika:</h2>
            <p>Popunite formular kako biste podneli zahtev za kreiranje naloga...</p>
        </div>
        
        <div class="container">
            <table class="table" id="table_reg">
                <tbody>
                    <tr>
                        <td>
                            Korisnicko ime:
                        </td>
                        <td>
                            <input type="text" placeholder="Unesite korisnicko ime..." v-model="newCustomer.username">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Ime:
                        </td>
                        <td>
                            <input type="text" placeholder="Unesite korisnicko ime..." v-model="newCustomer.name">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Prezime:
                        </td>
                        <td>
                            <input type="text" placeholder="Unesite korisnicko ime..." v-model="newCustomer.lastname">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Lozinka:
                        </td>
                        <td>
                            <input type="text" placeholder="Unesite korisnicko ime..." v-model="newCustomer.password">
                        </td>
                    </tr>
                     <tr>
                        <td>
                            Pol:
                        </td>
                        <td>
                            <span >
                                <select style="width:100%;padding:5px;" v-model="newCustomer.gender">
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
                            <vuejsDatepicker input-class="width:100%" class="form-control" placeholder="Odaberite datum rodjenja" v-model="selectedDate"></vuejsDatepicker>
                        </td>
                    </tr>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="2">
                            <div class="text-center">
                                <button class="btn btn-success" v-on:click='createCustomer()'>Potvrdi</button>
                                <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
                                <div v-if='messages.errorResponse' class="alert alert-danger" v-html="messages.errorResponse"></div>
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
    name:'customer-new',
    data(){
        return{
            selectedDate: '',
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
        'selectedDate' : function(val){
            console.log('Datum rodjenja pre: ' + this.selectedDate);
            // this.newCustomer.dateOfBirth = this.newCustomer.dateOfBirth.toString().substring(4, 15);
             this.newCustomer.dateOfBirth = this.selectedDate.toString().substring(4, 15);
             console.log('Datum rodjenja posle: ' + this.newCustomer.dateOfBirth);
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
            });
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
#table_reg td:first-child{
    font-weight: 700;
    width:30%;
    
}
#table_reg tr:nth-child(even){
    background-color: lightgray;
    font-weight: 600;
}
#table_reg td:last-child{
    width:100%;
}
#table_reg{
    border-style: solid;
}
input{
    width: 100%;
}

</style>