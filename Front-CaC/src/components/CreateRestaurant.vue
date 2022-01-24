<template>
    <div style="width:50%">
        <div class="container">
            <h2>Kreiranje novog restorana</h2>
            <p>Popunite formular kako biste kreirali novi restoran...</p>
            <table class="table"  id="table_newrestaurant">
                <tbody>
                    <tr>
                        <td>
                            <label class='label'>Naziv restorana:</label>
                        </td>
                        <td>
                            <input type="text" placeholder="Unesite naziv restorana..."  v-model="newRestaurant.name">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class='label'>Tip restorana:</label>
                        </td>
                        <td>
                            <select style="width:100%;padding:5px;" v-model="newRestaurant.type">
                                <option disabled value="">Tip restorana</option>
                                <option  v-bind:key="tipRestorana.naziv"  v-for="tipRestorana in tipoviRestorana">{{tipRestorana.naziv}}</option> 
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class='label'>Restoran aktivan:</label>
                        </td>
                        <td>
                            <input  type="radio" value="true" v-bind="newRestaurant.opened">
                        </td>
                    </tr>
                    <tr>
                        <td>Adresa</td>
                        <td>
                            <div style="display:inline-block">
                                <input style="width:30%" class="input-class" type="text" placeholder="Naziv ulice..." v-model="adresa.ulica"/>
                                <input style="width:10%" class="input-class" type="number" min="0" placeholder="Broj..." v-model="adresa.broj"/>
                                <input style="width:30%" class="input-class" type="text" placeholder="Naziv mesta..." v-model="adresa.mesto"/>
                                <input style="width:25%" class="input-class" type="text" placeholder="Naziv drzave..." v-model="adresa.drzava"/>
                            </div>
                            <div style="display:inline-block;margin-top:20px;">
                                <button  @click="getLocationFromAddress()"  class='btn btn-warning'>Izracunaj koordinate</button>
                                <input style="display:inline-block;width:30%" type="text" disabled placeholder="Latitude..." v-model="adresa.xCoord">
                                <input style="display:inline-block;width:30%" type="text" disabled placeholder="Longitude..." v-model="adresa.yCoord">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>Odabir menadzera:</td>
                        <td>
                            <table id="tabela_5" class="table table-light">
                                <thead>
                                    <tr>
                                        <th class="text-center" colspan="2">Slobodni menadzeri:</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div style="height:200px; overflow-y: scroll;" class="container">
                                                <div class="col-ml-4" v-bind:key="tempManager.username" v-for="tempManager in slobodniMenadzeri">
                                                    <div class="card">
                                                        <div class="card-img-top" style="margin-top:10px; margin-bottom:10px">{{tempManager.username}}</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tfoot>
                                    <td colspan="2" class="text-center">
                                        <div>
                                            <button style="font-weight:600;display:inline-block;width:100px;" class="btn btn-warning"  @click="createManager()">Dodaj</button>
                                        </div> 
                                    </td>
                                </tfoot>
                            </table>
                        </td>
                    </tr>
                    <tr style="height:500px">
                        <td>Logo restorana:</td>
                        <td>
                            <table id="tabela_5" class="table table-dark">
                                <thead>
                                    <tr>
                                        <th class="text-center" colspan="2">Odabrani logo restorana(jedna slika):</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div style="height:300px; overflow-y: scroll;" class="container">
                                                <div class="col-ml-4" v-bind:key="tempSlika" v-for="tempSlika in logo">
                                                    <div class="card">
                                                        <img class="card-img-top" style="margin-top:10px; margin-bottom:10px" :src="tempSlika">
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tfoot>
                                    <td colspan="2" class="text-center">
                                        <div>
                                            <input  type="file" style="display:inline-block; width:70%" class="filestyle"  @change="uploadImage"/>
                                            <button style="font-weight:600;display:inline-block;width:100px;" class="btn btn-warning"  @click="ponistiIzborLoga()">Ponisti</button>
                                        </div> 
                                    </td>
                                </tfoot>
                            </table>
                        </td>
                    </tr>
                    <tr style="height:500px">
                        <td>Slike restorana:</td>
                        <td>
                            <table id="tabela_5" class="table table-dark">
                                <thead>
                                    <tr>
                                        <th class="text-center" colspan="2">Odabrane slike restorana(vise slika):</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div style="height:300px; overflow-y: scroll;" class="container">
                                                <div class="col-ml-4" v-bind:key="tempSlika" v-for="tempSlika in images">
                                                    <div class="card">
                                                        <img class="card-img-top" style="margin-top:10px; margin-bottom:10px" :src="tempSlika">
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tfoot>
                                    <td colspan="2" class="text-center">
                                        <div>
                                            <input  type="file" style="display:inline-block; width:70%" class="filestyle"  @change="uploadImage" multiple/>
                                            <button style="font-weight:600;display:inline-block;width:100px;" class="btn btn-warning"  @click="ponistiIzbor()">Ponisti</button>
                                        </div> 
                                    </td>
                                </tfoot>
                            </table>
                        </td>
                    </tr>

                </tbody>
            </table>
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
        </div>
        <!-- <div class="container">

            <div style="margin-top: 20px" v-if="messages.errorName" class="alert alert-danger" v-html="messages.errorName"></div>
            <div style="margin-top: 20px" v-if="messages.errorType" class="alert alert-danger" v-html="messages.errorType"></div>
            <div style="margin-top: 20px" v-if="messages.errorLogo" class="alert alert-danger" v-html="messages.errorLogo"></div>
            <div style="margin-top: 20px" v-if="messages.errorManager" class="alert alert-danger" v-html="messages.errorManager"></div>

            <label class='label'>Naziv restorana:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unesite naziv restorana..."  v-model="newRestaurant.name">
            <label class='label'>Tip restorana:</label>
            <select style="padding:5px;" v-model="newRestaurant.type">
                <option disabled value="">Tip restorana</option>
                <option  v-bind:key="tipRestorana.naziv"  v-for="tipRestorana in tipoviRestorana">{{tipRestorana.naziv}}</option> 
            </select><br>
            <label class='label'>Logo:</label>
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0">
                    <span>
                        <label>Images</label>
                        <input type="file" name="file" class="filestyle" multiple @change="uploadImage"/>
                    </span>
            </div>
        </div>


            
            <label class='label'>Menadzer:</label>
            <select  @change="addChoosenManager()" style="padding:5px;" v-model="newRestaurant.manager">
                <option disabled value="">Odabir slobodnih menadzera</option>
                <option v-bind:key="freeManager.username" v-for="freeManager in freeManagers">{{freeManager.username}}</option>
            </select>
            <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
            <div v-if='messages.errorResponse' class="alert alert-success" v-html="messages.errorResponse"></div>
            <button class="btn btn-success" v-on:click='createRestaurant()'>Potvrdi</button>
        </div> -->
    </div>
