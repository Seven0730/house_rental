import router from './routers'
import store from './store'
import NProgress from 'nprogress' // progress bar顶部加载进度条

NProgress.configure({showSpinner: false})// NProgress Configuration


//后台管理员访问白名单 ,不用登录  即可以访问
const whiteList = [
    '/admin/login',//后台
    '/usersinfo',//用户信息


]// no redirect whitelist


//前台 访问白名单 ,不用登录  即可以访问
const whiteList_web = [

    '/default',//首页
    '/articleinfo',//列表图片版
    '/articleinfoDetail',//内容页
    '/login',//用户登录 页
    '/register',// 登录页


    '/landlord',//
    '/landlordDetail',//


    '/housing_info',//
    '/housing_infoDetail',//

    '/login_2',//房东登录 页
    '/usersinfo',//用户信息 页
]// no redirect whitelist


/*实现导航守卫（路由跳转前验证登录）
* 路由跳转前做一些验证，比如登录验证（未登录去登录页），是网站中的普遍需求。对此，vue-route 提供的 beforeRouteUpdate 可以方便地实现导航守卫（navigation-guards）。
导航守卫（navigation-guards）这个名字，听起来怪怪的，但既然官方文档是这样翻译的，就姑且这么叫吧。
*/
router.beforeEach((to, from, next) => {

    NProgress.start() // start progress bar顶部加载条
    document.title =  process.env.title   // 设定标题


    //console.log(to.path);
    let routersPath = to.path
    const isadmin = routersPath.indexOf('admin/') > 0//是否访问后台地址
    //console.log(isadmin)
    if (isadmin) {
        //后台过滤
        if (sessionStorage.getItem("Admin-Token")) { // determine if there has token
            next()
        } else if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
            next()
        } else {
            store.dispatch('FedLogOut')
            next(`/admin/login`) // 否则全部重定向到首页
            NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
        }
    } else {
        //前台过滤
        if (sessionStorage.getItem("Web-Token")) { // determine if there has token
            next()
        } else if (whiteList_web.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
            next()
        } else {
            store.dispatch('FedLogOut_web')
            next(`/default`) // 否则全部重定向到首页
            NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
        }
    }


})

router.afterEach(() => {
    //console.log(5);
    NProgress.done() // finish progress bar
})

function contains(arr, obj) {
    if (arr && arr.length > 0) {
        var i = arr.length;
        while (i--) {
            //console.log(arr[i])
            if (arr[i] === obj) {
                return true;
            }
        }
    }
    return false;
}
