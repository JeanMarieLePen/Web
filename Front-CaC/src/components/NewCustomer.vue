<template>
    <div>
        <div class="container">
            <label class='label'>Korisnicko ime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite korisnicko ime..."  v-model="newCustomer.username">
            <label class='label'>Ime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite korisnicko ime..."  v-model="newCustomer.name">
            <label class='label'>Prezime:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite korisnicko ime..."  v-model="newCustomer.lastname">
            <label class='label'>Lozinka:</label>
            <input style="width:100%; padding:10px; margin-bottom:25px" type="text" placeholder="Unestite korisnicko ime..."  v-model="newCustomer.password">
            <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
            <div v-if='messages.errorResponse' class="alert alert-success" v-html="messages.errorResponse"></div>
            <button class="btn btn-success" v-on:click='addCustomer()'>Potvrdi</button>
        </div>
    </div>
</template>

<script>
import dataService from '../services/DataService'
export default {
    name:'customer-new',
    data(){
        return{
            newCustomer:{
                username:'',
                name:'',
                lastname:'',
                password:'',
            },
            messages:{
                successResponse:'',
                errorResponse:'',
            }
        }
    },
    methods:{
        addCustomer:function(){
            dataService.addCustomer(this.newCustomer).then(Response => {
                console.log("DEBUG")
                this.messages.successResponse = `<h4>Novi cenovnik je uspešno dodat!</h4>`;
                this.resetFields();
                setTimeout(() => this.messages.successResponse='', 5000);  
            }).catch(error => {
                if(error.response.status === 500 || error.response.status === 404){
                        this.messages.errorResponse= `<h4>We had some server errors, please try again later!</h4>`;
                        setTimeout(() => this.messages.errorResponse='', 5000);
                    }
            })
        }
    }
}
</script>


<style scoped>


</style>