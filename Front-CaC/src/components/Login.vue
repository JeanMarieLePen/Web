 <template>
 <div id="Login">
 <!-- login forma -->
    <div class="container-fluid">
    <div class="row no-gutter">
        <div id='bgImage' class="d-none d-md-flex col-md-4 col-lg-6 ">
         <img src='src/assets/logo.png'>
        </div>
        <div class="col-md-8 col-lg-6">
        <div class="login d-flex align-items-center py-5">
            <div class="container">
            <div class="row">
                <div class="col-md-9 col-lg-8 mx-auto">
                <h3 class="login-heading mb-4">Welcome back!</h3>

                
                <form>
                    <div class="form-label-group">
                    <input  v-model="username"  id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                    <label for="inputEmail">Username</label>
                    </div>

                    <div class="form-label-group">
                    <input v-model ="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
                    <label for="inputPassword">Password</label>
                    </div>

                    <button type="button" class="btn btn-lg btn-primary btn-block btn-login text-uppercase font-weight-bold mb-2" v-on:click='submition()'>
                        Sign in
                    </button>
                    <div v-if='errorMessage' class="alert alert-danger" v-html="errorMessage"></div>
                </form>
                </div>
            </div>
            </div>
        </div>
        </div>
    </div>
    </div>

  </div>
</template>

<script>

import {bus} from '../main'
import axios from "axios";
export default {
    name: 'Login',
    data () {
      return {
        emailAddress:'',
        username:'',
        password: '',
        praznaPolja: false,
        token: null,
        neispravniPodaci: false,

        generatedCode:'',
        errorMessage:'',
      }
    },
    methods: {
      submition: function () {
        console.log(this.username);
        console.log(this.password);
        axios.post(`http://localhost:8080/WebProjekat2021/rest/login/${this.username}`, {
          password:this.password,
         }).then(response => {
            if(response.status === 200){
              console.log("Sa beka stiglo: " + response.data);
              if(response.data !== ''){
                  console.log("Status 200");
                  this.token = response.data;
                  // axios.defaults.headers.common['Authorization'] = 'Bearer ' + response.data.username;
                  console.log("Token koji se smesta u localstorage: " + JSON.stringify(this.token))
                  console.log(this.token.role)
                  if(this.token.role !== null){
                    if(this.token.role == "banovan"){
                      this.errorMessage = `<h4>Ovaj korisnicki nalog je suspendovan zbog krsenja pravila poslovanja.</h4>`;
                      setTimeout(()=>this.errorMessage='', 5000);
                    }else{
                      localStorage.setItem('token', JSON.stringify(this.token))
                      bus.$emit('loggedIn',true);
                      this.$router.push('/home'); 
                    }
                      
                  }else{
                    this.errorMessage = `<h4>Username ili password su pogresno uneti!</h4>`;
                    setTimeout(()=>this.errorMessage='',3000);
                  }
                   
              }
                       
            }           
          }).catch(error => {
            if(error.response.status === 401  && error.response.data ==='Bad credentials!'){
            this.errorMessage = `<h4>Username ili password su pogresno uneti!</h4>`;
            
            setTimeout(()=>this.errorMessage='',3000);
          }
          });
        },
    },
    computed:{
        
    },
    created(){
      if(JSON.parse(localStorage.getItem('token')) != null){
            this.$router.push(`/`);
      }else{
            
      }
    }
  }
</script>


<style>
:root {
  --input-padding-x: 1.5rem;
  --input-padding-y: 0.75rem;
}

.login,
.image {
  min-height: 100vh;
}

/* .bg-image {
  background-image: url('../assets/login.jpg');
  background-size: cover;
  background-position: center;
} */

.login-heading {
  font-weight: 300;
}

.btn-login {
  font-size: 0.9rem;
  letter-spacing: 0.05rem;
  padding: 0.75rem 1rem;
  border-radius: 2rem;
}

.form-label-group {
  position: relative;
  margin-bottom: 1rem;
}

.form-label-group>input,
.form-label-group>label {
  padding: var(--input-padding-y) var(--input-padding-x);
  height: auto;
  border-radius: 2rem;
}

.form-label-group>label {
  position: absolute;
  top: 0;
  left: 0;
  display: block;
  width: 100%;
  margin-bottom: 0;
  /* Override default `<label>` margin */
  line-height: 1.5;
  color: #495057;
  cursor: text;
  /* Match the input under the label */
  border: 1px solid transparent;
  border-radius: .25rem;
  transition: all .1s ease-in-out;
}

.form-label-group input::-webkit-input-placeholder {
  color: transparent;
}

.form-label-group input:-ms-input-placeholder {
  color: transparent;
}

.form-label-group input::-ms-input-placeholder {
  color: transparent;
}

.form-label-group input::-moz-placeholder {
  color: transparent;
}

.form-label-group input::placeholder {
  color: transparent;
}

.form-label-group input:not(:placeholder-shown) {
  padding-top: calc(var(--input-padding-y) + var(--input-padding-y) * (2 / 3));
  padding-bottom: calc(var(--input-padding-y) / 3);
}

.form-label-group input:not(:placeholder-shown)~label {
  padding-top: calc(var(--input-padding-y) / 3);
  padding-bottom: calc(var(--input-padding-y) / 3);
  font-size: 12px;
  color: #777;
}

/* Fallback for Edge
-------------------------------------------------- */

@supports (-ms-ime-align: auto) {
  .form-label-group>label {
    display: none;
  }
  .form-label-group input::-ms-input-placeholder {
    color: #777;
  }
}

/* Fallback for IE
-------------------------------------------------- */

@media all and (-ms-high-contrast: none),
(-ms-high-contrast: active) {
  .form-label-group>label {
    display: none;
  }
  .form-label-group input:-ms-input-placeholder {
    color: #777;
  }
}
</style>