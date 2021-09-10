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
        }
    },
    methods:{
        getRestaurant(){
            dataService.getRestaurant(this.id).then(response => {
                this.loadRestaurant = response.data;
                console.log("Naziv pronadjenog restorana je: " + this.loadRestaurant.name);
                console.log("string slike: " + this.loadRestaurant.images[0])
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
        if(JSON.parse(localStorage.getItem('token')) == null){
            this.$router.push(`/login`);
        }else{
            this.getRestaurant();
        }
    },
    components:{
        "star-rating" : starRating
    }





}
</script>

<style scoped>
</style>