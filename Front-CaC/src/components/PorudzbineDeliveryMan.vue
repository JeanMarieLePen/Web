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
        
    },
    methods:{
        takeOrder(order, index){
            order.idDeliveryMana = this.username;
            order.statusPorudzbine = 'uTransportu';
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
        
    },
    data() {
        return{
            username: '',
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