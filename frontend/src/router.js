import Vue from 'vue';
import Router from 'vue-router';
import MainPage from './components/MainPage.vue';
import PlanManagement from './components/PlanManagement.vue';
import CreatePlan from './components/CreatePlan.vue';
import EditPlan from './components/EditPlan.vue'; // EditPlan 컴포넌트 추가
import LoginPage from './components/LoginPage.vue';
import MyPage from './components/MyPage.vue';
import signup from './components/signup.vue';
Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'MainPage',
      component: MainPage,
    },
    {
      path: '/plan-management',
      name: 'PlanManagement',
      component: PlanManagement,
    },
    {
      path: '/create-plan',
      name: 'CreatePlan',
      component: CreatePlan,
    },
    {
      path: '/edit-plan/:id', // EditPlan 라우트 추가
      name: 'EditPlan',
      component: EditPlan,
      props: true, // URL 파라미터를 props로 전달
    },
    {
      path: '/login',
      name: 'LoginPage',
      component: LoginPage,
    },
    {
      path: '/mypage',
      name: 'MyPage',
      component: MyPage,
    },
    {
      path: '/signup',
      name: 'signup',
      component: signup, // SignupPage 컴포넌트 추가
    },
  ],
});