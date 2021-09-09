<template>
    <div>
        <div class="container">
            <label class='label'>Naziv artikla:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite naziv artikla"  v-model="newProduct.name">
            <label  for="ddd" class='label'>Cena artikla:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="number" id="ddd" name="ddd" min="1" max="500000" placeholder="Unestite cenu artikla"  v-model="newProduct.cena">
            <label class='label'>Unesite tip artikla:</label>
            <span class="col-xl-3 col-md-6 mb-1">
                <select style="padding:5px;" v-model="newProduct.tip">
                    <option disabled value="">Odabir tipa</option>
                    <option v-bind:key="tipTemp.tipp" v-for="tipTemp in tipovi">{{tipTemp.tipp}}</option>
                </select>
            </span>
            <label class='label'>Restoran kom pripada:</label>
            <select style="padding:5px;" v-model="newProduct.restaurant">
                <option disabled value="">Odabir restorana</option>
                <option v-bind:key="restoran.naziv" v-for="restoran in restorani">{{restoran.naziv}}</option>
            </select>
            <label class='label'>Kolicina:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="number" placeholder="Unestite kolicinu"  v-model="newProduct.kolicina">
            <!-- <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite restoran kojim upravlja menadzer..."  v-model="newManager.password">   -->
            <label class='label'>Opis:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="number" placeholder="Unestite opis"  v-model="newProduct.opis">
            <label class='label'>Slika artikla:</label>
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0">
                    <span>
                        <label>Images</label>
                        <input type="file" name="file" class="filestyle" multiple @change="uploadImage"/>
                    </span>
                </div>
            </div>
            <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
            <div v-if='messages.errorResponse' class="alert alert-success" v-html="messages.errorResponse"></div>
            <button class="btn btn-success" v-on:click='createProduct()'>Potvrdi</button>
        </div>
    </div>
</template>

<script>
import dataService from '../services/DataService'

export default {
    data(){
        return{
             restorani:[
                // {naziv:'test1'},
                // {naziv:'test2'}
            ],
            newProduct:{
                naziv:'',
                cena:'',
                tip:'',
                kolicina:'',
                restorankompripada:'',
                opis:'',               
                images:null,
                restaurant:''              
            },
            messages:{
                successResponse:'',
                errorResponse:'',
            },
            tipovi:[
                {tipp:'jelo'},
                {tipp:'pice'}
            ]
        }
    },
    methods:{
        createProduct:function(){
            

            console.log(JSON.stringify(this.newProduct));
            console.log(this.newProduct);
            dataService.addProduct(this.newProduct).then(response => {
                if(response.data !== ''){
                    this.messages.successResponse= "<h4>Uspesno ste kreirali artikal.</h4>"
                    setTimeout(() => this.messages.successResponse='', 3000);
                }else{
                    this.messages.errorResponse= "<h4>Vec postoji artikal sa tim imenom.</h4>"
                    setTimeout(() => this.messages.errorResponse='', 3000);
                }
                
                
            }).catch(error => {
                if(error.response.status === 500 || error.response.status === 404){
                        this.messages.errorResponse= `<h4>We had some server errors, please try again later!</h4>`;
                        setTimeout(() => this.messages.errorResponse='', 5000);
                }
            })
        },
        getRestaurants:function(){
            dataService.getAllRestaurants().then(response => {
                this.restorani.response.data;
            });
        }
    },
    created(){      
        this.getRestaurants();
    }
}
</script>


<style scoped>


</style>