import axios_request from '@/utils/axios_request'


//前台用户的缓存
const store_web = {
    state: {
        token_web: sessionStorage.getItem("Web-Token"),
        usersid_web: sessionStorage.getItem("usersid_web"),
        role_web: sessionStorage.getItem("role_web"),

        info_web: JSON.parse(sessionStorage.getItem("info_web"))//将存储的JSON转为对象,供使用
    },

    mutations: {
        SET_USERSID_WEB: (state, usersid_web) => {
            state.usersid_web = usersid_web
            sessionStorage.setItem("usersid_web", usersid_web);
        },
        SET_TOKEN_WEB: (state, token_web) => {
            state.token_web = token_web
            sessionStorage.setItem("Web-Token", token_web);

        },
        SET_ROLE_WEB: (state, role_web) => {
            state.role_web = role_web
            sessionStorage.setItem("role_web", role_web);

        },

        SET_INFO_WEB: (state, info_web) => {

            var checkedIdStr = JSON.stringify(info_web);//后端过来的是实体对象,要转换为json存储
            state.info_web = checkedIdStr
            sessionStorage.setItem("info_web", checkedIdStr);
        },

    },

    actions: {
        // 用户名登录
        SetStoreLoginByusername_web({commit}, userInfo) {

            //console.log(userInfo)
            return new Promise((resolve, reject) => {
                axios_request.post('/web/login.action', userInfo).then(response => {
                    const data = response
                    //console.log(data)
                    if (data.code === 200) {
                        //console.log(data.data.info)

                        commit('SET_TOKEN_WEB', data.data.token)
                        commit('SET_USERSID_WEB', parseInt(data.data.usersid))
                        commit('SET_INFO_WEB', data.data.info)
                        commit('SET_ROLE_WEB', data.data.role)

                        resolve()
                    } else {
                        reject("登录失败");

                    }
                }).catch(error => {
                    console.log(error)
                    reject("登录失败请重新登录")
                })
            })
        },
        // 房东 用户名登录
        SetStoreLoginByusername_web_2({commit}, userInfo) {

            console.log(userInfo)
            return new Promise((resolve, reject) => {
                axios_request.post('/web/login_2.action', userInfo).then(response => {
                    const data = response
                    //console.log(data)
                    if (data.code === 200) {
                        //console.log(data.data.info)

                        commit('SET_TOKEN_WEB', data.data.token)
                        commit('SET_USERSID_WEB', parseInt(data.data.usersid))
                        commit('SET_INFO_WEB', data.data.info)
                        commit('SET_ROLE_WEB', data.data.role)

                        resolve()
                    } else {
                        reject("登录失败");

                    }
                }).catch(error => {
                    console.log(error)
                    reject("登录失败请重新登录")
                })
            })
        },


        // 人工操作登出
        LogOut_web({commit, state}) {
            return new Promise((resolve, reject) => {
                console.log(state);
                //console.log(logout(state.token));
                //logout(state.token).then(() => {
                commit('SET_TOKEN_WEB', '')
                commit('SET_USERSID_WEB', '')

                commit('SET_INFO_WEB', '')
                commit('SET_ROLE_WEB', '')
                resolve()
                /* }).catch(error => {
                     reject(error)
                 })*/
            })
        },

        // 代码中机器前端 登出
        FedLogOut_web({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN_WEB', '')
                commit('SET_USERSID_WEB', '')

                commit('SET_INFO_WEB', '')
                commit('SET_ROLE_WEB', '')
                resolve()
            })
        },


    }
}

export default store_web
