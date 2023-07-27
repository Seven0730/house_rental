<template>
    <el-container class="app-layout">

        <el-header>
            <navbar/>
        </el-header>

        <el-container>
            <el-aside width="208px">
                <sidebar/>
            </el-aside>
            <el-main>

                <main>

                    <app-main/>
                </main>
            </el-main>
        </el-container>
    </el-container>
</template>


<script name="Layout" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息



import navbar from './Navbar'
import AppMain from './AppMain'
import sidebar from './Sidebar'







</script>

<style lang="scss" scoped>

.el-container {
    background: #F7F8F9;
    height: 100%;
    display: flex;
    overflow: hidden;

    .el-header {
        padding: 0 !important
    }


    // 左边菜单
    .el-aside {
        width: 208px !important;
        flex-shrink: 0;
        height: 100%;
        overflow-y: auto;
        background: #333333;
        color: #D6D6D6;
        transition: width ease .3s;


    }

    // 右边内容
    .el-main {
        width: 100%;
        height: 100%;
        padding: 0;
        position: relative;
        display: flex;
        flex-direction: column;
        overflow: hidden;


    }
}

// 页面过渡动画
.fade-enter-active, .fade-leave-active {
    transition: all .5s;
    opacity: 1;
    position: absolute;
    width: 100%;
}

.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */
{
    transform: translateY(200px);
    opacity: 0;
    transition: all .5s;
    position: absolute;
}
</style>
