import {createStore} from 'vuex'
import store_admin from './store_admin'

import store_web from './store_web'



export default createStore({
    modules: {

        store_admin: store_admin,
        store_web: store_web,
    },
    getters: {


        token: state => state.store_admin.token,
        adminid: state => state.store_admin.adminid,
        username: state => state.store_admin.username,
        roleId: state => state.store_admin.roleId,
        info: state => state.store_admin.info,

        token_web: state => state.store_web.token_web,
        usersid_web: state => state.store_web.usersid_web,
        info_web: state => state.store_web.info_web,
        role_web: state => state.store_web.role_web,

    }
})

