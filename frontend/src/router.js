
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import PlanPlanManager from "./components/listers/PlanPlanCards"
import PlanPlanDetail from "./components/listers/PlanPlanDetail"


import NotificationNotificationManager from "./components/listers/NotificationNotificationCards"
import NotificationNotificationDetail from "./components/listers/NotificationNotificationDetail"

import FollowFollowManager from "./components/listers/FollowFollowCards"
import FollowFollowDetail from "./components/listers/FollowFollowDetail"

import Manager from "./components/listers/Cards"
import Detail from "./components/listers/Detail"


export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/plans/plans',
                name: 'PlanPlanManager',
                component: PlanPlanManager
            },
            {
                path: '/plans/plans/:id',
                name: 'PlanPlanDetail',
                component: PlanPlanDetail
            },


            {
                path: '/notifications/notifications',
                name: 'NotificationNotificationManager',
                component: NotificationNotificationManager
            },
            {
                path: '/notifications/notifications/:id',
                name: 'NotificationNotificationDetail',
                component: NotificationNotificationDetail
            },

            {
                path: '/follows/follows',
                name: 'FollowFollowManager',
                component: FollowFollowManager
            },
            {
                path: '/follows/follows/:id',
                name: 'FollowFollowDetail',
                component: FollowFollowDetail
            },

            {
                path: '//',
                name: 'Manager',
                component: Manager
            },
            {
                path: '///:id',
                name: 'Detail',
                component: Detail
            },



    ]
})
