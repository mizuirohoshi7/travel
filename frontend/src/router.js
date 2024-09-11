import Vue from 'vue';
import Router from 'vue-router';
import MainPage from './components/MainPage.vue'; // 메인 페이지 컴포넌트
import PlanManagement from './components/PlanManagement.vue'; // 계획 관리 컴포넌트
import CreatePlan from './components/CreatePlan.vue'; // 계획 생성 컴포넌트
import LoginPage from './components/LoginPage.vue'; // 로그인 페이지 추가
import MyPage from './components/MyPage.vue'; // 마이 페이지 추가

Vue.use(Router);

export default new Router({
  mode: 'history', // 브라우저 히스토리 모드 사용
  routes: [
    {
      path: '/', // 기본 경로를 MainPage로 설정
      name: 'MainPage',
      component: MainPage,
    },
    {
      path: '/plan-management', // 계획 관리 화면 경로
      name: 'PlanManagement',
      component: PlanManagement,
    },
    {
      path: '/create-plan', // 계획 생성 화면 경로
      name: 'CreatePlan',
      component: CreatePlan,
    },
    {
      path: '/login', // 로그인 페이지 경로 추가
      name: 'LoginPage',
      component: LoginPage,
    },
    {
      path: '/mypage', // 마이 페이지 경로 추가
      name: 'MyPage',
      component: MyPage,
    },
  ],
});
