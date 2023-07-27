import {createApp} from 'vue'
import App from './App.vue'
import router from './routers' //路由
import './permission' // permission control权限控制
import * as utils from './utils' // global filters
import '@/styles_web/index.scss' // 前台样式css
//import vuex
import store from './store'
import ElementPlus from 'element-plus';

import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'




import VueUeditorWrap from 'vue-ueditor-wrap'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App).use(VueUeditorWrap)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(store)

// register global utility filters.注册 全局过滤器,可直接在EL表格中过滤数据
app.config.globalProperties.$filters = utils




app.use(ElementPlus, {
    size: 'default',// set element-ui default size,
    locale: zhCn
})

app.use(store).use(router).mount('#app')

