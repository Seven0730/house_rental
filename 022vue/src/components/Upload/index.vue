<!--文件上传-->
<template>
    <div class="upload-container">
        <el-upload
            class="image-uploader"
            :accept="accept"
            :multiple="false"
            :show-file-list="false"
            :action="BASE_API+'system/image.action'"
            :on-success="handleImageScucess"
            name="image"

        >
            <el-button type="primary">点击上传</el-button>
        </el-upload>
        {{ modelValue }}

        <div class="image-preview" v-if="imageUrl&&typeof(imageUrl)!=='undefined'"><!--如果不等于服务器地址代表上传成功 显示预览图-->
            <div class="image-preview-wrapper">
                <img :src="imageUrl"/>
                <!--                <div class="image-preview-action">
                                    <i @click="rmImage" class="el-icon-delete">删除</i>
                                </div>-->
            </div>
        </div>
    </div>
</template>

<script name="singleImageUpload"
        setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import {computed} from 'vue'

const emits = defineEmits(['update:modelValue'])

const props = defineProps({
    modelValue: String,
    accept: String//允许上传文件类型".jpg,.png,.gif,.rar,.zip"
});


// 计算属性
let imageUrl = computed(() => {
    //console.log(  props.accept)
    if (props.accept == '.jpg,.png,.gif' && props.modelValue && typeof (props.modelValue) !== 'undefined') {

        //console.log(process.env.VUE_APP_BASE_API + '/' + props.modelValue)
        return process.env.VUE_APP_BASE_API + '/' + props.modelValue//用于上传后的显示 要加服务器地址
    } else {
        return null
    }
})


const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');


function rmImage() {
    //删除
    emits('update:modelValue', '')//给上级赋值
}

function handleImageScucess(file) {
    //上传成功后
    //console.log(file)
    //console.log(file.data.imageFileName)
    emits('update:modelValue', file.data.imageFileName)//给上级赋值
}

</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "src/styles_web/mixin.scss";

.upload-container {
    width: 100%;
    position: relative;
    @include clearfix;

    .image-uploader {

        float: left;
    }

    .image-preview {
        width: 60px;
        height: 60px;
        position: relative;
        border: 1px dashed #d9d9d9;
        float: left;
        margin-left: 50px;

        .image-preview-wrapper {
            position: relative;
            width: 100%;
            height: 100%;

            img {
                width: 100%;
                height: 100%;
            }
        }

        .image-preview-action {
            position: absolute;
            width: 100%;
            height: 100%;
            left: 0;
            top: 0;
            cursor: default;
            text-align: center;
            color: #fff;
            opacity: 0;
            background-color: rgba(0, 0, 0, .5);
            transition: opacity .3s;
            cursor: pointer;
            text-align: center;


        }

        &:hover {
            .image-preview-action {
                opacity: 1;
            }
        }
    }

}
</style>
