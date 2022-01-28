<template>
    <div>
        <div class="container" style="min-height:300px;">
            <h2 style="margin-left:50px;">Korpa</h2>
                <p style="margin-left:50px;">Prikaz sadrzaja dodatog u korpu...</p>
            <section style="margin-left: 10px; margin-bottom:100px">
                <div>
                <!-- if we are 3 cards wide start a new row -->
                    <div class="row">
                        <div class="col-md-6" v-bind:key="index" v-for="(tempArtikal, index) in sadrzajKorpe">
                            <div style="margin-bottom:30px;" class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">Artikal: {{tempArtikal.nazivArtikla}}</h5>
                                    <p class="card-text">Restoran: {{tempArtikal.idRestorana}}</p>
                                    <p class="card-text">Cena: {{tempArtikal.cenaJedinice}}</p>
                                    <input id="kolicina" v-model="inputValues[index]" style="width:50%" type="number" ref="inputValue" placeholder="Kolicina..." min="0">
                                    <button v-on:click="changeInCart(tempArtikal, index)" class="btn btn-primary">Dodaj</button>
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
            <div class="text-center">
                <button @click="createOrder()" class="btn btn-success">Naruci</button>
            </div>
        </div>
    </div>
</template>



<script>
export default {
    data(){
        return{
            sadrzajKorpe:[],
            inputValues:[],
        }
    },
    created(){
        this.sadrzajKorpe = JSON.parse(localStorage.getItem('shoppingList')) || [];
        let tempLista = JSON.parse(localStorage.getItem('shoppingList')) || [];
        for(let i = 0; i < tempLista.length; i++){
            this.inputValues.push(tempLista[i].brKomada);
        }
    },
    methods:{
        createOrder(){
            let tempListofOrders = [];
            for(let i = 0; i < this.sadrzajKorpe.length; i++){
                for(let j = 0; j < tempListofOrders.length; j++){
                    if(tempListofOrders[j].idRestorana != )
                }
            }
        },
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
        changeInCart(product, index){
            let tempUsername = JSON.parse(localStorage.getItem('token')).username;
            console.log('Username: ' + tempUsername)

            product.brKomada = this.inputValues[index];

            // this.tempProduct.brKomada = this.$refs.inputValue,
            // this.tempProduct.brKomada = document.getElementById('kolicina').value;
            // console.log('Kolicina: ' + document.getElementById('kolicina').value);
            // console.log('Ref inputValue: ' + this.$refs.inputValue)
            let tempLista = JSON.stringify(this.sadrzajKorpe);
            localStorage.setItem('shoppingList', tempLista);
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
    },
}
</script>



<style scoped>


</style>