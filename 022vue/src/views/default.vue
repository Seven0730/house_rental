<template>
    <div>


        <div class="carousel-images " v-if="favs&&favs.length>0">
            <!--轮播-->
            <el-carousel :interval="5000" arrow="always" height="620px">
                <el-carousel-item v-for="(item,index) of favs" :key="index">
                    <router-link :to="'/articleinfoDetail?id='+item.articleid">
                        <img :src="BASE_API+item.image" class="image">
                    </router-link>
                </el-carousel-item>

            </el-carousel>
        </div>

        <div class="   new-wrap        ">
            <div class="new_title">


                最新内容

            </div>
            <div class="pic_grid app_width">
            <div v-for="(item,index) of housing_infos" :key="index">
                <div class="icon-item">
                    <router-link :to="'/housing_infoDetail?id='+item.housing_infoid">
                     <div :style="{backgroundImage: `url(${BASE_API+item.image})`}" class="pan-thumb"></div>

                            <span> {{ item.housing_infoname }} </span>
                        </router-link>
                    </div>
                </div>

            </div>
        </div>



        <!--按分类名称显示新闻列表  前两个个栏目     文字 -->
        <div class="   new-wrap        ">
            <div class="new_title">


                新闻资讯


            </div>
            <div class="app_width">
                <el-row>
                    <el-col style="margin-bottom: 10px" :span="12"
                            v-for="(item,index) of fronts" :key="index"
                    >
                        <div class="text_list_box" v-if="index<2">
                            <div class="title">
                                <router-link :to="'/articleinfo?bannerid='+item.bannerid">
                                    <span>{{ item.bannername }} </span></router-link>
                            </div>

                    <ul class="textlink_ul">
                        <template v-for="(articleinfo,index_articleinfo) of item.articleList">
                            <li
                                v-if="index_articleinfo<4"
                                :key="index_articleinfo">
                                <span> {{ articleinfo.addtime }} </span>
                                <router-link :to="'/articleinfoDetail?id='+articleinfo.articleid">
                                    {{ articleinfo.title }}
                                </router-link>
                            </li>
                        </template>

                    </ul>

                        </div>
                    </el-col>
                </el-row>
            </div>
        </div>

    </div>
</template>

<script name="dashboard" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import axios_request from '@/utils/axios_request'
import {parseTime,} from '@/utils' //日期格式化


const favs = ref(null);//轮播新闻

const fronts = ref(null);//所有分类的条目 新闻

const tops = ref(null);//置顶 新闻

const housing_infos = ref(null);//房东房源

const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');
onMounted(() => {
    getList()

})


function getList() {
    //获取分类
    axios_request.get('/web/web_default.action', {params: null}).then(response => {
        favs.value = response.data.favList
        favs.value.forEach((item, i) => {
            item.addtime = parseTime(item.addtime, '{m}月{d}日')//排时间戳处理成日期 日期转换
        })

        fronts.value = response.data.frontList
        fronts.value.forEach((item, i) => {
            item.articleList.forEach((item_a, i) => {
                item_a.addtime = parseTime(item_a.addtime, '{m}月{d}日')//排时间戳处理成日期 日期转换
            })

        })


        housing_infos.value = response.data.housing_infoList

        tops.value = response.data.topList
        tops.value.forEach((item, i) => {
            item.addtime = parseTime(item.addtime, '{m}月{d}日')//排时间戳处理成日期 日期转换
        })

    })
}

</script>


<style rel="stylesheet/scss" lang="scss" scoped>
.image_banner {
    width: 100%;
    height: 100%;
    max-height: 320px;

}


.carousel-images {
    font-size: 14px;

    margin-bottom: 15px;

    padding: 0;
    color: #666;

    .image {
        width: 100%;
        height: 100%;

    }


}


.text_list_box {
    padding-left: 10px;
    padding-right: 10px;

    .title {

        height: 40px;
        border-bottom: 1px solid #ddd;
        text-align: center;

        margin-bottom: 10px;

        span {
            display: inline-block;
            line-height: 39px;
            border-bottom: 2px solid #533336;
            float: left;
            font-size: 18px;
            font-weight: bold;

        }
    }


    .textlink_ul {

        margin: 0;
        padding: 0;

        li {
            line-height: 35px;
            display: block;
            font-size: 14px;
            text-overflow: ellipsis;
            white-space: nowrap;
            overflow: hidden;
            text-align: left;

            span {
                float: right;
            }
        }
    }


}


</style>
