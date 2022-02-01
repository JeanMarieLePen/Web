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
                <!-- <div class='container ' id='search'>
                    <nav class="navbar navbar-light bg-light justify-content-between">
                        <a style='font-weight:bold;margin-top:10px;color:#35424a;' class="navbar-brand">Search</a>
                            <form class="form-inline">
                                <div style='display:inline;'>
                                    <div style="margin-top:20px" v-if='messages.errorDates' class="alert alert-danger" v-html="messages.errorDates"></div> 
                                    <div id='first-row'  class="row">
                                            <button style='margin-left:10px;margin-right:500px;' class='btn btn-outline-primary btn-clear' v-on:click="resetFilter()">Reset all</button>

                                        
                                        <span class="span_search">Od</span>
                                        <vuejsDatepicker  placeholder="Select Start Date"
                                            v-model="selectedDate1" :highlighted=" searchedPorudzbine" :disabled-dates="disabledDates">
                                        </vuejsDatepicker>
                                        
                                        
                                        <span class="span_search">Do</span>
                                        <vuejsDatepicker  placeholder="Select End Date"
                                            v-model="selectedDate2" :highlighted="searchedPorudzbine" :disabled-dates="disabledDates">
                                        </vuejsDatepicker>

                                        <span style='margin-left:50%;margin-bottom:5%;'>
                                        <span class="span_search">Cena</span>
                                        <input class="form-control mr-sm-2" type="text" placeholder="min cena" aria-label="Search" v-model="searchedPorudzbine.cenaOd">
                                        <span style="padding-right:6px;" class="span_search"> - </span>
                                        <input class="form-control mr-sm-2" type="text" placeholder="max cena" aria-label="Search" v-model="searchedPorudzbine.cenaDo">
                                        </span>

                                        <span class="col-xl-2 col-md-6 mb-3 marg-top" >
                                        <button style="margin-left:90px;margin-top:150px" class="btn btn-outline-success my-2 my-sm-0" type="button" v-on:click.prevent='searchPorudzbine()'>Search</button>
                                    </span>
                                    <span id='searcIcon' class="col-xl-1 marg-top" style='margin-left:45px;'>
                                        <img v-on:click='isSearch = !isSearch' src='../assets/searchIcon1.png' style="display:inline;">
                                    </span>
                                </div>
                                </div>
                        </form>
                    </nav>
                </div> -->
            </div>
            <div class="container" style="margin-bottom:100px;max-height:300px; overflow:auto;">
                <h2>Pregled svih svojih porudzbina</h2>
                <section style="margin-left: 10px; margin-bottom:10px">
                    <div>
                    <!-- if we are 3 cards wide start a new row -->
                        <div class="row">
                            <div class="col-md-2" v-bind:key="tempPorudzbina.id" v-for="tempPorudzbina in porudzbine">
                                <div style="margin-bottom:30px;" class="card h-100">
                                    <!-- <img class="card-img-top" :src="product.thumbnailUrl" alt="card image collar"> -->
                                    <div style="" class="card-body">
                                         <h5 class="card-title">Porudzbina ID: {{tempPorudzbina.id}}</h5>
                                        <p class="card-text">cena: {{tempPorudzbina.cena}}</p>
                                        <button v-on:click="orderDetails(tempPorudzbina.id)" class="btn btn-primary">Detalji</button>
                                        <!-- <button v-show="isOwner" v-on:click="removeEntity(tempVikendica.id)" class="btn btn-danger">Ukloni</button> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
                    
            <div class="container" style="min-height:300px;">
                <h2>Pregled nedostavljenih porudzbina</h2>
                <section style="margin-left: 10px; margin-bottom:100px">
                    <div>
                    <!-- if we are 3 cards wide start a new row -->
                        <div class="row">
                            <div class="col-md-4" v-bind:key="tempPorudzbina.idPorudzbine" v-for="tempPorudzbina in nedostavljenePorudzbine">
                                <div style="margin-bottom:30px;" class="card h-100">
                                    <!-- <img class="card-img-top" :src="product.thumbnailUrl" alt="card image collar"> -->
                                    <div class="card-body">
                                        <div style="height:150px;overflow-y: scroll;">
                                            <h5 class="card-title">Porudzbina ID: {{tempPorudzbina.idPorudzbine}}</h5>
                                            <p class="card-text">Ukupna cena: {{tempPorudzbina.cena}}</p>
                                            <!-- <button v-on:click="orderDetails(tempPorudzbina.id)" class="btn btn-primary">Detalji</button> -->
                                            <ul>
                                                <li  v-bind:key="index" v-for="(tempArtikal, index) in tempPorudzbina.listaNarucenihArtikala">{{tempArtikal.nazivArtikla}}</li>
                                            </ul>
                                        </div>
                                        
                                        <button v-show="tempPorudzbina.statusPorudzbine !== 'otkazana'" v-on:click="cancelOrder(tempPorudzbina.idPorudzbine)" class="btn btn-primary">Odustani</button>
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
        console.log('Username: ' + tempUsername)
        try{
            dataService.getSvojePorudzbine(tempUsername).then(response => {
            console.log("stigli podaci o svojim porudzbinama");
            this.porudzbine = response.data;
            });
            dataService.getNedostavljenePorudzbine(tempUsername).then(response => {
            console.log("stigli podaci o nedostavljenim porudzbinama");
            this.nedostavljenePorudzbine = response.data;
            });
        }catch(error){
            // this.profile = this.profileTemp;
            this.porudzbine = this.testPorudzbina;
            this.nedostavljenePorudzbine = this.testNedostavljenihPorudzbina;
        }
        
    },
    computed:{
        
    },
    methods:{
        cancelOrder(orderId){
            dataService.cancelOrder(orderId).then(response => {
                console.log('uspesno otkazan narudzbina id-a: ' + response.data.idNarudzbine);

            }).catch(error => {
                console.log(error.response);
            });
        },
        entityDetails(tempId){
            this.$router.push(`/order/${tempId}`);
        },
        resetFilter:function(){
            this.searchedPorudzbine.datumOd =  null;
            this.searchedPorudzbine.datumDo =  null;
            this.searchedPorudzbine.cenaOd =  null;
            this.searchedPorudzbine.cenaDo =  null;
            this.searchedPorudzbine.nazivRestorana = null;

            this.getAllPorudzbine();
        },
        getAllPorudzbine:function(){
            dataService.getAllPorudzbine().then(response => {
                this.porudzbinee = response.data;
                console.log(JSON.stringify(this.porudzbinee));
            });
        
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
            selectedDate1: '',
            selectedDate2: '',
            testPorudzbina: [
                    {
                        id:'223232',
                        poruceniArtikli: [
                            {naziv: 'artikal1'},
                            {naziv: 'artikal2'}, 
                            {naziv: 'artikal3'}
                            ],
                        restoranIzKogJePoruceno:'restoran1',
                        datumivreme: 'datumivreme1',
                        cena: '200',
                        kupac: 'Marko Markovic',
                        Status: 'Ceka dostavljaca',
                    },
                     {
                         id:'2233232',
                        poruceniArtikli: [
                            {naziv: 'artikal13'},
                            {naziv: 'artikal23'}, 
                            {naziv: 'artikal33'}
                            ],
                        restoranIzKogJePoruceno:'restoran1',
                        datumivreme: 'datumivreme13',
                        cena: '2030',
                        kupac: 'Marko Markovic3',
                        Status: 'Ceka dostavljaca3',
                    },
            ],

            testNedostavljenihPorudzbina: [
                    {
                        id:'223232xddddddd',
                        poruceniArtikli: [
                            {naziv: 'artikal1xdd'},
                            {naziv: 'artikal2xd'}, 
                            {naziv: 'artikal3xd'}
                            ],
                        restoranIzKogJePoruceno:'restoran1xddd',
                        datumivreme: 'datumivreme1xdd',
                        cena: '200xdd',
                        kupac: 'Marko Markovicxdd',
                        Status: 'Ceka dostavljacaxxddd',
                    },
                     {
                         id:'2233232xdd',
                        poruceniArtikli: [
                            {naziv: 'artikal13xdd'},
                            {naziv: 'artikal23xdd'}, 
                            {naziv: 'artikal33xdd'}
                            ],
                        restoranIzKogJePoruceno:'restoranxdd1',
                        datumivreme: 'datumivreme13xdd',
                        cena: '2030xdd',
                        kupac: 'Marko Markovic3xddd',
                        Status: 'Ceka dostavljaca3xdd',
                    },
            ],
            messages: {
                errorAddress: '',
                errorPrice: '',
                errorPlannedCm: '',
                errorDates: '',
                errorResponse: '',
                successResponse: '',
            },
            searchedPorudzbine: {
                //Prilikom povezivanja preimenovati da odgovara nazivima atributa sa beka
                datumOd: null,
                datumDo: null,
                cenaOd:null,
                cenaDo:null,
                nazivRestorana: '',
            },
            //pomocna varijabla za prikaz rezultata pretrage
            searchResults: [],
             dates: {
                from: null,
                to: null
            },
            disabledDates: {
                to: null
            },


            
        porudzbine:[],
        nedostavljenePorudzbine:[],
        }
    },
    components: {
        vuejsDatepicker:Datepicker,
        "star-rating": StarRating,
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