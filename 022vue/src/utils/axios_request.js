import axios from 'axios'
import { ElMessageBox, ElMessage } from 'element-plus'
import store from '@/store'
import qs from 'qs'; // npm install --save axios vue-axios qs


// console.log(process.env)
// create an axios instance
const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url// api的base_url

    // baseURL: process.env.NODE_ENV === "development" ? '/api' : "",//所有请求连接加API,用于跳转使用
    timeout: 60000 * 3, // request timeout响应时间
    // 默认情况下，一般浏览器的CORS跨域请求都是不会发送cookie等认证信息到服务端的，
    // 除非指定了xhr.withCredentials = true，
    // 但是只有用户端单方面的设置了这个值还不行，
    // 服务端也需要同意才可以，所以服务端也需要设置好返回头Access-Control-Allow-Credentials: true；
    // 还有一点要注意的，返回头Access-Control-Allow-Origin的值不能为星号，必须是指定的域，否则cookie等认证信息也是发送不了。
    withCredentials: false, // default

    // `transformRequest` 允许在向服务器发送前，修改请求数据
    // 只能用在 'PUT', 'POST' 和 'PATCH' 这几个请求方法
    // 后面数组中的函数必须返回一个字符串，或 ArrayBuffer，或 Stream
    transformRequest: [function (data) {
        // 对 data 进行任意转换处理
        // 将vue页面提缴 的POST数据(OBJECT类型的)转换为 可接收的  aa=1111&bb=4444这样的URL形式
        if (data && !Array.isArray(data)) {
            // let keys2 = Object.keys(data);
            /* 这里就是把json变成url形式，并进行encode */
            //  console.log('key====', keys2)
            // return encodeURI(keys2.map(name => `${name}=${data[name]}`).join('&'));


            // 将POST的参数序列化为?&这样的格式;
            // brackets如果POST数据里有数组的话,格式修改为permissions_ids[]=1&permissions_ids[]=2
            // indices(不写的话默认此参数)如果POST数据里有数组的话,格式修改为permissions_ids[1]=1&permissions_ids[2]=2
            // console.log(data)


            /* 当然，也可以通过arrayFormat 选项进行格式化输出，如下代码所示：
                qs.stringify({ a: ['b', 'c'] }, { arrayFormat: 'indices' })
                // 'a[0]=b&a[1]=c'
                qs.stringify({ a: ['b', 'c'] }, { arrayFormat: 'brackets' })
                // 'a[]=b&a[]=c'
                qs.stringify({ a: ['b', 'c'] }, { arrayFormat: 'repeat' })
                // 'a=b&a=c'
                 */

            return (qs.stringify(data, {arrayFormat: 'indices'}))
        }
        return data;
    }]
})

// request interceptor
service.interceptors.request.use(
    config => {
        // do something before request is sent
        if (store.getters.token || store.getters.token_web) {
            // console.log(sessionStorage.getItem("Admin-Token"))
            // console.log(sessionStorage.getItem("Web-Token"))
            // config.headers['X-CSRF-TOKEN'] = getToken() // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改
            config.headers['token'] = sessionStorage.getItem("Admin-Token") || sessionStorage.getItem("Web-Token")//   token传给资源服务器。

            // console.log(config.headers['token'] )

            config.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8' // 将vue页面提缴 的POST数据(OBJECT类型的)转换为 可接收的  aa=1111&bb=4444这样的URL形式  对应的transformRequest设置,否则PHP接收不到
            // config.headers['Access-Control-Allow-Credentials'] = true   //将vue页面提缴 的POST数据(OBJECT类型的)转换为 可接收的  aa=1111&bb=4444这样的URL形式  对应的transformRequest设置,否则PHP接收不到
            config.headers['X-Requested-With'] = 'XMLHttpRequest'
        }
        return config
    },
    error => {
        // do something with request error
        console.log(error) // for debug
        return Promise.reject(error)
    }
)

// response  interceptor返回信息接口
service.interceptors.response.use(
    response => {

        //console.dir(response.data.code)
        let status_code = response.data.code
        let message_temp = ''
        let message = '<br>' + response.data.message + '<br>'

        if (status_code === 401) {
            ElMessageBox.alert('登录状态失效，请重新登录', '提示', {
                confirmButtonText: '重新登录',
                type: 'warning'
            }).then(() => {
                store.dispatch('FedLogOut')
                store.dispatch('FedLogOut_web')
                location.reload();// 为了重新实例化vue-router对象 避免bug
               /* store.dispatch('FedLogOut').then(() => {
                    location.reload();// 为了重新实例化vue-router对象 避免bug
                });*/
            })
            //return Promise.reject(error)
        } else if (status_code === 500) {
            // 500或其他的错误
            // console.dir(error)// for debug
            message_temp += status_code + ' ' + message + ' 错误:访问失败,请重试 '

        }
        if (message_temp !== '') {
            ElMessage({
                dangerouslyUseHTMLString: true, // 启用html代码
                message: message_temp,
                type: 'error',
                duration: 5 * 1000
            })
        }
        return response.data
    },
    error => {
        /* 错误 的处理过程 */

        console.dir(error)

        // 如果是response返回来的 这样取值,
        // 如果不是response返回的 比如请求超时 没有status和data,直接取message

        let status_code = 0
        let errors_data = []
        let message = ''
        if (error.response) {
            status_code = error.response.status
            errors_data = error.response.data.errors
            message = error.response.data.message
        } else {
            message = error.message + '<br>'
        }
        if (status_code === 401) {
            ElMessageBox.alert('登录状态失效，请重新登录', '提示', {
                confirmButtonText: '重新登录',
                type: 'warning'
            }).then(() => {
                store.dispatch('FedLogOut')
                store.dispatch('FedLogOut_web')
                location.reload();// 为了重新实例化vue-router对象 避免bug
            })
        } else if (status_code === 422) {
            // 422的API读取错误
            // console.dir(error)// for debug
            message += '<b>' + status_code + '错误</b>'
            if (error.response.data) {
                message += ':' + message
                // console.log(message)
            }


            // console.log(errors_data )
            let error_i = 0
            for (const item in errors_data) {
                for (const i in errors_data[item]) {
                    error_i++
                    message += '<br>' + error_i + ' ' + (errors_data[item][i])
                }
            }
        } else if (status_code === 403) {
            // 403的无权限错误
            //console.dir(message)// for debug
            //console.dir(error.response.data)// for debug
            message = '<b>' + status_code + '错误</b> ' + message
            /*if (error.response.data) {
                message += ':' + message
                // console.log(message)
            }*/
        } else {
            // 500或其他的错误
            // console.dir(error)// for debug
            message += status_code + ' 错误:访问失败,请重试 '
        }
        ElMessage({
            dangerouslyUseHTMLString: true, // 启用html代码
            message: message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export default service
