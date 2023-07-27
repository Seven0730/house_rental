<template>
    <div>
        <el-form
            status-icon :model="postForm" :rules="rules"
            ref="postFormRef"          label-width="100px"
            size="small"
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

            <el-form-item label="房东名称" prop="title">
                <el-input placeholder="请输入" v-model="postForm.title"/>
            </el-form-item>

            <el-form-item label="区域" prop="categoryid">
                <el-select size="small" v-model="postForm.categoryid" placeholder="请选择">
                    <el-option
                        v-for="item in categoryOptions"
                        :key="item.categoryid"
                        :label="item.categoryname"
                        :value="item.categoryid">
                    </el-option>
                </el-select>


            </el-form-item>


            <el-form-item label="图片" prop="image">
                <Upload v-model="postForm.image" accept=".jpg,.png,.gif"/>
            </el-form-item>
            <el-form-item label="宣传口号" prop="slogan">
                <el-input placeholder="请输入" v-model="postForm.slogan"/>
            </el-form-item>

            <el-form-item label="上线日期" prop="createdate">
                <el-date-picker
                    value-format="YYYY-MM-DD"

                    :editable="false"
                    size="small"
                    v-model="postForm.createdate"
                    type="date"
                    placeholder="选择日期"
                    :disabled-date="pickerDayNoAfterOptions.disabledDate"
                    style="width:190px"

                >
                </el-date-picker>
            </el-form-item>

            <el-form-item label="联系方式" prop="contact">
                <el-input placeholder="请输入" v-model="postForm.contact"/>
            </el-form-item>

            <el-form-item label="房东介绍" prop="contents">


                <vue-ueditor-wrap
                    v-model="postForm.contents"
                    :config="ueditorconfig1"
                    style="line-height:20px"/>

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
    import {mapGetters} from "vuex"; //表单校验规则如身份证号 手机号
    import {validateMobileFormat} from '@/utils/validate'
    import { pickerRangOptions, pickerRangNoAfterOptions, pickerDayNoAfterOptions, pickerDayUseAfterOptions,pickerDayNobefoOptions } from '@/components/mixin/mixin_publicData'  // 一些常用的DATA里的参数,如日期选择的参数项等
    import {parseTime,} from '@/utils'

    import Upload from '@/components/Upload'

    let formData = {
        username: '',
        password: '',
        title: '',
        slogan: '',
        createdate: parseTime(new Date().getTime(), '{y}-{m}-{d}'),  /*'{y}-{m}-{d} {h}:{i}:{s}'*/
        contact: '',
        image: '',
        contents: '',

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


                const categoryOptions = ref(null)

  const postForm = ref({});//这里写注释

  const                 rules= {
                    username: [{required: true, message: "请填写"}],
                    password: [{required: true, message: "请填写"}],
                    title: [{required: true, message: "请填写"}],
                    contact: [
                        {required: true, message: "请填写"},
                        {min: 11, max: 11, message: '手机号应为11个数字', trigger: 'blur'},
                        {validator: validateMobileFormat, trigger: 'blur'}//手机格式检查
                    ],

                }
 const      ueditorconfig1= {
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
                    axios_request.get('/admin/landlord/getById.action?id='+props.dataId).then(response => {
                            if (response.data) {
                                categoryOptions.value = response.data.categoryList

                                postForm.value = response.data.landlord
                            }
                        }
                    )
                } else {
                    //显示添加数据
                    axios_request.get('/admin/landlord/create.action' ).then(response => {
                            if (response.data) {
                                categoryOptions.value = response.data.categoryList
                                postForm.value.categoryid = response.data.categoryList[0].categoryid// 首次打开,默认选中第一个

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
                            postForm.value.landlordid = props.dataId
                            axios_request.post('/admin/landlord/update.action', postForm.value).then(response => {
                                ElMessage({message: response.message})
                                if (response.message) {
                                    closeDialog()//必须在AJX异步过程内
                                }
                            })
                        } else {
                            //保存添加数据

                            axios_request.post('/admin/landlord/add.action', postForm.value).then(response => {
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
