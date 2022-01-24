<template>
    <div style="width:50%">
        <div class="container">
        <h2>Kreiranje novog artikla</h2>
        <p>Popunite formular kako biste kreirali novi artikal...</p>
        <table class="table" id="table_newproduct">
            <tbody>
                <tr>
                   <td>
                       <label class='label'>Naziv artikla:</label>
                    </td> 
                    <td>
                        <input type="text" placeholder="Unesite naziv artikla(najvise 100 karaktera)" maxlength="100" v-model="newProduct.naziv">
                    </td>
                </tr>
                <tr>
                   <td>
                       <label class='label'>Cena artikla:</label>
                    </td> 
                    <td>
                        <input type="number" min="0" placeholder="Unesite cenu artikla(ne moze biti negativna vrednost)..."  v-model="newProduct.cena">
                    </td>
                </tr>
                <tr>
                   <td>
                       <label class='label'>Unesite tip artikla:</label>
                    </td> 
                    <td>
                        <span >
                            <select style="width:100%;padding:5px;" v-model="newProduct.typeOfArtikal">
                                <option disabled value="">Odabir tipa</option>
                                <option v-bind:key="tipTemp.tipp" v-for="tipTemp in tipovi">{{tipTemp.tipp}}</option>
                            </select>
                        </span>                    
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class='label'>Kolicina:</label>
                    </td>
                    <td>
                        <input type="number" placeholder="Unesite kolicinu"  v-model="newProduct.kolicina">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class='label'>Opis:</label>
                    </td>
                    <td>
                        <input type="number" placeholder="Unesite opis"  v-model="newProduct.opis">
                    </td>
                </tr>
                <tr style="height:500px">
                        <td>Slike artikla:</td>
                        <td>
                            <table id="tabela_5" class="table table-light">
                                <thead>
                                    <tr>
                                        <th class="text-center" colspan="2">Odabrane slike artikla:</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div style="height:300px; overflow-y: scroll;" class="container">
                                                <div class="col-ml-4" v-bind:key="tempSlika" v-for="tempSlika in slike">
                                                    <div class="card">
                                                        <img class="card-img-top" style="margin-top:10px; margin-bottom:10px" :src="tempSlika">
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tfoot>
                                    <td colspan="2" class="text-center">
                                        <div>
                                            <input  type="file" style="display:inline-block; width:70%" class="filestyle"  @change="uploadImage" multiple/>
                                            <button style="font-weight:600;display:inline-block;width:100px;" class="btn btn-warning"  @click="ponistiIzbor()">Ponisti</button>
                                        </div> 
                                        
                                    </td>
                                </tfoot>
                            </table>
                        </td>
                    </tr>
            </tbody>
            <tfoot style="margin-bottom:200xp; height:150px;">
                <td colspan="2" class="text-center">
                    <button class="btn btn-success" v-on:click='createProduct()'>Potvrdi</button>
                    <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
                    <div v-if='messages.errorResponse' class="alert alert-danger" v-html="messages.errorResponse"></div>
                    
                </td>
            </tfoot>
        </table>
        <!-- <div class="container"><label class='label'>Slika artikla:</label>
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card border-0">
                    <span>
                        <label>Images</label>
                        <input type="file" name="file" class="filestyle" multiple @change="uploadImage"/>
                    </span>
                </div>
            </div>
            <div v-if='messages.successResponse' class="alert alert-success" v-html="messages.successResponse"></div>
            <div v-if='messages.errorResponse' class="alert alert-danger" v-html="messages.errorResponse"></div>
            <button class="btn btn-success" v-on:click='createProduct()'>Potvrdi</button>
        </div> -->
        </div>
        <div style="height:100px">

        </div>
    </div>
    
</template>

<script>
import dataService from '../services/DataService'

export default {
    data(){
        return{
            username:'',
             restorani:[
                // {naziv:'test1'},
                // {naziv:'test2'}
            ],

            slike:[],
            newProduct:{
                naziv:'',
                cena:'',
                typeOfArtikal:'',
                kolicina:'',
                opis:'',               
                slike:[],       
            },
            messages:{
                successResponse:'',
                errorResponse:'',
            },
            tipovi:[
                {tipp:'jelo'},
                {tipp:'pice'}
            ]
        }
    },
    watch:{
        'newProduct.cena':function(val){
            this.newProduct.cena = val.replace(/[^0-9]/g, "");
        },
        'newProduct.kolicina':function(val){
            this.newProduct.kolicina = val.replace(/[^0-9]/g, "");
        },
    },
    methods:{
        ponistiIzbor(){
            this.newProduct.slike = [];
            this.slike = [];
        },
        createProduct:function(){
            

            console.log(JSON.stringify(this.newProduct));
            console.log(this.newProduct);
            console.log("NA BEK IDE KORISNICKO IME: " + this.username);
            dataService.addProduct(this.username, this.newProduct).then(response => {
                if(response.data !== ''){
                    this.messages.successResponse= "<h4>Uspesno ste kreirali artikal.</h4>"
                    setTimeout(() => this.messages.successResponse='', 3000);
                }else{
                    this.messages.errorResponse= "<h4>Vec postoji artikal sa tim imenom.</h4>"
                    setTimeout(() => this.messages.errorResponse='', 3000);
                }
                
                
            }).catch(error => {
                if(error.response.status === 500 || error.response.status === 404){
                        this.messages.errorResponse= `<h4>We had some server errors, please try again later!</h4>`;
                        setTimeout(() => this.messages.errorResponse='', 5000);
                }
            })
        },
        uploadImage:function(e){
            console.log("uslo u upload");
            // ne moze da stoji const reader jer je filereader asinhron a ovde imamo
            // for loop sto znaci da je neophodno vise filereadera
            //const reader = new FileReader();
            let images = [];
            for(let i = 0; i < e.target.files.length; i++){
                images.push(e.target.files[i]);
                console.log("DODATA SLIKA BROJ: " + i);
            }
            for(let i = 0; i < images.length; i++){
                //kreiramo FileReader za svaku sliku pojedinacno
                let reader = new FileReader();
                reader.readAsDataURL(images[i]);
                reader.onload = () => {
                    this.newProduct.slike.push(reader.result);
                    this.slike.push(reader.result);
                }
            }
        },
    },
    created(){      
        let temp = JSON.parse(localStorage.getItem('token'));
        this.username = temp.username;
    }
}
</script>


<style scoped>

#table_newproduct{
    border-style: solid;
}
#table_newproduct tr:nth-child(even){
    background-color: lightgray;
    font-weight: 600;
}
#table_newproduct tr:nth-child(odd){
    font-weight: 600;
    background-color: white;
}
#table_newproduct td:first-child{
    width: 30%;
}
#table_newproduct td:last-child{
    width: 100%;
}
input{
    width: 100%;
}

</style>