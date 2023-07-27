<template>
    <div>
        <el-form
            status-icon :model="postForm" :rules="rules"
            ref="postFormRef" label-width="100px"
            size="small"
        >
            <el-form-item label="用户" prop="usersid">
                <el-select size="small" v-model="postForm.usersid" placeholder="请选择">
                    <el-option
                        v-for="item in usersOptions"
                        :key="item.usersid"
                        :label="item.realname"
                        :value="item.usersid">
                    </el-option>
                </el-select>


            </el-form-item>
            <el-form-item label="房源" prop="housing_infoid">
                <el-select size="small" v-model="postForm.housing_infoid" placeholder="请选择">
                    <el-option
                        v-for="item in housing_infoOptions"
                        :key="item.housing_infoid"
                        :label="item.housing_infoname"
                        :value="item.housing_infoid">
                    </el-option>
                </el-select>


            </el-form-item>


            <el-form-item label="添加时间" prop="addtime">
                <el-date-picker
                    value-format="YYYY-MM-DD HH:mm:ss"

                    :editable="false"
                    size="small"
                    v-model="postForm.addtime"
                    type="datetime"
                    placeholder="选择日期"
                    :disabled-date="pickerDayNoAfterOptions.disabledDate"
                    style="width:190px"

                >
                </el-date-picker>
            </el-form-item>


            <el-form-item label="金额" prop="number">
                <el-input placeholder="请输入" v-model="postForm.number"/>
            </el-form-item>

            <el-form-item label="附件" prop="contract_info_files">
                <Upload v-model="postForm.contract_info_files"  />

             </el-form-item>


            <el-form-item label="说明" prop="memo">
                <el-input placeholder="请输入" v-model="postForm.memo"/>
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
import {parseTime,} from '@/utils'

let formData = {
    usersid: [],
    housing_infoid: [],
    number: 0,
    contract_info_files: '',
    memo: '',
    addtime: parseTime(new Date().getTime(), '{y}-{m}-{d} {h}:{i}:{s}'),


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


const usersOptions = ref(null);//这里写注释
const housing_infoOptions = ref(null);//这里写注释


const postForm = ref({});//这里写注释

const rules = {
    number: [{required: true, message: "请填写"}],
    contract_info_files: [{required: true, message: "请填写"}],


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
        axios_request.get('/web/getByIdContract_info.action?id=' + props.dataId).then(response => {
                if (response.data) {
                    usersOptions.value = response.data.usersList
                    housing_infoOptions.value = response.data.housing_infoList

                    postForm.value = response.data.contract_info
                    postForm.value.housing_infoid = parseInt(postForm.value.housing_infoid)

                }
            }
        )
    } else {
        //显示添加数据
        axios_request.get('/web/createContract_info.action').then(response => {
                if (response.data) {
                    usersOptions.value = response.data.usersList
                    housing_infoOptions.value = response.data.housing_infoList

                    postForm.value.usersid = response.data.usersList[0].usersid// 首次打开,默认选中第一个
                    postForm.value.housing_infoid = response.data.housing_infoList[0].housing_infoid// 首次打开,默认选中第一个

                    //console.log(response.data.specialty[0].id)
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


            if (props.dataId > 0) {
                //保存编辑数据
                postForm.value.contract_infoid = props.dataId
                axios_request.post('/web/updateContract_info.action', postForm.value).then(response => {
                    ElMessage({message: response.message})
                    if (response.message) {
                        closeDialog()//必须在AJX异步过程内
                    }
                })
            } else {
                //保存添加数据

                axios_request.post('/web/addContract_info.action', postForm.value).then(response => {
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
