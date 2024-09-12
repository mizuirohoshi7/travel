<template>
  <div class="login-page">
    
    <h1>Kt-ravel Login</h1>
    <div class="login-buttons">
      <img src="@/assets/naver.png" alt="Naver Login" class="login-img" @click="loginWithNaver" />
      <img src="@/assets/google.png" alt="Google Login" class="login-img" @click="loginWithGoogle" />
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

h1 {
  font-size: 24px;
  margin-bottom: 20px;
}

.login-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.login-img {
  width: 150px;
  cursor: pointer;
}

.login-img:hover {
  opacity: 0.8;
}
</style>
