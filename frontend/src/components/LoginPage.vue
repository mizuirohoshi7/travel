<template>
  <div class="login-page">
    
    <h1>Kt-ravel Login</h1>
    <div class="login-buttons">
      <button class="naver-btn" @click="loginWithNaver">네이버 로그인</button>
      <button class="google-btn" @click="loginWithGoogle">구글 로그인</button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    loginWithNaver() {
      // 네이버 OAuth URL로 리디렉션
      window.location.href = 'http://localhost:8080/oauth2/authorization/naver';
    },
    loginWithGoogle() {
      // 구글 OAuth URL로 리디렉션
      window.location.href = 'http://localhost:8080/oauth2/authorization/google';
    }
  }
}
</script>

<style scoped>
.login-page {
  text-align: center;
  margin-top: 50px;
}

.logo {
  width: 200px;
  margin-bottom: 30px;
}

h1 {
  font-size: 24px;
  margin-bottom: 20px;
}

.login-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.naver-btn, .google-btn {
  width: 150px;
  padding: 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
}

.naver-btn {
  background-color: #1ec800;
  color: white;
}

.google-btn {
  background-color: #4285f4;
  color: white;
}
</style>
