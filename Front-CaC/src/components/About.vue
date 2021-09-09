<template>
    <div id='about'>
        <section id="main">
            <div class="container">
               <article id="main-col">
                    <h1 class="page-title">O nama</h1>
                    <p>
                        Cash n' curry je aplikacija koja vam omogucava da dobijete najbolju hranu u samo nekoliko minuta u vasem gradu. Mi spajamo korisnike, restorane i dostavljace hrane kako bismo to omogucili. Pomocu ove aplikacije, zelimo da pruzimo svima lak pristup bilo kojem restoranu u ovom gradu,
                        sa odrzivim uticajem na ekonomiju, drustvo i zivotnu sredinu.
                    </p>
                    <p style="font-size:40px; color:orange;" class="dark">
                        <b><center>Sta mi nudimo?</center></b>
                        <center>Jednostavnu, sigurnu i brzu dostavu hrane.</center>
                    </p>
               </article>

               <aside id="sidebar">
                    <div class="dark">
                        <img src="../assets/hrana.jpg" width="330" height="370">
                    </div>
                </aside>

            </div>
        </section>
    </div>
</template>
<script>

export default {
    name:'About',
    methods:{

    }
}
</script>
<style scoped>
body{
    background-color:#f4f4f4;
    margin:0;
    padding:0;
    font-family: Arial, Helvetica, sans-serif;
    font-size: 15px;
    line-height: 1.5em;
    /*isto*/
    font: 15px 1.5em Arial, Helvetica, sans-serif;
}

/*global*/
.container{
    width:80%;
    margin:auto;
    overflow: hidden;
}

.button_1{
    height:38px;
    background-color:#e8491d;
    color:#fff;
    border:none;
    padding: 0 20px;
}

.dark{
    padding:15px;
    background-color:#35424a;
    color:#fff;
    margin:10px 0;
}

.button_1:hover{
    background-color:#cccccc;
    color:#fff;
    font-weight: bold;
}
/* Ostaviti za sada ako ne obrisati */
/*Sidebar*/
aside#sidebar{
    float: right;
    width:33%;
    margin-top:10px;
}

article#main-col{
    float: left;
    width:65%;
}
</style>



