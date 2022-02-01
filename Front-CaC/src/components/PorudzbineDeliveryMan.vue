<template>
    <div id="user-profile">
        <div>
            <div class="container" id='page-title'>
                <h1 style="margin-top:10px;"><span id='titleEffect'>Pregled porudzbina</span></h1>
                <hr style='background:#35424a;height:1px;'>
            <div class='container ' id='search'>
                <nav class="navbar navbar-light bg-light justify-content-between">
                    <a class="navbar-brand">Pretraga porudzbina</a>
                    <form class="form-inline">     
                        <div style='display:inline;' id="advanced_search">
                            <div id='second-row' class="row"  style="margin-top:5px;">
                                
                                <div class="input-group">
                                    <input class="form-control mr-sm-2" type="text" placeholder="Naziv restorana" aria-label="Search" v-model="searchedPorudzbine.nazivRestorana">
                                    
                                    <input class="form-control mr-sm-2" type="number" min="0" placeholder="min cena" aria-label="Search" v-model="searchedPorudzbine.cenaOd">
                                    <span style="padding-right:6px;" class="span_search"> - </span>
                                    <input class="form-control mr-sm-2" type="number" min="0" placeholder="max cena" aria-label="Search" v-model="searchedPorudzbine.cenaDo">
                                
                                    <span class="span_search">Od</span>
                                    <vuejsDatepicker style="font-size:20px;"  placeholder="Pocetni datum..."
                                        v-model="selectedDate1" :highlighted=" searchedPorudzbine" :disabled-dates="disabledDates">
                                    </vuejsDatepicker>
                                    <span class="span_search">Do</span>
                                    <vuejsDatepicker style="font-size:20px;"  placeholder="Krajnji datum..."
                                        v-model="selectedDate2" :highlighted="searchedPorudzbine" :disabled-dates="disabledDates">
                                    </vuejsDatepicker>
                                </div>
                                
                                
                            </div><!--/second_row-->
                            <br>
                            <div class="button-group">
                                <span class="col-xl-2 col-md-6 mb-1">
                                    <button style='margin-right:5px;' class='btn btn-outline-primary my-2 my-sm-0' v-on:click="resetFilter()">Reset all</button>
                                </span> 
                                <span class="col-xl-3 col-md-6 mb-2"> 
                                    <button style="margin-left: 5px;" class="btn btn-outline-success my-2 my-sm-0" type="button" v-on:click.prevent='search()'>Search</button>
                                </span>
                            </div>
                            
                        </div><!--/advanced_search-->
                        <div v-if='messages.errorResponse' class="alert alert-danger" v-html="messages.errorResponse"></div>
                        <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>  
                    </form>
                </nav>
            </div>
                <div style="margin-top:30px;" class="container" id='main'>
            <div style="margin-bottom:30px;">
                <span class="span_search">Filtriranje po tipu restorana</span>
                <input v-model="filterInput">
                <span class="span_search">Filtriranje po statusu porudzbine</span>
                <input v-model="filterStatus">
            </div>
            
            
            <table class="table">
                <thead>
                    <tr>
                        <th @click="sort('nazivRestorana')" class="arrow">Naziv
                            <img v-if='currentSortDir == "asc" && currentSort== "nazivRestorana"' src='../assets/up-arrow1.1.png'>
                            <img v-if='currentSortDir == "desc" && currentSort== "nazivRestorana" ' src='../assets/down-arrow1.1.png'>
                        </th>
                        <th>Tip</th>
                        <th @click="sort('datumPorudzbine')" class="arrow">Datum 
                            <img v-if='currentSortDir == "asc" && currentSort== "datumPorudzbine"' src='../assets/up-arrow1.1.png'>
                            <img v-if='currentSortDir == "desc" && currentSort== "datumPorudzbine" ' src='../assets/down-arrow1.1.png'>
                        </th>
                        <th @click="sort('cenaPorudzbine')" class="arrow"> Cena
                            <img v-if='currentSortDir == "asc" && currentSort== "cenaPorudzbine"' src='../assets/up-arrow1.1.png'>
                            <img v-if='currentSortDir == "desc" && currentSort== "cenaPorudzbine" ' src='../assets/down-arrow1.1.png'>
                        </th>
                        
                    </tr>
                </thead>
                <tbody>
                    <!-- <tr v-bind:key='rst.name' v-for="rst in sortPorudzbine">
                        <td>{{rst.name}}</td>
                        <td>{{rst.type}}</td>
                        <td>{{rst.datum}}</td>
                        <td>{{rst.cena}}</td>
                        
                        
                    </tr> -->
                </tbody>
                
            </table>
        </div>
            </div>
            <div class="container" style="margin-bottom:100px;max-height:300px; overflow:auto;">
                <h2>Pregled svih svojih porudzbina</h2>
                <section style="margin-left: 10px; margin-bottom:10px">
                    <div>
                    <!-- if we are 3 cards wide start a new row -->
                        <div class="row">
                            <div class="col-md-2" v-bind:key="tempPorudzbina.idPorudzbine" v-for="tempPorudzbina in porudzbine">
                                <div style="margin-bottom:30px; width:200px;" class="card h-100">
                                    <!-- <img class="card-img-top" :src="product.thumbnailUrl" alt="card image collar"> -->
                                    <div style="" class="card-body">
                                        <h5 class="card-title">Porudzbina ID: {{tempPorudzbina.idPorudzbine}}</h5>
                                        <p class="card-text">cena: {{tempPorudzbina.cena}}</p>
                                        <p class="card-text">Kupac: {{tempPorudzbina.idKupca}}</p>
                                        <!-- <button v-on:click="orderDetails(tempPorudzbina.id)" class="btn btn-primary">Detalji</button> -->
                                        <!-- <button v-show="isOwner" v-on:click="removeEntity(tempVikendica.id)" class="btn btn-danger">Ukloni</button> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>

            <div class="container" style="margin-bottom:100px;max-height:300px; overflow:auto;">
                <h2>Pregled svih svojih nedostavljenih porudzbina</h2>
                <section style="margin-left: 10px; margin-bottom:10px">
                    <div>
                    <!-- if we are 3 cards wide start a new row -->
                        <div class="row">
                            <div class="col-md-2" v-bind:key="tempPorudzbina.idPorudzbine" v-for="tempPorudzbina in nedostavljenePorudzbine">
                                <div style="margin-bottom:30px;" class="card h-100">
                                    <!-- <img class="card-img-top" :src="product.thumbnailUrl" alt="card image collar"> -->
                                    <div style="" class="card-body">
                                        <h5 class="card-title">Porudzbina ID: {{tempPorudzbina.idPorudzbine}}</h5>
                                        <p class="card-text">cena: {{tempPorudzbina.cena}}</p>
                                        <!-- <button v-on:click="orderDetails(tempPorudzbina.idPorudzbine)" class="btn btn-primary">Detalji</button> -->
                                        <!-- <button v-show="isOwner" v-on:click="removeEntity(tempVikendica.id)" class="btn btn-danger">Ukloni</button> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
                    
            <div class="container" style="min-height:300px;">
                <h2>Pregled porudzbina koje cekaju dostavljaca</h2>
                <section style="margin-left: 10px; margin-bottom:100px">
                    <div>
                    <!-- if we are 3 cards wide start a new row -->
                        <div class="row">
                            <div class="col-md-4" v-bind:key="index" v-for="(tempPorudzbina, index) in porudzbineCekanju">
                                <div style="margin-bottom:30px;" class="card h-100">
                                    <!-- <img class="card-img-top" :src="product.thumbnailUrl" alt="card image collar"> -->
                                    <div class="card-body">
                                        <h5 class="card-title">Porudzbina ID: {{tempPorudzbina.idPorudzbine}}</h5>
                                        <p class="card-text">cena: {{tempPorudzbina.cena}}</p>
                                        <p class="card-text">cena: {{tempPorudzbina.datumIVremePorudzbine}}</p>
                                        <button v-on:click="takeOrder(tempPorudzbina, index)" class="btn btn-primary">Preuzmi</button>
                                        <!-- <button v-show="isOwner" v-on:click="removeEntity(tempVikendica.id)" class="btn btn-danger">Ukloni</button> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
