import Vue from 'vue';
import App from './App.vue';
import router from './router';
import axios from 'axios';

// axios 기본 설정
axios.defaults.baseURL = 'http://localhost:8088'; // Spring Gateway 서버 주소
axios.defaults.withCredentials = true;

// axios를 Vue 인스턴스에 추가
Vue.prototype.$axios = axios;

Vue.config.productionTip = false;

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');