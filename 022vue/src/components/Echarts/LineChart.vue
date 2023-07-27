<template>

    <div ref="chartRef" :class="className" :style="{height:height,width:width}"/>

</template>

<script name="chart_line" setup>
import * as echarts from 'echarts'
import {merge} from "lodash";
import {formatNumbToRMB} from "@/utils/";
import {useEventListener, tryOnUnmounted, useTimeoutFn, templateRef} from "@vueuse/core";

import('echarts/theme/macarons') // echarts theme
import('echarts/theme/dark') // echarts theme
import('echarts/theme/infographic') // echarts theme
import('echarts/theme/roma') // echarts theme
import('echarts/theme/shine') // echarts theme
import('echarts/theme/vintage') // echarts theme


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
        default: '350px'
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
    },
    tooltip: {
        trigger: 'axis',
        /* 触发类型 item主要在散点图，饼图等无类目轴的图表中使用。
           axis 坐标轴触发，主要在柱状图，折线图等会使用类目轴的图表中使用。
          */
        axisPointer: {
            type: 'cross',/*cross直线十字  shadow阴影  数值轴指示器 */
            crossStyle: {
                color: '#999'/*数值轴直线指示器*/
            }
        },
        /*textStyle: {
            align: 'right'/!*右对齐*!/
        }*/
    },
    grid: {
        left: '3%',
        right: '3%',
        containLabel: true,
    },
    toolbox: {
        feature: {
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            saveAsImage: {show: true}
        }
        /*,    orient: "vertical"竖排，注销后横排工具栏 icon 的布局朝向。*/

    },
    legend: {
        itemStyle: {
            shadowBlur: 1.5/*阴影*/
        },
        icon: "pin",
        tooltip: {
            show: true
        },
    },
    yAxis: [
        {
            type: 'value',
            nameTextStyle: {
                color: "rgba(17, 0, 0, 1)",
                fontWeight: "bold"
            }
            , axisTick: {
                show: true/*是否显示坐标轴刻度。*/
            }
        }
    ],
    xAxis: [
        {
            nameLocation: "middle",
            nameGap: 23,/*坐标轴名称与轴线之间的距离。*/

            nameTextStyle: {
                color: "rgba(17, 0, 0, 1)",
                fontWeight: "bold"
            },
            axisPointer: {/*默认不显示。但是如果 tooltip.trigger 设置为 'axis' 或者 tooltip.axisPointer.type 设置为 'cross'，则自动显示 axisPointer。坐标系会自动选择显示显示哪个轴的 axisPointer，也可以使用 tooltip.axisPointer.axis 改变这种选择。*/
                type: 'shadow',/*阴影指示器*/
            },
            boundaryGap: true,/*坐标轴两边留白策略，类目轴和非类目轴的设置和表现不一样。*/
            axisTick: {
                alignWithLabel: true/*X轴标记在中间  类目轴中在 boundaryGap 为 true 的时候有效，可以保证刻度线和标签对齐*/
            },
            splitLine: {
                show: true/*网格线，是否显示分隔线。默认数值轴显示，类目轴不显示。*/
            },
            /* inverse: false*/  //反向X轴输出

        }
    ],


})
const optionDataNow = computed(() => {
    // 合并配置对象
    if (props.optionData.series) {
        //加入数据样式
        props.optionData.series.forEach((item, i) => {
            //console.log(series_bar)
            // console.log(this.optionData.series[i])
            props.optionData.series[i] = merge({}, series_bar, props.optionData.series[i]);
        })
    }
    return merge({}, optionData_default.value, props.optionData);
});

//监听数据的改变
watch(() => props.optionData, () => {
    //console.log('监听到改变')
    if (Object.prototype.toString.call(props.optionData) === "[object Object]") {
        init();
        chart.value[chartTemp.value].setOption(props.optionData);

    }
}, {deep: true})


const series_bar = {
    animationEasing: "linear",
    emphasis: {
        focus: 'series',/*在高亮图形时，是否淡出其它数据的图形已达到聚焦的效果。*/
        blurScope: 'coordinateSystem'
    },
    itemStyle: {
        borderRadius: [5, 5, 0, 0]/*柱图上部圆角*/
    },
    label: {
        /* 每个柱子上面是有数字显示的，而且数字是有千分位符号的：*/
        show: true,
        position: 'top',
        formatter: function (num) {
            return formatNumbToRMB(num.value, 0);
        }
        /*
                , fontStyle: "oblique"
        */
        , fontSize: 12
    },
    markLine: {
        symbol: 'none',
        data: [
            {type: 'average', name: '平均值'}
        ]
    },
    smooth: true,/*如果是line 则显示平滑的*/

}

function init() {


    // 初始化
    /*if (chart.value[chartTemp.value]) {
        chart.value[chartTemp.value].dispose();
        chart.value[chartTemp.value] = null;
    }
*/

    chart.value[chartTemp.value] = echarts.init(
        chartRef.value,
        null,
        props.initOptions
    );
    //chart[chartTemp.value].clear() //清除旧画布 重新渲染
    if (chart.value[chartTemp.value]) {
        //console.log(chart)

        chart.value[chartTemp.value].on('click', function (params) {
            //console.log(params)
            //给父组件 传递图表点击参数
            emits("update:clickParams", params) // 将新的值,发给父组件
        })
    }

    if (optionDataNow.value) {
        //console.log(optionDataNow.value)
        // console.log(chart)
        chart.value[chartTemp.value].setOption(optionDataNow.value);
    }
}


/*

onBeforeMount(() => {
    nextTick(() => {
    });
});
*/


onMounted(() => {
    //页面加载后
    chartTemp.value = Math.random();

    nextTick(() => {

        init();

        useEventListener("resize", () => {
            if (!chart.value || !chart.value[chartTemp.value]) return;
            useTimeoutFn(() => {
                chart.value[chartTemp.value].resize();
            }, 180);
        });
    });
});

tryOnUnmounted(() => {
    //console.log(chart.value)
    if (!chart.value) return;
    //chart.value.dispose();
    chart.value = null;
});

</script>
