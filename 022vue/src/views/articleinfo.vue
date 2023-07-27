<template>
    <div>


        <div class="new-wrap">
            <div class="new_title">


                列表内容

            </div>


            <div class="pic_grid app_width">

                <el-row style="padding: 15px;            ">
                    <el-input placeholder="搜索" v-model="listQuery.keyword" style="width: 350px;">


                        <template #append>
                            <el-button type="primary" @click="getList" icon="search">搜索</el-button>
                        </template>

                    </el-input>
                </el-row>
            </div>
            <div class="pic_grid app_width">
                        <div v-for="(item,index) of articleinfos"

                             :key="index">


                    <div class="icon-item">
                        <router-link :to="'/articleinfoDetail?id='+item.articleid">

                            <div :style="{backgroundImage: `url(${BASE_API+item.image})`}" class="pan-thumb"></div>
                            <span>{{ item.title }}</span>
                        </router-link>
                    </div>

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
</template>

<script name="articleinfo" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息



    import axios_request from '@/utils/axios_request'







                        const  articleinfos=ref(null)//这里写注释
                const BASE_API = ref(process.env.VUE_APP_BASE_API + '/'   );

const listQuery= ref({//当前页数，支持 .sync 修饰符
    pageNumber:1,//当前页
    pageSize:12,//,每页多少
    rowCountTotal:0//记录总数 默认全是0,让后台重复记算总数,因为存在,点了搜索选项,然后直接点翻页,带入总数的话,翻页出错
})

onMounted(() =>
 {
            getList()


})


  watch(() => route, newdata => {
     // 如果路由参数有变化，会再次执行获取列表数据
    getList()

}, {deep: true})

             function getList() {

                //搜索
                if (route.query.keyword !== '') listQuery.value.keyword = route.query.keyword


                //分类检索
                if (route.query.bannerid > 0) listQuery.value.bannerid = route.query.bannerid
                axios_request.get('/web/article.action',{
                    params: listQuery.value
                }).then(response => {
                    articleinfos.value = null
                    articleinfos.value = response.data.articleList
                    listQuery.value.rowCountTotal = response.data.pager.rowCountTotal

                })

            }
            //当前页输入框
            function handleCurrentChange(val){
                listQuery.value.pageNumber = val
                getList()
            }



</script>

<style rel="stylesheet/scss" lang="scss" scoped>


</style>
