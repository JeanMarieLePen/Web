<template>
	<div style="margin-bottom:150px;" id="user-profile-update">
		<div id='main'>
			<div class="container" id='page-title'>
				<h1 style="margin-top:10px;color:#35424a;">Izmena profila <span id='titleEffect'>{{role}}</span></h1>
				<hr style='background:#35424a;height:1px;'>
			</div>

            <div class="container" v-show="isDeliveryMan">
				<h4>Promena podataka</h4>
				<hr>
				<fieldset class="form-group">
					<label>Korisnicko ime</label>
					<input type="text" class="form-control" v-model="profileDeliveryMan.username" disabled />
				</fieldset>

				<div v-if='messages.errorFirstName' id='testError' class="alert alert-danger" v-html="messages.errorFirstName"></div>
				<fieldset class="form-group" >
					<label>Ime</label>
					<input type="text" class="form-control" v-model="profileDeliveryMan.name" />
				</fieldset>

				<div v-if='messages.errorLastName' class="alert alert-danger" v-html="messages.errorLastName"></div>
				<fieldset class="form-group" >
					<label>Prezime</label>
					<input type="text" class="form-control" v-model="profileDeliveryMan.lastname" />
				</fieldset>

				<div class="form-label-group" >
					<label>Pol</label>
					<br>
<<<<<<< HEAD
					<span >
                        <select style="padding:5px; width:100%;" v-model="profileDeliveryMan.pol">
                            <option disabled value="">Odabir pola</option>
                            <option v-bind:key="polTemp.naziv" v-for="polTemp in polovi">{{polTemp.naziv}}</option>
                        </select>
                    </span>
=======
					<input type="radio" v-model="profileDeliveryMan.gender" required value="Muski"> Muski
					<input type="radio" v-model="profileDeliveryMan.gender" required value="Zenski"> Zenski
