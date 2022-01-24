<template>
    <div>
        <div class="container">

            <label class='label'>Naziv restorana:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text"  v-model="loadRestaurant.name">
            <label class='label'>Tip restorana:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text"  v-model="loadRestaurant.type">
            <label class='label'>Logo:</label>
            <div>
                <img width="750" height="500" :src="`${loadRestaurant.images[0]}`" />
            </div>
            <label class='label'>Menadzer:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text"  v-model="loadRestaurant.manager.username">
            <div class="container">
                <h3>Pregled artikala:</h3>
                    <div class="col-xl-3 col-md-6 mb-4" v-bind:key="art.naziv" v-for="art in loadRestaurant.menuItems">
                        <div class="card border-0 shadow">
                                <img :src="`${art.slika}`" class="card-img-top" alt="...">
                                    <div class="card-body text-center">
                                        <h5 class="card-title mb-0">{{art.naziv}}</h5>
                                    </div>
                        </div>
                    </div>
            </div>
            <div class="container">
                <h3>Pregled komentara:</h3>
                    <div class="col-xl-3 col-md-6 mb-4" v-bind:key="comm.kupacKomentator" v-for="comm in loadRestaurant.comments">
                        <div class="card border-0 shadow">
                                <div id='username'>{{comm.kupacKomentator}} </div>
                                <div id='comment'>
                                    <input width="300" height="200" readonly v-model="comm.text"/>
                                    
                                </div>
                        </div>
                    </div>
            </div>
        </div>
    </div>
</template>

<script>
import dataService from '../services/DataService'
import starRating from 'vue-star-rating'

export default {
    
    data(){
        return{
            loadRestaurant:{
                name:'',
                type:'',
                menuItems:null,
                opened:'',
                lokacija:null,
                images:[],
                comments:null,
                manager:'',
                ocena:''
            },
            username:'',
        }
    },
    methods:{
        getRestaurant(){
            console.log("uislo u getRestaurant")
            dataService.getRestaurantByManager(this.username).then(response => {
                this.loadRestaurant = response.data;
                console.log("KOMENTARI" + this.loadRestaurant.comments)

                dataService.getProducts(this.username).then(response => {
                    this.loadRestaurant.menuItems = response.data;
                })
            })
        }
    },
    computed:{
        id(){
            return this.$route.params.id;
        },
        getOtherImgs: function () {
            console.log("getOtherImgs: ");
            //Prva slika mora da se manuelno postavi, a ostale se dodaju preko v-for:
            let imgs = this.loadRestaurant.images.slice(1);
            //Ako ima samo jednu sliku onda se sklanjaju strelice < > za kretanje kroz slike.
            if (imgs.length === 0) {
                console.log("imgs.lenght je = 0 ");
                this.isOtherImgs = false;
                console.log("this.isOtherImgs: " + this.isOtherImgs);
            } else {
                //vec je true, ali za svaki slucaj
                this.isOtherImgs = true;
                return imgs;
            }
    },
    },
    created(){
        // if(JSON.parse(localStorage.getItem('token')) == null){
        //     this.$router.push(`/login`);
        // }else{
        //     let temp = JSON.parse(localStorage.getItem('token'));
        //     this.username = temp.username;
        //     this.getRestaurant();
        // }
    },
    components:{
        "star-rating" : starRating
    }





}
</script>

<style scoped>
</style>