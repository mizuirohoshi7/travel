<template>
    <div class="signup-wrap">
      <div class="signup-container">
        <div class="signup-header">
          <!-- Kt-ravel 로고 -->
          <img src="@/assets/KT.png" alt="Kt-ravel Logo" class="logo" />
          <h2>K-TRAVEL 회원가입</h2>
        </div>
        <form class="signup-form" @submit.prevent="handleSignup">
          <div class="form-group">
            <label for="email">이메일</label>
            <input type="email" id="email" v-model="email" placeholder="이메일" required />
          </div>
          <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" v-model="password" placeholder="비밀번호를 입력하세요" required />
          </div>
          <div class="form-group">
            <label for="confirmPassword">비밀번호 확인</label>
            <input type="password" id="confirmPassword" v-model="confirmPassword" placeholder="비밀번호 확인" required />
          </div>
          <div class="form-group">
            <label for="nickname">닉네임</label>
            <input type="text" id="nickname" v-model="nickname" placeholder="닉네임을 입력하세요" required />
          </div>
          <button type="submit" class="signup-button">회원가입</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import memberService from '@/services/memberService';
  
  export default {
    data() {
      return {
        email: '',
        password: '',
        confirmPassword: '',
        nickname: ''
      };
    },
    methods: {
      async handleSignup() {
        if (this.password !== this.confirmPassword) {
          alert("비밀번호가 일치하지 않습니다.");
          return;
        }
  
        try {
          const newMember = {
            name: this.nickname,
            email: this.email
          };
          const response = await memberService.createMember(newMember);
          alert(`회원가입이 완료되었습니다, ${response.name}님!`);
          this.$router.push('/'); // 회원가입 후 메인 페이지로 이동
        } catch (error) {
          console.error('회원가입 실패:', error);
          alert('회원가입에 실패했습니다. 다시 시도해주세요.');
        }
      }
    }
  };
  </script>
  
  <style scoped>
  /* 전체 배경 설정 */
  body, html {
    margin: 0;
    padding: 0;
    height: 100%;
    font-family: 'Poppins', sans-serif;
    background: linear-gradient(135deg, #b2b3df, #072178); /* 부드러운 그라데이션 */
  }
  
  /* 회원가입 페이지 래핑 */
  .signup-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }
  
  /* 회원가입 컨테이너 */
  .signup-container {
    background-color: #ffffff;
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    width: 400px;
    max-width: 90%;
    text-align: center;
  }
  
  /* 로고 */
  .logo {
    width: 250px;
    margin-bottom: 20px;
  }
  
  /* 헤더 */
  .signup-header h2 {
    color: #283b9b;
    font-size: 24px;
    margin-bottom: 30px;
  }
  
  /* 폼 */
  .signup-form {
    display: center;
    width:90%;
    flex-direction: column;
  }
  
  .form-group {
    margin-bottom: 10px;
    text-align: left;
  }
  
  .form-group label {
    display: block;
    color: #200b6b;
    margin-bottom: 8px;
    font-weight: 600;
  }
  
  .form-group input {
    width: 100%;
    padding: 12px 15px;
    border: 1px solid #8086cb;
    border-radius: 8px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    font-size: 16px;
    transition: border-color 0.3s ease;
  }
  
  .form-group input:focus {
    border-color: #264fa6;
    outline: none;
  }
  
  /* 회원가입 버튼 */
  .signup-button {
    width: 100%;
    padding: 10px;
    background-color: #000069;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 18px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .signup-button:hover {
    background-color: #004d40;
  }
  
  /* 반응형 디자인 */
  @media (max-width: 600px) {
    .signup-container {
      padding: 30px;
      width: 100%; /* 작은 화면에서는 너비 90%로 설정 */
    }
  
    .logo {
      width: 200px;
    }
  
    .signup-header h2 {
      font-size: 20px;
    }
  
    /* 회원가입 버튼 */
  .signup-button {
    width: 30%; /* 원하는 너비 설정 (여기서는 80%로 설정) */
    padding: 25px;
    background-color: #003569;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 18px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    margin: 0 auto; /* 버튼을 중앙에 정렬 */
    display: block; /* 중앙 정렬을 위해 block으로 설정 */
  }

  .signup-button:hover {
    background-color: #000d4d;
  }
  }
  </style>
  