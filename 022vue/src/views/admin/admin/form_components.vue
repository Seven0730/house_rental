<template>
    <div>
        <el-form
            status-icon :model="postForm" :rules="rules"
            ref="postFormRef"          label-width="100px"
            size="small"
            style="width: 50%;padding-top:20px "
        >


            <el-form-item label="用户名" prop="username">
                <el-input name="username" type="text" v-model="postForm.username"
                          autoComplete="on"
                          placeholder="请填写"/>
            </el-form-item>


            <el-form-item label="密码" prop="password">
                <el-input name="password" type="password" v-model="postForm.password"
                          autoComplete="on"
                          placeholder="请填写"/>
            </el-form-item>


            <el-form-item label="姓名" prop="realname">
                <el-input placeholder="请输入" v-model="postForm.realname"/>
            </el-form-item>
            <el-form-item label="联系手机" prop="contact">
                <el-input placeholder="请输入" type="text" v-model="postForm.contact"></el-input>
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
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息



    import axios_request from '@/utils/axios_request'
    import {validateMobileFormat} from '@/utils/validate'
    import {mapGetters} from "vuex"; //表单校验规则如身份证号 手机号

    let formData = {
        username: '',
        password: '',
        realname: '',
        contact: '',

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

  const                 rules= {
                    username: [{required: true, message: "请填写"}],
                    password: [
                        {required: true, message: "请输入"},
                        {min: 6, message: '密码必须6个字符以上', trigger: 'blur'},
                     ],

                    realname: [{required: true, message: "请填写"}],

                    contact: [
                        {required: true, message: "请填写"},
                        {min: 11, max: 11, message: '手机号应为11个数字', trigger: 'blur'},
                        {validator: validateMobileFormat, trigger: 'blur'}//手机格式检查
                    ],

                }
//监听数据的改变
watch(() => props.dataId,  newdata => {
    //console.log('监听到改变')
    fetchData()//根据id的变化 更新表单的值

}, {deep: true})

watch(() => props.dialogFormVisible,  newdata => {
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

onMounted(() =>
 {
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
                    axios_request.get('/admin/admin/getById.action?id='+ props.dataId).then(response => {
                            if (response.data) {

                                postForm.value = response.data.admin
                            }
                        }
                    )
                } else {
                    //显示添加数据
                    axios_request.get('/admin/admin/create.action' ).then(response => {
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

                        if (props.dataId > 0) {
                            //保存编辑数据
                            postForm.value.adminid=props.dataId
                            axios_request.post('/admin/admin/update.action', postForm.value).then(response => {
                                ElMessage({message: response.message})
                                if (response.message) {
                                    closeDialog()//必须在AJX异步过程内
                                }
                            })
                        } else {
                            //保存添加数据

                            axios_request.post('/admin/admin/add.action', postForm.value).then(response => {
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
