<template>
    <section class="main">
        <div class="logo">
            <h1>房屋租赁管理系统</h1>
        </div>
        <div class="content-w3ls">


            <el-form status-icon autoComplete="on" :model="loginForm" :rules="loginRules"
                     ref="postFormRef"          label-position="left">
                <el-input size="small" name="username" type="text" v-model="loginForm.username" autoComplete="on"
                          prefix-icon="user" placeholder="用户名"/>


                <el-input name="password" type="password" @keyup.enter.native="handleLogin"
                          prefix-icon="user" v-model="loginForm.password" autoComplete="on"
                          placeholder="密码"/>

                <div class="captcha-input">


                    <el-input
                        name="loginCode"
                        type="number"
                        @keyup.enter.native="handleLogin"
                        v-model="loginCode"
                        autoComplete="off"
                        placeholder="验证码" style="width:160px"   prefix-icon="user"/>


                    <span
                        style="font-weight: bold;font-size: 26px;color: #FFFFFF;vertical-align: middle"> {{
                            loginCode_create
                        }}</span>


                </div>
                <el-button :loading="loading" @click="handleLogin">登&nbsp;&nbsp;录</el-button>
            </el-form>
        </div>
        <div class="copyright">
            <p>
                <router-link to="/default"
                             style="color: #ff2222;font-weight: bold;text-decoration : none;margin-top: 10px">回到首页
                </router-link>
            </p>
        </div>
        <div class="copyright">
            <p>© 版权所有</p>
        </div>
    </section>
</template>


<script name="login" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息



import {mapGetters} from "vuex";





                const loginForm=ref({
                username: 'admin',
                password: '111111',
                roleId: '0'
            })

  const             loginRules= {
                username: [{required: true, message: "请输入用户名", trigger: 'blur'}],
                password: [{required: true, message: "请输入密码", trigger: 'blur'}]
            }

             const loading=ref(false)//加载提示



  const loginTabs = ref( 'userPwd');//登录tab  密码登录
            const  loginCode=ref(null)//这里写注释
             const  loginCode_create=ref(Math.floor(Math.random() * (9999 - 1000)) + 1000)//生成随机验证码


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
        if (adminid.value > 0) {
            //如果已经 登录则跳转到 首页
            router.push({path: '/admin/default'})

        }

        loginCode.value = loginCode_create.value

})





        function handleLogin(){
            console.log(loginCode_create.value)
            console.log(loginCode.value)
            if (loginCode_create.value != loginCode.value) {
                ElMessage.error("验证码错误,请重新输入")

                return
            }
            postFormRef.value.validate(valid => {
                if (valid) {
                    loading.value = true
                    store.dispatch('SetStoreLoginByusername', loginForm.value).then(response => {

                        loading.value = false
                        router.push({path: '/admin/orders/search'})
                        //location.reload()//整个界面刷新  强制刷新  否则navbar获取不到 缓存数据

                    }).catch(err => {
                        //console.log(err)
                        loading.value = false
                        ElMessage.error(err)

                        //return false
                    })
                } else {
                    //console.log('error submit!!')
                    return false
                }
            })
        }




</script>

<style scoped lang="scss">
:deep(.el-input__wrapper) {
    background-color: initial !important;
    box-shadow: initial !important;
}

.main {
    background: url(../../../static/login.jpg);
    background-size: cover;

    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    -ms-background-size: cover;
    min-height: 100vh;
    position: relative;
    z-index: 1;
    justify-content: center;
    display: grid;
    align-items: center;

}

.main:before {
    position: absolute;
    content: '';
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.6);
    z-index: -1;
}

.logo h1 {

    padding-top: 200px;
    font-size: 42px;
    color: #fff;
    text-transform: capitalize;
    font-weight: bold;
    text-align: center;
}

.content-w3ls {
    margin: 1em auto;
    padding: 1em 1em;
    width: 60%;


    .el-input {
        padding: 14px 30px;
        font-size: 16px;
        color: #999;
        letter-spacing: 0.5px;
        background: #fff;
        box-sizing: border-box;
        font-family: 'Noto Sans JP', sans-serif;
        height: 54px;
        border-radius: 35px;
        -webkit-border-radius: 35px;
        -moz-border-radius: 35px;
        -ms-border-radius: 35px;
        -o-border-radius: 35px;
        margin-bottom: 14px;


    }
}

// 验证码输入
.captcha-input {
    .el-input__wrapper {
        background-color: initial !important;
        box-shadow: initial !important;
    }


    .el-input {
        width: 60%;
    }

    span, img {
        height: 100%;
        flex-shrink: 0;
        margin-left: 16px;
    }

    span {
        color: #fff;
        font-size: 28px;
        height: 60px;
        /*background: #f7f7f7;
       border-radius: 8px;*/
    }
}

.copyright {
    padding: 0 20px;
}

.copyright p {

    color: #606263;
    font-size: 16px;
    line-height: 26px;
    text-align: center;
    letter-spacing: 1px;

}

.el-button {


    padding: 14px 30px;
    font-size: 16px;
    color: #fff;
    letter-spacing: 0.5px;
    border: 1px solid #2E68EC;
    background: #fff;
    box-sizing: border-box;
    font-family: 'Noto Sans JP', sans-serif;
    width: 100%;
    height: 54px;
    border-radius: 35px;
    -webkit-border-radius: 35px;
    -moz-border-radius: 35px;
    -ms-border-radius: 35px;
    -o-border-radius: 35px;
    background: linear-gradient(130deg, #2E68EC 0%, #2E68EC 100%);
}
</style>
