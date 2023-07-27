<template>
    <div>
        <div class="header-logo ">
            <div class="logo  ">

                <img src="../../static/logo.png">
                房屋租赁管理系统

            </div>
            <ul class="clearfix nav-wrap">
                <li>
                    <router-link class="nav-item" :to="'/default'">

                        首页
                    </router-link>
                </li>


                <li v-for="(item,index) of menu">
                    <router-link class="nav-item" :to="'/articleinfo?bannerid='+item.bannerid"

                    > {{ item.bannername }}
                    </router-link>
                </li>

                <li  >
                    <router-link class="nav-item" :to="'/landlord'"

                    > 房东信息
                    </router-link>
                </li>

                <li  >
                    <router-link class="nav-item" :to="'/housing_info'"

                    > 房源信息
                    </router-link>
                </li>


            </ul>

        </div>
        <div class="header-nav">
            <div class="header-con">
                <div class=" header-link">

                    <router-link :to="'/login'" v-if="!usersid_web>0">

                        登录
                    </router-link>


                    <router-link :to="'/register'" v-if="!usersid_web>0">

                        注册
                    </router-link>
                    <span index="1000" v-if="usersid_web>0&&role_web==1" style="float: right">

                    <span style=" font-weight: bold; ">用户 <!--{{info_web.realname }}-->
                        {{ info_web.username }}</span>

                        <!-- 修改密码-->
                        <a
                            @click="dialogPwdFormVisible = true"


                        >
                            密码修改
                        </a>


                        <router-link :to="'/editinfo'"
                        >
                            资料修改
                        </router-link>


                        <router-link
                            :to="'/myorders'"
                        >
                            我的预约看房

                        </router-link>


                        <router-link
                            :to="'/contract_info'"
                        >
                            合同信息

                        </router-link>


                        <a @click="logout"
                        >
                            退出
                        </a>

                    </span>


                    <!--房东用户-->
                    <span index="1000" v-if="usersid_web>0&&role_web==2" style="float: right">

                            <span style=" font-weight: bold; "> 房东 {{
                                    info_web.username
                                }}</span>

                        <!-- 修改密码-->
                        <a
                            @click="dialogPwdFormVisible = true"


                        >
                            密码修改
                        </a>


                        <router-link
                            :to="'/editinfo_2'"
                        >
                            资料修改
                        </router-link>



                   <router-link
                            :to="'/listorders_landlord'"
                        >
                            预约看房列表

                        </router-link>


                   <router-link
                            :to="'/landlord/housing_info'"
                        >
                            房源信息

                        </router-link>

                   <router-link
                            :to="'/landlord/contract_info'"
                        >
                            合同信息

                        </router-link>




                        <a
                            @click="logout"
                        >
                            退出
                        </a>

                    </span>


                </div>
                <div class="head-form ">


                        <router-link v-for="(item,index) of categoryList" class="nav-item" :to="'/housing_info?categoryid='+item.categoryid"

                        > {{ item.categoryname }}
                        </router-link>



                </div>
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
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息

import {mapGetters} from 'vuex'
import changePwd from '@/views/changePwd'
import axios_request from '@/utils/axios_request'


const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');


const dialogPwdFormVisible = ref(false);//是否显示密码修改对应框


const badge_number = ref(0);//

const menu = ref({});//这里写注释
const categoryList = ref({});//这里写注释


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


onMounted(() => {
    getList()

})


function getList() {
    //获取分类
    axios_request.get('/web/bannerList.action', {params: null}).then(response => {
        menu.value = response.data.bannerList
        //console.log(usersid_web.value)
        //console.log(token_web.value)
        //console.dir(info_web.value)

    })
    //获取区域
    axios_request.get('/web/categoryList.action', {params: null}).then(response => {
        categoryList.value = response.data.categoryList
        //console.log(usersid_web.value)
        //console.log(token_web.value)
        //console.dir(info_web.value)

    })
    if (usersid_web.value > 0) {

    }
}

function handleToRouterLink(routerPath) {
    //菜单 的路由跳转
    router.push(routerPath)

}

async function logout() {
    ElMessageBox.confirm('确定退出?', '提示').then(async () => {
        await store.dispatch('LogOut_web')
        // 必须用GO,跳转并刷新页面,
        //
        router.go(`/default`)
    }).catch(() => {
    });
}


</script>

<style rel="stylesheet/scss" lang="scss" scoped>


.header-nav {
    width: 100%;
    height: 38px;
    background: #f2f2f2;
    border-bottom: 5px solid #533336;
    line-height: 35px;

    .header-con {
        width: 1200px;
        margin: 0 auto;
        position: relative;

        .header-link {

            float: left;

            a {
                color: #533336;
                padding: 0 5px;
            }
        }


        .head-form {

            width: 280px;
            float: right;

            line-height: 35px;
            a {
                color: #533336;
                padding: 0 5px;
            }
        }

    }
}


.header-logo {
    width: 1200px;
    height: 88px;
    line-height: 88px;
    margin: 0 auto;
    position: relative;
    clear: both;

    .logo {

        float: left;
        width: 29%;

        font-weight: bold;
        font-size: 32px;

        img {
            height: 60px;
            vertical-align: middle;
        }

    }


    .nav-wrap {
        float: right;
    }

    .nav-wrap > li, .nav-item {
        float: left;
        width: 138px;
        text-align: center;
        color: #533336;
        font-size: 18px;
    }

    .nav-item:hover {
        background-color: #533336;
        color: #fff;
    }

    .nav-wrap .nav-active {
        background-color: #533336;
        color: #FFFFFF;
    }

}


</style>



