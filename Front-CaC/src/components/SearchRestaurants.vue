<template>
    <div id="restaurants-overview">
        <div class="container" id='page-title'>
            <h1 style="margin-top:10px;color:#35424a;">Lista <span id='titleEffect'>restorana</span></h1>
            <hr style='background:#35424a;height:1px;'>
        </div>
        
        <div class='container ' id='search'>
            <nav class="navbar navbar-light bg-light justify-content-between">
                <a class="navbar-brand">Pretraga restorana</a>
                <form class="form-inline">     
                    <div style='display:inline;' id="advanced_search">
                        <div id='second-row' class="row"  style="margin-top:5px;">
                            
                            <div class="input-group">
                                <span class="col-xl-3 col-md-6 mb-1">
                                <span class="span_search">Naziv restorana</span>
                                <input class="form-control mr-sm-2" type="text" placeholder="Naziv restorana" aria-label="Search" v-model="searchedRestoran.name">
                            </span>
                            <span class="col-xl-3 col-md-6 mb-3"> 
                                <span class="span_search">Tip restorana</span>
                                <select style="padding:5px;" v-model="searchedRestoran.type">
                                    <option disabled value="">Tip restorana </option>
                                    <option @select="ponistiIzbor()" value=''>Ponisti izbor</option>
                                    <option v-bind:key="tempTip.naziv"  v-for="tempTip in typesOfRestaurants">{{tempTip.naziv}}</option>
                                </select>
                            </span>
                            <span class="col-xl-3 col-md-6 mb-3"> 
                                <span class="span_search">Lokacija restorana</span>
                                <input class="form-control mr-sm-2" type="text" placeholder="Drzava..." aria-label="Search" v-model="searchedRestoran.lokacija.drzava">
                            </span>
                            <span class="col-xl-3 col-md-6 mb-3"> 
                                <span class="span_search">Ocena restorana</span>
                                <input class="form-control mr-sm-2" type="text" placeholder="Ocena restorana..." aria-label="Search" v-model="searchedRestoran.ocena">
                            </span>
                            </div>
                            
                            
                        </div><!--/second_row-->
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
                <span class="span_search">Filter restorana</span>
                <input v-model="filterInput">
                <span class="span_search">Prikaz samo otvorenih restorana</span>
                <input type="checkbox" v-model="filterOpenedOnly">
            </div>
            
            
            <table class="table">
                <thead>
                    <tr>
                        <th @click="sort('nazivRestorana')" class="arrow">Naziv
                            <img v-if='currentSortDir == "asc" && currentSort== "nazivRestorana"' src='../assets/up-arrow1.1.png'>
                            <img v-if='currentSortDir == "desc" && currentSort== "nazivRestorana" ' src='../assets/down-arrow1.1.png'>
                        </th>
                        <th>Tip</th>
                        <th @click="sort('lokacijaRestorana')" class="arrow">Lokacija
                            <img v-if='currentSortDir == "asc" && currentSort== "lokacijaRestorana"' src='../assets/up-arrow1.1.png'>
                            <img v-if='currentSortDir == "desc" && currentSort== "lokacijaRestorana" ' src='../assets/down-arrow1.1.png'>
                        </th>
                        <th @click="sort('ocenaRestorana')" class="arrow"> Ocena
                            <img v-if='currentSortDir == "asc" && currentSort== "ocenaRestorana"' src='../assets/up-arrow1.1.png'>
                            <img v-if='currentSortDir == "desc" && currentSort== "ocenaRestorana" ' src='../assets/down-arrow1.1.png'>
                        </th>
                        <th>Menadzer</th>
                        <th>Logo:</th>
                        <th>Otvoren</th>
                        <th>Detaljno</th>
                        <th>Komentarisi</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-bind:key='rst.name' v-for="rst in sortRestaurants">
                        <td>{{rst.name}}</td>
                        <td>{{rst.type}}</td>
                        <td>{{rst.lokacija.ulica}}, {{rst.lokacija.broj}}, {{rst.lokacija.mesto}}, {{rst.lokacija.drzava}}</td>
                        <!-- <td>{{rst.ocena}}</td> -->
                        <td><starrating read-only v-model="rst.ocena" :star-size="20"></starrating></td>
                        <td>{{rst.manager}}</td>
                        <td>
                            <img class="card-img-top" style="margin-top:10px; margin-bottom:10px" :src="rst.logo">
                        </td>
                        <td v-show='rst.opened'>{{"otvoren"}}</td>
                        <td v-show='!rst.opened'>{{"zatvoren"}}</td>
                        <td>
                            <button style="width:100px; height:40px;" @click="showDetails(rst.name)" class=' btn btn-sm classButton shadow'>Detalji</button>
                        </td>
                        <td>
                            <button style="width:100px; height:40px;" v-show="isCustomer" @click="comment(rst.name)" class=' btn btn-sm classButton shadow'>Komentarisi</button>
                        </td>
                    </tr>
                </tbody>
                <tfoot>
                    <tr style="height:100px;">
                        <td colspan="10"></td>
                    </tr>
                    <tr>
                        <td colspan="10">
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
                </tfoot>
            </table>
        </div>

    </div>
