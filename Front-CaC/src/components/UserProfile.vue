<template>
<div id="user-profile">
    <div>
        <div class="container" id='page-title'>
            <h1 style="margin-top:10px;color:#35424a;">Profil <span id='titleEffect'>Usera</span></h1>
            <hr style='background:#35424a;height:1px;'>
        </div>
        <div class="container">
            <form>
                <ul id="services" class="list-group">
                <!-- Osoba -->
                    <li class="list-group-item" >
                        <h5 class="header5">Korisnicko ime</h5>
                        <h4>{{profile.username}}</h4>
                    </li>
                    <li class="list-group-item" >
                        <h5 class="header5">Ime</h5>
                        <h4>{{profile.name}}</h4>
                    </li>

                    <li class="list-group-item" >
                        <h5 class="header5">Prezime</h5>
                        <h4>{{profile.lastname}}</h4>
                    </li>

                    <li class="list-group-item" >
                        <h5 class="header5">Pol</h5>
                        <h4>{{profile.gender}}</h4>
                    </li>

                    <li class="list-group-item" >
                        <h5 class="header5">Datum rodjenja</h5>
                        <h4>{{profile.dateOfBirth}}</h4>
                    </li>
                </ul>

                    <div id='buttonUpdate'>
                    <button type='button' class="btn btn-lg btn-success marg" v-on:click='updateUser(profile.id)'> Update</button> 
                </div>
            </form>
        </div>
    </div>
</div>
</template>
<script>

import UserDataService from '../services/DataService'
export default {
    data(){
        return{
         profile: {
                id:'',
                username:'',
                name:'',
                lastname:'',
                password:'',
                gender:'',
                dateOfBirth:''
            }
        }
    },
    methods:{
        getUserProfileData(id){
            UserDataService.getUser(id).then(response => {
                this.profile = response.data;
            })
        },
        updateUser(id){ 
            this.$router.push(`/profile/${id}/update`);
        },
        
    },
    computed:{
        id() {
            return this.$route.params.id; //preuzimam id usera na cijoj sam stranici za prikaz profila
        },
    },

    created(){
        let parsToken = JSON.parse(localStorage.getItem('parsToken'));
        this.getUserProfileData(parsToken.id);
    },

}
</script>

<style scoped>

#titleEffect{
  color:gold;
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

#user-profile#buttonUpdate{
  float:left;
}

.header5{
    color:gold;
    font-weight: bold;
}

.marg{
    margin-top:15px;
}

</style>