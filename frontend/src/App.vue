<template>
    <div id="app">
      <router-view />  <!-- router-view를 통해 현재 라우터에 맞는 컴포넌트를 표시합니다 -->
      <footer class="footer">
      <img src="@/assets/KT.png" alt="KT Logo" class="kt-logo" />
      <p>Copyright © KT company</p>
    </footer>
    
    </div>
    
  </template>
  
  <script>
  export default {
    name: 'App',
  };
  </script>
  
  <style>
  /* 전역 스타일 설정 */
  body {
    font-family: 'Arial', sans-serif;
    background-color: #f9f9f9;
    margin: 0;
    padding: 0;
  }
  
  #app {
    max-width: 1200px;
    margin: 0 auto;
    padding: 10px;
  }
  .footer {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.5); /* 투명한 배경색 설정 */
  border-top: 1px solid rgba(221, 221, 221, 0.5); /* 투명한 경계선 */
 
  position: fixed;
  width: 100%;
  bottom: 0;
  left: 0;
  text-align: center;
  height: 0.5cm; /* 박스 높이 5cm로 설정 */
}

.kt-logo {
  width: 90px; /* 로고 크기 */
  margin-right: 10px; /* 텍스트와 로고 간격 */
}

  </style>
  