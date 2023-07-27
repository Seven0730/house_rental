<template>


    <el-card style="padding-left: 20%;padding-right: 20%">

        <div class="new_title">


            <span>用户注册</span>
        </div>


        <el-form status-icon :model="loginForm" :rules="pwdRules" ref="postFormRef" label-width="120px"
                 style="margin-bottom: 150px">
                        <el-form-item label="用户名" prop="username">
                            <el-input name="username" type="text" v-model="loginForm.username"
                                      autoComplete="on"
                                      placeholder="请填写"/>
                        </el-form-item>


                        <el-form-item label="密码" prop="password">
                            <el-input name="password" type="password" v-model="loginForm.password"
                                      autoComplete="on"
                                      placeholder="请填写"/>
                        </el-form-item>
                        <el-form-item label="确认密码" prop="password1">
                            <el-input name="password1" type="password" v-model="loginForm.password1"
                                      autoComplete="on"
                                      placeholder="请再次确认"/>
                        </el-form-item>

                        <el-form-item label="姓名" prop="realname">
                            <el-input placeholder="请输入" v-model="loginForm.realname"/>
                        </el-form-item>
                        <el-form-item label="QQ" prop="qq">
                            <el-input placeholder="请输入" v-model="loginForm.qq"/>
                        </el-form-item>
                        <el-form-item label="身份证号" prop="idcard">
                            <el-input placeholder="请输入" v-model="loginForm.idcard"/>
                        </el-form-item>
                        <el-form-item label="邮件" prop="email">
                            <el-input placeholder="请输入" v-model="loginForm.email"/>
                        </el-form-item>
                        <el-form-item label="毕业学校" prop="college">
                            <el-input placeholder="请输入" v-model="loginForm.college"/>
                        </el-form-item>
                        <el-form-item label="兴趣爱好" prop="major">
                            <el-input placeholder="请输入" v-model="loginForm.major"/>
                        </el-form-item>
                        <el-form-item label="性别" prop="sex">
                            <el-radio-group v-model="loginForm.sex">
                                <el-radio label='男' key='男'>男</el-radio>
                                <el-radio label='女' key='女'>女</el-radio>
                            </el-radio-group>

                        </el-form-item>
                        <el-form-item label="出生日期" prop="createdate">
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

                        <el-form-item label="联系手机" prop="contact">
                            <el-input placeholder="请输入" type="text" v-model="loginForm.contact"></el-input>
                        </el-form-item>
                        <el-form-item label="联系地址" prop="address">
                            <el-input placeholder="请输入" type="text" v-model="loginForm.address"></el-input>
                        </el-form-item>
                        <el-form-item label="照片" prop="image">
                            <Upload v-model="loginForm.image" accept=".jpg,.png,.gif"/>
                        </el-form-item>


                        <el-form-item>
                            <el-button :loading="loading" type="primary" @click="saveData()"
                                       icon="coin">注册
                            </el-button>
                        </el-form-item>
                    </el-form>
                </el-card>


</template>


<script name="changePwd" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息



    import {mapGetters} from 'vuex'
    import axios_request from '@/utils/axios_request'
    import {validateMobileFormat,validateIdentityCodeFormat} from '@/utils/validate'
    import { pickerRangOptions, pickerRangNoAfterOptions, pickerDayNoAfterOptions, pickerDayUseAfterOptions,pickerDayNobefoOptions } from '@/components/mixin/mixin_publicData'  // 一些常用的DATA里的参数,如日期选择的参数项等
    import {parseTime,} from '@/utils'

    import Upload from '@/components/Upload'

    let formData = {
        username: '',
        password: '',
        password1: '',
        realname: '',
        sex: '男',
        createdate: parseTime(new Date().getTime(), '{y}-{m}-{d}'),  /*'{y}-{m}-{d} {h}:{i}:{s}'*/
        contact: '',
        image: '',
        qq: '',
        idcard: '',
        email: '',

        iscooled: '',
        college:'',
        major:'',
        contract_info0:'',
        contract_info1:'',
        contract_info2:'',
        contract_info3:'',
        contract_info4:'',
        contract_info5:'',
        contract_info6:'',
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
                const loginForm=ref(formData)


const validatePass2 = (rule, value, callback) => {
    if (value !==  loginForm.value.password) {
        callback(new Error('两次输入密码不一致!'));
    } else {
        callback();
    }
}

  const                 pwdRules= {
                    username: [{required: true, message: "请填写"}],
                    password: [
                        {required: true, message: "请输入"},
                        {min: 6, message: '密码必须6个字符以上', trigger: 'blur'},

                    ],
                    password1: [
                        {required: true, message: "请输入确认密码"},
                        {min: 6, message: '密码必须6个字符以上', trigger: 'blur'},
                        {validator: validatePass2, trigger: 'blur'}
                    ],
                    realname: [{required: true, message: "请填写"}],
                    contact: [
                        {required: true, message: "请填写"},
                        {min: 11, max: 11, message: '手机号应为11个数字', trigger: 'blur'},
                        {validator: validateMobileFormat, trigger: 'blur'}//手机格式检查
                    ],
                    idcard: [
                        {required: true, message: "请填写"},

                        {validator: validateIdentityCodeFormat, trigger: 'blur'}//身份证格式检查
                    ],
                }

onMounted(() =>
 {

            loginForm.value = JSON.parse(JSON.stringify(formData))//初始化数据
            if (postFormRef.value) {
                //这个fetchData第一次是created调用的,界面还未初始化,resetFields功能是不可使用的
                //然后watch变化 后,再调用 就可以使用了
                postFormRef.value.resetFields()
            }//对整个表单进行重置，将所有字段值重置为初始值并移除校验结果 只能在methods中使用

})



            //
             function saveData() {
                //console.dir(postForm.value.depidArray)
                postFormRef.value.validate(valid => {


                    if (valid) {

                        axios_request.post('/web/register.action', loginForm.value).then(response => {

                            ElMessage({
                                message: response.message
                            })

                            //如果操作成功则到 登录页,操作不成功 还留在此页
                            if (response.success) {

                                router.push({path: '/login'})
                            }
                        })
                    }
                })
            }


</script>

<style rel="stylesheet/scss" lang="scss">

.el-card__header {

    background-color: #ffffff !important;
    border-bottom: 0 !important;
}

</style>