>>>>>>> 22f9fc2444d5dc8e037001f21990eaaefc6e11f3
					<br>
					<br>
				</div>

                <label style="padding:5px;" class='label'>Datum rodjenja:</label>
                <vuejsDatepicker style="padding:5px;" placeholder="Odaberite datum rodjenja" v-model="selectedDate1">
                </vuejsDatepicker>

                <hr>
				<h4>Promena lozinke</h4>
				<hr>
				<div v-if='messages.errorNotEqualOldPassword' class="alert alert-danger" v-html="messages.errorNotEqualOldPassword"></div>
				<div v-if='messages.errorOldPass' class="alert alert-danger" v-html="messages.errorOldPass"></div>
				<fieldset class="form-group">
					<label>Stara lozinka</label>
					<input type="password" class="form-control" v-model="changedPassword.oldPassword" placeholder="Unesite staru loznku..." />
				</fieldset>

				<div v-if='messages.errorNewPass' class="alert alert-danger" v-html="messages.errorNewPass"></div>
				<fieldset class="form-group">
					<label>Nova lozinka</label>
					<input type="password" class="form-control" v-model="changedPassword.newPassword" placeholder="Unesite novu loznku..." />
				</fieldset>

				<div v-if='messages.errorNotEqualNewPassword' class="alert alert-danger" v-html="messages.errorNotEqualNewPassword"></div>
				<div v-if='messages.errorRepNewPass' class="alert alert-danger" v-html="messages.errorRepNewPass"></div>
				<fieldset class="form-group">
					<label>Ponovite novu lozinku</label>
					<input type="password" class="form-control" v-model="changedPassword.newPasswordRepeat" placeholder="Ponovite novu loznku..."  />
				</fieldset>                    

				<button type="button" class="btn btn-lg btn-success" v-on:click='updateProfile(profileDeliveryMan)'> Save </button>
				<hr>
				<div v-if='messages.errorResponse' class="alert alert-danger" v-html="messages.errorResponse"></div>
				<div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>

			</div>

			<div class="container" v-show="isAdmin">
				<h4>Promena podataka</h4>
				<hr>
				<fieldset class="form-group">
					<label>Korisnicko ime</label>
					<input type="text" class="form-control" v-model="profileAdmin.username" disabled />
				</fieldset>

				<div v-if='messages.errorFirstName' id='testError' class="alert alert-danger" v-html="messages.errorFirstName"></div>
				<fieldset class="form-group" >
					<label>Ime</label>
					<input type="text" class="form-control" v-model="profileAdmin.name" />
				</fieldset>

				<div v-if='messages.errorLastName' class="alert alert-danger" v-html="messages.errorLastName"></div>
				<fieldset class="form-group" >
					<label>Prezime</label>
					<input type="text" class="form-control" v-model="profileAdmin.lastname" />
				</fieldset>

				<div class="form-label-group" >
					<label>Pol</label>
					<br>
					<span >
                        <select style="padding:5px; width:100%;" v-model="profileAdmin.pol">
                            <option disabled value="">Odabir pola</option>
                            <option v-bind:key="polTemp.naziv" v-for="polTemp in polovi">{{polTemp.naziv}}</option>
                        </select>
                    </span>
					<br>
					<br>
				</div>

                <label style="padding:5px;" class='label'>Datum rodjenja:</label>
                <vuejsDatepicker  style="padding:5px;" placeholder="Odaberite datum rodjenja" v-model="selectedDate2">
                </vuejsDatepicker>

                <hr>
				<h4>Promena lozinke</h4>
				<hr>
				<div v-if='messages.errorNotEqualOldPassword' class="alert alert-danger" v-html="messages.errorNotEqualOldPassword"></div>
				<div v-if='messages.errorOldPass' class="alert alert-danger" v-html="messages.errorOldPass"></div>
				<fieldset class="form-group">
					<label>Stara lozinka</label>
					<input type="password" class="form-control" v-model="changedPassword.oldPassword" placeholder="Unesite staru loznku..." />
				</fieldset>

				<div v-if='messages.errorNewPass' class="alert alert-danger" v-html="messages.errorNewPass"></div>
				<fieldset class="form-group">
					<label>Nova lozinka</label>
					<input type="password" class="form-control" v-model="changedPassword.newPassword" placeholder="Unesite novu loznku..." />
				</fieldset>

				<div v-if='messages.errorNotEqualNewPassword' class="alert alert-danger" v-html="messages.errorNotEqualNewPassword"></div>
				<div v-if='messages.errorRepNewPass' class="alert alert-danger" v-html="messages.errorRepNewPass"></div>
				<fieldset class="form-group">
					<label>Ponovite novu lozinku</label>
					<input type="password" class="form-control" v-model="changedPassword.newPasswordRepeat" placeholder="Ponovite novu loznku..."  />
				</fieldset>                    

				<button type="button" class="btn btn-lg btn-success" v-on:click='updateProfile(profileAdmin)'> Save </button>
				<hr>
				<div v-if='messages.errorResponse' class="alert alert-danger" v-html="messages.errorResponse"></div>
				<div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>

			</div>

            <div class="container" v-show="isManager">
				<h4>Promena podataka</h4>
				<hr>
				<fieldset class="form-group">
					<label>Korisnicko ime</label>
					<input type="text" class="form-control" v-model="profileManager.username" disabled />
				</fieldset>

				<div v-if='messages.errorFirstName' id='testError' class="alert alert-danger" v-html="messages.errorFirstName"></div>
				<fieldset class="form-group" >
					<label>Ime</label>
					<input type="text" class="form-control" v-model="profileManager.name" />
				</fieldset>

				<div v-if='messages.errorLastName' class="alert alert-danger" v-html="messages.errorLastName"></div>
				<fieldset class="form-group" >
					<label>Prezime</label>
					<input type="text" class="form-control" v-model="profileManager.lastname" />
				</fieldset>

				<div class="form-label-group" >
					<label>Pol</label>
					<br>
					<span >
                        <select style="padding:5px; width:100%;" v-model="profileManager.pol">
                            <option disabled value="">Odabir pola</option>
                            <option v-bind:key="polTemp.naziv" v-for="polTemp in polovi">{{polTemp.naziv}}</option>
                        </select>
                    </span>
					<br>
					<br>
				</div>

                <label style="padding:5px;" class='label'>Datum rodjenja:</label>
                <vuejsDatepicker style="padding:5px;" placeholder="Odaberite datum rodjenja" v-model="selectedDate3">
                </vuejsDatepicker>

                <hr>
				<h4>Promena lozinke</h4>
				<hr>
				<div v-if='messages.errorNotEqualOldPassword' class="alert alert-danger" v-html="messages.errorNotEqualOldPassword"></div>
				<div v-if='messages.errorOldPass' class="alert alert-danger" v-html="messages.errorOldPass"></div>
				<fieldset class="form-group">
					<label>Stara lozinka</label>
					<input type="password" class="form-control" v-model="changedPassword.oldPassword" placeholder="Unesite staru loznku..." />
				</fieldset>

				<div v-if='messages.errorNewPass' class="alert alert-danger" v-html="messages.errorNewPass"></div>
				<fieldset class="form-group">
					<label>Nova lozinka</label>
					<input type="password" class="form-control" v-model="changedPassword.newPassword" placeholder="Unesite novu loznku..." />
				</fieldset>

				<div v-if='messages.errorNotEqualNewPassword' class="alert alert-danger" v-html="messages.errorNotEqualNewPassword"></div>
				<div v-if='messages.errorRepNewPass' class="alert alert-danger" v-html="messages.errorRepNewPass"></div>
				<fieldset class="form-group">
					<label>Ponovite novu lozinku</label>
					<input type="password" class="form-control" v-model="changedPassword.newPasswordRepeat" placeholder="Ponovite novu loznku..."  />
				</fieldset>                    

				<button type="button" class="btn btn-lg btn-success" v-on:click='updateProfile(profileManager)'> Save </button>
				<hr>
				<div v-if='messages.errorResponse' class="alert alert-danger" v-html="messages.errorResponse"></div>
				<div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>

			</div>



            <div class="container" v-show="isCustomer">
				<h4>Promena podataka</h4>
				<hr>
				<fieldset class="form-group">
					<label>Korisnicko ime</label>
					<input type="text" class="form-control" v-model="profileCustomer.username" disabled />
				</fieldset>

				<div v-if='messages.errorFirstName' id='testError' class="alert alert-danger" v-html="messages.errorFirstName"></div>
				<fieldset class="form-group" >
					<label>Ime</label>
					<input type="text" class="form-control" v-model="profileCustomer.name" />
				</fieldset>

				<div v-if='messages.errorLastName' class="alert alert-danger" v-html="messages.errorLastName"></div>
				<fieldset class="form-group" >
					<label>Prezime</label>
					<input type="text" class="form-control" v-model="profileCustomer.lastname" />
				</fieldset>

				<div class="form-group" >
					<label>Pol</label>
					<span>
                        <select style="padding:5px; width:100%;" v-model="profileCustomer.pol">
                            <option disabled value="">Odabir pola</option>
                            <option v-bind:key="polTemp.naziv" v-for="polTemp in polovi">{{polTemp.naziv}}</option>
                        </select>
                    </span>
					<br>
					<br>
				</div>

                <label style="padding:5px;" class='label'>Datum rodjenja:</label>
                <vuejsDatepicker style="padding:5px;" placeholder="Odaberite datum rodjenja" v-model="selectedDate4">
                </vuejsDatepicker>

                <hr>
				<h4>Promena lozinke</h4>
				<hr>
				<div v-if='messages.errorNotEqualOldPassword' class="alert alert-danger" v-html="messages.errorNotEqualOldPassword"></div>
				<div v-if='messages.errorOldPass' class="alert alert-danger" v-html="messages.errorOldPass"></div>
				<fieldset class="form-group">
					<label>Stara lozinka</label>
					<input type="password" class="form-control" v-model="changedPassword.oldPassword" placeholder="Unesite staru loznku..." />
				</fieldset>

				<div v-if='messages.errorNewPass' class="alert alert-danger" v-html="messages.errorNewPass"></div>
				<fieldset class="form-group">
					<label>Nova lozinka</label>
					<input type="password" class="form-control" v-model="changedPassword.newPassword" placeholder="Unesite novu loznku..." />
				</fieldset>

				<div v-if='messages.errorNotEqualNewPassword' class="alert alert-danger" v-html="messages.errorNotEqualNewPassword"></div>
				<div v-if='messages.errorRepNewPass' class="alert alert-danger" v-html="messages.errorRepNewPass"></div>
				<fieldset class="form-group">
					<label>Ponovite novu lozinku</label>
					<input type="password" class="form-control" v-model="changedPassword.newPasswordRepeat" placeholder="Ponovite novu loznku..."  />
				</fieldset>                    

				<button type="button" class="btn btn-lg btn-success" v-on:click='updateProfile(profileCustomer)'> Save </button>
				<hr>
				<div v-if='messages.errorResponse' class="alert alert-danger" v-html="messages.errorResponse"></div>
				<div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>

			</div>

		</div>
	</div>
