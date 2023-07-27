
import { createRouter, createWebHashHistory } from 'vue-router'
/* Layout */
import Layout_web from '@/layout_web/Layout' //引入前台界面框架
import Layout from '@/layout/Layout' //引入后台界面框架


//const _import = require('./_import_' + process.env.NODE_ENV)
// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading


/** note: submenu only apppear when children.length>=1
 *   detail see  https://panjiachen.github.io/vue-element-admin-site/#/router-and-nav?id=sidebar
 **/

/**
 * hidden: true                   if `hidden:true` 是否显示在侧栏(default is false)
 * redirect: noredirect           if `redirect:noredirect`是否在当前位置显示 为空则不显示
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)  这个不写名称  不会显示标签 但会在历史记录里
 * meta : {

     noCache: true                if fasle ,the page will no be cached(default is false)
  }
 **/
export const constantRouterMap = [


    //---------------------------------------------------------------前台
    /*首页*/
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            name: '/default',
            path: '/default',
            component: () => import('@/views/default'),
        }]
    },


    /*列表图片版*/
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/articleinfo',
            component: () => import('@/views/articleinfo'),
        }]
    },


    /*内容页*/
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/articleinfoDetail',
            component: () => import('@/views/articleinfoDetail'),
        }]
    },


    /* 普通用户登录页*/
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/login',
            component: () => import('@/views/login'),
        }]
    },
    /* 房东 登录页*/
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/login_2',
            component: () => import('@/views/login_2'),
        }]
    },

    /*注册*/
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/register',
            component: () => import('@/views/register'),
        }]
    },






    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/landlord',
            component: () => import('@/views/landlord'),
        }]
    },

    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/landlordDetail',
            component: () => import('@/views/landlordDetail'),
        }]
    },


    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/housing_info',
            component: () => import('@/views/housing_info'),
        }]
    },
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/housing_infoDetail',
            component: () => import('@/views/housing_infoDetail'),
        }]
    },







    //////////////////////////////////前台普通用户-----------------------------------------------------
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/editinfo',
            component: () => import('@/views/users/editinfo'),
        }]
    },

    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/addorders',
            component: () => import('@/views/users/addorders'),
        }]
    },
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/myorders',
            component: () => import('@/views/users/myorders'),
        }]
    },

    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/contract_info',
            component: () => import('@/views/users/contract_info'),
        }]
    },





    //////////////////////////////////前台房东 用户-----------------------------------------------------


    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/editinfo_2',
            component: () => import('@/views/landlord/editinfo_2'),
        }]
    },

    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/landlord/articleinfo',
            component: () => import('@/views/landlord/articleinfo'),
        }]
    },

    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/listorders_landlord',
            component: () => import('@/views/landlord/listorders_landlord'),
        }]
    },
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/landlord/housing_info',
            component: () => import('@/views/landlord/housing_info'),
        }]
    },

    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_web,
        children: [{
            path: '/landlord/contract_info',
            component: () => import('@/views/landlord/contract_info'),
        }]
    },


























    //---------------------------------------------------------------后台
    //单页显示 无框架
    {
        path: '/admin/login',
        component: () => import('@/views/admin/login'),
        hidden: true
    },
    {
        path: '/usersinfo',
        component: () => import('@/views/admin/users/usersinfo'),
        hidden: true
    },
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        hidden: true,
        redirect: 'default',  //则跳转到这个首页
        component: Layout,
        children: [{
             path: '/admin/default',
            component: () => import('@/views/admin/default'),


        }]
    },
    {
        path: '',
        hidden: true,
        component: Layout,
        children: [{
            path: 'admin/orders/orderMoneyTotal',
            component: () => import('@/views/admin/orders/orderMoneyTotal'),

        }]
    }, {
        path: '',
        hidden: true,
        component: Layout,
        children: [{
            path: 'admin/orders/getcontract_infonumber',
            component: () => import('@/views/admin/orders/getcontract_infonumber'),

        }]
    },

    {
        path: '',
        hidden: true,
        component: Layout,
        children: [{
            path: 'admin/admin/search',
            component: () => import('@/views/admin/admin/search'),

        }]
    },
    {
        path: '',
        hidden: true,
        component: Layout,
        children: [{
            path: 'admin/articleinfo/search',
            component: () => import('@/views/admin/articleinfo/search'),

        }]
    },

    {
        path: '',
        hidden: true,
        component: Layout,
        children: [{
            path: 'admin/banner/search',
            component: () => import('@/views/admin/banner/search'),

        }]
    },



    {
        path: '',
        hidden: true,
        component: Layout,
        children: [{
            path: 'admin/users/search',
            component: () => import('@/views/admin/users/search'),

        }]
    },


    {
        path: '',
        hidden: true,
        component: Layout,
        children: [{
            path: 'admin/orders/search',
            component: () => import('@/views/admin/orders/search'),

        }]
    },
    {
        path: '',
        hidden: true,
        component: Layout,
        children: [{
            path: 'admin/category/search',
            component: () => import('@/views/admin/category/search'),

        }]
    },


    {
        path: '',
        hidden: true,
        component: Layout,
        children: [{
            path: 'admin/landlord/search',
            component: () => import('@/views/admin/landlord/search'),

        }]
    },

    {
        path: '',
        hidden: true,
        component: Layout,
        children: [{
            path: 'admin/housing_info/search',
            component: () => import('@/views/admin/housing_info/search'),

        }]
    },

    {
        path: '',
        hidden: true,
        component: Layout,
        children: [{
            path: 'admin/contract_info/search',
            component: () => import('@/views/admin/contract_info/search'),

        }]
    },


]


///404判断必须在最后
export const footRouterMap = [
    /*
        {path: '*', redirect: '/404', hidden: true}
    */
]



const router = createRouter({
  history: createWebHashHistory(),
  scrollBehavior: () => ({ top: 0 }),
    routes: constantRouterMap
})
export default router

