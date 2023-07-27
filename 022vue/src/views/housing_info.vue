<template>
    <div>


        <div class="new-wrap">
            <div class="new_title">


                <span>房源列表</span>
            </div>


            <div class="pic_grid app_width">
                <el-input placeholder="房源搜索" v-model="listQuery.keyword" style="width: 350px;">


                    <template #append>
                        <el-button type="primary" @click="getList" icon="search">搜索</el-button>
                    </template>

                </el-input>
            </div>
            <div class="pic_grid app_width">
                <div v-for="(item,index) of housing_infos"

                     :key="index">


                    <div class="icon-item">
                        <router-link :to="'/housing_infoDetail?id='+item.housing_infoid">

                            <div :style="{backgroundImage: `url(${BASE_API+item.image})`}" class="pan-thumb"></div>
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
    </div>
</template>

<script name="housing_info" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import axios_request from '@/utils/axios_request'


const housing_infos = ref(null);

const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');

const listQuery = ref({//当前页数，支持 .sync 修饰符

    keyword: '',//搜索关键词
    pageNumber: 1,//当前页
    pageSize: 12,//,每页多少
    rowCountTotal: 0//记录总数 默认全是0,让后台重复记算总数,因为存在,点了搜索选项,然后直接点翻页,带入总数的话,翻页出错
})

onMounted(() => {
    getList()


})


watch(() => route, newdata => {
    // 如果路由参数有变化，会再次执行获取列表数据
    getList()

}, {deep: true})

function getList() {


    //分类检索
    if (route.query.landlordid > 0) listQuery.value.landlordid = route.query.landlordid
    if (route.query.categoryid > 0) listQuery.value.categoryid = route.query.categoryid
    axios_request.get('/web/housing_info.action', {params: listQuery.value}).then(response => {
        housing_infos.value = null
        housing_infos.value = response.data.housing_infoList
        listQuery.value.rowCountTotal = response.data.pager.rowCountTotal

    })

}

//当前页输入框
function handleCurrentChange(val) {
    listQuery.value.pageNumber = val
    getList()
}


</script>

<style rel="stylesheet/scss" lang="scss" scoped>


</style>
