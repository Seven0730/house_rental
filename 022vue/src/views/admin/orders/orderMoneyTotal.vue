<template>
    <div>
        <div class="app-container">

            <div class="filter-container" ref="toolbar_filter">

                <!--
                                :disabled-date="pickerDayNoAfterOptions.disabledDate"
                -->

                <el-date-picker
                    value-format="YYYY-MM-DD"

                    :editable="false"

                    v-model="listQuery.createdate1"
                    type="date"
                    placeholder="开始日期"
                    style="width:190px"

                >
                </el-date-picker>
                <el-date-picker
                    value-format="YYYY-MM-DD"

                    :editable="false"

                    v-model="listQuery.createdate2"
                    type="date"
                    placeholder="结束日期"
                    style="width:190px"

                >
                </el-date-picker>

                <el-button type="primary" icon="search" @click="handleFilter">搜索</el-button>

            </div>

            <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
                <line-chart key="a2" :option-data="dayData" v-loading="listLoading"
                            element-loading-text="正在加载..."></line-chart>
            </el-row>


        </div>
    </div>
</template>

<script name="orderMoneyTotal" setup>
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

import {formatNumbToRMB, parseTime} from '@/utils' // 格式化时间戳
import LineChart from '@/components/Echarts/LineChart'


// ----------------------------------------------------按日的柱图
const dayData = ref({
    title: {text: ' 每日分析'},

    yAxis: [
        {
            type: 'value',
            name: '数量',
            axisLabel: {
                formatter: '{value}  '
            }
        }
    ],
    xAxis: [
        {
            type: 'category',

        }
    ],
    series: [
        {
            name: '预约看房人数',
            type: 'bar',

        },

    ]

})


const listLoading = ref(false)//加载提示


const listQuery = ref({// 搜索参数
    createdate1: null,
    createdate2: null
})
onMounted(() => {
    getList()

})


//搜索
function handleFilter() {

    //listQuery.value.rowCountTotal = 0
    getList()
}

function getList() {
    // console.log(year)


    listLoading.value = true
    // console.log(search.value)
    axios_request.get('/admin/orders/orderMoneyTotal.action', {params: listQuery.value}).then(response => {
        // console.log(111)
        // monthData.value = response

        listQuery.value.createdate1 = response.data.createdate1
        listQuery.value.createdate2 = response.data.createdate2
        let tips = ""
        if (listQuery.value.createdate1 && listQuery.value.createdate2) {
            tips = ' ' + listQuery.value.createdate1 + '到' + listQuery.value.createdate2
        }
        dayData.value = {

            xAxis: [
                {
                    data: response.data.dateList
                }
            ],

            series: [
                {
                    data: response.data.moneyList
                },

            ]
        }
        listLoading.value = false
    })
}


</script>
