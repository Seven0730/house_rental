<template>
    <div class="menu">

        <el-scrollbar  >
            <el-menu
                :default-active="$route.path"
                ref="menuRef"
                text-color="#fff"
                active-text-color="#fff"
                :collapse="false"
                :collapse-transition="false"
                background-color="#333333"
            >


                <el-menu-item v-if="adminid==1" index="21-1" @click="handleToRouterLink('/admin/orders/search')">
                    <el-icon>
                        <Menu/>
                    </el-icon>
                    预约看房列表
                </el-menu-item>


                <el-menu-item index="22-1" @click="handleToRouterLink('/admin/category/search')">
                    <el-icon>
                        <Menu/>
                    </el-icon>
                    区域管理
                </el-menu-item>
                <el-menu-item index="22-1" @click="handleToRouterLink('/admin/landlord/search')">
                    <el-icon>
                        <Menu/>
                    </el-icon>
                    房东管理
                </el-menu-item>
                <el-menu-item index="22-2" @click="handleToRouterLink('/admin/housing_info/search')">
                    <el-icon>
                        <Menu/>
                    </el-icon>
                    房源管理
                </el-menu-item>


                <el-menu-item
                    index="133"
                    key="133"
                    @click="handleToRouterLink('/admin/contract_info/search')"
                >
                    <el-icon>
                        <Menu/>
                    </el-icon>
                    合同信息
                </el-menu-item>
                <el-sub-menu index="22" v-if="adminid==1">
                    <template #title>
                        <el-icon>
                            <Menu/>
                        </el-icon>
                        统计分析
                    </template>
                    <el-menu-item
                        index="orderMoneyTotal"
                        key="orderMoneyTotal"
                        @click="handleToRouterLink('/admin/orders/orderMoneyTotal')"
                    >

                        预约看房人数分析
                    </el-menu-item>
                    <el-menu-item
                        index="getcontract_infonumber"
                        key="getcontract_infonumber"
                        @click="handleToRouterLink('/admin/orders/getcontract_infonumber')"
                    >

                        区域房源分析
                    </el-menu-item>
                </el-sub-menu>

                <el-menu-item v-if="adminid==1"
                              index="1"
                              key="1"
                              @click="handleToRouterLink('/admin/admin/search')"
                >
                    <el-icon>
                        <Menu/>
                    </el-icon>
                    后台管理员列表
                </el-menu-item>
                <el-menu-item v-if="adminid==1"
                              index="12"
                              key="12"
                              @click="handleToRouterLink('/admin/banner/search')"
                >
                    <el-icon>
                        <Menu/>
                    </el-icon>
                    新闻分类列表
                </el-menu-item>
                <el-menu-item v-if="adminid==1"
                              index="13"
                              key="13"
                              @click="handleToRouterLink('/admin/articleinfo/search')"
                >
                    <el-icon>
                        <Menu/>
                    </el-icon>
                    网站新闻列表
                </el-menu-item>


                <el-menu-item v-if="adminid==1"
                              index="15"
                              key="15"
                              @click="handleToRouterLink('/admin/users/search')"
                >
                    <el-icon>
                        <Menu/>
                    </el-icon>
                    用户列表
                </el-menu-item>


            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息

import {mapGetters} from 'vuex'




// defaultOpeneds:1

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


function handleToRouterLink(routerPath) {
    //console.log(routerPath)
    //菜单 的路由跳转
    router.push(routerPath)
}


</script>
<style lang="scss" scoped>

.menu {
    height: 100%;
    display: flex;
    flex-direction: column;


}
 .el-scrollbar {
    height:  100% ;
}
.el-scrollbar__wrap {
    overflow-x: hidden !important;
}

// 菜单样式
.el-menu {
    border-right: 0 !important;
    user-select: none;
    background: #333333 !important;
    color: #D6D6D6;

    .el-menu-item {
        color: #D6D6D6;
        background: #333333 !important;
        // 选中状态
        &.is-active {
            background: #007465 !important;
        }

        // 悬浮
        &:hover {
            background-color: transparent;
            background-color: #76b4ac !important;
        }

        &:focus {
            background: #007465 !important;
        }
    }

    // 子菜单
    .el-sub-menu {
        .el-sub-menu__title {
            background-color:  #333333;
            // 悬浮
            &:hover {
                background-color: #76b4ac !important;
            }
            color: #D6D6D6;


        }

        &.is-active {

           /* .el-sub-menu__title {
                background-color: #ff0000;
            }
*/
            .el-menu .el-menu-item {
                background-color: #ff0000;
                // 悬浮
                &:hover {
                    background-color: #76b4ac !important;
                }
            }
        }

        // 菜单上下箭头
        .el-sub-menu__title i {
            color: #D6D6D6;
        }
    }

    // 菜单图标
    i:not(.el-sub-menu__icon-arrow) {
        color: #f7f7f7 !important;
        position: relative;
        top: -1px;
        // 自定义图标
        &[class^="eva-icon-"] {
            width: 24px;
            margin-right: 5px;
            background-size: 15px;
        }
    }
}
</style>
