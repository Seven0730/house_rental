<template>
    <div>




            <el-card v-if="articleinfo!=null">


                <div class="post  app_width">
                    <div class="post-block">
                        <span class="title">{{ articleinfo.title }}</span>
                        <span class="description">


                                发布时间：{{ $filters.parseTime(articleinfo.addtime, '{m}月{d}日') }}
                                 查看{{ articleinfo.hits }}次
                            </span>
                    </div>


                    <div style="text-align: center;margin-top: 10px" v-if="articleinfo.image!=null">

                        <img
                            :src="BASE_API+articleinfo.image"
                            style="text-align: center;width:80%"
                        />
                    </div>
                    <div class="text" v-html="articleinfo.contents"
                         style="white-space: pre-line;">

                    </div>

                </div>


            </el-card>





    </div>
</template>

<script name="articleinfoDetail" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息



import axios_request from '@/utils/axios_request'
import {mapGetters} from "vuex";






const  articleinfo = ref({
                title: null,
                image: null,
                contents: null,
            })
            const BASE_API = ref(process.env.VUE_APP_BASE_API + '/'   );





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


  watch(() => route, newdata => {
     // 如果路由参数有变化，会再次执行获取列表数据
    getList()

}, {deep: true})

         function getList() {
            //获取分类
            if (route.query.id > 0) {
                //console.log("content" + route.query.id)

                let listQuery = {}
                listQuery.id = route.query.id

                axios_request.get('/web/articleDetail.action', {params: listQuery}).then(response => {

                    articleinfo.value = response.data.article
                    articleinfo.value.contents = articleinfo.value.contents.replace(/\n|\\r\\n/g, '<br>')//替换掉内容里的/r/nVUE处理不了

                })
            }
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