</template>

<script>
import dataService from "../services/DataService"
import Datepicker from 'vuejs-datepicker'
export default {
    components:{
        vuejsDatepicker:Datepicker,
    },
    data(){
        return{
            role:'',

<<<<<<< HEAD
            polovi:[
                {naziv:'musko'},
                {naziv:'zensko'}
            ],
            trenutniPol:'',

=======
>>>>>>> 22f9fc2444d5dc8e037001f21990eaaefc6e11f3
            profileDeliveryMan: {
                id:'',
                obrisan:false,
                username:'',
                password:'',
                name:'',
                lastname:'',
                gender:'',
                dateOfBirth:'',


                listOfActiveOrders:[],
                listOfAllOrders:[],

                oldPassword:'',
            },

            profileManager: {
                id:'',
                obrisan:false,
                username:'',
                password:'',
                name:'',
                lastname:'',
                gender:'',
                dateOfBirth:'',


                restaurant:'',

                oldPassword:'',
            },

            profileCustomer:{
                id:'',
                obrisan:false,
                username:'',
                password:'',
                name:'',
                lastname:'',
                gender:'',
                dateOfBirth:'',

<<<<<<< HEAD
                listOfAllOrders:[],
                listOfCompletedOrders:[],
                listOfCanceledOrders:[],
                cart:[],
                numberOfCanceledOrders: 0,
                numberOfPoints:0,
                typeOfCustomer:'',
                listOfCommentsMade:[],
=======
                listOfAllOrders:null,
                cart:null,
                numberOfPoints:0,
                typeOfCustomer:'',
>>>>>>> 22f9fc2444d5dc8e037001f21990eaaefc6e11f3


                oldPassword:'',
            },

            profileAdmin:{
                id:'',
                obrisan:false,
                username:'',
                password:'',
                name:'',
                lastname:'',
                gender:'',
                dateOfBirth:'',

                oldPassword:'',
            },
			changedPassword: {
                oldPassword: '',
                newPassword: '',
                newPasswordRepeat: '',
            },

            //pomocne promenljive za hvatanje vrednosti datuma
            selectedDate1:'',
			selectedDate2:'',
            selectedDate3:'',
            selectedDate4:'',
			messages: {
                errorFirstName: '',
                errorLastName: '',
                errorCompanyName:'',
                errorPIB:'',
                errorAddress:'',
                errorOldPass: '',
                errorNewPass: '',
                errorRepNewPass: '',
                errorNotEqualOldPassword: '',
                errorNotEqualNewPassword: '',
                errorResponse: '',
                successResponse: '',
            },

            //pomocne promenljive za prikaz segmenata spram uloge
            isAdmin:false,
            isManager:false,
            isCustomer:false,
            isDeliveryMan:false,
        }
    },
    watch:{
        'selectedDate1' : function(val){
            console.log(this.profileCustomer.dateOfBirth);
            this.profileCustomer.dateOfBirth = this.selectedDate1.toString().substring(4, 15);
<<<<<<< HEAD
            console.log(this.profileCustomer.dateOfBirth);
=======
>>>>>>> 22f9fc2444d5dc8e037001f21990eaaefc6e11f3
        },
        'selectedDate2' : function(val, oldVal){
            console.log('Datum rodjenja pre obrade: ' + this.profileAdmin.dateOfBirth);
            this.profileAdmin.dateOfBirth = this.selectedDate2.toString().substring(4, 15);
            console.log('Datum rodjenja posle obrade: ' + this.profileAdmin.dateOfBirth);
        },
        'selectedDate3' : function(val){
            console.log(this.profileManager.dateOfBirth);
            this.profileManager.dateOfBirth = this.selectedDate3.toString().substring(4, 15);
<<<<<<< HEAD
            console.log(this.profileManager.dateOfBirth);
=======
>>>>>>> 22f9fc2444d5dc8e037001f21990eaaefc6e11f3
        },
        'selectedDate4' : function(val){
            console.log(this.profileDeliveryMan.dateOfBirth);
            this.profileDeliveryMan.dateOfBirth = this.selectedDate4.toString().substring(4, 15);
<<<<<<< HEAD
            console.log(this.profileDeliveryMan.dateOfBirth);
=======
>>>>>>> 22f9fc2444d5dc8e037001f21990eaaefc6e11f3
        }
    },
    methods:{
            
           
        updateProfile: function (profile) {
        //OSOBA
            if  (profile.name == '') {
                this.messages.errorFirstName = `<h4>Polje ime ne moze biti prazno!</h4>`;
                setTimeout(() => this.messages.errorFirstName = '', 5000);
            }
            else if (profile.lastname == '') {
                this.messages.errorLastName = `<h4>Polje prezime ne moze biti prazno!</h4>`;
                setTimeout(() => this.messages.errorLastName = '', 5000);
            }
        //ZA SVE
            
         
        //Password check
            //Ako je unet old password, a nije uneto jedno od polja newPassword ili newPassword reapeat unosi se error
            else if (this.changedPassword.oldPassword !== '' && this.changedPassword.newPassword !== '' && this.changedPassword.newPasswordRepeat === '') {
                this.messages.errorRepNewPass = `<h4>Morate ponoviti Vasu novu sifru!</h4>`;
                setTimeout(() => this.messages.errorRepNewPass = '', 55000);
            }

            //Ako je unet old password, a nije uneto jedno od polja newPassword ili newPassword reapeat unosi se error
            else if (this.changedPassword.oldPassword !== '' && this.changedPassword.newPassword === '' && this.changedPassword.newPasswordRepeat !== '') {
                this.messages.errorNewPass = `<h4>Morate uneti Vasu novu sifru!</h4>`;
                setTimeout(() => this.messages.errorNewPass = '', 5000);
            }

            //Ako je unet old password, a nije uneto jedno od polja newPassword ili newPassword reapeat unosi se error
            else if (this.changedPassword.oldPassword !== '' && this.changedPassword.newPassword === '' && this.changedPassword.newPasswordRepeat === '') {
                this.messages.errorNewPass = `<h4>Morate uneti Vasu novu sifru!</h4>`;
                this.messages.errorRepNewPass = `<h4>Morate ponoviti Vasu novu sifru!</h4>`;
                setTimeout(() => this.messages.errorNewPass = '', 5000);
                setTimeout(() => this.messages.errorRepNewPass = '', 5000);
            }

            //Ako nije unet old password, a jesu novi ispisuje se error
            else if (this.changedPassword.oldPassword === '' && this.changedPassword.newPassword !== '' && this.changedPassword.newPasswordRepeat !== '') {
                this.messages.errorOldPass = `<h4>Morate prvo uneti Vasu staru sifru!</h4>`;
                setTimeout(() => this.messages.errorOldPass = '', 5000);
            }

            //Ako su uneta sva 3 passworda, nema errora za prazna polja, ali se proverava validnost samog unosa
            else if (this.changedPassword.oldPassword !== '' && this.changedPassword.newPassword !== '' && this.changedPassword.newPasswordRepeat !== '') {
                //Provera da li se nove sifre poklapaju
                if (this.changedPassword.newPassword !== this.changedPassword.newPasswordRepeat) {
                    this.messages.errorNotEqualNewPassword = `<h4>Vase nove sifre se ne poklapaju! Molimo Vas pokusajte ponovo...</h4>`;
                    setTimeout(() => this.messages.errorNotEqualNewPassword = '', 3000);
                }
                else {
                    //ako je stara sifra dobro uneta, a nove se poklapaju,
                    //stara sifra se menja novom.
                    profile.oldPassword = this.changedPassword.oldPassword;
                    profile.password = this.changedPassword.newPassword;

                    console.log('ispis old passworda: ' + profile.oldPassword)
                    console.log('ispis new passworda: ' + profile.password)

                    if(this.role === "admin"){
                        console.log('Kreiranje admina rodjenog: ' + this.profileAdmin.dateOfBirth)
                        dataService.updateAdmin(profile).then(response => {
                            this.messages.successResponse = `<h4>Vas profil je uspesno izmenjen!</h4>`;
                            setTimeout(() => this.messages.successResponse = '', 3000);
                            profile = response.data;
                        }).catch(error => {
<<<<<<< HEAD
                            console.log(error.response);
=======
                            console.log(error.response)
                        if(error.response.status === 500  && error.response.data.message==='Incorrect old password!'){
                            this.messages.errorResponse = `<h4>Vasa stara sifra je netacna! Molimo Vas pokusajte ponovo...</h4>`;
            
                            setTimeout(()=>this.messages.errorResponse='', 5000);
                        }
                        else if (error.response.status === 500 || error.response.status === 404) {
                            this.messages.errorResponse = `<h4>Imali smo nekih problema na serveru,  molimo Vas pokusajte ponovo kasnije!</h4>`;
                            setTimeout(() => this.messages.errorResponse = '', 5000);
                        }
                
                        });
                    }
                     if(this.role === "delivery_man"){
                        dataService.updateDeliveryMan(profile).then(response => {
                            this.messages.successResponse = `<h4>Vas profil je uspesno izmenjen!</h4>`;
                            setTimeout(() => this.messages.successResponse = '', 3000);
                            profile = response.data;
                        }).catch(error => {
>>>>>>> 22f9fc2444d5dc8e037001f21990eaaefc6e11f3
                        if(error.response.status === 500  && error.response.data.message==='Incorrect old password!'){
                            this.messages.errorResponse = `<h4>Vasa stara sifra je netacna! Molimo Vas pokusajte ponovo...</h4>`;
            
                            setTimeout(()=>this.messages.errorResponse='', 5000);
                        }
                        else if (error.response.status === 500 || error.response.status === 404) {
                            this.messages.errorResponse = `<h4>Imali smo nekih problema na serveru,  molimo Vas pokusajte ponovo kasnije!</h4>`;
                            setTimeout(() => this.messages.errorResponse = '', 5000);
                        }
                
                        });
                    }
                     if(this.role === "delivery_man"){
                        dataService.updateDeliveryMan(profile).then(response => {
                            this.messages.successResponse = `<h4>Vas profil je uspesno izmenjen!</h4>`;
                            setTimeout(() => this.messages.successResponse = '', 3000);
                            profile = response.data;
                        }).catch(error => {
                        if(error.response.status === 500  && error.response.data.message==='Incorrect old password!'){
                            this.messages.errorResponse = `<h4>Vasa stara sifra je netacna! Molimo Vas pokusajte ponovo...</h4>`;
            
                            setTimeout(()=>this.messages.errorResponse='', 5000);
                        }
                        else if (error.response.status === 500 || error.response.status === 404) {
                            this.messages.errorResponse = `<h4>Imali smo nekih problema na serveru,  molimo Vas pokusajte ponovo kasnije!</h4>`;
                            setTimeout(() => this.messages.errorResponse = '', 5000);
                        }
                
                        });
                    }

                    if(this.role === "manager"){
                        dataService.updateManager(profile).then(response => {
                            this.messages.successResponse = `<h4>Vas profil je uspesno izmenjen!</h4>`;
                            setTimeout(() => this.messages.successResponse = '', 3000);
                            profile = response.data;
                        }).catch(error => {
                        if(error.response.status === 500  && error.response.data.message==='Incorrect old password!'){
                            this.messages.errorResponse = `<h4>Vasa stara sifra je netacna! Molimo Vas pokusajte ponovo...</h4>`;
            
                            setTimeout(()=>this.messages.errorResponse='', 5000);
                        }
                        else if (error.response.status === 500 || error.response.status === 404) {
                            this.messages.errorResponse = `<h4>Imali smo nekih problema na serveru,  molimo Vas pokusajte ponovo kasnije!</h4>`;
                            setTimeout(() => this.messages.errorResponse = '', 5000);
                        }
                
                        });
                    }

                    if(this.role === "customer"){
                        console.log('slanje na bek');
                        dataService.updateCustomer(profile).then(response => {
                            this.messages.successResponse = `<h4>Vas profil je uspesno izmenjen!</h4>`;
                            setTimeout(() => this.messages.successResponse = '', 3000);
                            profile = response.data;
                        }).catch(error => {
                            console.log(JSON.stringify(error));
                            
                        // if(error.response.status === 500  && error.response.data.message==='Incorrect old password!'){
                        //     this.messages.errorResponse = `<h4>Vasa stara sifra je netacna! Molimo Vas pokusajte ponovo...</h4>`;
            
                        //     setTimeout(()=>this.messages.errorResponse='', 5000);
                        // }
                        // else if (error.response.status === 500 || error.response.status === 404) {
                        //     this.messages.errorResponse = `<h4>Imali smo nekih problema na serveru,  molimo Vas pokusajte ponovo kasnije!</h4>`;
                        //     setTimeout(() => this.messages.errorResponse = '', 5000);
                        // }
                
                        });
                    }

                }
                
            }
        
		},
		getUserProfileData(id){
            console.log("utvrdjivanje uloge")
            let parsToken = JSON.parse(localStorage.getItem('token'));
            this.role = parsToken.role;
            if(parsToken.role === "admin"){
                this.isAdmin = true;
                dataService.getAdmin(id).then(response => {
                    this.profileAdmin = response.data;
                })
            }
            if(parsToken.role === "delivery_man"){
                this.isDeliveryMan = true;
                dataService.getDeliveryMan(id).then(response => {
                    console.log("korisnik je delivery_man")
                    this.profileDeliveryMan = response.data;
                })
            }
            if(parsToken.role === "manager"){
                this.isManager = true;
                dataService.getManager(id).then(response => {
                    this.profileManager = response.data;
                })
            }
            if(parsToken.role === "customer"){
                this.isCustomer = true;
                dataService.getCustomer(id).then(response => {
                    this.profileCustomer = response.data;
                })
            }
        },
        isNumeric(num) {
            //isNaN(num) returns true if the variable does NOT contain a valid number
            return isNaN(num);
        }
    },
    computed:{
        id() {
            return this.$route.params.id; //preuzimam id usera na cijoj sam stranici za prikaz komentara
        }
    },
    created(){
        if(JSON.parse(localStorage.getItem('token')) == null){
            this.$router.push(`/login`);
        }else{
            this.getUserProfileData(this.id);
        }
    },
    
}
</script>

<style scoped>

#titleEffect{
  color:gold;
  font-weight: bold;
}

#user-profile-update .one-forth{
  width:24%;
  padding:10px;
  margin-bottom:15px
}

#user-profile-update label{
    color:#35424a;
    display: block;
    margin: 20px 0 10px;
    font-size: 20px;
    font-weight: bold;
}

</style>