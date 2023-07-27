<template>
    <div>
        <el-row :gutter="20">


            <el-col :span="24" :xs="24">
                <el-card>
                    <template #header class="clearfix">
                        <span>预约看房 </span>
                    </template>
                    <el-form status-icon :model="postForm" :rules="Rules" ref="postFormRef"          label-width="100px">
                        <el-form-item label="房源名称" prop="housing_infoname">
                            {{housing_info.landlordtitle}} {{housing_info.housing_infoname}}

                        </el-form-item>





                        <el-form-item label="备注" prop="contact">
                            <el-input placeholder="请输入" type="text" v-model="postForm.memo"></el-input>
                        </el-form-item>


                        <el-form-item>
                            <el-button :loading="loading" type="primary" @click="saveData()"
                                       icon="coin">保存
                            </el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </el-col>

        </el-row>
    </div>
</template>


<script name="addorders" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息



    import {mapGetters} from 'vuex'
    import axios_request from '@/utils/axios_request'
        import { pickerRangOptions, pickerRangNoAfterOptions, pickerDayNoAfterOptions, pickerDayUseAfterOptions,pickerDayNobefoOptions } from '@/components/mixin/mixin_publicData'  // 一些常用的DATA里的参数,如日期选择的参数项等
    import {parseTime,} from '@/utils'

    let formData = {
        housing_infoid: 0,
        ordertime: parseTime(new Date().getTime(), '{y}-{m}-{d}'),  /*'{y}-{m}-{d} {h}:{i}:{s}'*/
        memo: '',


    }





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
                const postForm=ref(formData)//表单


  const housing_info = ref( {});//房源信息

  const Rules = ref({});//这里写注释


  watch(() => route, newdata => {
     // 如果路由参数有变化，会再次执行获取列表数据
    getList()

}, {deep: true})


onMounted(() =>
 {



            getList()

})


            //获取数据
             function getList() {

                postForm.value = JSON.parse(JSON.stringify(formData))//初始化数据
                if (postFormRef.value) {
                    //这个fetchData第一次是created调用的,界面还未初始化,resetFields功能是不可使用的
                    //然后watch变化 后,再调用 就可以使用了
                    postFormRef.value.resetFields()
                }//对整个表单进行重置，将所有字段值重置为初始值并移除校验结果 只能在methods中使用

                axios_request.post('/web/preOrders.action?id='+route.query.id).then(response => {
                    housing_info.value = response.data.housing_info

                })

            }
            //保存数据
             function saveData() {
                //console.dir(postForm.value.depidArray)
                postFormRef.value.validate(valid => {


                    if (valid) {


                        ElMessageBox.confirm("确定进行此操作吗?", "提示").then(() => {
                            postForm.value.housing_infoid = housing_info.value.housing_infoid
                             		axios_request.post('/web/addOrders.action', postForm.value).then(response => {

                                ElMessage({
                                    message: response.message
                                })

                                //如果操作成功则到 ,操作不成功 还留在此页
                                if (response.success) {

                                    router.push({path: '/myorders'})
                                }

                            })
                        })
                    }
                })
            }


</script>
