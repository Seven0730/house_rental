<template>
    <div class="app-wrapper">
        <div>
            <navbar/>
            <app-main/>
            <el-affix>
                <bottom/>
            </el-affix>
        </div>
    </div>
</template>


<script name="layout" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息


    import navbar from './Navbar'
    import bottom from './Bottom'
    import AppMain from './AppMain'






</script>
