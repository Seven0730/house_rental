<template>
    <div>


        
                <el-card v-if="housing_info!=null">


                    <div class="post   app_width">
                        <div class="post-block">
                            <span class="title">{{ housing_info.housing_infoname }}</span>
                            <span class="description">
                                <!--房东连接-->
                               <router-link :to="'/landlordDetail?id='+housing_info.landlordid">
                                     <span>{{ housing_info.landlordtitle }}</span>
                                </router-link>
                            </span>
                        </div>
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <div style="text-align: center;margin-top: 10px" v-if="housing_info.image!=null">

                                    <img
                                        :src="BASE_API+housing_info.image"
                                        style="text-align: center;width:80%;height:80%;min-height: 340px;max-height: 340px"/>
                                </div>

                            </el-col>
                            <el-col :span="12" style="padding-top: 10px">

                                <div class="text"> 可预约看房人数：{{ housing_info.countrequire }}</div>

                                <div class="text"> 发布日期：{{ housing_info.createdate }}</div>


                                <div class="text">支付类型:{{ housing_info.housing_info_pay }}</div>
                                <div class="text">地理位置:{{ housing_info.address }}</div>
                                <div class="text">
                                    户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:{{ housing_info.housing_info_type }}
                                </div>

                                <div class="text">
                                    面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;积:{{ housing_info.housing_info_area }}
                                </div>
                                <div class="text">
                                    价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格:{{ housing_info.housing_info_price }}
                                </div>
                                <div class="text">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;段:{{
                                        housing_info.housing_info_location
                                    }}
                                </div>
                                <div class="text">联系方式:

                                    <b> {{ housing_info.person }} {{ housing_info.person_phone }}</b>


                                </div>
                                <div class="text" v-if="housing_info.countrequire>0">


                                    <el-button
                                        v-if=" usersid_web>0&&role_web==1"
                                        type="primary"
                                        @click="handleToRouterLink('addorders?id='+housing_info.housing_infoid)"
                                        icon="shopping-cart-full">点击预约看房
                                    </el-button>
                                    <el-button
                                        v-else
                                        type="primary"
                                        disabled
                                        icon="shopping-cart-full">用户登录后可预约看房
                                    </el-button>
                                    <span v-if="housing_info.countrequire<0||housing_info.countrequire==0">名额已满</span>
                                    <el-button
                                        @click="jump3D"
                                        icon="shopping-cart-full">3d看房</el-button>

                                </div>
                                
                            </el-col>
                        </el-row>
                        <div class="text" v-html="housing_info.remark"></div>

                        <div style="text-align: center;margin-top: 10px" v-if="housing_info.image!=null">
                            <video v-if="housing_info.files&&housing_info.files!=''"
                                   :poster="BASE_API+housing_info.image"
                                   :src="BASE_API+housing_info.files"
                                   data-ckin="default" controls="controls" data-overlay="1"
                            />
                            <!--
                                                        <img
                                                            :src="BASE_API+housing_info.image"
                                                            style="text-align: center;width:80%"
                                                        />-->
                        </div>


                    </div>

        </el-card>

 
    </div>
</template>

<script name="housing_infoDetail" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import axios_request from '@/utils/axios_request'
import {mapGetters} from "vuex";


const housing_info = ref({});//这里写注释
const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');


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


watch(() => route, newdata => {
    // 如果路由参数有变化，会再次执行获取列表数据
    getList()

}, {deep: true})

function getList() {
    //获取分类
    if (route.query.id > 0) {
        //  console.log("content"+route.query.id)


        axios_request.get('/web/housing_infoDetail.action?id=' + route.query.id).then(response => {

            housing_info.value = response.data.housing_info

        })
    }
}

function handleToRouterLink(routerPath) {
    //菜单 的路由跳转
    router.push(routerPath)

}

function jump3D(){
        window.location.href="../../static/index.html"
    }
</script>


<style rel="stylesheet/scss" lang="scss" scoped>


.post {
    font-size: 14px;

    color: #666;

    .image {
        width: 80%;
        height: 100%;

    }


    .text {
        text-indent: 25px;
        font-size: 16px;
        line-height: 35px;
        white-space: pre-line;
    }
}

.post-block {
    text-align: center;
    border-bottom: 1px solid #d2d6de;
    padding-bottom: 15px;

    .description {
        display: block;

        padding: 2px 0;
        margin-top: 15px;
        margin-bottom: 15px;
        line-height: 30px;
    }

    .title {
        font-size: 30px;
        color: #000;
        text-align: center;
        font-weight: bold;
    }


}
</style>
