<template>
    <div ref="chartRef" :class="className" :style="{height:height,width:width}"></div>
</template>

<script name="chart_pie" setup>
import * as echarts from 'echarts'
import {merge} from "lodash";
import {formatNumbToRMB} from "@/utils/";
import {tryOnUnmounted, useEventListener, useTimeoutFn} from "@vueuse/core/index";


import('echarts/theme/macarons') // echarts theme
import('echarts/theme/dark') // echarts theme
import('echarts/theme/infographic') // echarts theme
import('echarts/theme/roma') // echarts theme
import('echarts/theme/shine') // echarts theme
import('echarts/theme/vintage') // echarts theme


let chart = ref({});
let chartRef = ref(null);
const chartTemp = ref(null)
const emits = defineEmits(['update:clickParams'])
const optionData_default = ref({
    title: {
        textStyle: {
            fontSize: 12,
            /*
                        fontStyle: "italic",
            */
            fontWeight: "bold",
            textBorderColor: "rgba(81, 193, 16, 1)",
            textBorderWidth: 0.5,
            textBorderType: "solid",
            textShadowColor: "rgba(81, 193, 16, 1)",
            textShadowBlur: 1,
            textShadowOffsetX: 1,
            textShadowOffsetY: 0.5
        },
        x: "center"

    },
    tooltip: {
        trigger: 'item',
        /* 鼠标划过标签*/
        /* formatter: "{b} : {c} ({d}%)"B对应name c对应 value  d对应百分比 */
        formatter: function (num) {
            // console.log(num);
            return num.name + ':' + formatNumbToRMB(num.value, 0) + '(' + num.percent + '%)'
        }
    },

    legend: {
        itemStyle: {
            shadowBlur: 1.5/*阴影*/
        },
        icon: "pin",
        tooltip: {
            show: true
        },
        bottom: '0', /*底部中间显示*/
        left: 'center'

    },

    series: [{
        type: 'pie',
        radius: '60%',
        center: ['50%', '60%'],
        label: {
            formatter: function (num) {
                // console.log(num);
                return '{name|' + num.name + '}\n' +
                    '{data|' + formatNumbToRMB(num.value, 0) + '(' + num.percent + '%)}'
            },
            alignTo: 'edge',
            minMargin: 5,
            edgeDistance: 10,
            lineHeight: 15,
            rich: {
                name: {
                    fontSize: 12,
                    fontWeight: 'bold',
                },
                data: {
                    fontSize: 10,
                    color: '#999'
                }
            },

        },
        labelLine: {
            length: 15,
            length2: 0,
            maxSurfaceAngle: 80
        },
        labelLayout: function (params) {
            //console.log(params)
            const isLeft = params.labelRect.x < params.labelRect.width / 2;

            const points = params.labelLinePoints;
            // Update the end point.
            points[2][0] = isLeft
                ? params.labelRect.x
                : params.labelRect.x + params.labelRect.width;
            return {
                labelLinePoints: points
            };
        },
        itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
        }


    }]
})

const props = defineProps({
    className: {
        type: String,
        default: 'chart'
    },
    width: {
        type: String,
        default: '100%'
    },
    height: {
        type: String,
        default: '450px'
    },
    optionData: {
        //数据
        type: Object,
        default: null
    },
    initOptions: {
        //参数配置
        type: Object,
        default: {locale: 'ZH'}
    },
    clickParams: null//给父组件 传递图表点击参数
});
const optionDataNow = computed(() => {
    return merge({}, optionData_default.value, props.optionData);// 合并配置对象
});

//监听数据的改变
watch(() => props.optionData, () => {
    //console.log('监听到改变')
    if (Object.prototype.toString.call(props.optionData) === "[object Object]") {
        chart.value[chartTemp.value].setOption(props.optionData);
    }
}, {deep: true})

function init() {
    // 初始化
    //console.log(optionData)
    // console.log(initOptions)
    if (chart.value[chartTemp.value]) {
        chart.value[chartTemp.value].dispose();
        chart.value[chartTemp.value] = null;
    }

    /* console.log(        proxy )
     console.log(        proxy.$refs['chart'])*/

    chart.value[chartTemp.value] = echarts.init(
        chartRef.value,
        null,
        props.initOptions
    );
    if (chart.value[chartTemp.value]) {
        //console.log(chart)

        chart.value[chartTemp.value].on('click', function (params) {
            //console.log(params)
            //给父组件 传递图表点击参数
            emits('update:clickParams', params) // 将新的值,发给父组件
        })
    }

    if (optionDataNow.value) {
        chart.value[chartTemp.value].setOption(optionDataNow.value);
    }
}

/*

onBeforeMount(() => {
    nextTick(() => {
        init();
    });
});
*/


onMounted(() => {
    //页面加载后
    chartTemp.value = Math.random();

    nextTick(() => {

        init();

        useEventListener("resize", () => {
            if (!chart.value||!chart.value[chartTemp.value]) return;
            useTimeoutFn(() => {
                chart.value[chartTemp.value].resize();
            }, 180);
        });
    });
});

tryOnUnmounted(() => {
    // console.log(chart.value)
    if (!chart.value) return;
    //chart.value.dispose();
    chart.value = null;
});

</script>
