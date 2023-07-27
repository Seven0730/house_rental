<template>
    <div class=" app_width">
        <el-row :gutter="20">


            <el-col :span="24" :xs="24">
                <el-card>
                    <template #header class="clearfix">
                        <span>资料修改 </span>
                    </template>
                    <el-form status-icon :model="loginForm" :rules="pwdRules" ref="postFormRef" label-width="120px">
                        <el-form-item label="用户名" prop="username">
                            {{ loginForm.username }}

                        </el-form-item>


                        <el-form-item label="房东名称" prop="title">
                            <el-input placeholder="请输入" v-model="loginForm.title"/>
                        </el-form-item>

                        <!--<el-form-item label="区域" prop="categoryid">
                            <el-select size="small" v-model="loginForm.categoryid" placeholder="请选择">
                                <el-option
                                    v-for="item in categoryOptions"
                                    :key="item.categoryid"
                                    :label="item.categoryname"
                                    :value="item.categoryid">
                                </el-option>
                            </el-select>


                        </el-form-item>-->


                        <el-form-item label="图片" prop="image">
                            <Upload v-model="loginForm.image" accept=".jpg,.png,.gif"/>
                        </el-form-item>
                        <el-form-item label="宣传口号" prop="slogan">
                            <el-input placeholder="请输入" v-model="loginForm.slogan"/>
                        </el-form-item>

                        <el-form-item label="上线日期" prop="createdate">
                            <el-date-picker
                                value-format="YYYY-MM-DD"

                                :editable="false"
                                size="small"
                                v-model="loginForm.createdate"
                                type="date"
                                placeholder="选择日期"
                                :disabled-date="pickerDayNoAfterOptions.disabledDate"
                                style="width:190px"

                            >
                            </el-date-picker>
                        </el-form-item>

                        <el-form-item label="联系方式" prop="contact">
                            <el-input placeholder="请输入" v-model="loginForm.contact"/>
                        </el-form-item>

                        <el-form-item label="房东介绍" prop="contents">


                            <vue-ueditor-wrap
                                v-model="loginForm.contents"
                                :config="ueditorconfig1"
                                style="line-height:20px"/>

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


<script name="changePwd" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import {mapGetters} from 'vuex'
import axios_request from '@/utils/axios_request'
import {validateMobileFormat} from '@/utils/validate'
import {
    pickerRangOptions,
    pickerRangNoAfterOptions,
    pickerDayNoAfterOptions,
    pickerDayUseAfterOptions,
    pickerDayNobefoOptions
} from '@/components/mixin/mixin_publicData'  // 一些常用的DATA里的参数,如日期选择的参数项等
import {parseTime,} from '@/utils'

import Upload from '@/components/Upload'

let formData = {
    username: '',
    realname: '',
    title: '',
    slogan: '',
    createdate: parseTime(new Date().getTime(), '{y}-{m}-{d}'),  /*'{y}-{m}-{d} {h}:{i}:{s}'*/
    contact: '',
    image: '',
    contents: '',

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


const loading = ref(false)//加载提示
const loginForm = ref(formData)

const pwdRules = {

    contact: [
        {required: true, message: "请填写"},
        {min: 11, max: 11, message: '手机号应为11个数字', trigger: 'blur'},
        {validator: validateMobileFormat, trigger: 'blur'}//手机格式检查
    ],

}
const ueditorconfig1 = {
    //多媒体编辑器的配置
    headers: {
        token: sessionStorage.getItem("Admin-Token") //   token传给资源服务器。
    },
    serverUrl: null,
    // UEditor 资源文件的存放路径，如果你使用的是 vue-cli 生成的项目，通常不需要设置该选项，vue-ueditor-wrap 会自动处理常见的情况，如果需要特殊配置，参考下方的常见问题2
    UEDITOR_HOME_URL: '/UEditor/',
    // 初始容器高度
    initialFrameHeight: 300,
    // 初始容器宽度
    initialFrameWidth: '100%',
    // 编辑器不自动被内容撑高
    autoHeightEnabled: false,
}

categoryOptions:{
}

onMounted(() => {

    loginForm.value = JSON.parse(JSON.stringify(formData))//初始化数据
    if (postFormRef.value) {
        //这个fetchData第一次是created调用的,界面还未初始化,resetFields功能是不可使用的
        //然后watch变化 后,再调用 就可以使用了
        postFormRef.value.resetFields()
    }//对整个表单进行重置，将所有字段值重置为初始值并移除校验结果 只能在methods中使用
    fetchData()

})


function fetchData() {

    axios_request.get('/web/preInfo_2.action?id=' + usersid_web.value).then(response => {
            if (response.data) {
                loginForm.value = response.data.landlord
            }
        }
    )


}

//
function saveData() {
    //console.dir(loginForm.value.depidArray)
    postFormRef.value.validate(valid => {


        if (valid) {

            axios_request.post('/web/editinfo_2.action', loginForm.value).then(response => {

                ElMessage({
                    message: response.message
                })
                ElMessageBox.alert('修改成功，请重新登录', '提示', {
                    confirmButtonText: '重新登录',
                    type: 'warning'
                }).then(() => {
                    store.dispatch('FedLogOut')
                    store.dispatch('FedLogOut_web')
                    location.reload();// 为了重新实例化vue-router对象 避免bug
                    /* store.dispatch('FedLogOut').then(() => {
                         location.reload();// 为了重新实例化vue-router对象 避免bug
                     });*/
                })


            })
        }
    })
}


</script>
