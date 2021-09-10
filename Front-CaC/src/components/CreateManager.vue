<template>
    <div>
        <div class="container">
            <label class='label'>Korisnicko ime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite korisnicko ime..."  v-model="newManager.username">
            <label class='label'>Ime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite ime menadzera..."  v-model="newManager.name">
            <label class='label'>Prezime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite  prezime menadzera..."  v-model="newManager.lastname">
            <label class='label'>Pol:</label>
            <span class="col-xl-3 col-md-6 mb-1">
                <select style="padding:5px;" v-model="newManager.gender">
                    <option disabled value="">Odabir pola</option>
                    <option v-bind:key="polTemp.naziv" v-for="polTemp in polovi">{{polTemp.naziv}}</option>
                </select>
            </span>
            <span>
            <label class='label'>Datum rodjenja:</label>
            <vuejsDatepicker placeholder="Odaberite datum rodjenja" v-model="newManager.dateOfBirth">
            </vuejsDatepicker>
            </span>
            <label class='label'>Restoran:</label>
            <select style="padding:5px;" v-model="newManager.restaurant">
                <option disabled value="">Odabir restorana</option>
                <option v-bind:key="restoran.naziv" v-for="restoran in restoraniBezMenadzera">{{restoran.naziv}}</option>
            </select>
            <!-- <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite restoran kojim upravlja menadzer..."  v-model="newManager.password">   -->
            <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
            <div v-if='messages.errorResponse' class="alert alert-success" v-html="messages.errorResponse"></div>
            <button class="btn btn-success" v-on:click='createManager()'>Potvrdi</button>
        </div>
    </div>
</template>


<script>
import dataService from '../services/DataService'
import Datepicker from 'vuejs-datepicker'

export default{
    data(){
        return{
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
        'newManager.dateOfBirth' : function(val, oldVal){
            console.log(this.newManager.dateOfBirth);
            this.newManager.dateOfBirth = this.newManager.dateOfBirth.toString().substring(0, 10);
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
        if(JSON.parse(localStorage.getItem('token')) == null){
            this.$router.push(`/login`);
        }else{
            this.getUsernames();
            this.getRestaurantsWithNoManagers();
        }
    }
}
</script>


<style scoped>


</style>