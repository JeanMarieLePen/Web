<template>
    <div id="user-profile">
        <div>
            <div class="container" id='page-title'>
                <h1 style="margin-top:10px;"><span id='titleEffect'>Pregled porudzbine</span></h1>
                <hr style='background:#35424a;height:1px;'>
            </div>
            <div class="container" style="margin-bottom:200px;">
                <form>
                    <ul id="services" class="list-group">
                    <!-- Osoba -->
                        <li class="list-group-item" >
                            <h5 class="header5">ID porudzbine:</h5>
                            <h4>{{porudzbina.idPorudzbine}}</h4>
                        </li>
                        <li class="list-group-item" >
                            <h5 class="header5">Artikli koji su poruceni:</h5>
                            <div class="col-md-4" v-for="tempArtikal in porudzbina.listaNarucenihArtikala" :key="tempArtikal.naziv">
                                <div style="margin-bottom:10px;" class="card h-100">
                                    <!-- <img class="card-img-top" :src="product.thumbnailUrl" alt="card image collar"> -->
                                    <div class="card-body">
                                        <p class="card-text">{{tempArtikal.nazivArtikla}}</p>
                                        <p class="card-text">{{tempArtikal.brojKomada}}</p>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <h5 class="header5">Restoran iz kojeg je poruceno:</h5>
                            <h4>{{porudzbina.izKogRestorana}}</h4>
                        </li>
                        <li class="list-group-item" >
                            <h5 class="header5">Datum i vreme porudzbine:</h5>
                            <h4>{{porudzbina.datumIVremePorudzbine}}</h4>
                        </li>
                         <li class="list-group-item" >
                            <h5 class="header5">Cena:</h5>
                            <h4>{{porudzbina.cena}}</h4>
                        </li>
                         <li class="list-group-item" >
                            <h5 class="header5">Kupac:</h5>
                            <h4>{{porudzbina.idKupca}}</h4>
                        </li>
                         <li class="list-group-item" >
                            <h5 class="header5">Status porudzbine:</h5>
                            <h4>{{porudzbina.statusPorudzbine}}</h4>
                        </li>

                    </ul>
                </form>
               
            </div>
            
        </div>
    </div>
</template>

<script>
import dataService from '../services/DataService'

export default{
    created(){
        try{
            dataService.getOrderById(this.$route.params.id).then(response => {
                console.log("stigli podaci o porudzbini");
                this.porudzbina = response.data;
            });
        }catch(error){
            console.log("test1");
            this.porudzbina = this.testPorudzbina;
            console.log("test2");
        }
        
    },
    computed:{
        
    },
    methods:{
        entityDetails(tempId){
            this.$router.push(`/vikendicaOverview/${tempId}`);
        },
        subscriberDetails(tempId){
            this.$router.push(`/profile/${tempId}`);
        },
        removeEntity(tempId){
            this.profile.vikendice.filter(function(elem){
                for(let i = 0; i < this.profile.vikendice.length; i++){
                    if(this.profile.vikendice[i].id == tempId){
                        this.$delete(this.profile.vikendice, i);
                    }
                }
             });
        }
    },
    data() {
        return{
            porudzbina: null,
            testPorudzbina: {
                        id: '2323',
                        poruceniArtikli: [
                            {naziv: 'artikal1'},
                            {naziv: 'artikal2'}, 
                            {naziv: 'artikal3'}
                            ],
                        restoranIzKogJePoruceno:'restoran1',
                        datumivreme: 'datumivreme1',
                        cena: '200',
                        kupac: 'Marko Markovic',
                        Status: 'U obradi',
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


.header5{
    
    font-weight: bold;
}

.marg{
    margin-top:15px;
}
</style>