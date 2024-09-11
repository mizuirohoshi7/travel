<template>
    <div class="my-page">
      <h1>My Page</h1>
      <div class="top-section">
        <div class="token-info">
          <h2>토큰 현황</h2>
          <p>보유 토큰: {{ tokenAmount }}</p>
        </div>
        <div class="token-charge">
          <h2>토큰 충전</h2>
          <button @click="goToTokenCharge">토큰 충전하기</button>
        </div>
      </div>
      <div class="bottom-section">
        <div class="liked-plans">
          <h2>좋아요한 플랜 목록</h2>
          <ul>
            <li v-for="plan in likedPlans" :key="plan.id">
              {{ plan.location }} - {{ plan.travelDate }}
            </li>
          </ul>
        </div>
        <div class="following-list">
          <h2>팔로우 중인 사용자</h2>
          <ul>
            <li v-for="follow in followingList" :key="follow.id">
              {{ follow.name }}
            </li>
          </ul>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'MyPage',
    data() {
      return {
        tokenAmount: 0,
        likedPlans: [],
        followingList: [],
      };
    },
    methods: {
      async fetchData() {
        try {
          const memberId = 1; // 실제로는 로그인한 사용자의 ID를 사용해야 합니다.
          const [memberResponse, likeResponse, followResponse] = await Promise.all([
            axios.get(`/members/${memberId}`),
            axios.get(`/likes?memberId=${memberId}`),
            axios.get(`/follows?fromId=${memberId}`),
          ]);
  
          this.tokenAmount = memberResponse.data.tokenAmount;
          
          // 좋아요한 플랜 정보 가져오기
          const planPromises = likeResponse.data.map(like => 
            axios.get(`/plans/${like.planId}`)
          );
          const planResponses = await Promise.all(planPromises);
          this.likedPlans = planResponses.map(response => response.data);
  
          // 팔로우한 사용자 정보 가져오기
          const userPromises = followResponse.data.map(follow => 
            axios.get(`/members/${follow.toId}`)
          );
          const userResponses = await Promise.all(userPromises);
          this.followingList = userResponses.map(response => response.data);
        } catch (error) {
          console.error('데이터 가져오기 실패:', error);
        }
      },
      goToTokenCharge() {
        // 토큰 충전 페이지로 이동
        this.$router.push('/token-charge');
      },
    },
    mounted() {
      this.fetchData();
    },
  };
  </script>
  
  <style scoped>
  .my-page {
    padding: 20px;
  }
  
  .top-section, .bottom-section {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;
  }
  
  .token-info, .token-charge, .liked-plans, .following-list {
    width: 48%;
    background-color: #f0f0f0;
    padding: 15px;
    border-radius: 5px;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
  }
  
  li {
    margin-bottom: 5px;
  }
  
  button {
    padding: 10px 20px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #45a049;
  }
  </style>