<template>
    <div v-loading="loading">
        <input id="excel-upload-input" type="file" accept=".xlsx, .xls" class="c-hide" @change="handkeFileChange">
        <el-button style="margin-left:16px;" size="small" type="primary" @click="handleUpload">浏览文件</el-button>
        <span>{{ uploadFileName }}</span>
    </div>
</template>

<script
    setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息

import XLSX from 'xlsx'

const emits = defineEmits(['on-selected-file'])
const loading = ref(false)//加载提示

const excelSheetData = ref({});//这里写注释
const uploadFileName = ref('')

//上传后将数据发送给父组件
function generateDate(datas) {
    //excelSheetData.value.header = header
    //excelSheetData.value.results = results
    excelSheetData.value = datas
    emits('on-selected-file', excelSheetData.value)
    loading.value = false
}

function handleUpload() {
    document.getElementById('excel-upload-input').click()
}

function handkeFileChange(e) {
    //console.log(e)
    const files = e.target.files
    const itemFile = files[0] // only use files[0]
    readerData.value(itemFile)
}

function readerData(itemFile) {
    if (itemFile) {
        const filesName = itemFile.name
        uploadFileName.value = filesName//显示文件名称
        loading.value = true
        const reader = new FileReader()
        reader.onload = e => {
            const data = e.target.result
            const fixedData = fixdata.value(data)
            const workbook = XLSX.read(btoa(fixedData), {type: 'base64'})
            // 只获取一个工作表
            /*                        const firstSheetName = workbook.SheetNames[0]
                                    const worksheet = workbook.Sheets[firstSheetName]
                                    const header = get_header_row.value(worksheet)
                                    const results = XLSX.utils.sheet_to_json(worksheet)
*/


            let datas = {}
            //循环获取每个表
            for (let i = 0; i < workbook.SheetNames.length; i++) {
                const sheetName = workbook.SheetNames[i]
                const worksheet = workbook.Sheets[sheetName]
                let header = get_header_row.value(worksheet)
                let results = XLSX.utils.sheet_to_json(worksheet)
                datas[i] = {header, results}
            }


            generateDate.value(datas)
        }
        reader.readAsArrayBuffer(itemFile)
    } else {
        //未选择文件 取消选择后清空
        const header = []
        const results = []
        uploadFileName.value = ''//显示文件名称
        generateDate.value({header, results})
    }
}

function fixdata(data) {
    let o = ''
    let l = 0
    const w = 10240
    for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)))
    o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)))
    return o
}

function get_header_row(sheet) {
    const headers = []
    const range = XLSX.utils.decode_range(sheet['!ref'])
    let C
    const R = range.s.r
    /* start in the first row */
    for (C = range.s.c; C <= range.e.c; ++C) { /* walk every column in the range */
        var cell = sheet[XLSX.utils.encode_cell({c: C, r: R})]
        /* find the cell in the first row */
        var hdr = 'UNKNOWN ' + C // <-- replace with your desired default
        if (cell && cell.t) hdr = XLSX.utils.format_cell(cell)
        headers.push(hdr)
    }
    return headers
}

</script>

<style scoped>
#excel-upload-input {
    display: none;
    z-index: -9999;
}
</style>
