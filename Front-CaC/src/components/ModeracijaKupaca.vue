<template>
    <div style="width:50%">
        <div class="container">
            <h2>Moderacija kupaca</h2>
            <p>Klikom na dugme Suspenduj suspendujete odabrani korisnicki nalog</p>

            <div>
                <h3>Prikaz problematicnih korisnika</h3>
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
                                                <p class="card-text">Ime: {{tempQuitter.name}}</p>
                                                <p class="card-text">Prezime: {{tempQuitter.lastname}}</p>
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

            <div>
                <h3>Prikaz suspendovanih korisnika</h3>
                <p>Prikazani su korisnici koji se nalaze na listi suspendovanih korisnika...</p>
                <div>
                    <div class="container" style="min-height:300px;overflow:auto;">
                        <section style="margin-left: 10px; margin-bottom:100px">
                            <div>
                                <div class="row">
                                    <div class="col-md-4" v-bind:key="tempQuitter.username" v-for="tempQuitter in bannedUsers">
                                        <div style="margin-bottom:30px;" class="card h-100">
                                            <!-- <img class="card-img-top" :src="tempArtikal.slika" alt="card image collar"> -->
                                            <div class="card-body">
                                                <h5 class="card-title">Podaci o korisniku</h5>
                                                <p class="card-text">Username:{{tempQuitter.username}}</p>
                                                <button style="margin-top:10px;" v-on:click="unsuspendUser(tempQuitter)" class="btn btn-primary">Unsuspend</button>
                                                <div v-if='messages.successResponse2' class="alert alert-success" v-html="messages.successResponse"></div>
                                                <div v-if='messages.errorResponse2' class="alert alert-success" v-html="messages.errorResponse"></div>
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
    data(){
        return{
            quitters:[],
            bannedUsers:[],
            messages:{
                errorResponse:'',
                successResponse:'',
                errorResponse2:'',
                successResponse2:''
            }
        }
    },
    methods:{
        getAllBanned(){
            dataService.getAllBanned().then(response => {
                this.bannedUsers = response.data;
                console.log('lista banovanih ima: ' + this.bannedUsers.length + " clanova");
            }).catch(error => {
                console.log(error.response);
            });
        },
        getAllQuitters(){
            dataService.getQuitters().then(response => {
                this.quitters = response.data;
                console.log('pronadjeno je: ' + this.quitters.length + ' quittera.');
            }).catch(error => {
                console.log(error.response);
            });
        },
        suspendUser(user){
            dataService.suspendUser(user.username).then(response => {
                console.log('suspendovan korisnik');
                this.successResponse = "<h4>Uspesno ste suspendovali korisnika.</h4>"
                setTimeout(() => this.successResponse='', 3000);
                this.getAllQuitters();
               
            }).catch(error => {
                this.errorResponse = "<h4>Doslo je do greske!.</h4>"
                setTimeout(() => this.errorResponse='', 3000);
                console.log(error.response);
            });
        },
        unsuspendUser(user){
            dataService.unsuspendUser(user.username).then(response => {
                console.log('korisnik vise nije suspendovan');
                this.successResponse2 = "<h4>Uspesno ste unsuspendovali korisnika.</h4>"
                setTimeout(() => this.successResponse2 = '', 3000);
                this.getAllBanned();
                this.getAllQuitters();
            }).catch(error => {
                this.errorResponse2 = "<h4>Doslo je do greske!.</h4>"
                setTimeout(() => this.errorResponse2='', 3000);
                console.log(error.response);
            });
        }
    },
    created(){
        this.getAllQuitters();
        this.getAllBanned();
    }
}
</script>

<style scoped>

</style>