</template>

<script>
import dataService from '../services/DataService'
import Datepicker from 'vuejs-datepicker';
import StarRating from "vue-star-rating";

export default{
     created(){
        let tempUsername = JSON.parse(localStorage.getItem('token')).username;
        this.username = tempUsername;
        console.log('Username: ' + tempUsername)
        try{
            
            this.getSvojeNedostavljenePorudzbine(tempUsername);
            this.getZaduzenePorudzbine(tempUsername)
            this.getSveCekanje();
        }catch(error){
        }
        
    },
    
    computed:{
         sortPorudzbine:function(){
            return this.porudzbine.filter(this.filterByType).filter(this.filterByStatus).sort(this.sortiraj);}
    },
     components: {
        vuejsDatepicker:Datepicker,
        "star-rating": StarRating,
    },
    methods:{
        takeOrder(order, index){
            order.idDeliveryMana = this.username;
            order.statusPorudzbine = 'zahtevZaDostavu';
            dataService.takeOrder(order).then(response => {
                console.log('preuzeli ste narudzbinu');
                this.porudzbineCekanju.splice(index, 1);
            });
        },
        getSvojeNedostavljenePorudzbine(username){
            dataService.getSvojeNedostavljenePorudzbine(username).then(response => {
            console.log("stigli podaci o svojim porudzbinama koje su nedostavljene");
            for(let i = 0; i < response.data.length; i++){
                this.nedostavljenePorudzbine.push(response.data[i])
            }
            console.log(response.data);
            });
        },
        getZaduzenePorudzbine(username){
            dataService.getZaduzenePorudzbine(username).then(response => {
            console.log("stigli podaci o svojim porudzbinama");
            console.log(response.data);
            for(let i = 0; i < response.data.length; i++){
                this.porudzbine.push(response.data[i])
            }
            });
        },
        resetFilter:function(){
            this.searchedPorudzbine.datumOd =  null;
            this.searchedPorudzbine.datumDo =  null;
            this.searchedPorudzbine.cenaOd =  null;
            this.searchedPorudzbine.cenaDo =  null;
            this.searchedPorudzbine.nazivRestorana = null;
            

            this.getAllPorudzbine();
        },
        
        sortiraj(){
            if(this.currentSort == 'nazivRestorana'){
                if(this.currentSortDir == 'asc'){
                    return this.porudzbine.sort((a, b) => (a.name > b.name) ? 1 : -1)
                }
                else{
                    return this.porudzbine.sort((a, b) => (a.name < b.name) ? 1 : -1)
                }
            }
            if(this.currentSort == 'cenaPorudzbine'){
                if(this.currentSortDir == 'asc'){
                    return this.porudzbine.sort((a, b) => (a.ocena > b.ocena) ? 1 : -1)
                }
                else{
                    return this.porudzbine.sort((a, b) => (a.ocena < b.ocena) ? 1 : -1)
                }
            }
            if(this.currentSort == 'datumPorudzbine'){
                if(this.currentSortDir == 'asc'){
                    return this.porudzbine.sort((a, b) => (a.lokacija > b.lokacija) ? 1 : -1)
                }
                else{
                    return this.porudzbine.sort((a, b) => (a.lokacija < b.lokacija) ? 1 : -1)
                }
            }
        },
        getSveCekanje(){
            dataService.getSveCekanje().then(response => {
            console.log("stigli podaci o porudzbinama koje cekaju dostavljaca");
            console.log(response.data);
            
            for(let i = 0; i < response.data.length; i++){
                this.porudzbineCekanju.push(response.data[i])
            }
            console.log('duzina odgovora: ' + response.data.length)
            console.log('duzina odgovora: ' + this.porudzbineCekanju.length)
            });
        },
        entityDetails(tempId){
            this.$router.push(`/order/${tempId}`);
        },
        searchPorudzbine() {     
                    
            // if (this.searchedPorudzbine.datumOd == null) {
            //     this.messages.errorDates = `<h4>Morate odabrati početni termin porudzbine!</h4>`;
            //     setTimeout(() => this.messages.errorDates = '', 5000);
            // }
            // else if (this.searchedPorudzbine.datumDo == null) {
            //     this.messages.errorDates = `<h4>Morate odabrati krajnji termin porudzbine!</h4>`;
            //     setTimeout(() => this.messages.errorDates = '', 5000);
            // }
                //Kada se jednom izvrsi pretraga, bila ona uspesna ili neuspesna uklanja se default prikaz stranice....
                this.isAlreadySearched = true;
                let searchedQuery = 'datumOd:';
                if (!!this.searchedPorudzbine.datumOd) {
                    let od_datuma = this.searchedPorudzbine.datumOd;
                    searchedQuery += od_datuma;
                }
                else{
                    searchedQuery += "_";
                }
                searchedQuery += "&datumDo:";
                if (!!this.searchedPorudzbine.datumDo) {
                    let do_datuma = this.searchedPorudzbine.datumDo;
                    searchedQuery += do_datuma;
                }
                else{
                    searchedQuery += "_";
                }
                searchedQuery += "&cenaOd:";
                if (!!this.searchedPorudzbine.cenaOd) {
                    searchedQuery += this.searchedPorudzbine.cenaOd;
                }
                else{
                    searchedQuery += "_";
                }
                searchedQuery += "&cenaDo:";
                if (!!this.searchedPorudzbine.cenaDo) {
                    searchedQuery += this.searchedPorudzbine.cenaDo;
                }
                else{
                    searchedQuery += "_";
                }
                searchedQuery += "&nazivRestorana:";
                if (!this.searchedPorudzbine.nazivRestorana == '') {
                    searchedQuery += this.searchedPorudzbine.nazivRestorana;
                }
                else{
                    searchedQuery += "_";
                }
                console.log('Zahtev nakon formiranja: ' + searchedQuery);
                dataService.searchPorudzbine(searchedQuery).then(response => {
                   console.log('stigao filtrirane narudzbine');
                   this.porudzbine = response.data;

                }).catch(error =>{
                    console.log(error.response);
                });
            
            
        },
        
    },
        
        
    
    
     watch:{
        'selectedDate1':function(){
            console.log('Datum pre obrade: ' + this.selectedDate1);
            this.searchedPorudzbine.datumOd = this.selectedDate1.toString().substring(4, 15);
            console.log("Datum posle obrade: " + this.searchedPorudzbine.datumOd)
        },
        'selectedDate2':function(){
            console.log('Datum pre obrade: ' + this.selectedDate2);
            this.searchedPorudzbine.datumDo = this.selectedDate2.toString().substring(4, 15);
            console.log("Datum posle obrade: " + this.searchedPorudzbine.datumDo)
        }
    },
    data() {
        return{
            username: '',
            porudzbine:[],
            nedostavljenePorudzbine:[],
            porudzbineCekanju:[],

             currentSort: 'cenaPorudzbine',
            currentSortDir: 'asc',
            filterInput:'',
            // displayedPorudzbine:[],
            filterStatus:'',

             selectedDate1: '',
            selectedDate2: '',
             searchedPorudzbine: {
                //Prilikom povezivanja preimenovati da odgovara nazivima atributa sa beka
                datumOd: null,
                datumDo: null,
                cenaOd:null,
                cenaDo:null,
                nazivRestorana: '',
            },
            disabledDates: {
                to: null
            },
            messages: {
                errorAddress: '',
                errorPrice: '',
                errorPlannedCm: '',
                errorDates: '',
                errorResponse: '',
                successResponse: '',
            },
        }
    },
}
</script>

<style scoped>
#titleEffect{
  
  font-weight: bold;
}

#user-profile ul#services li{
    list-style: none;
    padding:15px;
    border-bottom: gray solid 1px;
    border-radius: 15px;
    margin-bottom: 3px; 
    background-color:#fff;
    width:90%;
}

/* #user-profile#buttonUpdate{
  float:left;
} */


#searcIcon img:hover{
    width:45px;
    padding-bottom:3px;
    cursor: pointer;
}

.span_search{
    /* padding:5px; */
    padding-top:5px;
    padding-left:5px;
    padding-right:5px;
    color:#35424a;
    font-weight: bold;
}

.header5{
    color:#1E90FF;
    font-weight: bold;
}

#search_icon:hover{
    cursor: pointer;
}

.marg{
    margin-top:15px;
}
</style>