</template>


<script>

import dataService from '../services/DataService'

export default {
    mounted(){
        this.setLocationLatLng();
    },
    data(){
        return {
            //dodatne promenljive za mapu
            center: { 
                lat: 39.7837304, 
                lng: -100.4458825 
            },
            lokacije:[],

            //dodatne promenljive za prikaz loga i slika
            logo:[],
            images:[],
            //dodatna promenljiva za kupljenje podataka o adresi
            adresa:{
                ulica:'',
                broj:'',
                mesto:'',
                drzava:'',
                xCoord:'',
                yCoord:'',
            },
            //promenljiva koja ce ici na bek kao novoformirani restoran
            newRestaurant:{
                name:'',
                type:'',
                menuItems:null,
                opened:'',
                lokacija:{

                },
                images:[],
                comments:null,
                manager:'',
                ocena:''
            },
            //predefinisani tipovi restorana, za sada zakucani
            tipoviRestorana:[
                {
                    naziv:'porodicni'
                },
                {
                    naziv: 'fastfood'
                },
                {
                    naziv: 'caffee'
                },
                {
                    naziv: 'poslasticarnica'
                }
            ],

            //pomocne promenljive za razdvajanje zauzetih od slobodnih menadzera -> trebalo bi na beku da se uradi ovo
            freeManagers:[],
            allManagers:[],
            restaurantsWithManager:[],
            //pomocna promenljiva koja je kontejner poruka koje se ispisuju korisniku
            messages:{
                successResponse:'',
                errorResponse:'',
                errorName:'',
                errorType:'',
                errorLogo:'',
                errorManager:'',
                errorAddress:'',
            },
            
        }
            
    },
    methods:{
        createManager(){

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
            var str = this.adresa.ulica + ', ' + this.adresa.broj+ ', ' + this.adresa.mesto+ ', ' + this.adresa.drzava
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
                    self.adresa.xCoord = tempLat;
                    self.adresa.yCoord = tempLng;
                }else {
                }
            });                
        },
        addChoosenType:function(tipRestorana){
            this.newRestaurant.type = tipRestorana;
        },
        addChoosenManager:function(){
            for(let i = 0; i < this.freeManagers.length; i++){
                if(this.newRestaurant.manager == this.freeManagers[i].username){
                    this.newRestaurant.manager = this.freeManagers[i];
                    break;
                }
            }
            console.log("Za menadzera je postavljen: " + this.newRestaurant.manager.username);
        },
        getRestaurantsWithManagment:function(){
            dataService.getRestaurantsWithManager().then(response => {
                //dobijamo listu svih restorana koji imaju menadzera
                this.restaurantsWithManager = response.data;
                
                console.log("Restorani sa menadzerom: " + this.restaurantsWithManager.length);
                console.log("USERNAME JEDNOG OD MENADZERA: " + this.restaurantsWithManager[0].manager.name);
            })
        },
        getManagers:function(){
            console.log("GET ALL MANAGERS")
            dataService.getFreeManagers().then(response => {
                this.freeManagers = response.data;
                console.log("USERNAME JEDNOG OD MENADZERA 2: " + this.freeManagers[0].username);
                console.log("STIGLO SA BEKA:" + this.freeManagers[1].lastname);
                this.separateFreeManagers();
            })
        },
        separateFreeManagers(){
            //u tempManagers su privremeno smesteni SVI menadzeri iz baze
            // console.log("AAAAAAAAA")
            let tempManagers = this.freeManagers;
            this.freeManagers = [];
            // console.log("BROJ ELEMENATA U tempManagers: " + tempManagers.length);
            let menadzerImaZaduzenje = false;
            for(let i = 0; i < tempManagers.length; i++){
                for(let j = 0; j < this.restaurantsWithManager.length; j++){
                    if(tempManagers[i].username === this.restaurantsWithManager[j].manager.username){
                        console.log('uslo u if')
                        menadzerImaZaduzenje = true;
                        
                    }
                }
                
                if(menadzerImaZaduzenje === false){
                    // console.log("Ustanovljeno da menadzer: " + tempManagers[i].username + " ne radi u restoranu")
                    // console.log("DODAT JEDAN")
                    this.freeManagers.push(tempManagers[i]);
                }else{
                    menadzerImaZaduzenje = false;
                }
            }


        },
        getUsernames:function(){
            dataService.getAllManagers().then(response => {
                this.allManagers = response.data;
            })
        },
        createRestaurant:function(){
            if (this.newRestaurant.name == "") {
                this.messages.errorName = `<h4>Polje naziv restorana ne moze biti prazno!</h4>`;
                setTimeout(() => this.messages.errorName = '', 3000);
            }
            else if (this.newRestaurant.type == "") {
                this.messages.errorType = `<h4>Polje tip restorana ne moze biti prazno!</h4>`;
                setTimeout(() => this.messages.errorType = '', 3000);
            }
            else if (this.newRestaurant.images.length == 0) {
                this.messages.errorLogo= `<h4>Morate izabrati logo restorana!</h4>`;
                setTimeout(() => this.messages.errorLogo = '', 3000);
            }
            else if (this.newRestaurant.manager === "") {
                this.messages.errorManager = `<h4>Morate izabrati menadzera restorana!</h4>`;
                setTimeout(() => this.messages.errorManager = '', 3000);
            }
            else if (this.newRestaurant.adresa.xCoord === "" || this.newRestaurant.adresa.yCoord === "") {
                this.messages.errorAddress = `<h4>Niste izracunali koordinate lokacije!</h4>`;
                setTimeout(() => this.messages.errorManager = '', 3000);
            }

            else{
                console.log("NA BEK SE SALJE OBJEKAT newRestaurant: " + JSON.stringify(this.newRestaurant));
                dataService.addRestaurant(this.newRestaurant).then(response =>{
                    alert("Uspesno ste dodali restoran.");
                    this.$router.push("/home");
                });
            }

            
        },
        uploadImage:function(e){
            const reader = new FileReader();
            let image = e.target.files[0];
            console.log(image);
            reader.readAsDataURL(image);
            reader.onload = () => {
                console.log("uslo u onload")
                this.newRestaurant.images.push(reader.result);
                console.log("Broj slika: " + this.newRestaurant.images.length)
            }
            
        }
    },
    created(){
        // if(JSON.parse(localStorage.getItem('token')) == null){
        //     this.$router.push(`/login`);
        // }else{
        //     this.getRestaurantsWithManagment();
        //     this.getManagers();
        // }
        
    }
    
}
</script>

<style scoped>
#table_newrestaurant td:first-child{
    font-weight: 700;
    width:30%;
    
}
#table_newrestaurant tr:nth-child(even){
    background-color: lightgray;
    font-weight: 600;
}
#table_newrestaurant td:last-child{
    width:100%;
}
#table_newrestaurant{
    border-style: solid;
}
input{
    width: 100%;
}



</style>