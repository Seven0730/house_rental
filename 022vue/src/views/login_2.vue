<template>

    <div class="pc-login-bj">
        <div class=" pc-login-box_aa">
            <div class=" pc-login-box">
                <div class="pc-login-title"><h2>房东登录</h2></div>


                <el-form status-icon :model="loginForm" :rules="loginRules" ref="postFormRef" label-width="0px"
                >
                    <el-form-item label="" prop="username">
                        <el-input name="username" type="text" v-model="loginForm.username"
                                  autoComplete="on"
                                  placeholder="请填写用户名"/>
                    </el-form-item>

                    <el-form-item label="" prop="password">
                        <el-input name="password" type="password" v-model="loginForm.password"
                                  autoComplete="on"
                                  placeholder="请填写密码"/>
                    </el-form-item>

                    <el-form-item>
                        <el-button :loading="loading" type="primary" @click="handleLogin()"
                        >登录
                        </el-button>
                    </el-form-item>
                </el-form>


            </div>
        </div>
    </div>

</template>


<script name="login_2" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


//

import PanThumb from '@/components/PanThumb'
import {mapGetters} from "vuex";


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
const loginForm = ref({
    username: 'test',
    password: '111111',

})

const loginRules = {
    username: [{required: true, message: "请输入用户名", trigger: 'blur'}],
    password: [{required: true, message: "请输入密码", trigger: 'blur'}]
}
onMounted(() => {
    if (usersid_web.value > 0) {
        //如果已经 登录则跳转到 首页
        router.push({path: '/default'})

    }

})


function handleLogin() {
    //console.log(loginForm.value)
    postFormRef.value.validate(valid => {
        if (valid) {
            loading.value = true
            store.dispatch('SetStoreLoginByusername_web_2', loginForm.value).then(response => {

                loading.value = false
                router.push({path: '/default'})
                location.reload()//整个界面刷新  强制刷新  否则navbar获取不到 缓存数据
            }).catch(err => {
                //console.log(err)
                loading.value = false
                //ElMessage.error(err)

                //return false
            })
        } else {
            //console.log('error submit!!')
            return false
        }
    })
}


</script>


<style lang="scss" scoped>


.pc-login-bj {
    background-image: url("../../static/login.jpg");
    background-size: 100%;
    background-repeat: no-repeat;
    height: 550px;
}

.pc-login-box_aa {
    width: 1200px;
    margin: 0 auto;


    .pc-login-box {
        float: right;
        background-color: rgba(255, 255, 255, 0.92);
        display: inline;
        margin-top: 80px;
        margin-right: 80px;

        padding: 20px;
        width: 356px;

        .pc-login-title {
            padding-bottom: 20px;

            h2 {
                border-bottom: 1px solid #ccc;
                padding-bottom: 10px;
                font-size: 14px;
            }
        }


    }
}

.el-button {
    border: medium none;
    border-radius: 2px;
    color: #fff;
    cursor: pointer;
    display: inline;

    height: 40px;
    width: 100%;

}

.pc-reg {
    text-align: right;
    padding-top: 10px;
}

.pc-reg a {
    padding-left: 20px;
    color: #ea4949;
}
</style>
