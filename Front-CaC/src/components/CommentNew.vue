<template>
    <div style="margin-top:50px;width:50%" id="car-new-comment">
        <div v-if="showEntity">
            <div v-if="!komentarisaoRestoran" class="comment-form">    
                <textarea type="text" v-model="noviKomentar.tekstKomentara" placeholder="Tekst komentara..."></textarea>
                <label style="display:inline-block">
                    <input readonly type="text" v-model="noviKomentar.kupacKomentator">
                </label>
                <starrating v-model="ocena" @rating-selected="setRating()" style="margin-left:50px;display:inline-block"></starrating>
                <br/>
                <button style="border-radius: 20rem;" @click="addComment()">Add Comment</button>
            </div>
            <div v-if="messages.successMsg" style="margin-left: 200px;" class="alert alert-success" v-html="messages.successMsg"></div>
            <div v-if="messages.errorMsg" style="margin-left: 200px;" class="alert alert-danger" v-html="messages.errorMsg"></div>
            <div style="overflow-y: scroll;margin-bottom:100px;">
                <div  v-for="tempComment in komentari" v-bind:key="tempComment.id" class="comments-box">
                <div>
                    <p class="author">
                    {{tempComment.kupacKomentator}} 
                    <starrating v-model="tempComment.ocena" :star-size="20" read-only style="margin-left:10px;display:inline-block"></starrating>
                    </p>
                </div>
                
                <p v-html="tempComment.tekstKomentara" class="content-comment"></p>
                <!-- <p v-show="isAdmin" @click="removeComment($index)" class="delete">Delete</p> -->
                </div>
                <div style="margin-left: 200px;margin-top:100px" v-show="komentari.length == 0 ">
                    <h1>Trenutno nema komentara</h1>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import dataService from '../services/DataService'
import starRating from 'vue-star-rating'
export default {
    components:{
        starrating : starRating,
    },
    data(){
        return{
            isAdmin : false,
            
            //pomocna varijabla za prikaz sekcije za kreiranej komentara
            showEntity: true,
            komentarisaoRestoran: false,
            ocena:0,
            //pomocna varijabla u koju ce se ucitavati svi komentari o restoranu 
            komentari:[],

            messages: {
                errorText: '',
                errorResponse: '',
                successResponse: '',
            },
            noviKomentar: {
                kupacKomentator: '',
                komentarisaniRestoran:'',
                tekstKomentara: '',
                ocena: '',
            },
        }
    },
    methods : {
        loadComments(){
            dataService.getAllCommentsByRestaurantId(this.$route.params.id).then(response => {
                console.log('stigla lista svih komentara za prikaz');
                this.komentari = response.data;
            }).catch(error => {
                console.log(error.response);
            });
        },
        setRating(){
            console.log('selektovani rejting je: ' + this.ocena);
            this.noviKomentar.ocena = this.ocena;
        },
        addComment: function () {
            if (this.noviKomentar.tekstKomentara == '') {
                this.messages.errorText = `<h4>Tekst komentara ne moze biti prazan!</h4>`;
                setTimeout(() => this.messages.errorText = '', 3000);
            }
            else{

                console.log('na bek ide: ' + JSON.stringify(this.noviKomentar));
                dataService.addComment(this.noviKomentar).then(response => {
                    console.log("dodat novi komentar");
                }).catch(error => {
                    console.log(error.response);
                });
            }
            this.showEntity = false;
            // else{
            //     dataService.addComment()
            // }
        }
    },
    computed:{
        // id(){
        //     return this.$route.params.id;
        // },
    },
    created(){
        let temp = JSON.parse(localStorage.getItem('token'));
        this.noviKomentar.kupacKomentator = temp.username;
        this.noviKomentar.komentarisaniRestoran = this.$route.params.id;
        console.log('komentarisani restoran je: ' + this.$route.params.id);
        console.log('komentarisani restoran je: ' + this.noviKomentar.komentarisaniRestoran);
        this.loadComments();
    }
}
</script>

<style scoped>
.comment-form{
    display: block;
    width: 80%;
    margin: auto;
}

textarea{
    width: 100%;
    border: 2px solid #ccc;
    border-radius: 7px;
    height: 70px;
    font-family: Verdana, Helvetica, sans-serif;
    padding: 5px;
}

input{
    border: 2px solid #ccc;
    border-radius: 5px;
    padding: 5px;
}

button{
    background: #04AA6D;
    color: #fff;
    border: 0;
    padding: 5px;
    cursor: pointer;
}

/*Comment Box*/

.comments-box{
    width: 90%;
    margin-left: 200px;
    padding: 20px 0;
    border-bottom: 1px solid #000;
}

.delete{
    background: red;
    color: #fff;
    font-size: 12px;
    cursor: pointer;
    display: inline;
    padding: 3px;
}

.author{
    margin: 10px 0;
    font-weight: bold;
}

</style>