<template>
    <div>


        <el-card v-if="landlord!=null">


            <div class="post   app_width">
                <div class="post-block">
                    <span class="title">{{ landlord.title }}</span>
                    <span class="description">
                                上线日期：{{ landlord.createdate }}

                            </span>
                </div>


                <div class="text"> 联系方式：{{ landlord.contact }}</div>
                <div class="text"> 宣传口号：{{ landlord.slogan }}</div>
                <div class="text" v-html="landlord.contents"></div>
                <div style="text-align: center;margin-top: 10px" v-if="landlord.image!=null">

                    <img
                        :src="BASE_API+landlord.image"
                        style="text-align: center;width:80%"
                    />
                </div>


            </div>


            <br/>
            <br/>
            <br/>
            <br/>
            <div v-if="housing_infos!=null">
                <el-divider><b>当前房东包含的房源</b></el-divider>


                <div class="grid">
                    <div v-for="(item,index) of housing_infos"

                         :key="index">


                        <div class="icon-item">
                            <router-link :to="'/housing_infoDetail?id='+item.housing_infoid">
                                <img :src="BASE_API+item.image">
                                <span>{{ item.housing_infoname }}</span>
                            </router-link>
                        </div>

                    </div>
                </div>

                <el-pagination background @current-change="handleCurrentChange"
                               :current-page.sync="listQuery.pageNumber "
                               :page-size="listQuery.pageSize"
                               layout="total,    ->,prev,pager, next "
                               :total="listQuery.rowCountTotal">
                </el-pagination>

            </div>

        </el-card>


    </div>
</template>

<script name="landlordDetail" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import axios_request from '@/utils/axios_request'
import {mapGetters} from "vuex";


const landlord = ref({
    title: null,
    image: null,
    contents: null,
})
const housing_infos = ref(null);

const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');

const listQuery = ref({//当前页数，支持 .sync 修饰符
    pageNumber: 1,//当前页
    pageSize: 12,//,每页多少
    rowCountTotal: 0//记录总数 默认全是0,让后台重复记算总数,因为存在,点了搜索选项,然后直接点翻页,带入总数的话,翻页出错
})


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

        listQuery.value.id = route.query.id

        axios_request.get('/web/landlordDetail.action', {params: listQuery.value}).then(response => {

            landlord.value = response.data.landlord
            housing_infos.value = null
            housing_infos.value = response.data.housing_infoList
            listQuery.value.rowCountTotal = response.data.pager.rowCountTotal

        })
    }
}

//当前页输入框
function handleCurrentChange(val) {
    listQuery.value.pageNumber = val
    getList()
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
        line-height: 25px;
    }
}

.post-block {
    text-align: center;
    border-bottom: 1px solid #d2d6de;
    padding-bottom: 15px;

    .description {
        display: block;

        padding: 2px 0;
    }

    .title {
        font-size: 30px;
        color: #000;
        text-align: center;
        font-weight: bold;
    }


}


li {
    list-style: none;
}


.grid {
    position: relative;
    display: grid;
    grid-template-columns: 25% 25% 25% 25%;
}

.icon-item {
    text-align: center;
    font-size: 30px;
    color: #24292e;
    cursor: pointer;
    margin-bottom: 30px;

    img {
        width: 90%;
        height: 150px;

    }

    span {
        clear: both;
        display: inline-block;
        font-size: 16px;
        text-overflow: ellipsis;
        white-space: nowrap;
        width: 85%;
        overflow: hidden;
        text-align: center;
        margin: 0;
        padding: 0;

    }
}


</style>
