<template>
    <div style="width:50%">
        <div class="container">
            <h2>Moderacija kupaca</h2>
            <p>Klikom na dugme Suspenduj suspendujete odabrani korisnicki nalog</p>

            <div>
                <h3>Prikaz korisnika</h3>
                <p>Prikazani su korisnici koji ucestalo otkazuju narudzbine...</p>
                <div>
                    <div class="container" style="min-height:300px;overflow:auto;">
                        <section style="margin-left: 10px; margin-bottom:100px">
                            <div>
                                <div class="row">
                                    <div class="col-md-4" v-bind:key="tempQuitter.id" v-for="tempQuitter in quitters">
                                        <div style="margin-bottom:30px;" class="card h-100">
                                            <!-- <img class="card-img-top" :src="tempArtikal.slika" alt="card image collar"> -->
                                            <div class="card-body">
                                                <h5 class="card-title">Podaci o korisniku</h5>
                                                <p class="card-text">Ime: {{tempQuitter.ime}}</p>
                                                <p class="card-text">Prezime: {{tempQuitter.prezime}}</p>
                                                <p class="card-text">Broj otkazivanja: {{tempQuitter.numberOfCanceledOrders}}</p>
                                                <button style="margin-top:10px;" v-on:click="suspendUser(tempQuitter)" class="btn btn-danger">Suspenduj</button>
                                                <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
                                                <div v-if='messages.errorResponse' class="alert alert-success" v-html="messages.errorResponse"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
import dataService from '../services/DataService'


export default {
    components:{
        starrating: starRating,
    },
    data(){
        return{
            quitters:[],
        }
    },
    methods:{
        suspendUser(user){
            dataService.suspendUser(user.username).then(response => {
                console.log('suspendovan korisnik');
            }).catch(error => {
                console.log(error.response);
            });
        },
        unsuspendUser(user){
            dataService.unsuspendUser(user.username).then(response => {
                console.log('korisnik vise nije suspendovan');
            }).catch(error => {
                console.log(error.response);
            });
        }
    },
    created(){

    }
}
</script>

<style scoped>

</style>