<template>
    <div class="create-plan">
        <img src="C:\alpb\travelplan\frontend\src\assets\home.png" alt="Home" class="home-btn" @click="goToMainPage" />
      <h1>Create a new plan</h1>
  
      <!-- 계획 생성 폼 -->
      <form @submit.prevent="savePlan">
        <div class="form-group">
          <label for="destination">Destination</label>
          <input v-model="plan.destination" type="text" id="destination" placeholder="Enter destination" required />
        </div>
  
        <div class="form-group">
          <label for="people">Number of people</label>
          <input v-model.number="plan.people" type="number" id="people" placeholder="Enter number of people" required />
        </div>
  
        <div class="form-group">
          <label for="budget">Budget</label>
          <input v-model.number="plan.budget" type="number" id="budget" placeholder="Enter budget" required />
        </div>
  
        <div class="form-group">
          <label for="start-date">Start date</label>
          <input v-model="plan.startDate" type="date" id="start-date" required />
        </div>
  
        <div class="form-group">
          <label for="end-date">End date</label>
          <input v-model="plan.endDate" type="date" id="end-date" required />
        </div>
  
        <div class="form-group">
          <label for="notes">Notes</label>
          <textarea v-model="plan.notes" id="notes" placeholder="Add additional details"></textarea>
        </div>
  
        <button type="submit" class="save-btn">Save plan</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        plan: {
          id: Date.now(), // 고유 ID 생성
          destination: '',
          people: 1,
          budget: 0,
          startDate: '',
          endDate: '',
          notes: '',
          image: '/assets/images/default.png', // 기본 이미지
          category: 'upcoming', // 기본 카테고리 설정
          itinerary: [] // 빈 여행 계획
        }
      };
    },
    methods: {
      savePlan() {
        // 새 계획의 데이터가 누락되지 않도록 기본 필드와 함께 계획 저장
        if (!this.plan.destination || !this.plan.startDate || !this.plan.endDate) {
          alert('필수 필드를 모두 입력해주세요.');
          return;
        }
  
        // 생성된 계획을 PlanManagement 페이지로 전달
        this.$router.push({
          name: 'PlanManagement',
          query: { newPlan: JSON.stringify(this.plan) }
        });
      },
        // Home 버튼 클릭 시 메인 페이지로 이동
        goToMainPage() {
            this.$router.push({ name: 'MainPage' });
        }
    }
  };
  </script>
  

  
  <style scoped>
  /* 전체 컨테이너 스타일 */
  .create-plan {
    max-width: 600px;
    margin: 50px auto;
    padding: 40px;
    background-color: #daf4ff;
    border-radius: 10px;
    box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
    text-align: center;
  }
  
  /* 제목 스타일 */
  h1 {
    font-size: 28px;
    font-weight: 700;
    color: #333;
    margin-bottom: 30px;
  }
  
  /* 폼 그룹 (레이블과 입력 필드 포함) 스타일 */
  .form-group {
    margin-bottom: 20px;
    text-align: left;
  }
  
  /* 레이블 스타일 */
  label {
    display: block;
    margin-bottom: 8px;
    font-weight: 600;
    color: #333;
  }
  
  /* 입력 필드 및 텍스트박스 스타일 */
  input[type="text"],
  input[type="number"],
  input[type="date"],
  textarea {
    width: 100%;
    padding: 12px 15px;
    border: 1px solid #ccc;
    border-radius: 8px;
    font-size: 16px;
    color: #333;
    background-color: #fff;
    transition: border 0.3s ease;
  }
  
  /* 입력 필드 포커스 시 스타일 */
  input[type="text"]:focus,
  input[type="number"]:focus,
  input[type="date"]:focus,
  textarea:focus {
    border-color: #007bff;
    outline: none;
    box-shadow: 0 0 8px rgba(0, 123, 255, 0.2);
  }
  
  /* 텍스트 박스 크기 조정 */
  textarea {
    height: 120px;
    resize: none;
  }
  
  /* 저장 버튼 스타일 */
  .save-btn {
    width: 100%;
    padding: 15px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-size: 18px;
    font-weight: 600;
    transition: background-color 0.3s ease;
    margin-top: 20px;
  }
  
  /* 저장 버튼 호버 시 스타일 */
  .save-btn:hover {
    background-color: #0056b3;
  }
  
  /* 저장 버튼 포커스 시 스타일 */
  .save-btn:focus {
    outline: none;
    box-shadow: 0 0 10px rgba(55, 135, 220, 0.5);
  }
  
  /* 폼 전체적인 반응형 레이아웃 */
  @media (max-width: 768px) {
    .create-plan {
      padding: 20px;
    }
  
    h1 {
      font-size: 24px;
    }
  
    .save-btn {
      font-size: 16px;
      padding: 12px;
    }
  }
  </style>
  