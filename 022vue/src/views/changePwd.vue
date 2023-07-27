<template>
    <div>
        <el-form status-icon :model="pwdForm" :rules="pwdRules" ref="postFormRef"          label-width="120px">
            <el-form-item label="用户名">
                {{info_web.username}}
            </el-form-item>
            <el-form-item label="旧密码" prop="password">
                <el-input name="password" type="password" v-model="pwdForm.password" autoComplete="on"
                          placeholder="请填写旧密码"/>
            </el-form-item>
            <el-form-item label="新密码" prop="new_password">
                <el-input name="new_password" type="password" v-model="pwdForm.new_password" autoComplete="on"
                          placeholder="请填写新密码"/>
            </el-form-item>
            <el-form-item label="确认密码" prop="repassword">
                <el-input name="repassword" type="password" v-model="pwdForm.repassword" autoComplete="on"
                          placeholder="请再次确认新密码"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="closeDialog" icon="more">取消</el-button>
            <el-button :loading="loading" type="primary" @click="saveData()" icon="coin">保存</el-button>
        </div>
    </div>
</template>


<script name="changePwd" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息



    import {mapGetters} from 'vuex'
    import axios_request from '@/utils/axios_request'

    let formData = {
        password: '',
        new_password: '',
        repassword: ''

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
                const pwdForm=ref(formData)//表单


const validatePass1 = (rule, value, callback) => {
    if (value ==  pwdForm.value.password) {
        callback(new Error('新输入的密码不能与旧密码相同!'));
    } else {
        callback();
    }
}
const validatePass2 = (rule, value, callback) => {
    if (value !==  pwdForm.value.new_password) {
        callback(new Error('两次输入密码不一致!'));
    } else {
        callback();
    }
}


const pwdRules = {
    password: [{required: true, message: "请输入旧密码"}],
    new_password: [
        {required: true, message: "请输入新密码"},
        {min: 6, message: '密码必须6个字符以上', trigger: 'blur'},
        {validator: validatePass1, trigger: 'blur'}
    ],
    repassword: [
        {required: true, message: "请输入确认密码"},
        {min: 6, message: '密码必须6个字符以上', trigger: 'blur'},
        {validator: validatePass2, trigger: 'blur'}
    ]
}

onMounted(() =>
 {

            pwdForm.value = JSON.parse(JSON.stringify(formData))//初始化数据
            if (postFormRef.value) {
                //这个fetchData第一次是created调用的,界面还未初始化,resetFields功能是不可使用的
                //然后watch变化 后,再调用 就可以使用了
                postFormRef.value.resetFields()
            }//对整个表单进行重置，将所有字段值重置为初始值并移除校验结果 只能在methods中使用

})




//关闭弹窗
const emits = defineEmits(['emits'])
function closeDialog() {
    //关闭的时候 不能恢复数据,如果恢复的话,会引起子组件,传递给父组件行数据出错
    emits('close') //事件通知父组件修改el-model的显示状态
}
            //密码修改
             function saveData() {
                //console.dir(postForm.value.depidArray)
                postFormRef.value.validate(valid => {

                    console.log(pwdForm.value.password)
                    console.log(info_web.value.password)
                    if (pwdForm.value.password !== info_web.value.password) {
                        ElMessage({message: "旧密码错误"})
                        return
                    }

                    if (valid) {

                       let url='/web/editpwd.action'//普通用户的
                    if (role_web.value == 2)  url='/web/editpwd_2.action'//另一个权限的修改密码连接
                    axios_request.post(url, pwdForm.value).then(response => {
                                ElMessage({
                                    message: response.message
                                })
                                if (response.success) {


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

                                    closeDialog()
                                }
                            })
                        }

                })
            }


</script>
