<template>
    <div>
        <div class="app-container">

            <div class="filter-container" ref="toolbar_filter">





            </div>

            <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">

                    <!-- 占比饼图-->
                    <pie-chart key="a2" :option-data="ByData" element-loading-text="正在加载..."></pie-chart>

            </el-row>


        </div>
    </div>
</template>

<script name="getcontract_infonumber" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import {tableHeight} from '@/components/mixin/mixin_publicList' // 列表组件参数利用,获取表格高度
import {
    pickerRangOptions,
    pickerRangNoAfterOptions,
    pickerDayNoAfterOptions,
    pickerDayUseAfterOptions,
    pickerDayNobefoOptions
} from '@/components/mixin/mixin_publicData'  // 一些常用的DATA里的参数,如日期选择的参数项等
import axios_request from '@/utils/axios_request'

import {formatNumbToRMB} from '@/utils' // 格式化时间戳
import LineChart from '@/components/Echarts/LineChart'
import PieChart from '@/components/Echarts/PieChart';


/*  饼图*/
const ByData = ref({
    title: {
        text: '区域房源比例',

    },

})




const housing_infoOptions = ref(null);//这里写注释
const listQuery = ref({})
onMounted(() => {
    getList()

})


//搜索
function handleFilter() {

    //listQuery.value.rowCountTotal = 0
    getList()
}

function getList() {

    axios_request.get('/admin/housing_info/getAll.action', {params: listQuery.value}).then(response => {
        housing_infoOptions.value = response.data.housing_infoList


        //listLoading.value = false
    })

    // console.log(search.value)
    axios_request.get('/admin/orders/getcontract_infonumber.action', {params: listQuery.value}).then(response => {
        console.log(response.data.countList)
        if (response.data.housing_infoid) listQuery.value.housing_infoid = parseInt(response.data.housing_infoid)
        ByData.value = {

            series: [
                {
                    data: response.data.countList
                }
            ]
        };

    })
}


</script>
