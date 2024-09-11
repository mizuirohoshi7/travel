<template>
    <div id="app">
      <router-view />  <!-- router-view를 통해 현재 라우터에 맞는 컴포넌트를 표시합니다 -->
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
    padding: 20px;
  }
  </style>
  