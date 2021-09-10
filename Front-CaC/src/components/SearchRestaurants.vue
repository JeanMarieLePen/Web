<template>
    <div id="restaurants-overview">
        <div class="container" id='page-title'>
            <h1 style="margin-top:10px;color:#35424a;">Lista <span id='titleEffect'>restorana</span></h1>
            <hr style='background:#35424a;height:1px;'>
        </div>
        
        <div class='container ' id='search'>
            <nav class="navbar navbar-light bg-light justify-content-between">
                <a class="navbar-brand">Search</a>
                <form class="form-inline">     
                       
                        <div style='display:inline;' id="advanced_search">
                            <div id='second-row' class="row"  style="margin-top:5px;">
                                <span class="col-xl-2 col-md-6 mb-1">
                                    <button style='margin-right:5px;' class='btn btn-outline-primary my-2 my-sm-0' v-on:click="resetFilter()">Reset all</button>
                                </span> 
                                <span class="col-xl-3 col-md-6 mb-1">
                                    <span class="span_search">Naziv restorana</span>
                                    <input class="form-control mr-sm-2" type="text" placeholder="Naziv restorana" aria-label="Search" v-model="searchedRestoran.name">
                                </span>
                                <span class="col-xl-3 col-md-6 mb-3"> 
                                    <span class="span_search">Tip restorana</span>
                                    <select style="padding:5px;" v-model="searchedRestoran.type">
                                        <option disabled value="">Tip restorana </option>
                                        <option v-bind:key="tempTip.naziv"  v-for="tempTip in typesOfRestaurants">{{tempTip.naziv}}</option>
                                    </select>
                                </span>
                                <span class="col-xl-3 col-md-6 mb-3"> 
                                    <span class="span_search">Lokacija restorana</span>
                                    <input class="form-control mr-sm-2" type="text" placeholder="Country/City" aria-label="Search" v-model="searchedRestoran.lokacija">
                                </span>
                                
                            </div><!--/second_row-->
                            <span class="col-xl-3 col-md-6 mb-2"> 
                                <button style="margin-left: 5px;" class="btn btn-outline-success my-2 my-sm-0" type="button" v-on:click.prevent='searchRestaurant()'>Search</button>
                            </span>
                        </div><!--/advanced_search-->
                        <div v-if='messages.errorResponse' class="alert alert-danger" v-html="messages.errorResponse"></div>
		    <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>  
                </form>
            </nav>
        </div>
        <div class="container" id='main'>
            <span class="span_search">Filter restorana</span>
            <input v-model="filterInput">
            <span class="span_search">Prikaz samo otvorenih restorana</span>
            <input type="checkbox" v-model="filterOpenedOnly">
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
                        <!-- <th>Logo</th> -->
                        <th>Otvoren</th>
                        <th>Detaljno</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-bind:key='rst.name' v-for="rst in sortRestaurants">
                        <td>{{rst.name}}</td>
                        <td>{{rst.type}}</td>
                        <td>{{rst.lokacija}}</td>
                        <td>{{rst.ocena}}</td>
                        <td>{{rst.manager.username}}</td>
                        <!-- <td>{{rst.images}}</td> -->
                        <td v-show='rst.opened'>{{"otvoren"}}</td>
                        <td v-show='!rst.opened'>{{"zatvoren"}}</td>
                        <td>
                            <button @click="showDetails(rst.name)" class=' btn btn-sm classButton shadow'>Detaljni prikaz</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

    </div>
</template>

<script>
import dataService from '../services/DataService'
export default {
    data(){
        return{
            filterOpenedOnly:false,
            filterInput:'',
            currentSort: 'ocenaRestorana',
            currentSortDir: 'asc',



            searchedRestoran:{
                name:'',
                type:'',
                menuItems:null,
                opened:'',
                lokacija:'',
                images:null,
                comments:null,
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
            locations:[
                {
                    country:'Serbia',
                    city:'Novi Sad'
                },
                {
                    country:'Serbia',
                    city:'Belgrade'
                },
                {
                    country:'Serbia',
                    city:'Nis'
                },
                {
                    country:'Serbia',
                    city:'Pristina'
                },
                {
                    country:'Serbia',
                    city:'Subotica'
                }
            ],
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

    methods:{
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
        searchRestaurant(){
            console.log("na bek ide restoran: " + JSON.stringify(this.searchedRestoran));
            let zahtev = 'name:';
            if(this.searchedRestoran.name != ""){
                zahtev += this.searchedRestoran.name;
            }
            else{
                zahtev += "_";
            }
            zahtev += "&lokacija:"
            if(this.searchedRestoran.lokacija != ""){
                zahtev += this.searchedRestoran.lokacija;
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
                this.displayedRestaurants = response.data;
            })
        },
        filterReset(){
            this.searchedRestoran.name = null;
            this.searchedRestoran.type = null;
            this.searchedRestoran.lokacija = null;
            this.searchedRestoran.prosecnaOcena = null;
            this.searchedRestoran.logo = null;
            this.searchedRestoran.manager = null;
        },
        getAllRestaurants(){
            dataService.getAllRestaurants().then(response => {
                this.displayedRestaurants = response.data;
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
        }
    },
    created(){
        if(JSON.parse(localStorage.getItem('token')) == null){
            this.$router.push(`/login`);
        }else{
            this.getAllRestaurants();
        }
        
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