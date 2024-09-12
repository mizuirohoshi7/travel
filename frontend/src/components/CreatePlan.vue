<template>
  <div class="create-plan">
    <img src="@/assets/home.png" alt="Home" class="home-btn" @click="goToMainPage" />
    <h1>Create a New Plan</h1>
    <form @submit.prevent="createPlan">
      <div class="form-group">
        <label for="location">Destination</label>
        <input v-model="plan.location" type="text" id="location" placeholder="Enter destination" required />
      </div>

      <div class="form-group">
        <label for="groupSize">Number of people</label>
        <input v-model.number="plan.groupSize" type="number" id="groupSize" placeholder="Enter number of people" required />
      </div>

      <div class="form-group">
        <label for="budget">Budget</label>
        <input v-model.number="plan.budget" type="number" id="budget" placeholder="Enter budget" required />
      </div>

      <div class="form-group">
        <label for="travelDate">Travel Date</label>
        <input v-model="plan.travelDate" type="date" id="travelDate" required />
      </div>

      <div class="form-group">
        <label for="details">Notes</label>
        <textarea v-model="plan.details" id="details" placeholder="Add additional details"></textarea>
      </div>

      <button type="submit" class="save-btn">Save Plan</button>
    </form>
  </div>
</template>

<script>
import PlanService from '@/services/PlanService';

export default {
  data() {
    return {
      plan: {
        memberId: 1, // 로그인한 사용자의 ID를 설정해야 함
        location: '',
        groupSize: 1,
        budget: 0,
        travelDate: '',
        details: ''
      }
    };
  },
  methods: {
    async createPlan() {
      if (!this.plan.location || !this.plan.travelDate) {
        alert('Please fill in all required fields.');
        return;
      }

      try {
        const response = await PlanService.createPlan(this.plan);
        const newPlan = response.data;

        // PlanManagement 페이지로 이동하면서 새 계획을 query로 전달
        this.$router.push({ name: 'PlanManagement', query: { newPlan: JSON.stringify(newPlan) } });
      } catch (error) {
        console.error('Error creating plan:', error);
        alert('An error occurred while creating the plan.');
      }
    },
    goToMainPage() {
      this.$router.push({ name: 'MainPage' });
    }
  }
};
</script>

<style scoped>
.create-plan {
  max-width: 600px;
  margin: 50px auto;
  padding: 40px;
  background-color: #daf4ff;
  border-radius: 10px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h1 {
  font-size: 28px;
  font-weight: 700;
  color: #333;
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: 600;
  color: #333;
}

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

input:focus,
textarea:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0 0 8px rgba(0, 123, 255, 0.2);
}

textarea {
  height: 120px;
  resize: none;
}

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

.save-btn:hover {
  background-color: #0056b3;
}

.home-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  width: 40px;
  height: 40px;
  cursor: pointer;
}
</style>
