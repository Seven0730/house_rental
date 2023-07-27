<template>
    <div>


        <div class="app-container">
            <div class="filter-container" ref="toolbar">
                <div class="fr">

                </div>


                <el-input v-model="listQuery.keyword"
                          @keyup.enter.native="handleFilter"
                          style="width: 200px;"
                          placeholder="请输入关键字"
                          clearable @change="getList"
                >
                </el-input>
                <el-button type="primary" icon="search" @click="handleFilter">搜索</el-button>

            </div>
            <el-table stripe border fit highlight-current-row
                      :key='tableKey'
                      :data="listData"
                      v-loading="listLoading"
                      element-loading-text="正在加载..."
                      style="width: 100%"
                      :height="tableHeight"
            >


                <el-table-column align="center" header-align="center" label="用户名" width="120">
                    <template #default="scope">
                        {{ scope.row.username }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="用户姓名" width="120">
                    <template #default="scope">

                        {{ scope.row.realname }}

                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="房源名称">
                    <template #default="scope">

                        {{ scope.row.housing_infoname }}

                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="添加日期" width="120">
                    <template #default="scope">
                        {{ $filters.parseTime(scope.row.addtime, '{m}月{d}日') }}
                    </template>
                </el-table-column>

                <el-table-column align="center" header-align="center" label="状态">
                    <template #default="scope">
                        {{ scope.row.status }}
                    </template>
                </el-table-column>

                <el-table-column align="center" header-align="center" label="备注">
                    <template #default="scope">
                        {{ scope.row.memo }}
                    </template>
                </el-table-column>


                <el-table-column align="center" label="操作" width="320">
                    <template #default="scope">
                        <!--<router-link :to="'/usersinfo?id='+scope.row.usersid" target="_blank">

                            <span style="font-size: 12px"> 用户详情 </span>
                        </router-link>-->
                        <el-button type="danger" size="small" @click="handleDelete(scope)"> 删除</el-button>
                        <el-button type="primary"  v-if="scope.row.status!='取消'"   size="small"
                                   @click="handleOver(scope,'审核通过')">审核通过
                        </el-button>
                        <el-button type="danger" v-if="scope.row.status!='取消'"   size="small"
                                   @click="handleOver(scope,'审核失败')">审核失败
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>


        </div>
        <div class="toolBarBottom_body" ref="pagebar">
            <el-pagination background @current-change="handleCurrentChange"
                           :current-page.sync="listQuery.pageNumber "
                           :page-size="listQuery.pageSize"
                           layout="total,    ->,prev,pager, next "
                           :total="listQuery.rowCountTotal">
            </el-pagination>
        </div>

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
    axios_request.get('/admin/orders/getAll.action', {params: listQuery.value}).then(response => {
        listData.value = response.data.ordersList
        listQuery.value.rowCountTotal = response.data.pager.rowCountTotal

        listLoading.value = false
    })
}

//删除过程
function handleDelete(scope) {
    ElMessageBox.confirm("确定要进行此操作吗?", "提示").then(() => {
        //删除
        const id = scope.row.ordersid
        axios_request.post('/admin/orders/delete.action?id=' + id).then(response => {
            ElMessage({
                message: response.message
            })
            if (response.message) {
                getList()
            }

        })
    }).catch(() => {
    });

}

//状态改变
function handleOver(scope, status) {
    ElMessageBox.confirm("确定要进行此操作吗?", "提示").then(() => {
        const id = scope.row.ordersid


        axios_request.post('/admin/orders/over.action?id=' + id + '&status=' + status).then(response => {
            ElMessage({
                message: response.message
            })
            if (response.message) {
                getList()
            }

        })
    }).catch(() => {
    });

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
