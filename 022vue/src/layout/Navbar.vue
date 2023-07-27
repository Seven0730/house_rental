<template>
    <div class="main-header">
        <div class="header">
             <div class="logo">
                 房屋租赁管理系统
            </div>
            <div class="user">


                 欢迎您： <!--{{adminid }}-&#45;&#45;{{roleId }}-&#45;&#45;  -->  {{info.realname}} {{username}}

                <span style="margin-right: 20px"></span>

                <el-button  text      @click="handleToRouterLink('/admin/default')">首页</el-button>
                <el-button  text     @click="dialogPwdFormVisible = true">修改密码</el-button>
                <el-button  text  @click.native="logout">退出登录</el-button>

            </div>
        </div>

        <!--单列标准尺寸 width="40%" top="10vh"-->
        <el-dialog v-if="dialogPwdFormVisible" :close-on-click-modal="false" title="密码修改"
                   v-model="dialogPwdFormVisible" width="40%" top="10vh" key="dialogPwdFormVisible">
            <change-pwd
                @close="dialogPwdFormVisible=false">
            </change-pwd>
        </el-dialog>

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



    // 初始化

onMounted(() =>
 {


})



        function handleToRouterLink(routerPath){
            //console.log(routerPath)
            //菜单 的路由跳转
            router.push(routerPath)

        }

        async  function  logout() {
            ElMessageBox.confirm('确定退出?', '提示').then(async () => {
                await store.dispatch('LogOut')
                // 必须用GO,跳转并刷新页面,
                //
                router.go(`/admin/login`)
            }).catch(() => {
            });
        }


</script>

<style scoped lang="scss">

.header {
    overflow: hidden;
    padding: 0 25px;
    background: #232323;
    display: flex;
    line-height: 60px;
    height: 60px;

    .img_logo {
        height: 80%;
        position: relative;
        top: 8px;
        margin-right: 30px;
        opacity: 0.9;
        filter: drop-shadow(0 2px 2px rgba(255,255,255,.3));
    }

    .logo {
        height: 60px;
        flex-shrink: 0;
        line-height: 60px;

        text-align: center;
        font-weight: bold;
        font-size: 28px;
        color: #D3D3D3;


    }

    h2 {
        width: 50%;
        flex-shrink: 0;
        font-size: 19px;
        font-weight: 600;
        color: #606263;
        display: inline;


    }

    .user {
        width: 20%;
        margin-right: 10px;
        text-align: right;
        flex: 1;
        color: #FFFFFF;
        .el-dropdown {
            top: 2px;
        }

        img {
            width: 32px;
            position: relative;
            top: 10px;
            margin-right: 10px;
        }
        .el-button--text{
            color: #ffffff;
        }
    }
}

</style>

