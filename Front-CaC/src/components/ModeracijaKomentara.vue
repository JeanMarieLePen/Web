<template>
    <div style="width:50%">
        <div class="container">
            <h2>Moderacija komentara o restoranu</h2>
            <p>Klikom na dugme Odobri/Odbaci, omogucavate odobravanje/odbacivanje komentara</p>

            <div>
                <h3>Prikaz neodobrenih komentara</h3>
                <div>
                    <div class="container" style="min-height:300px;">
                        <h2>Pregled ponude restorana</h2>
                        <p>Prikaz komentara o restoranu i usluzi koji nisu prosli moderaciju...</p>
                        <section style="margin-left: 10px; margin-bottom:100px">
                            <div>
                                <div class="row">
                                    <div class="col-md-4" v-bind:key="tempKomentar.id" v-for="tempKomentar in neodobreniKomentari">
                                        <div style="margin-bottom:30px;" class="card h-100">
                                            <!-- <img class="card-img-top" :src="tempArtikal.slika" alt="card image collar"> -->
                                            <div class="card-body">
                                                <h5 class="card-title">Komentar</h5>
                                                <p class="card-text">Komentator: {{tempKomentar.kupacKomentator}}</p>
                                                <p class="card-text">Restoran: {{tempKomentar.komentarisaniRestoran}}</p>
                                                <p class="card-text">Tekst komentara: {{tempKomentar.tekstKomentara}}</p>
                                                <starrating v-model="tempKomentar.ocena" :star-size="20" read-only></starrating>
                                                <button style="margin-top:10px;" v-on:click="odobriKomentar(tempKomentar)" class="btn btn-primary">Odobri</button>
                                                <button style="margin-top:10px;" v-on:click="odbaciKomentar(tempKomentar)" class="btn btn-danger">Odbaci</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                    <div style="margin-bottom:100px;" class="text-center">
                        <button class="btn btn-success" @click="potvrdiModeraciju()">Potvrdi</button>
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
    components:{
        starrating: starRating,
    },
    data(){
        return{
            idManager: '',
            neodobreniKomentari:{

            },
             sviKomentari:{

             }
        }
    },
    methods:{
        potvrdiModeraciju(){
            dataService.sendModeratedCommentList(this.idManager, this.neodobreniKomentari).then(response => {
                console.log('moderacija poslata na server, stigao odgovor');
            }).catch(error => {
                console.log(error.response);
            })
        },
        odbaciKomentar(cmnt){
            cmnt.odobren = false;
        },
        odobriKomentar(cmnt){
            cmnt.odobren = true;
        },
        getId(){
            this.idManager = (JSON.parse(localStorage.getItem('token'))).username;
            console.log('idManager: ' + this.idManager);
        },
        getUnmoderated(){
            // let temp = JSON.parse(localStorage.getItem('token'));
            // idManager = temp.username;
            dataService.getAllUnmoderatedComments(this.idManager).then(response => {
            console.log('stigli svi nemoderirani komentari');
            this.neodobreniKomentari = response.data;
            }).catch(error => {
                console.log(error.response);
            });
        },
        getModerated(){
            dataService.getAllCommentsById(this.idManager).then(response => {
            console.log('stigli svi komentari');
            this.sviKomentari = response.data;
            }).catch(error => {
                console.log(error.response);
            });
        }
    },
    created(){
        this.getId();
        this.getModerated();
        this.getUnmoderated();
    }
}
</script>

<style scoped>

</style>