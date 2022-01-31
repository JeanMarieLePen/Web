<template>
    <div style="width:50%">
        <div class="container">
            <h2>Pregled detalja o restoranu {{loadRestaurant.name}}</h2>
            <p>Prikaz detalja i statistike o restoranu...</p>
            <table class="table" id="table_restaurantdetails">
                <tbody>
                    <tr>
                        <td colspan="2"> 
                            <div width="90%">
                                <!-- <img style="width:400px;height:400px" src="/src/assets/profile.png"> -->
                                <carousel-3d style="width:400px;height:600">
                                    <slide :index="0">
                                        <img  :src="loadRestaurant.logo">
                                    </slide>
                                    <!-- <slide :index="0">
                                        <img  src="/src/assets/dadada.png">
                                    </slide>
                                    <slide :index="1">
                                        <img  src="/src/assets/hrana.jpg">
                                    </slide>
                                    <slide :index="2">
                                        <img  src="/src/assets/restau.png">
                                    </slide>
                                    <slide :index="3">
                                        <img  src="/src/assets/delivery.png">
                                    </slide> -->
                                    <!-- <slide v-for="(slide, i) in loadRestaurant.logo" :index="i" :key="i">
                                        <template slot-scope="{ index, isCurrent, leftIndex, rightIndex }">
                                            <img :data-index="index" :class="{ current: isCurrent, onLeft: (leftIndex >= 0), onRight: (rightIndex >= 0) }" :src="slide.src">
                                        </template>
                                    </slide> -->
                                </carousel-3d>
                            </div>
                            <div  class="text-center">
                                <button @click="showComments()" class="btn btn-primary">Komentari</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>Naziv restorana:</label>
                        </td>
                        <td>
                           
                            <label>{{loadRestaurant.name}}</label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>Tip restorana:</label>
                        </td>
                        <td>
                            <label>{{loadRestaurant.type}}</label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>Menadzer restorana:</label>
                        </td>
                        <td>
                            <label>{{loadRestaurant.manager}}</label>
                            <button class="btn btn-primary" @click="managerProfile()">Profil</button>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>Ocena restorana({{ocenaRestorana}}):</label>
                            <!-- <button class="btn btn-primary" style="background:white">{{ocenaRestorana}}</button> -->
                        </td>
                        <td>
                            <starrating v-model="loadRestaurant.ocena" :star-size="30" read-only></starrating>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>Adresa restorana:</label>
                        </td>
                        <td>
                            <div style="display:inline-block">
                                <label style="width:100%">{{loadRestaurant.lokacija.ulica}} {{loadRestaurant.lokacija.broj}} {{loadRestaurant.lokacija.mesto}} {{loadRestaurant.lokacija.drzava}}</label>
                            </div>
                        </td>
                    </tr>
                    
                </tbody>
                <tr>
                    <td colspan="2">
                        <GmapMap
                            :center="center"
                            :zoom="15"
                            map-type-id="terrain"
                            style="margin-bottom:200px;width: 100%; height: 500px;"
                            >
                            <GmapMarker
                                :key="index"
                                v-for="(m, index) in lokacije"
                                :position="m.position"
                                :clickable="true"
                                :draggable="true"
                                @click="center=m.position"
                            />
                        </GmapMap>
                    </td>
                </tr>
            </table>
            <div class="container" style="min-height:300px;">
                <h2>Pregled ponude restorana</h2>
                 <p>Prikaz artikala u ponudi...</p>
                <section style="margin-left: 10px; margin-bottom:100px">
                    <div>
                    <!-- if we are 3 cards wide start a new row -->
                        <div class="row">
                            <div class="col-md-6" v-bind:key="index" v-for="(tempArtikal, index) in loadRestaurant.menuItems">
                                <div style="margin-bottom:30px;" class="card h-100">
                                    <img class="card-img-top" style="width:100%;height:50%;" :src="tempArtikal.slika" alt="card image collar">
                                    <div class="card-body">
                                        <h5 class="card-title">Artikal: {{tempArtikal.naziv}}</h5>
                                        <p class="card-text">Cena: {{tempArtikal.cena}}</p>
                                        <input id="kolicina" v-model="inputValues[index]" style="width:50%" type="number" ref="inputValue" placeholder="Kolicina..." min="0">
                                        <button v-on:click="addToCart(tempArtikal, index)" class="btn btn-primary">Dodaj</button>
                                        <button v-if="isCartEmpty" v-on:click="removeFromCart(tempArtikal, index)" class="btn btn-warning">Ponisti</button>
                                        <br>
                                        <p class="card-text" style="margin-top:10px;font-weight:800">Ukupno: {{calculatePrice(tempArtikal, index)}} din.</p>
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
import starRating from 'vue-star-rating'
import {Carousel3d, Slide} from 'vue-carousel-3d'

