<template>
  <div class="edit-plan">
    <h1>여행 계획 수정</h1>
    <form @submit.prevent="updatePlan">
      <div class="form-group">
        <label for="location">목적지</label>
        <input v-model="plan.location" type="text" id="location" required />
      </div>

      <div class="form-group">
        <label for="groupSize">인원 수</label>
        <input v-model.number="plan.groupSize" type="number" id="groupSize" required />
      </div>

      <div class="form-group">
        <label for="budget">예산</label>
        <input v-model.number="plan.budget" type="number" id="budget" required />
      </div>

      <div class="form-group">
        <label for="travelDate">여행 날짜</label>
        <input v-model="plan.travelDate" type="date" id="travelDate" required />
      </div>

      <div class="form-group">
        <label for="details">세부사항</label>
        <textarea v-model="plan.details" id="details"></textarea>
      </div>

      <div class="button-container">
        <button type="submit" class="save-btn">계획 수정</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'EditPlan',
  props: ['id'],
  data() {
    return {
      plan: {
        id: null,
        location: '',
        groupSize: 1,
        budget: 0,
        travelDate: '',
        details: ''
      }
    };
  },
  created() {
    const planData = this.$route.query.plan;
    if (planData) {
      this.plan = JSON.parse(planData);
    } else {
      this.fetchPlan();
    }
  },
  methods: {
    async fetchPlan() {
      try {
        const response = await axios.get(`/plans/${this.id}`);
        this.plan = response.data;
      } catch (error) {
        console.error('계획을 가져오는 중 오류 발생:', error);
        alert('계획 정보를 불러오는 데 실패했습니다.');
      }
    },
    async updatePlan() {
      try {
        await axios.put(`/plans/${this.id}`, this.plan);
        alert('계획이 성공적으로 수정되었습니다.');
        this.$router.push({ name: 'PlanManagement' });
      } catch (error) {
        console.error('계획 수정 중 오류 발생:', error);
        alert('계획 수정에 실패했습니다.');
      }
    }
  }
};
</script>

<style scoped>
.edit-plan {
  font-family: 'Poppins', sans-serif;
  max-width: 600px;
  margin: 40px auto;
  padding: 40px;
  background: linear-gradient(135deg, #f0f9ff, #c2e9fb);
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 28px;
  color: #333;
  font-weight: bold;
  text-align: center;
  margin-bottom: 30px;
  text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
}

.form-group {
  margin-bottom: 24px;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #444;
}

input, textarea {
  width: 100%;
  padding: 12px;
  border: 2px solid #ddd;
  border-radius: 8px;
  font-size: 16px;
  transition: all 0.3s ease;
  background-color: rgba(255, 255, 255, 0.8);
}

input:focus, textarea:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.2);
}

input:hover, textarea:hover {
  border-color: #bbb;
}

textarea {
  min-height: 120px;
  resize: vertical;
}

.button-container {
  text-align: center;
  margin-top: 30px;
}

.save-btn {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 14px 28px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 18px;
  font-weight: 500;
  cursor: pointer;
  border-radius: 8px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.save-btn:hover {
  background-color: #2980b9;
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15);
}

.save-btn:active {
  transform: translateY(0);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
  .edit-plan {
    padding: 20px;
    margin: 20px;
  }

  h1 {
    font-size: 24px;
  }

  .save-btn {
    padding: 12px 24px;
    font-size: 16px;
  }
}
</style>