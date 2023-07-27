<template>
    <div>




            <el-card>

                <div class="app-container">

                    <el-table stripe border fit highlight-current-row
                              :key='tableKey'
                              :data="listData"
                              v-loading="listLoading"
                              element-loading-text="正在加载..."
                              style="width: 100%"
                    >

                        <el-table-column align="center" header-align="center" label="用户" width="150">
                            <template #default="scope">
                                {{ scope.row.username }} {{ scope.row.realname }}

                            </template>
                        </el-table-column>

                        <el-table-column align="left" header-align="center" label="房源名称" show-overflow-tooltip>
                            <template #default="scope">
                                {{ scope.row.housing_infoname }}
                            </template>
                        </el-table-column>
                        <el-table-column align="center" header-align="center" label="添加时间" width="180">
                            <template #default="scope">
                                {{ scope.row.addtime }}

                            </template>
                        </el-table-column>
                        <el-table-column align="center" header-align="center" label="金额" width="120">
                            <template #default="scope">
                                {{ scope.row.number }}
                            </template>
                        </el-table-column>

                        <el-table-column align="left" header-align="center" label="附件" show-overflow-tooltip>
                            <template #default="scope">
                                 <a :href="BASE_API+'/'+scope.row.contract_info_files" target="_blank">查看附件</a>
                            </template>
                        </el-table-column>

                        <el-table-column align="center" header-align="center" label="备注" width="180">
                            <template #default="scope">
                                {{ scope.row.memo }}
                            </template>
                        </el-table-column>


                    </el-table>


                </div>

                <el-pagination background @current-change="handleCurrentChange"
                               :current-page.sync="listQuery.pageNumber "
                               :page-size="listQuery.pageSize"
                               layout="total,    ->,prev,pager, next "
                               :total="listQuery.rowCountTotal">
                </el-pagination>

            </el-card>


    </div>
</template>

<script name="search" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import {tableHeight} from '@/components/mixin/mixin_publicList' // 列表组件参数利用,获取表格高度
import axios_request from '@/utils/axios_request'
import {mapGetters} from 'vuex'

const BASE_API = ref(process.env.VUE_APP_BASE_API);

const tableKey = ref(0);//表格key
const listData = ref(null)//列表数据

const listLoading = ref(true);//加载条

const listQuery = ref({//当前页数，支持 .sync 修饰符
    pageNumber: 1,//当前页
    pageSize: 10,//,每页多少
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


function getList() {

    listLoading.value = true
    axios_request.get('/web/contract_info.action', {params: listQuery.value}).then(response => {
        listData.value = response.data.contract_infoList
        listQuery.value.rowCountTotal = response.data.pager.rowCountTotal

        listLoading.value = false
    })
}








//搜索
function handleFilter() {
    listQuery.value.pageNumber = 1
    //listQuery.value.rowCountTotal = 0
    getList()
}

//当前页输入框
function handleCurrentChange(val) {
    listQuery.value.pageNumber = val
    getList()
}


</script>
