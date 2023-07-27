<template>
    <div>
        <div class="app-container">
            <el-row>
                <el-col>

                    <el-card>
                        <template #header class="clearfix text-center ">
                            欢迎使用<!--{{token}}-->
                        </template>
                        <div style="min-height: 100px;padding: 20px">
                            {{username}}
                            <!--  {{info.password}}-->
                        </div>
                    </el-card>
                </el-col>

            </el-row>

        </div>
    </div>
</template>

<script name="dashboard" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息





    import {mapGetters} from 'vuex'







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


})


</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .el-form-item {
        margin-bottom: 5px;
    }

    .panel-group {
        .card-panel-col {
            margin-bottom: 32px;
        }

        .card-panel {
            border-radius: 4px;
            border: 1px solid rgba(0, 0, 0, .05);
            height: 108px;
            font-size: 12px;
            position: relative;
            overflow: hidden;
            color: #666;
            background: #fff;
            box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);

            &:hover {
                .card-panel-icon-wrapper {
                    color: #fff;
                }

                .icon-people {
                    background: #40c9c6;
                }

                .icon-message {
                    background: #36a3f7;
                }

                .icon-money {
                    background: #f4516c;
                }

                .icon-shoppingCard {
                    background: #34bfa3
                }
            }

            .icon-people {
                color: #40c9c6;
            }

            .icon-message {
                color: #36a3f7;
            }

            .icon-money {
                color: #f4516c;
            }

            .icon-shoppingCard {
                color: #34bfa3
            }

            .card-panel-icon-wrapper {
                float: left;
                margin: 14px 0 0 14px;
                padding: 16px;
                transition: all 0.38s ease-out;
                border-radius: 6px;
            }

            .card-panel-icon {
                float: left;
                font-size: 48px;
            }

            .card-panel-description {
                float: right;
                font-weight: bold;
                margin: 26px;
                margin-left: 0px;

                .card-panel-text {
                    line-height: 18px;
                    color: rgba(0, 0, 0, 0.45);
                    font-size: 16px;
                    margin-bottom: 12px;
                    float: right;
                }

                .card-panel-num {
                    float: right;
                    line-height: 18px;
                    font-size: 20px;
                }
            }
        }
    }
</style>
