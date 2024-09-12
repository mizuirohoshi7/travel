<template>
  <div class="login-wrap">
    <div class="login-container">
      <div class="login-header">
        <!-- Kt-ravel 로고 -->
        <img src="@/assets/KT.png" alt="Kt-ravel Logo" class="logo" />
        <h2>K-TRAVEL 로그인</h2>
      </div>
      <form class="login-form" @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="email">이메일</label>
          <input type="email" id="email" v-model="email" placeholder="이메일" required />
        </div>
        
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input type="password" id="password" v-model="password" placeholder="비밀번호를 입력하세요" required />
        </div>
        
        <button type="submit" class="login-button">로그인</button>
      </form>
      <div class="signup-link">
        <p>계정이 없으신가요? <router-link to="/signup">회원가입</router-link></p>
      </div>
    </div>
  </div>
</template>

<script>
import memberService from '@/services/memberService';

export default {
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    async handleLogin() {
      try {
        // 이메일을 JSON 객체로 전달
        const response = await memberService.login(this.email);
        alert(`환영합니다, ${response.name}님!`);
        // 사용자 정보를 로컬 스토리지에 저장
        localStorage.setItem('id', response.id);
        localStorage.setItem('name', response.name);
        this.$router.push('/'); // 로그인 성공 후 메인 페이지로 리디렉션
      } catch (error) {
        console.error('로그인 실패:', error);
        alert('로그인 정보가 잘못되었습니다.');
      }
    }
  }
};
</script>

<style scoped>
/* 전체 배경 */
body, html {
  margin: 0;
  padding: 0;
  height: 100%;
  font-family: 'Poppins', sans-serif;
  background: linear-gradient(135deg, #b2c1df, #80cbc4); /* 부드러운 녹색 그라데이션 배경 */
}

/* 로그인 컨테이너 */
.login-wrap {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login-container {
  background-color: #ffffff;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  width: 400px;
  max-width: 90%; /* Keep width responsive */
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: space-between; /* Consistent spacing */
}

/* 로고 */
.logo {
  width: 250px;
  margin-bottom: 20px;
}

/* 헤더 */
.login-header h2 {
  color: #001a69;
  font-size: 24px;
  margin-bottom: 30px;
}

/* 폼 */
.login-form {
  display: flex;
  flex-direction: column;
  gap: 20px; /* Consistent spacing between input fields */
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
}

.form-group label {
  display: block;
  color: #002a69;
  margin-bottom: 8px;
  font-weight: 600;
}

.form-group input {
  width: 100%;
  padding: 14px; /* Adjusted padding for consistency */
  border: 1px solid #808ecb;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  font-size: 16px;
  transition: border-color 0.3s ease;
}

.form-group input:focus {
  border-color: #2668a6;
  outline: none;
}

/* 로그인 버튼 */
.login-button {
  width: 100%;
  padding: 15px;
  background-color: #003569;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 18px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.login-button:hover {
  background-color: #000d4d;
}

/* 회원가입 링크 */
.signup-link {
  margin-top: 20px;
  color: #003369;
}

.signup-link a {
  color: #00064d;
  text-decoration: none;
  font-weight: 600;
}

.signup-link a:hover {
  text-decoration: underline;
}

/* 반응형 디자인 */
@media (max-width: 600px) {
  .login-container {
    padding: 30px;
    width: 90%;
  }
  
  .logo {
    width: 200px;
  }
  
  .login-header h2 {
    font-size: 20px;
  }
  
  .login-button {
    font-size: 16px;
  }
}

</style>
