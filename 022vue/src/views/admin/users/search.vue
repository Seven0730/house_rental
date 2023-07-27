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

                <el-table-column align="center" header-align="center" label="照片"
                >
                    <template #default="scope">

                            <img :src="BASE_API+scope.row.image" style="width: 60px;height: 60px">


                    </template>
                </el-table-column>

                <el-table-column align="center" header-align="center" label="用户名">
                    <template #default="scope">
                        {{ scope.row.username }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="姓名">
                    <template #default="scope">
                        {{ scope.row.realname }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="毕业学校">
                    <template #default="scope">
                        {{ scope.row.college }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="兴趣爱好">
                    <template #default="scope">
                        {{ scope.row.major }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="性别">
                    <template #default="scope">
                        {{ scope.row.sex }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="出生日期">
                    <template #default="scope">
                        {{scope.row.createdate  }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="联系方式">
                    <template #default="scope">
                        {{ scope.row.contact }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="注册日期">
                    <template #default="scope">
                         {{ $filters.parseTime(scope.row.regdate, '{m}月{d}日') }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="状态">
                    <template #default="scope">
                        <el-tag type="success" v-if="scope.row.iscooled==='0'">正常</el-tag>
                        <el-tag type="danger" v-if="scope.row.iscooled==='1'">冻结</el-tag>


                    </template>
                </el-table-column>



                <el-table-column align="center" label="操作" width="270">
                    <template #default="scope">
                        <el-button type="danger" size="small"
                                   @click="handleIscooled(scope,'1')">冻结
                        </el-button>
                        <el-button type="primary" size="small"
                                   @click="handleIscooled(scope,'0')">正常
                        </el-button>

                        <el-button type="danger" size="small" @click="handleDelete(scope)" >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>



        </div>
        <div class="toolBarBottom_body" ref="pagebar">
            <el-pagination background   @current-change="handleCurrentChange"
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
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息


    import {tableHeight} from '@/components/mixin/mixin_publicList' // 列表组件参数利用,获取表格高度
    import axios_request from '@/utils/axios_request'
     import {mapGetters} from 'vuex'







                        const BASE_API = ref(process.env.VUE_APP_BASE_API + '/'   );

  const tableKey = ref( 0);//表格key
                const listData=ref( null)//列表数据

  const listLoading = ref( true);//加载条

const listQuery= ref({//当前页数，支持 .sync 修饰符
    pageNumber:1,//当前页
    pageSize:10,//,每页多少
    rowCountTotal:0//记录总数 默认全是0,让后台重复记算总数,因为存在,点了搜索选项,然后直接点翻页,带入总数的话,翻页出错
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



onMounted(() =>
 {
            getList()

})



             function getList() {

                listLoading.value = true
                axios_request.get('/admin/users/getAll.action',{                params: listQuery.value            }).then(response => {
                    listData.value = response.data.usersList
                    listQuery.value.rowCountTotal = response.data.pager.rowCountTotal

                    listLoading.value = false
                })
            }

            //删除过程
            function handleDelete(scope){
                ElMessageBox.confirm("确定要进行此操作吗?", "提示").then(() => {
                    //删除
                    const id = scope.row.usersid
                    axios_request.post('/admin/users/delete.action?id='+ id).then(response => {
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
            //状态操作过程
            function handleIscooled(scope,iscooled_int){
                ElMessageBox.confirm("确定要进行此操作吗?", "提示").then(() => {
                    //删除
                    const id = scope.row.usersid
                    axios_request.post('/admin/users/iscooled.action?id=' + id + '&iscooled=' + iscooled_int).then(response => {
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
