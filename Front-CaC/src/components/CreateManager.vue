<template>
    <div style="width:50%">
        
        <div class="container">
            <h2>Kreiranje novog menadzera</h2>
            <p>Popunite formular kako biste kreirali novog menadzera...</p>
            <table class="table"  id="table_newmanager">
                <tbody>
                    <tr>
                        <td>
                            <label class='label'>Korisnicko ime menadzera:</label>
                        </td>
                        <td>
                            <input type="text" placeholder="Unesite korisnicko ime menadzera restorana..."  v-model="newManager.username">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class='label'>Ime menadzera:</label>
                        </td>
                        <td>
                            <input  type="text" placeholder="Unestite ime menadzera..."  v-model="newManager.name">
                        </td>
                    </tr>
                     <tr>
                        <td>
                            <label class='label'>Prezime menadzera:</label>
                        </td>
                        <td>
                            <input type="text" placeholder="Unestite ime menadzera..."  v-model="newManager.lastname">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class='label'>Pol menadzera:</label>
                        </td>
                        <td>
                            <span >
                                <select style="padding:5px; width:100%;" v-model="newManager.gender">
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
                             <vuejsDatepicker style="padding:5px; width:100%;" placeholder="Odaberite datum rodjenja" v-model="selectedDate"></vuejsDatepicker>
                        </td>
                    </tr>
                    <tr>
                        <td>*Restoran:</td>
                        <td>
                            <select style="padding:5px; width:100%;" v-model="newManager.restaurant">
                                <option disabled value="">Odabir restorana</option>
                                <option v-bind:key="restoran.naziv" v-for="restoran in restoraniBezMenadzera">{{restoran.naziv}}</option>
                            </select>
                        </td>
                    </tr>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="2">
                            <div class="text-center">
                                <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
                                <div v-if='messages.errorResponse' class="alert alert-success" v-html="messages.errorResponse"></div>
                                <button class="btn btn-success" v-on:click='createManager()'>Potvrdi</button>
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

export default{
    data(){
        return{

            selectedDate:'',
            
            restoraniBezMenadzera:[
                // {naziv:'test1'},
                // {naziv:'test2'}
            ],
            polovi:[
                {naziv:'musko'},
                {naziv:'zensko'}
            ],
            newManager:{
                username:'',
                name:'',
                lastname:'',
                password:'1234',
                gender:'',
                dateOfBirth:'',
                restaurant:''
            },
            messages:{
                successResponse:'',
                errorResponse:'',
            },
            allManagers:[]
        }
        
    },
    watch:{
        'selectedDate' : function(val, oldVal){
            console.log('Datum pre obrade: ' + this.newManager.dateOfBirth);
            this.newManager.dateOfBirth = this.selectedDate.toString().substring(4, 15);
            console.log("Datum posle obrade: " + this.newManager.dateOfBirth)
        }
    },
    methods:{
        formatDate(){
            Console.log("USAO U FORMATDATE")
            this.newManager.dateOfBirth = this.newManager.dateOfBirth.substring(0,10);
        },
        createManager:function(){
            console.log(JSON.stringify(this.newManager));
            console.log(this.newManager);
            dataService.addManager(this.newManager).then(response => {
                if(response.data !== ''){
                    this.messages.successResponse= "<h4>Uspesno ste kreirali menadzera.</h4>"
                    setTimeout(() => this.messages.successResponse='', 3000);
                }else{
                    this.messages.errorResponse= "<h4>Vec postoji menadzer sa tim korisnickim imenom.</h4>"
                    setTimeout(() => this.messages.errorResponse='', 3000);
                }
                
                
            }).catch(error => {
                if(error.response.status === 500 || error.response.status === 404){
                        this.messages.errorResponse= `<h4>We had some server errors, please try again later!</h4>`;
                        setTimeout(() => this.messages.errorResponse='', 5000);
                }
            })
        },
        addChoosenGender:function(pickedGender){
            this.newManager.gender = pickedGender.naziv;
        },
        addChoosenRestaurant:function(restoran){

        },
        getRestaurantsWithNoManagers:function(){
            dataService.getAllRestaurantsWithNoManager().then(response => {
                this.restoraniBezMenadzera.response.data;
            });
        },
        getUsernames:function(){
            dataService.getAllManagers().then(response => {
                this.allManagers = response.data;
            })
        },
    },
    components:{
        vuejsDatepicker:Datepicker,
    },
    created(){      
        // if(JSON.parse(localStorage.getItem('token')) == null){
        //     this.$router.push(`/login`);
        // }else{
        //     this.getUsernames();
        //     this.getRestaurantsWithNoManagers();
        // }
    }
}
</script>


<style scoped>
#table_newmanager td:first-child{
    font-weight: 700;
    width:30%;
    
}
#table_newmanager tr:nth-child(even){
    background-color: lightgray;
    font-weight: 600;
}
#table_newmanager td:last-child{
    width:100%;
}
#table_newmanager{
    border-style: solid;
}
input{
    width: 100%;
}


</style>