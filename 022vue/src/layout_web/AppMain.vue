<template>
    <section class="app-main"   style="min-height: 100%;">

     <router-view v-slot="{ Component }">
            <transition name="fade-transform" mode="out-in">

                <component :is="Component"/>

            </transition>
        </router-view>
    </section>
</template>


<script name="AppMain" setup>
import store from '@/store'    
const postFormRef = ref(null) 
const route = useRoute();  
const router = useRouter();  
import {ElMessage,ElMessageBox} from "element-plus"; 



const is_login = computed(() => {
    let routename = route.name !== undefined ? route.name : route.path
    //console.log(routename)
    return routename === '/default'||routename === '/login'||routename === '/login_2'
});



</script>
<style lang="scss" scoped>
.app-main {
    /* 50= navbar  50  */
    min-height: calc(100vh - 50px);

    position: relative;
    margin: 0 auto;


}


</style>
