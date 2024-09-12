<template>
  <div class="plan-management">
    <div class="header">
      <!-- 홈 버튼 및 여행 계획 아이콘 -->
      <div class="icons">
        <div class="icon-button" @click="goToMainPage">
          <img src="@/assets/home.png" alt="Home" class="icon home-btn" />
          <p>Home</p>
        </div>
        <div class="icon-button" @click="goToCreatePlan">
          <img src="@/assets/plan.png" alt="Plan" class="icon plan-btn" />
          <p>Plan</p> 
        </div>
      </div>
      <h1>My trip plans</h1>
    </div>

    <ul v-if="plans.length" class="plan-list">
      <li v-for="plan in plans" :key="plan.id" class="plan-item">
        <div class="plan-overview">
          <h2>{{ plan.location }} 여행</h2>
          <p class="plan-date">날짜: {{ formatDate(plan.travelDate) }}</p>
          <p>예산: {{ plan.budget }}원</p>
          <p>인원: {{ plan.groupSize }}명</p>
        </div>
        <div class="button-group">
          <button @click="goToEditPlan(plan)" class="edit-btn">수정</button>
          <button @click="deletePlan(plan)" class="delete-btn">삭제</button>
          <button @click="requestAiRecommendation(plan)" class="ai-btn">AI 추천 받기</button>
          <button @click="showDetails(plan)" class="details-btn">상세 보기</button>
        </div>
      </li>
    </ul>

    <p v-else>현재 등록된 여행 계획이 없습니다. 새로운 계획을 추가해보세요!</p>

    <transition name="fade">
      <div v-if="selectedPlan" class="plan-details">
        <h2>{{ selectedPlan.location }} 여행 상세 정보</h2>
        <p>날짜: {{ formatDate(selectedPlan.travelDate) }}</p>
        <p>예산: {{ selectedPlan.budget }}원</p>
        <p>인원: {{ selectedPlan.groupSize }}명</p>
        <p>세부사항: {{ selectedPlan.details }}</p>
        <h3>AI 추천:</h3>
        <!-- vue-markdown 컴포넌트를 사용하여 마크다운을 렌더링 -->
        <vue-markdown v-if="selectedPlan.aiRecommendation">
          {{ selectedPlan.aiRecommendation }}
        </vue-markdown>
        <p v-else>아직 AI 추천이 없습니다.</p>
        <button @click="selectedPlan = null" class="close-btn">닫기</button>
      </div>
    </transition>
  </div>
</template>

<script>
import axios from 'axios';
import VueMarkdown from 'vue-markdown'; // vue-markdown 불러오기

export default {
  name: 'PlanManagement',
  components: {
    VueMarkdown, // 컴포넌트 등록
  },
  data() {
    return {
      plans: [],
      selectedPlan: null,
    };
  },
  created() {
    this.fetchPlans();
  },
  methods: {
    async fetchPlans() {
      try {
        const response = await axios.get('/plans');
        this.plans = response.data._embedded.plans.map(plan => ({
          ...plan,
          id: plan._links.self.href.split('/').pop()
        }));
      } catch (error) {
        console.error('계획을 가져오는 중 오류 발생:', error);
        alert('계획 목록을 불러오는 데 실패했습니다.');
      }
    },
    formatDate(dateString) {
      return new Date(dateString).toLocaleDateString();
    },
    showDetails(plan) {
      this.selectedPlan = plan;
    },
    goToCreatePlan() {
      this.$router.push({ name: 'CreatePlan' });
    },
    goToMainPage() {
      this.$router.push({ name: 'MainPage' });
    },
    goToEditPlan(plan) {
      if (!plan.id) {
        alert('수정할 수 없는 계획입니다. 유효하지 않은 ID입니다.');
        return;
      }
      this.$router.push({ name: 'EditPlan', params: { id: plan.id }, query: { plan: JSON.stringify(plan) } });
    },
    async deletePlan(plan) {
      if (confirm('정말로 이 계획을 삭제하시겠습니까?')) {
        try {
          await axios.delete(`/plans/${plan.id}`);
          this.plans = this.plans.filter(p => p.id !== plan.id);
          alert('계획이 성공적으로 삭제되었습니다.');
        } catch (error) {
          console.error('계획 삭제 중 오류 발생:', error);
          alert('계획 삭제에 실패했습니다.');
        }
      }
    },
    async requestAiRecommendation(plan) {
      try {
        const response = await axios.put(`/plans/${plan.id}/require`);
        if (response.status === 200) {
          alert('AI 추천이 요청되었습니다. 잠시 후 다시 확인해주세요.');
          await this.fetchPlan(plan.id);
        } else {
          alert('AI 추천 요청에 실패했습니다. 다시 시도해주세요.');
        }
      } catch (error) {
        console.error('AI 추천 요청 중 오류 발생:', error);
        alert('AI 추천 요청에 실패했습니다.');
      }
    },
    async fetchPlan(planId) {
      try {
        const response = await axios.get(`/plans/${planId}`);
        const updatedPlan = {
          ...response.data,
          id: planId
        };
        const index = this.plans.findIndex(p => p.id === planId);
        if (index !== -1) {
          this.plans.splice(index, 1, updatedPlan);
        }
        if (this.selectedPlan && this.selectedPlan.id === planId) {
          this.selectedPlan = updatedPlan;
        }
      } catch (error) {
        console.error('개별 plan 가져오기 중 오류 발생:', error);
        alert('계획 정보를 업데이트하는 데 실패했습니다.');
      }
    }
  }
};
</script>

<style scoped>
.plan-management {
  font-family: 'Poppins', sans-serif;
  max-width: 900px;
  margin: 0 auto;
  padding: 40px;
  background: linear-gradient(135deg, #f0f9ff, #c2e9fb);
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.icons {
  display: flex;
  gap: 20px;
  align-items: center;
}

.icon-button {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.icon-button:hover {
  transform: scale(1.05);
}

.icon {
  width: 50px; /* 아이콘 크기 설정 */
  height: 50px;
  margin-bottom: 8px;
}

.icon-button p {
  font-size: 14px;
  color: #333;
  margin: 0;
}

h1 {
  font-size: 28px;
  color: #333;
  font-weight: bold;
  text-align: center;
}

.plan-list {
  list-style-type: none;
  padding: 0;
}

.plan-item {
  background-color: #ffffff;
  margin-bottom: 20px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s ease, transform 0.3s ease;
}

.plan-item:hover {
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
  transform: translateY(-5px);
}

.plan-overview {
  margin-bottom: 15px;
}

.plan-date {
  color: #888;
  font-weight: 500;
}

.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 0.5cm;
}

.button-group button {
  background-color: #bdc3c7;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 8px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.button-group button:hover {
  background-color: #95a5a6;
  transform: scale(1.05);
}

.ai-btn {
  background-color: #9b59b6;
}

.details-btn {
  background-color: #f39c12;
}

.plan-details {
  background-color: #fafafa;
  padding: 25px;
  margin-top: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.close-btn {
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 8px;
  font-size: 14px;
  cursor: pointer;
}

.close-btn:hover {
  background-color: #c0392b;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s ease, transform 0.5s ease;
}

.fade-enter, .fade-leave-to {
  opacity: 0;
  transform: translateY(20px);
}
</style>
