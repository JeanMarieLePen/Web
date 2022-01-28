<template>
    <div id="user-profile">
        <div>
            <div class="container" id='page-title'>
                <h1 style="margin-top:10px;"><span id='titleEffect'>Pregled porudzbina</span></h1>
                <hr style='background:#35424a;height:1px;'>
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

            <div class="container" style="margin-bottom:100px;max-height:300px; overflow:auto;">
                <h2>Pregled svih svojih nedostavljenih porudzbina</h2>
                <section style="margin-left: 10px; margin-bottom:10px">
                    <div>
                    <!-- if we are 3 cards wide start a new row -->
                        <div class="row">
                            <div class="col-md-2" v-bind:key="tempPorudzbina.id" v-for="tempPorudzbina in nedostavljenePorudzbine">
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
                <h2>Pregled porudzbina koje cekaju dostavljaca</h2>
                <section style="margin-left: 10px; margin-bottom:100px">
                    <div>
                    <!-- if we are 3 cards wide start a new row -->
                        <div class="row">
                            <div class="col-md-4" v-bind:key="tempPorudzbina.id" v-for="tempPorudzbina in porudzbineCekanju">
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

export default{
     created(){
        try{
             this.dataService.getZaduzenePorudzbine(this.$route.params.id).then(response => {
            console.log("stigli podaci o svojim porudzbinama");
            this.porudzbine = response.data;
            });
            this.dataService.getSvojeNedostavljenePorudzbine(this.$route.params.id).then(response => {
            console.log("stigli podaci o svojim porudzbinama koje su nedostavljene");
            this.nedostavljenePorudzbine = response.data;
            });
            this.dataService.getSveCekanje().then(response => {
            console.log("stigli podaci o porudzbinama koje cekaju dostavljaca");
            this.porudzbineCekaju = response.data;
            });
        }catch(error){
            // this.profile = this.profileTemp;
            
            this.porudzbine=this.testPorudzbina;
            
            this.nedostavljenePorudzbine = this.testNedostavljenihPorudzbina;
            this.porudzbineCekanju = this.testPorudzbinaCekanje;
        }
        
    },
    computed:{
        
    },
    methods:{
        entityDetails(tempId){
            this.$router.push(`/order/${tempId}`);
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

            testPorudzbinaCekanje: [
                    {
                        id:'223232',
                        poruceniArtikli: [
                            {naziv: 'artikal1cekanje'},
                            {naziv: 'artikal2cekanje'}, 
                            {naziv: 'artikal3cekanje'}
                            ],
                        restoranIzKogJePoruceno:'restoran1cekanje',
                        datumivreme: 'datumivreme1cekanje',
                        cena: '200',
                        kupac: 'Marko Markovic',
                        Status: 'Ceka dostavljaca',
                    },
                     {
                         id:'2233232cekanje',
                        poruceniArtikli: [
                            {naziv: 'artikal13cekanje'},
                            {naziv: 'artikal23'}, 
                            {naziv: 'artikal33'}
                            ],
                        restoranIzKogJePoruceno:'restoran1cekanje',
                        datumivreme: 'datumivreme13',
                        cena: '2030',
                        kupac: 'Marko Markovic3cekanje',
                        Status: 'Ceka dostavljaca3cekanje',
                    },
            ],
            
        porudzbine:[],
        nedostavljenePorudzbine:[],
        porudzbineCekanju:[],
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
    color:#1E90FF;
    font-weight: bold;
}

.marg{
    margin-top:15px;
}
</style>