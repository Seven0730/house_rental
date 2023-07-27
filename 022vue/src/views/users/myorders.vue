<template>
    <div>


        <div class="app-container">

            <el-table stripe border fit highlight-current-row
                      :key='tableKey'
                      :data="listData"
                      v-loading="listLoading"
                      element-loading-text="正在加载..."
                      style="width: 100%"

            >

                <el-table-column align="center" header-align="center" label="房源名称"  width="400">
                    <template #default="scope">
                        <router-link :to="'/housing_infoDetail?id='+scope.row.housing_infoid" style="color: #00a0e9;">
                            {{ scope.row.housing_infoname }}
                        </router-link>
                    </template>
                </el-table-column>


                <el-table-column align="center" header-align="center" label="预约看房日期">
                    <template #default="scope">
                        {{ $filters.parseTime(scope.row.addtime, '{m}月{d}日') }}

                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="状态">
                    <template #default="scope">

                        {{scope.row.status}}

                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="备注">
                    <template #default="scope">

                        {{scope.row.memo}}

                    </template>
                </el-table-column>

                <el-table-column align="center" label="操作">
                    <template #default="scope">


                        <el-button v-if="scope.row.status=='待审核'"    text  size="small"
                                   @click="handleCancel(scope)">取消
                        </el-button>
                        <span v-if="scope.row.status=='审核通过'">审核通过</span>
                    </template>
                </el-table-column>
            </el-table>


        </div>


    </div>

</template>

<script name="search" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息


    import {tableHeight} from '@/components/mixin/mixin_publicList' // 列表组件参数利用,获取表格高度
    import axios_request from '@/utils/axios_request'
    import {mapGetters} from 'vuex'








  const tableKey = ref( 0);//表格key
                const listData=ref( null)//列表数据

  const listLoading = ref( true);//加载条




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
            getList()

})



             function getList() {

                listLoading.value = true
                            axios_request.get('/web/myOrders.action' ).then(response => {
                    listData.value = response.data.ordersList

                    listLoading.value = false
                })
            }

            //取消过程
            function handleCancel(scope){
                ElMessageBox.confirm("确定要进行此操作吗?", "提示").then(() => {
                    //删除
                    const id = scope.row.ordersid
                                    axios_request.get('/web/cancel.action?id='+ id).then(response => {
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
            function handleFilter(){
                listQuery.value.pageNumber = 1
                //listQuery.value.rowCountTotal = 0
                getList()
            }

            //当前页输入框
            function handleCurrentChange(val){
                listQuery.value.pageNumber = val
                getList()
            }



</script>
