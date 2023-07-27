<template>

    <div class="footer">


        @房屋租赁管理系统
        <div class="text-center copyright">
        <router-link tag="a" target="_blank" to="/admin/login">
            <span style="color: #999;font-weight: bolder"> 管理员登录 </span>
        </router-link>

            <router-link tag="a" to="/login_2">
                <span  style="color: #999;font-weight: bolder"> 房东登录</span>
            </router-link>
        </div>


    </div>
</template>

<script setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息

    import {mapGetters} from 'vuex'
    import changePwd from '@/views/admin/changePwd'





  const dialogPwdFormVisible = ref( false);//是否显示密码修改对应框


  const dialogSetttingFormVisible = ref( false);//系统设定
                const  role=ref('')//权限汉字名称


//获取登录后的信息
const token_web = computed(() => {
    return store.getters.token_web
})
const info_web = computed(() => {
    return store.getters.info_web
})
const usersid_web = computed(() => {
    return store.getters.usersid_web
})


const token = computed(() => {
    return store.getters.token
})
const username = computed(() => {
    return store.getters.username
})
const roleId = computed(() => {
    return store.getters.roleId
})
const info = computed(() => {

    return store.getters.info
})


const account = computed(() => {
    return store.getters.account
})
const adminid = computed(() => {
    return store.getters.adminid
})
const role_web = computed(() => {
    return store.getters.role_web
})



onMounted(() =>
 {



})








</script>


<style rel="stylesheet/scss" lang="scss" scoped>


.footer {
    border-top: 2px solid #533336;
    margin-top: 10px;
    padding-top: 30px;
    padding-bottom: 30px;
    background-color: #533336;
    text-align: center;
    color: #999;
    line-height: 25px;
    font: 12 px/150% Arial, Verdana, "microsoft yahei";

}


</style>



