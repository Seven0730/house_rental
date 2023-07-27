import axios_request from '@/utils/axios_request'

//管理员的缓存
const store_admin = {
    state: {
        token: sessionStorage.getItem("Admin-Token"),
        adminid: sessionStorage.getItem("adminid"),
        roleId: sessionStorage.getItem("roleId"),
        username: sessionStorage.getItem("username"),
        info: JSON.parse(sessionStorage.getItem("info"))//将存储的JSON转为对象,供使用

    },

    mutations: {
        SET_ADMINID: (state, adminid) => {
            state.adminid = adminid
            sessionStorage.setItem("adminid", adminid);
        },
        SET_TOKEN: (state, token) => {
            state.token = token
            sessionStorage.setItem("Admin-Token", token);

        },
        SET_USERNAME: (state, username) => {
            state.username = username
            sessionStorage.setItem("username", username);
        },
        SET_ROLEID: (state, roleId) => {
            state.roleId = roleId
            sessionStorage.setItem("roleId", roleId);
        },
        SET_INFO: (state, info) => {


            var checkedIdStr = JSON.stringify(info);//后端过来的是实体对象,要转换为json存储
            state.info = checkedIdStr
            sessionStorage.setItem("info", checkedIdStr);

        },

    },

    actions: {
        // 用户名登录
        SetStoreLoginByusername({commit}, userInfo) {

            //console.log(userInfo)
            return new Promise((resolve, reject) => {
                axios_request.post('/admin/login/login.action', userInfo).then(response => {
                    const data = response
                    //console.log(data)
                    if (data.code === 200) {
                        //console.log(data.data.token)

                        commit('SET_TOKEN', data.data.token)
                        commit('SET_ADMINID', parseInt(data.data.adminid))
                        commit('SET_USERNAME', data.data.username)
                        //commit('SET_ROLEID', parseInt(data.data.roleId))
                        commit('SET_INFO', data.data.info)

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
        LogOut({commit, state}) {
            return new Promise((resolve, reject) => {
                console.log(state);
                //console.log(logout(state.token));
                //logout(state.token).then(() => {
                commit('SET_TOKEN', '')
                commit('SET_ADMINID', '')
                commit('SET_USERNAME', '')
                commit('SET_ROLEID', '')
                commit('SET_INFO', '')
                resolve()
                /* }).catch(error => {
                     reject(error)
                 })*/
            })
        },

        // 代码中机器前端 登出
        FedLogOut({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                commit('SET_ADMINID', '')
                commit('SET_USERNAME', '')
                commit('SET_ROLEID', '')
                commit('SET_INFO', '')


                resolve()
            })
        },


    }
}

export default store_admin
