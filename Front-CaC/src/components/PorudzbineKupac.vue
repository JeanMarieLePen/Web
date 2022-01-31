<template>
    <div id="user-profile">
        <div>
            <div class="container" id='page-title'>
                <h1 style="margin-top:10px;"><span id='titleEffect'>Pregled porudzbina</span></h1>
                <hr style='background:#35424a;height:1px;'>

                <div class='container ' id='search'>
                <nav class="navbar navbar-light bg-light justify-content-between">
                    <a style='font-weight:bold;margin-top:10px;color:#35424a;' class="navbar-brand">Search</a>
                    <form class="form-inline">
                        <div style='display:inline;'>
                            <div style="margin-top:20px" v-if='messages.errorDates' class="alert alert-danger" v-html="messages.errorDates"></div> 
                            <div id='first-row'  class="row">
                                <!-- <span class="col-xl-2 col-md-6 mb-3 marg-top"> -->
                                    <button style='margin-left:10px;margin-right:500px;' class='btn btn-outline-primary btn-clear' v-on:click="resetFilter()">Reset all</button>
                                <!-- </span> -->

                                
                                <!-- <span class="col-xl-2 col-md-6 marg-right"> -->
                                <span class="span_search">Od</span>
                                <vuejsDatepicker  placeholder="Select Start Date"
                                    v-model=" searchedPorudzbine.od" :highlighted=" searchedPorudzbine" :disabled-dates="disabledDates">
                                </vuejsDatepicker>
                                <!-- </span> -->
                                <!-- <span class="col-xl-2 col-md-6 marg-right" > -->
                                
                                
                                <span class="span_search">Do</span>
                                <vuejsDatepicker  placeholder="Select End Date"
                                    v-model="searchedPorudzbine.do" :highlighted="searchedPorudzbine" :disabled-dates="disabledDates">
                                </vuejsDatepicker>
                                <!-- </span> -->

                                <span style='margin-left:50%;margin-bottom:5%;'>
                                <span class="span_search">Cena</span>
                                <input class="form-control mr-sm-2" type="text" placeholder="min cena" aria-label="Search" v-model="searchedPorudzbine.cenaMin">
                                <span style="padding-right:6px;" class="span_search"> - </span>
                                <input class="form-control mr-sm-2" type="text" placeholder="max cena" aria-label="Search" v-model="searchedPorudzbine.cenaMax">
                                </span>
                         </div>
                        </div>
                </form>
            </nav>
            </div>
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
                            <div class="col-md-4" v-bind:key="tempPorudzbina.id" v-for="tempPorudzbina in nedostavljenePorudzbine">
                                <div style="margin-bottom:30px;" class="card h-100">
                                    <!-- <img class="card-img-top" :src="product.thumbnailUrl" alt="card image collar"> -->
                                    <div class="card-body">
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
        entityDetails(tempId){
            this.$router.push(`/order/${tempId}`);
        },
        resetFilter:function(){
            this.searchedPorudzbine.od =  null;
            this.searchedPorudzbine.do =  null;
            this.searchedPorudzbine.cenaMin =  null;
            this.searchedPorudzbine.cenaMax =  null;
            // this.searchedCar.markaAutId =  null;
            // this.searchedCar.modelAutId =  null;

            this.getAllPorudzbine();
        },
        getAllPorudzbine:function(){
            dataService.getAllPorudzbine().then(response => {
                this.porudzbinee = response.data;
                console.log(JSON.stringify(this.porudzbinee));
            });
        
    },
    searchPorudzbine() {     
                    
            if (this.searchedPorudzbine.od == null) {
                this.messages.errorDates = `<h4>Morate odabrati početni termin porudzbine!</h4>`;
                setTimeout(() => this.messages.errorDates = '', 5000);
            }
            else if (this.searchedPorudzbine.do == null) {
                this.messages.errorDates = `<h4>Morate odabrati krajnji termin porudzbine!</h4>`;
                setTimeout(() => this.messages.errorDates = '', 5000);
            }
            else{
                // U slucaju da korisnik pritisne ResetAll Uklonio bi od i do iz this.searchedCar
                // Pa ako u tom momentu odluci da doda oglas u korpu prosledio bi prazne datume... 
                // Zato ih ovde cuvamo u odabraniDatum, da i nakon reseta budu dodati oglasi za zadnje unet interval.
                this.odabraniDatum.od = this.searchedPorudzbine.od;
                this.odabraniDatum.do = this.searchedPorudzbine.do;
                //Kada se jednom izvrsi pretraga, bila ona uspesna ili neuspesna uklanja se default prikaz stranice....
                this.isAlreadySearched = true;
  
                if (!!this.searchedPorudzbine.od) {
                    let od_datuma = this.searchedPorudzbine.od.getTime();
                    this.searchedQuery += 'od=' + od_datuma;
                }
                if (!!this.searchedPorudzbine.do) {
                    let do_datuma = this.searchedPorudzbine.do.getTime();
                    this.searchedQuery += '&do=' + do_datuma;
                }
                if (!!this.searchedPorudzbine.cenaMin) {
                    this.searchedQuery += '&cenaMin=' + this.searchedPorudzbine.cenaMin;
                }
                if (!!this.searchedPorudzbine.cenaMax) {
                    this.searchedQuery += '&cenaMax=' + this.searchedPorudzbine.cenaMax;
                }
                // if (!!this.searchedCar.markaAutId) {
                //     this.searchedQuery += '&markaAut=' + this.searchedCar.markaAutId;
                // }
                // if (!!this.searchedCar.modelAutId) {
                //     this.searchedQuery += '&modelAut=' + this.searchedCar.modelAutId;
                // }
               
                
                dataService.searchPorudzbine(this.searchedQuery).then(response => {
                    // this.porudzbinee = response.data;
                    // this.searchedQuery = '?';
                }).catch(error =>{
                    // if (error.response.status === 500 || error.response.status === 404) {
                    //     this.messages.errorResponse = `<h4>Imali smo nekih problema na serveru, molimo Vas pokusajte ponovo kasnije!</h4>`;
                    //     setTimeout(() => this.messages.errorResponse = '', 5000);
                    // }
                    console.log("dwad");
                    this.searchedQuery = '?';
                });
            }
            
        },
        
    },
    data() {
        return{
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
                od: null,
                do: null,
                cenaMin:null,
                cenaMax:null,
            },
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