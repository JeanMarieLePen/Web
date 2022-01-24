<template>
    <div id="footer" class="bg-gold text-white">
        <p>Copyrights &copy; Cash n' Curry</p>
    </div>
</template>

<script>
export default {
    name:'Footer',
    data:function(){
        return{

        }
    }
}
</script>

<style scoped>

#footer{
    position: fixed;
    bottom : 0;
    padding:20px;
    margin-top:50px;
    background-color:gold;
    color:#35424a;
    text-align: center;
    font-size: 14px;
    width:100%
}

</style>