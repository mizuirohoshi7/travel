import Vue from 'vue';
import App from './App.vue';
import router from './router';  // router.js에서 설정한 라우터 가져오기

Vue.config.productionTip = false;

new Vue({
  router,   // 라우터를 Vue 인스턴스에 등록
  render: h => h(App),  // App 컴포넌트를 렌더링
}).$mount('#app');  // Vue 인스턴스를 '#app' 엘리먼트에 마운트