</template>

<script>
import dataService from '../services/DataService'
import starRating from 'vue-star-rating'


export default {
    mounted(){
        this.setLocationLatLng();
    },
    data(){
        return{
            //dodatne promenljive za mapu
            center: { 
                lat: 39.7837304, 
                lng: -100.4458825 
            },
            lokacije:[],



            filterOpenedOnly:false,
            filterInput:'',
            currentSort: 'ocenaRestorana',
            currentSortDir: 'asc',

            isCustomer:true,

            searchedRestoran:{
                id:'',
                name:'',
                type:'',
                menuItems:[],
                opened:'',
                lokacija:{
                    id:'',
                    ulica:'',
                    broj:'',
                    mesto:'',
                    drzava:'',
                    xCoord:'',
                    yCoord:'',
                },
                logo:'',
                comments:[],
                manager:'',
                ocena:''
            },
            displayedRestaurants:[],
        
            typesOfRestaurants:[
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
             messages: {
                errorResponse: '',
                successResponse: '',
            },
            sort(s){
                if(s == this.currentSort){
                    this.currentSortDir = this.currentSortDir === 'asc' ? 'desc' : 'asc';
                }
                this.currentSort = s;
            }
        }
        
    },
    computed:{
        sortRestaurants:function(){
            return this.displayedRestaurants.filter(this.filterByName).filter(this.filterByOpened).sort(this.sortiraj);
        }
    },
    components:{
        starrating : starRating,
    },

    methods:{
        ponistiIzbor(){
            this.searchedRestoran.type = '';
        },
        setLocationLatLng(){
            navigator.geolocation.getCurrentPosition(geolocation => {
                this.center = {
                    lat: geolocation.coords.latitude,
                    lng: geolocation.coords.longitude
                };
            });
        },
        sortiraj(){
            if(this.currentSort == 'nazivRestorana'){
                if(this.currentSortDir == 'asc'){
                    return this.displayedRestaurants.sort((a, b) => (a.name > b.name) ? 1 : -1)
                }
                else{
                    return this.displayedRestaurants.sort((a, b) => (a.name < b.name) ? 1 : -1)
                }
            }
            if(this.currentSort == 'ocenaRestorana'){
                if(this.currentSortDir == 'asc'){
                    return this.displayedRestaurants.sort((a, b) => (a.ocena > b.ocena) ? 1 : -1)
                }
                else{
                    return this.displayedRestaurants.sort((a, b) => (a.ocena < b.ocena) ? 1 : -1)
                }
            }
            if(this.currentSort == 'lokacijaRestorana'){
                if(this.currentSortDir == 'asc'){
                    return this.displayedRestaurants.sort((a, b) => (a.lokacija > b.lokacija) ? 1 : -1)
                }
                else{
                    return this.displayedRestaurants.sort((a, b) => (a.lokacija < b.lokacija) ? 1 : -1)
                }
            }
        },
        search(){
            
            console.log("na bek ide restoran: " + JSON.stringify(this.searchedRestoran));
            let zahtev = 'name:';
            if(this.searchedRestoran.name != ""){
                zahtev += this.searchedRestoran.name;
            }
            else{
                zahtev += "_";
            }
            zahtev += "&lokacija:"
            if(this.searchedRestoran.lokacija.drzava != ''){
                zahtev += this.searchedRestoran.lokacija.drzava;
            }
            else{
                zahtev += "_";
            }
            zahtev += "&type:";
            if(this.searchedRestoran.type != ""){
                zahtev += this.searchedRestoran.type;
            }
            else{
                zahtev += "_";
            }
            zahtev += "&ocena:";
            if(this.searchedRestoran.ocena != ""){
                zahtev += this.searchedRestoran.ocena;
            }
            else{
                zahtev += "_";
            }
            console.log("ZAHTEV: " + zahtev);
            dataService.searchRestaurant(zahtev).then(response =>{
                console.log("Stigli rezultati pretrage");
                this.lokacije = [];
                this.displayedRestaurants = response.data;
                for(let i = 0; i < this.displayedRestaurants.length; i++){
                    console.log('dodata lokacija')
                    this.lokacije.push({position:{lat: this.displayedRestaurants[i].lokacija.xCoord, lng : this.displayedRestaurants[i].lokacija.yCoord}});
                    
                }
                this.center.lat = this.displayedRestaurants[0].lokacija.xCoord;
                this.center.lng = this.displayedRestaurants[0].lokacija.yCoord;
            }).catch(error => {
                console.log(error.response);
            });
        },
        filterReset(){
            this.searchedRestoran.name = null;
            this.searchedRestoran.type = null;
            this.searchedRestoran.lokacija = null;
            this.searchedRestoran.ocena = null;
            this.searchedRestoran.logo = null;
            this.searchedRestoran.manager = null;
        },
        getAllRestaurants(){
            dataService.getAllRestaurants().then(response => {
                this.displayedRestaurants = response.data;
                this.lokacije = [];
                this.displayedRestaurants = response.data;
                for(let i = 0; i < this.displayedRestaurants.length; i++){
                    console.log('dodata lokacija')
                    this.lokacije.push({position:{lat: this.displayedRestaurants[i].lokacija.xCoord, lng : this.displayedRestaurants[i].lokacija.yCoord}});
                    
                }
            })
        },
        filterByName:function(rst){
            if(this.filterInput.length != 0){
                return  (rst.name.toLowerCase().indexOf(this.filterInput.toLowerCase()) > -1);
            }
            return true;
        },
        filterByOpened:function(rst){
            if(this.filterOpenedOnly == true){
                return (rst.opened == true);
            }
            return true;
            
        },
        filterChange(){
            console.log("ispis filtera: " + this.filterOpenedOnly)
        },
        showDetails:function(id){
            this.$router.push(`/restaurants/${id}/details`);
        },
        comment:function(id){
            this.$router.push(`/commentNew/${id}`)
        },
        resetFilter:function(){
            this.searchedPorudzbine.datumOd =  null;
            this.searchedPorudzbine.datumDo =  null;
            this.searchedPorudzbine.cenaOd =  null;
            this.searchedPorudzbine.cenaDo =  null;
            this.searchedPorudzbine.nazivRestorana = null;

            this.getAllRestaurants();
        },
    },
    created(){
        // if(JSON.parse(localStorage.getItem('token')) == null){
        //     this.$router.push(`/login`);
        // }else{
        //     this.getAllRestaurants();
        // }
        this.getAllRestaurants();
    }
}
</script>


<style scoped>
#titleEffect{
  color:gold;
  font-weight: bold;
}

#second_raw{
    margin-top:5px;
}

#third_raw{
    margin-top:5px;
}

#fourth_raw{
    margin-top:5px;
}

.span_search{
    /* padding:5px; */
    padding-top:5px;
    padding-left:5px;
    padding-right:5px;
    color:#35424a;
    font-weight: bold;
}
#search_icon:hover{
    cursor: pointer;
    
}

.arrow:hover{
    cursor: pointer;
}


.classButton{
  margin-left: 5px;
  color:#fff;
  background-color:#35424a;
  padding:5px;
}

.classButton:hover{
  margin-left: 5px;
  color:#fff;
  background-color:gold;
  font-weight: bold;
  padding:10px;
}

form{
    position: relative;
}

form #search_icon{
    position: absolute;
    right:15px;
    top:-15px;
}


</style>