<template>
    <div style="padding-right:30%;padding-left: 30%;padding-top: 5%;padding-bottom:5%">
        <el-row :gutter="20">


            <el-card>
                <template #header class="clearfix">
                    <span>用户信息 </span>
                </template>
                <el-form status-icon :model="usersinfo"  ref="usersinfoRef"     label-width="100px">
                    <el-form-item label="用户名" prop="username">
                        {{usersinfo.username}}

                    </el-form-item>


                    <el-form-item label="姓名" prop="realname">
                        {{usersinfo.realname}}
                    </el-form-item>
                    <el-form-item label="QQ" prop="qq">
                        {{usersinfo.qq}}
                    </el-form-item>
                    <el-form-item label="身份证号" prop="idcard">
                        {{usersinfo.idcard}}
                    </el-form-item>
                    <el-form-item label="邮件" prop="email">
                        {{usersinfo.email}}
                    </el-form-item>
                    <el-form-item label="毕业学校" prop="college">
                        {{usersinfo.college}}
                    </el-form-item>
                    <el-form-item label="兴趣爱好" prop="major">
                        {{usersinfo.major}}
                    </el-form-item>
                    <el-form-item label="性别" prop="sex">
                        {{usersinfo.sex}}
                    </el-form-item>
                    <el-form-item label="出生日期" prop="createdate">
                        {{usersinfo.createdate}}
                    </el-form-item>

                    <el-form-item label="联系手机" prop="contact">
                        {{usersinfo.contact}}
                    </el-form-item>
                    <el-form-item label="联系地址" prop="address">
                        {{usersinfo.address}}
                    </el-form-item>
                    <el-form-item label="照片" prop="image">
                        <img :src="BASE_API+usersinfo.image" style="width:120px;height: 100%">

                    </el-form-item>


                </el-form>
            </el-card>

        </el-row>
    </div>
</template>


<script name="usersinfo" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息



    import {mapGetters} from 'vuex'
    import axios_request from '@/utils/axios_request'
    import { pickerRangOptions, pickerRangNoAfterOptions, pickerDayNoAfterOptions, pickerDayUseAfterOptions,pickerDayNobefoOptions } from '@/components/mixin/mixin_publicData'  // 一些常用的DATA里的参数,如日期选择的参数项等






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





                         const loading=ref(false)//加载提示

  const usersinfo = ref({});//这里写注释
                const BASE_API = ref(process.env.VUE_APP_BASE_API + '/'   );


onMounted(() =>
 {
            fetchData()


})


  watch(() => route, newdata => {
     // 如果路由参数有变化，会再次执行获取列表数据
    getList()

}, {deep: true})


            //获取数据,并初始化
             function fetchData() {

                //显示 数据
                axios_request.get('/admin/users/getById.action?id='+ route.query.id).then(response => {
                        if (response.data) {

                            usersinfo.value = response.data.users
                        }
                    }
                )


            }


</script>
