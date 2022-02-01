<template>
    <div id="user-profile">
        <div>
            <div class="container" id='page-title'>
                <h1 style="margin-top:10px;"><span id='titleEffect'>Pregled porudzbina</span></h1>
                <hr style='background:#35424a;height:1px;'>
            </div>
            <div class="container" style="margin-bottom:100px;max-height:600px; overflow:auto;">
                <h2>Pregled svih porudzbina</h2>
                <section style="margin-left: 10px; margin-bottom:10px">
                    <div>
                    <!-- if we are 3 cards wide start a new row -->
                        <div class="row">
                            <div class="col-md-2" v-bind:key="tempPorudzbina.idPorudzbine" v-for="tempPorudzbina in porudzbine">
                                <div style="margin-bottom:30px;" class="card h-100">
                                    <!-- <img class="card-img-top" :src="product.thumbnailUrl" alt="card image collar"> -->
                                    <div style="" class="card-body">
                                        <h5 class="card-title">Porudzbina ID: {{tempPorudzbina.idPorudzbine}}</h5>
                                        <p class="card-text">Cena: {{tempPorudzbina.cena}}</p>
                                        <p class="card-text">Kupac: {{tempPorudzbina.idKupca}}</p>
                                        <button v-on:click="entityDetails(tempPorudzbina.idPorudzbine)" class="btn btn-primary">Detalji</button>
                                       
                                        <button v-show="tempPorudzbina.statusPorudzbine == 'uPripremi'" style="margin-top:10px;" v-on:click="approveOrder(tempPorudzbina)" class="btn btn-warning">Odobri</button>
                                        <button v-show="tempPorudzbina.statusPorudzbine == 'obrada'" style="margin-top:10px;" v-on:click="acceptOrder(tempPorudzbina)" class="btn btn-success">Prihvati</button>
                                        <button v-show="tempPorudzbina.statusPorudzbine == 'zahtevZaDostavu'" style="margin-top:10px;" v-on:click="approveDeliveryMan(tempPorudzbina)" class="btn btn-success">Dodeli</button>
                                        <p class="card-text">Status: {{tempPorudzbina.statusPorudzbine}}</p>
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
        this.fetchRestaurant(tempUsername);
        // setTimeout(() => this.fetchAllOrders(), 300)
        // this.fetchAllOrders();
        
    },
    computed:{
        
    },
    methods:{
        approveDeliveryMan(order){
            order.statusPorudzbine = 'uTransportu';
            dataService.approveDeliveryMan(order).then(response => {
                console.log('prihvacen dostavljac');
                tempPorudzbina.statusPorudzbine = response.data.statusPorudzbine;
            }).catch(error => {
                console.log(error.response);
            });
        },
        acceptOrder(order){
            dataService.acceptOrder(order.idPorudzbine).then(response => {
                console.log('prihvacena porudzbina id-a: ' + order.idPorudzbine);
                tempPorudzbina.statusPorudzbine = response.data.statusPorudzbine;
            }).catch(error => {
                console.log(error.response);
            });
        },
        approveOrder(tempPorudzbina){
            dataService.approveOrder(tempPorudzbina.idPorudzbine).then(response => {
                console.log('odobrena porudzbina id-a: ' + tempPorudzbina.idPorudzbine);
                tempPorudzbina.statusPorudzbine = response.data.statusPorudzbine;
            }).catch(error => {
                console.log(error.response);
            });
        },
        fetchAllOrders(){
            dataService.getSvePorudzbine(this.restaurant.name).then(response => {
                console.log("stigli podaci o porudzbinama za svoj restoran");
                this.porudzbine = response.data;
                
            }).catch(error => {
                console.log(error.response);
            });
        },
        fetchRestaurant(username){
            dataService.getRestaurantByManager(username).then(response => {
                console.log('naziv restorana je: ' + response.data.name);
                this.restaurant = response.data;
                this.fetchAllOrders();
            }).catch(error => {
                console.log(error.response);
            });
        },
        entityDetails(tempId){
            this.$router.push(`/order/${tempId}`);
        },
    },
    data() {
        return{
            //pomocna varijabla za dobavljanje podataka o restoranu
            restaurant:null,
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
            
        porudzbine:[],
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