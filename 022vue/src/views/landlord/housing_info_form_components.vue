<template>
    <div>
        <el-form
            status-icon :model="postForm" :rules="rules"
            ref="postFormRef" label-width="150px"
            size="small"
        >


            <el-form-item label="房源名称" prop="housing_infoname">
                <el-input placeholder="请输入" v-model="postForm.housing_infoname"/>
            </el-form-item>

            <el-form-item label="图片" prop="image">
                <Upload v-model="postForm.image" accept=".jpg,.png,.gif"/>
            </el-form-item>
            <el-form-item label="录相mp4" prop="image">
                <Upload v-model="postForm.files" accept=".mp4"/>
            </el-form-item>
            <el-form-item label="房源内容" prop="remark">


                <vue-ueditor-wrap
                    v-model="postForm.remark"
                    :config="ueditorconfig1"
                    style="line-height:20px"/>

            </el-form-item>


            <el-form-item label="可预约看房人数" prop="countrequire">
                <el-input placeholder="请输入" type="number" v-model="postForm.countrequire"/>
            </el-form-item>



            <el-form-item label="支付类型">

                    <el-input placeholder="请输入"  v-model="postForm.address"/>
            </el-form-item>
            <el-form-item label="地理位置">

                    <el-input placeholder="请输入"  v-model="postForm.address"/>
            </el-form-item>
            <el-form-item label="户型">

                    <el-input placeholder="请输入"  v-model="postForm.housing_info_type"/>
            </el-form-item>
            <el-form-item label="面积">

                    <el-input placeholder="请输入"  v-model="postForm.housing_info_area"/>
            </el-form-item>
            <el-form-item label="价格">


                    <el-input placeholder="请输入"  v-model="postForm.housing_info_price"/>
            </el-form-item>
            <el-form-item label="地段">

                    <el-input placeholder="请输入"  v-model="postForm.housing_info_location"/>
            </el-form-item>
            <el-form-item label="联系人">

                <el-input placeholder="请输入"  v-model="postForm.person" />
            </el-form-item>
            <el-form-item label="联系方式">

                <el-input  placeholder="请输入"
                           v-model="postForm.person_phone"/>
            </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="saveData" icon="coin">保存</el-button>
        </div>

    </div>
</template>


<script name="dialogForm" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import axios_request from '@/utils/axios_request'
import {mapGetters} from "vuex"; //表单校验规则如身份证号 手机号
import {
    pickerRangOptions,
    pickerRangNoAfterOptions,
    pickerDayNoAfterOptions,
    pickerDayUseAfterOptions,
    pickerDayNobefoOptions
} from '@/components/mixin/mixin_publicData'  // 一些常用的DATA里的参数,如日期选择的参数项等

import Upload from '@/components/Upload'

let formData = {
    housing_infoname: '',
    image: '',
    remark: '',
    countrequire: '',


}


const props = defineProps({

    //数据ID,只在这个组件中使用
    dataId: {
        type: Number,
        default: 0
    },
    //隐藏与显示,用于监控和初始,只在这个组件中使用
    dialogFormVisible: {
        type: Boolean,
        default: false
    },

});


const postForm = ref({});//这里写注释

const rules = {
    housing_infoname: [{required: true, message: "请填写"}],
    countrequire: [{required: true, message: "请填写"}],


}

const landlordOptions = ref({});//这里写注释
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

//监听数据的改变
watch(() => props.dataId, newdata => {
    //console.log('监听到改变')
    fetchData()//根据id的变化 更新表单的值

}, {deep: true})

watch(() => props.dialogFormVisible, newdata => {
    //console.log('监听到改变')
    postForm.value = JSON.parse(JSON.stringify(formData))//深拷贝了一份数据模板,用于表单初始化
    if (newdata) fetchData()//监控弹窗是否显示,显示则初始化数据

}, {deep: true})

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


// 初始化

onMounted(() => {
    postForm.value = JSON.parse(JSON.stringify(formData))//深拷贝了一份数据模板,用于表单初始化

    if (route.query.id) props.dataId = route.query.id//如果是在菜单中直接打开的路由编辑界面(不是弹窗界面),则从路由获取ID
    fetchData()
    //深拷贝了一份数据模板,用于表单初始化

})


//关闭弹窗
const emits = defineEmits(['emits'])

function closeDialog() {
    //关闭的时候 不能恢复数据,如果恢复的话,会引起子组件,传递给父组件行数据出错
    emits('close') //事件通知父组件修改el-model的显示状态
}

//获取数据,并初始化
function fetchData() {
    if (props.dataId > 0) {
        //显示编辑数据
        axios_request.get('/web/getById.action?id=' + props.dataId).then(response => {
                if (response.data) {

                    postForm.value = response.data.housing_info
                }
            }
        )
    } else {
        //显示添加数据
        axios_request.get('/web/create.action').then(response => {
                if (response.data) {

                }
            }
        )
    }

}

//保存数据
function saveData() {

    postFormRef.value.validate(valid => {
        //console.log(valid);
        if (valid) {
            postForm.value.username = ''
            postForm.value.password = ''

            if (props.dataId > 0) {
                //保存编辑数据
                postForm.value.housing_infoid = props.dataId
                axios_request.post('/web/update.action', postForm.value).then(response => {
                    ElMessage({message: response.message})
                    if (response.message) {
                        closeDialog()//必须在AJX异步过程内
                    }
                })
            } else {
                //保存添加数据

                axios_request.post('/web/add.action', postForm.value).then(response => {
                    ElMessage({message: response.message})
                    if (response.message) {
                        closeDialog()//必须在AJX异步过程内
                    }
                })
            }
        }

    })

}

</script>