export default {
    mounted(){
        
        // this.setLocationLatLng();
         setTimeout(() => this.getLocationFromAddress(), 1500);
        // this.getLocationFromAddress();
    },
    
    data(){
        return{

           
            //mapa varijable
            center: { 
                lat: 39.7837304, 
                lng: -100.4458825 
            },
            lokacije:[],

            loadRestaurant:{
                name:'',
                type:'',
                menuItems:[],
                opened:true,
                lokacija:{
                    ulica:'',
                    broj:'',
                    mesto:'',
                    drzava:'',
                    xCoord:0,
                    yCoord: 0,
                },
                logo:'',
                comments:[],
                manager:'',
                ocena:0
            },

            //umesto tempProduct-a napravim slozeniji objekat sa poljem tipa string i listom kao atributima
            tempRestaurantOrder:{
                izKogRestorana: '',
                listOfProducts:[],
            },

             tempProduct:{
                izKogRestorana : '',
                nazivArtikla: '',
                datumIVremePorudzbine : '',
                cenaJedinice : '',
                idKupca : '',
                brKomada : '',
                artikal : ''
            },
            inputValues : [],
        }
    },
    methods:{
        removeFromCart(product, index){
            let tempUsername = JSON.parse(localStorage.getItem('token')).username;
            console.log('Username: ' + tempUsername);
            let tempLista = JSON.parse(localStorage.getItem('shoppingList')) || [];
            for(let i = 0; i < tempLista.length; i++){
                if(tempLista[i].nazivArtikla == product.naziv){
                    console.log('Iz korpe obrisan artikal: ' + tempLista[i].nazivArtikla);
                    tempLista.splice(i, 1);
                    
                }
            }
            let test = JSON.stringify(tempLista)
            localStorage.setItem('shoppingList', test);
        },
        addToCart(product, index){
            let tempUsername = JSON.parse(localStorage.getItem('token')).username;
            console.log('Username: ' + tempUsername)

            //postavljanje ID-a restorana na naziv restorana iz koga se narucuje
            this.tempRestaurantOrder.izKogRestorana = this.loadRestaurant.name;

            this.tempRestaurantOrder.izKogRestorana = this.loadRestaurant.name;
            this.tempProduct.izKogRestorana = this.loadRestaurant.name;
            this.tempProduct.nazivArtikla = product.naziv;
            this.tempProduct.datumIVremePorudzbine = '',
            this.tempProduct.cenaJedinice = product.cena;
            this.tempProduct.artikal = product;
            this.tempProduct.idKupca = tempUsername;

            // this.tempProduct.brKomada = this.$refs.inputValue,
            // this.tempProduct.brKomada = document.getElementById('kolicina').value;


            this.tempProduct.brKomada = this.inputValues[index];
            
            console.log('Kolicina: ' + this.tempProduct.brKomada);
            // console.log('Kolicina: ' + document.getElementById('kolicina').value);
            // console.log('Ref inputValue: ' + this.$refs.inputValue)
            console.log('Dodato u localstorage: ' + this.tempProduct.nazivArtikla + ', komada: ' + this.tempProduct.brKomada)
            let tempLista = JSON.parse(localStorage.getItem('shoppingList'));
            if(tempLista == null){
                console.log('tempLista je prazna')
                tempLista = [];
            }

            //petlja za proveru da li je iz istog restorana vec narucen taj artikal
            let vecPostoji = false;
            let indeksRestorana = 0;
            if(tempLista.length > 0){
                console.log('uslo u petlju')
                for(let cntr = 0; cntr < tempLista.length; cntr++){
                //ako postoji nesto naruceno iz tog restorana
                    if(tempLista[cntr].izKogRestorana == this.loadRestaurant.name){
                        indeksRestorana = cntr;
                        for(let i = 0; i < tempLista[cntr].listOfProducts.length; i++){
                            //ako postoji taj artikal porucen iz tog restorana
                            if(tempLista[cntr].listOfProducts[i].nazivArtikla == product.naziv){
                                console.log('Broj komada pre dodavanja: ' + tempLista[cntr].listOfProducts[i].brKomada);
                                console.log('Broj komada za dodavanje: ' + this.inputValues[index]);
                                let ukupno = parseInt(tempLista[cntr].listOfProducts[i].brKomada) + parseInt(this.inputValues[index]);
                                tempLista[cntr].listOfProducts[i].brKomada = ukupno.toString();
                                vecPostoji = true;
                            }
                        }
                        // if(tempLista[cntr].nazivArtikla == product.naziv){
                        //     console.log('Broj komada pre dodavanja: ' + tempLista[cntr].brKomada);
                        //     console.log('Broj komada za dodavanje: ' + this.inputValues[index]);
                        //     let ukupno = parseInt(tempLista[cntr].brKomada) + parseInt(this.inputValues[index]);
                        //     tempLista[cntr].brKomada = ukupno.toString(); 
                        //     vecPostoji = true;
                        // }
                    }
                }
            }
            

            if(vecPostoji == false){
                if(tempLista.length > 0){
                    console.log('Broj elemenata u shopping listi pre unosa: ' + tempLista[indeksRestorana].listOfProducts.length);
                    tempLista[indeksRestorana].listOfProducts.push(this.tempProduct);
                    console.log('Broj elemenata u shopping listi posle unosa: ' + tempLista[indeksRestorana].listOfProducts.length);
                    let test = JSON.stringify(tempLista);
                    localStorage.setItem('shoppingList', test);
                }
                else{
                    this.tempRestaurantOrder.izKogRestorana = this.loadRestaurant.name;
                    this.tempRestaurantOrder.listOfProducts.push(this.tempProduct);
                    tempLista.push(this.tempRestaurantOrder);
                    let test = JSON.stringify(tempLista);
                    localStorage.setItem('shoppingList', test);
                }
                
                
                // console.log('Broj elemenata u shopping listi pre unosa: ' + tempLista.length);
                // tempLista.push(this.tempProduct);
                // console.log('Broj elemenata u shopping listi posle unosa: ' + tempLista.length);
                // let test = JSON.stringify(tempLista);
                // localStorage.setItem('shoppingList', test);
            }else{
                console.log('aaaa')
                let test = JSON.stringify(tempLista);
                localStorage.setItem('shoppingList', test);
            }
            

            // let tempLista = Object.keys(localStorage.getItem('shoppingList'));
            // console.log('Broj elemenata u shopping listi: ' + tempLista.length);
            // tempLista.push(this.tempProduct);
            // let test = JSON.stringify(tempLista)
            // localStorage.setItem('shoppingList', test);
        },
        managerProfile(){
            this.$router.push(`/profile/${this.manager}`)
        },
        showComments(){
            this.$router.push(`/commentNew/${this.loadRestaurant.name}`);
        },
        getRestaurant(){
            dataService.getRestaurant(this.id).then(response => {
                this.loadRestaurant = response.data;
                console.log("Naziv pronadjenog restorana je: " + this.loadRestaurant.name);
                // console.log("string slike: " + this.loadRestaurant.logo)
            }).catch(error => {
                console.log(error.response);
            })
        },
        setLocationLatLng(){
            navigator.geolocation.getCurrentPosition(geolocation => {
                this.center = {
                    lat: geolocation.coords.latitude,
                    lng: geolocation.coords.longitude
                };
            });
        },
        getLocationFromAddress(){
            var geocoder = new google.maps.Geocoder();
            var str = this.loadRestaurant.lokacija.ulica + ', ' + this.loadRestaurant.lokacija.broj+ ', ' + this.loadRestaurant.lokacija.mesto+ ', ' + this.loadRestaurant.lokacija.drzava
            console.log('Adresa za koju ce traziti long i lat: ' + str)
            this.lokacije = [];
            var self = this;
            geocoder.geocode({'address': str}, function(results, status){
                if (status == google.maps.GeocoderStatus.OK) {
                    
                    console.log('status ok')
                    // this.center  = results[0].geometry.location
                    console.log('ispis rezultata: ' + results[0].geometry.location)

                    let tempString = results[0].geometry.location.toString();
                    let tempDuzina = tempString.length;
                    let tempString2 = tempString.slice(1, tempDuzina -2)
                    let tempSplits = tempString2.split(',')
                    let tempLat = parseFloat(tempSplits[0])
                    let tempLng = parseFloat(tempSplits[1])
                    let tempMarker = {
                        position:{
                            lat : tempLat,
                            lng : tempLng
                        }
                        
                    }
                    console.log('Broj markera pre: ' + self.lokacije.length)
                    self.lokacije.push(tempMarker)
                    console.log('Broj markera posle: ' + self.lokacije.length)
                    self.center = results[0].geometry.location;
                    self.loadRestaurant.lokacija.xCoord = tempLat;
                    self.loadRestaurant.lokacija.yCoord = tempLng;
                }else {
                }
            });                
        },
        calculatePrice(product, index){
            if(this.inputValues[index] == 0){
                return 0;
            }else{
                let result = this.inputValues[index] * product.cena;
            return result;
            }
            
        },
    },
    computed:{
        
        isCartEmpty(){
            let tempLista = JSON.parse(localStorage.getItem('shoppingList')) || [];
            if(tempLista.length == 0){
                return false;
            }else{
                return true;
            }
        },
        ocenaRestorana(){
            let tempSize = this.loadRestaurant.comments.length;
            let tempOcena = 0
            for(let i = 0; i < tempSize; i++){
                tempOcena += this.loadRestaurant.comments[i].ocena;
            }
            let ocena = tempOcena/tempSize;
            return  parseInt(ocena);
        },
        id(){
            return this.$route.params.id;
        },
        getOtherImgs: function () {
            console.log("getOtherImgs: ");
            //Prva slika mora da se manuelno postavi, a ostale se dodaju preko v-for:
            let imgs = this.loadRestaurant.images.slice(1);
            //Ako ima samo jednu sliku onda se sklanjaju strelice < > za kretanje kroz slike.
            if (imgs.length === 0) {
                console.log("imgs.lenght je = 0 ");
                this.isOtherImgs = false;
                console.log("this.isOtherImgs: " + this.isOtherImgs);
            } else {
                //vec je true, ali za svaki slucaj
                this.isOtherImgs = true;
                return imgs;
            }
    },
    },
    created(){
        // if(JSON.parse(localStorage.getItem('token')) == null){
        //     this.$router.push(`/login`);
        // }else{
        //     this.getRestaurant();
        // }
        this.getRestaurant();
    },
    components:{
        starrating : starRating,
        carousel3d:Carousel3d,
        slide:Slide,
    }





}
</script>

<style scoped>
#table_restaurantdetails{
    border-style: solid;
}
#table_restaurantdetails tr:nth-child(even){
    background-color: lightgray;
    font-weight: 600;
}
#table_restaurantdetails tr:nth-child(odd){
    font-weight: 600;
    background-color: white;
}
#table_restaurantdetails td:first-child{
    width: 30%;
}
#table_restaurantdetails td:last-child{
    width: 100%;
}
input{
    width: 100%;
}

</style>