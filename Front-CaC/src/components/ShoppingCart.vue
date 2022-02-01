<template>
    <div>
        <div class="container" style="min-height:300px;">
            <h2 >Korpa</h2>
                <p >Prikaz sadrzaja dodatog u korpu...</p>
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
            <div style="font-weight: 700;border-top: 1px solid #000; font-size:20px;" class="text-left">
                <label>Suma: {{calculateSum}} din.</label>
            </div>
            <div style="border-top: 1px solid #000;margin-bottom: 100px;" class="text-center">
                
                <button style="margin-top:20px;" @click="createOrder()" class="btn btn-success">Naruci</button>
            </div>
            <div v-if='messages.errorReport' class="alert alert-danger" v-html="messages.errorReport"></div>
		    <div v-if='messages.successReport' class="alert alert-success" v-html="messages.successReport"></div>  
        </div>
    </div>
</template>



<script>
import dataService from '../services/DataService';
export default {
    data(){
        return{
            sadrzajKorpe:[], 
            inputValues:[],

            //pomocne promenljive za ispis poruka o stanju porudzbine
            messages:{
                successReport:'',
                errorReport:'',
            },

            order: {
                // idPorudzbine:'',
                listaNarucenihArtikala : [],
                izKogRestorana : '',
                datumIVremePorudzbine : '',
                cena: '',
                idKupca : '',
                statusPorudzbine : 'cekaDostavljaca',
                idDeliveryMana : '',
            }
        }
    },
    created(){
        let tempSadrzajKorpe = JSON.parse(localStorage.getItem('shoppingList')) || [];
        console.log('Duzina tempSadrzajKorpe: ' + tempSadrzajKorpe.length)
        this.sadrzajKorpe = [];
        for(let i = 0; i < tempSadrzajKorpe.length; i++){
            for(let j = 0; j < tempSadrzajKorpe[i].listOfProducts.length; j++){
                console.log('dodat sadrzaj indeksa: ' + j)
                this.sadrzajKorpe.push(tempSadrzajKorpe[i].listOfProducts[j]);
                this.inputValues[i+j] = tempSadrzajKorpe[i].listOfProducts[j].brKomada;
            }
        }
        // localStorage.setItem('test', this.sadrzajKorpe)
        // this.sadrzajKorpe = JSON.parse(localStorage.getItem('shoppingList')) || [];
        let tempLista = JSON.parse(localStorage.getItem('shoppingList')) || [];

        // for(let i = 0; i < tempLista.length; i++){
        //     this.inputValues.push(tempLista[i].brKomada);
        // }
    },
    methods:{
        createOrder(){


            let tempUsername = JSON.parse(localStorage.getItem('token')).username;
            console.log('Username: ' + tempUsername)

            let tempSadrzajKorpe = JSON.parse(localStorage.getItem('shoppingList')) || [];
            console.log('Duzina tempSadrzajKorpe: ' + tempSadrzajKorpe.length)

            for(let i = 0; i < tempSadrzajKorpe.length; i++){
                this.order.izKogRestorana = tempSadrzajKorpe[i].izKogRestorana;
                this.order.datumIVremePorudzbine = new Date().toString().substring(4, 15);
                console.log('Ispis broja restorana: ' + tempSadrzajKorpe.length);
                console.log('Ispis datuma: ' + this.order.datumIVremePorudzbine);
                //treba izracunati cenu
                this.order.cena = 0;
                this.order.idKupca = tempUsername;
                this.order.statusPorudzbine = 'cekaDostavljaca';
                this.order.idDeliveryMana = "";
                console.log('Broj artikala: ' + tempSadrzajKorpe[i].listOfProducts.length);
                
                for(let j = 0; j < tempSadrzajKorpe[i].listOfProducts.length; j++){
                    let tempArtikal = {
                        brojKomada : tempSadrzajKorpe[i].listOfProducts[j].brKomada,
                        nazivArtikla : tempSadrzajKorpe[i].listOfProducts[j].nazivArtikla
                    }
                    this.order.listaNarucenihArtikala.push(tempArtikal);
                    this.order.cena = this.order.cena + (tempSadrzajKorpe[i].listOfProducts[j].brKomada * tempSadrzajKorpe[i].listOfProducts[j].cenaJedinice);
                }

                dataService.addOrder(this.order).then(response => {
                    console.log('poslata narudzbina na server. stigao odgovor');
                    localStorage.removeItem('shoppingList');
                    this.messages.successReport = `<h4>Vase narudzbine su uspesno poslate i cekaju dostavljaca.</h4>`;
                    setTimeout(() => this.messages.successReport = '', 3000)
                    setTimeout(() => this.$router.push('/home'), 5000);
                    // localStorage.setItem('shoppingList', {});
                }).catch(error => {
                    console.log(error.response);
                });
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
            window.dispatchEvent(new CustomEvent('shoppingList-changed', {
                detail: {
                    storage: localStorage.getItem('shoppingList')
                }
            }));
        },
        changeInCart(product, index){
            let tempUsername = JSON.parse(localStorage.getItem('token')).username;
            console.log('Username: ' + tempUsername)

            product.brKomada = this.inputValues[index];

            let tempSadrzajKorpe = JSON.parse(localStorage.getItem('shoppingList')) || [];
            console.log('Duzina tempSadrzajKorpe: ' + tempSadrzajKorpe.length)

            for(let i = 0; i < tempSadrzajKorpe.length; i++){
                for(let j = 0; j < tempSadrzajKorpe[i].listOfProducts.length; j++){
                    
                    if(tempSadrzajKorpe[i].listOfProducts[j].izKogRestorana == product.izKogRestorana && tempSadrzajKorpe[i].listOfProducts[j].nazivArtikla == product.nazivArtikla){
                        console.log('pronadjen')
                        console.log('Stari broj komada: ' + tempSadrzajKorpe[i].listOfProducts[j].brKomada);
                        tempSadrzajKorpe[i].listOfProducts[j].brKomada = product.brKomada;
                        console.log('Novi broj komada: ' + tempSadrzajKorpe[i].listOfProducts[j].brKomada);
                    }
                }
            }

            let noviUpis = JSON.stringify(tempSadrzajKorpe);
            console.log('Upis u localStorage: ' + noviUpis)
            localStorage.setItem('shoppingList', noviUpis);
            window.dispatchEvent(new CustomEvent('shoppingList-changed', {
                detail: {
                    storage: localStorage.getItem('shoppingList')
                }
            }));
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
        calculateSum(){
            let suma = 0;
            for(let i = 0; i < this.sadrzajKorpe.length; i++){
                suma += this.inputValues[i] * this.sadrzajKorpe[i].cenaJedinice;
            }
            console.log('Ukupna cena: ' + suma);
            return suma;
        }
    },
}
</script>



<style scoped>


</style>