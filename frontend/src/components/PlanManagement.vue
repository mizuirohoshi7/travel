<template>
  <div class="plan-management">
    <h1>Your plans</h1>

    <!-- Tabs for plan categories -->
    <div class="tabs">
      <button :class="{ active: activeTab === 'upcoming' }" @click="activeTab = 'upcoming'">Upcoming</button>
      <button :class="{ active: activeTab === 'saved' }" @click="activeTab = 'saved'">Saved</button>
      <button :class="{ active: activeTab === 'past' }" @click="activeTab = 'past'">Past</button>
    </div>

    <!-- Plan list -->
    <div class="plan-list">
      <div v-for="(plan, index) in filteredPlans" :key="index" class="plan-item" @click="viewItinerary(plan)">
        <img :src="plan.image" alt="plan image" class="plan-image" />
        <div class="plan-details">
          <h3>{{ plan.name }}</h3>
          <p>{{ plan.date }}</p>
        </div>
        <button class="edit-btn" @click.stop="openModal(plan)">
          <i class="fas fa-edit"></i> Edit
        </button>
        <button class="ai-btn" @click.stop="getAISuggestion(plan)">
          <i class="fas fa-lightbulb"></i> AI 추천
        </button>
      </div>
    </div>

    <!-- Add new plan button -->
    <div class="add-plan">
      <button @click="createPlan">
        <i class="fas fa-plus"></i> Add a plan
      </button>
    </div>

    <!-- Edit Modal -->
    <div v-if="showModal" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <h2>Edit Plan</h2>
        <form @submit.prevent="updatePlan">
          <div class="form-group">
            <label for="destination">Destination</label>
            <input v-model="selectedPlan.destination" type="text" id="destination" required />
          </div>

          <div class="form-group">
            <label for="people">Number of people</label>
            <input v-model.number="selectedPlan.people" type="number" id="people" required />
          </div>

          <div class="form-group">
            <label for="budget">Budget</label>
            <input v-model.number="selectedPlan.budget" type="number" id="budget" required />
          </div>

          <div class="form-group">
            <label for="start-date">Start date</label>
            <input v-model="selectedPlan.startDate" type="date" id="start-date" required />
          </div>

          <div class="form-group">
            <label for="end-date">End date</label>
            <input v-model="selectedPlan.endDate" type="date" id="end-date" required />
          </div>

          <div class="form-group">
            <label for="notes">Notes</label>
            <textarea v-model="selectedPlan.notes" id="notes"></textarea>
          </div>

          <div class="modal-actions">
            <button type="submit" class="save-btn">Save</button>
            <button type="button" class="delete-btn" @click="deletePlan">Delete</button>
          </div>
        </form>
        <button class="close-btn" @click="closeModal">Close</button>
      </div>
    </div>

    <!-- Itinerary View -->
    <div v-if="showItinerary" class="itinerary-overlay" @click="closeItinerary">
      <div class="itinerary-content" @click.stop>
        <h2>{{ selectedPlan.name }}'s Itinerary</h2>
        <div v-for="(day, index) in selectedPlan.itinerary" :key="index">
          <h3>Day {{ index + 1 }}</h3>
          <p>{{ day }}</p>
        </div>
        <button class="close-btn" @click="closeItinerary">Close</button>
      </div>
    </div>
  </div>
</template>

<script>
import planService from '@/services/planService';

export default {
  data() {
    return {
      activeTab: 'upcoming',
      plans: [
        { id: 1, name: 'Hawaii: 2022', date: 'July 1-5', image: '/assets/plan1.svg', category: 'upcoming', itinerary: ['Day 1: Arrival', 'Day 2: Beach', 'Day 3: Hiking'] },
        { id: 2, name: 'San Francisco: 2022', date: 'June 1-5', image: '/assets/images/plan2.png', category: 'upcoming', itinerary: ['Day 1: Golden Gate', 'Day 2: Alcatraz'] },
        { id: 3, name: 'Paris: 2022', date: 'May 1-5', image: '/assets/images/images/plan3.png', category: 'upcoming', itinerary: ['Day 1: Eiffel Tower', 'Day 2: Louvre'] },
        { id: 4, name: 'New York: 2022', date: 'April 1-5', image: '/assets/images/plan4.png', category: 'upcoming', itinerary: ['Day 1: Times Square', 'Day 2: Central Park'] },
        { id: 5, name: 'Tokyo: 2021', date: 'August 1-5', image: '/assets/images/plan5.jpg', category: 'past', itinerary: ['Day 1: Shibuya', 'Day 2: Asakusa'] },
      ],
      showModal: false,
      selectedPlan: null,
      showItinerary: false,
    };
  },
  computed: {
    filteredPlans() {
      return this.plans.filter(plan => plan.category === this.activeTab);
    },
  },
  methods: {
    createPlan() {
      // CreatePlan 페이지로 이동
      this.$router.push({ name: 'CreatePlan' });
    },
    openModal(plan) {
      this.selectedPlan = { ...plan };
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
      this.selectedPlan = null;
    },
    updatePlan() {
      alert('Plan updated successfully!');
      this.closeModal();
    },
    deletePlan() {
      if (confirm('Are you sure you want to delete this plan?')) {
        this.plans = this.plans.filter(p => p.id !== this.selectedPlan.id);
        this.closeModal();
      }
    },
    getAISuggestion(plan) {
      // AI 추천을 통해 여행 계획을 생성하고 itinerary에 저장
      const aiSuggestedItinerary = [
        'Day 1: Local Sightseeing',
        'Day 2: Explore Cultural Spots',
        'Day 3: Visit Popular Landmarks'
      ];

      plan.itinerary = aiSuggestedItinerary;
      alert(`AI 추천 여행 일정이 ${plan.name}에 저장되었습니다!`);
    },
    viewItinerary(plan) {
      this.selectedPlan = { ...plan };
      this.showItinerary = true;
    },
    closeItinerary() {
      this.showItinerary = false;
      this.selectedPlan = null;
    },
    // CreatePlan에서 새 계획 받아오는 메서드
    addNewPlan(plan) {
      this.plans.push(plan);
    }
  },
  mounted() {
    // CreatePlan에서 새 계획이 넘어온 경우 처리
    if (this.$route.query.newPlan) {
      const newPlan = JSON.parse(this.$route.query.newPlan);
      this.addNewPlan(newPlan);  // 새로운 계획을 추가
    }
  }
};
</script>


<style scoped>
/* Global Styles */
body {
  background-color: #e0f7fa;
  font-family: 'Roboto', sans-serif;
}

.plan-management {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
  background-color: white;
  border-radius: 15px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h1 {
  font-size: 32px;
  color: #00796b;
  margin-bottom: 20px;
}

/* Tabs */
.tabs {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
}

.tabs button {
  padding: 12px 24px;
  background-color: transparent;
  border: none;
  color: #00838f;
  font-size: 18px;
  cursor: pointer;
  border-bottom: 3px solid transparent;
  transition: border-bottom 0.3s ease, color 0.3s ease;
}

.tabs button.active {
  border-bottom: 3px solid #00796b;
  color: #00796b;
}

/* Plan List */
.plan-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.plan-item {
  display: flex;
  align-items: center;
  justify-content: space-between; /* 추가: 양쪽 끝으로 요소 배치 */
  padding: 15px;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease;
  cursor: pointer;
}
.plan-item:hover {
  transform: translateY(-5px);
}

.plan-image {
  width: 100px;
  height: 80px;
  object-fit: cover;
  border-radius: 10px;
  margin-right: 20px;
}

.plan-details h3 {
  margin: 0;
  font-size: 20px;
  color: #00796b;
}

.plan-details p {
  margin-top: 5px;
  font-size: 16px;
  color: #1c0244;
}

/* Buttons */
.edit-btn,
.ai-btn {
  background-color: #24559e;
  color: white;
  border: none;
  padding: 9px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease;
}
/* 두 버튼 사이에 10cm (약 377px)의 간격을 추가 */
.edit-btn {
  margin-right: 2px; /* 버튼 사이 간격 */
}

.plan-buttons {
  display: flex;
  margin-left: auto; /* 버튼들이 오른쪽 끝으로 이동 */
}

.add-plan button {
  background-color: #24559e;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease;
}

.add-plan button {
  background-color: #1f1c60;
}

.ai-btn {
  background-color: #4973ae;
}

.edit-btn:hover,
.ai-btn:hover,
.add-plan button:hover {
  left:100%;
  background-color: #0056b3;
}

.add-plan {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

/* Modal Styles */
.modal-overlay,
.itinerary-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content,
.itinerary-content {
  background-color: #f0f7f9;
  padding: 50px;
  border-radius: 20px;
  max-width: 800px;
  width: 100%;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  font-family: 'Arial', sans-serif;
  text-align: left;
}

h2 {
  font-size: 28px;
  color: #000a79;
  margin-bottom: 30px;
  text-align: center;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-size: 18px;
  color: #004d40;
  margin-bottom: 10px;
  font-weight: bold;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 15px;
  border: 1px solid #00796b;
  border-radius: 8px;
  font-size: 14px;
  background-color: #ffffff;
  color: #333;
}

textarea {
  height: 100px;
}

.form-group input:focus,
.form-group textarea:focus {
  outline: none;
  border-color: #004d40;
  box-shadow: 0 0 10px rgba(0, 77, 64, 0.2);
}

/* Modal Actions */
.modal-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.save-btn,
.delete-btn,
.close-btn {
  padding: 10px 40px;
  font-size: 14px;
  border-radius: 6px;
  border: none;
  cursor: pointer;
}

.save-btn {
  background-color: #00796b;
  color: white;
  font-weight: bold;
}

.save-btn:hover {
  background-color: #004d40;
}

.delete-btn {
  background-color: #dc3545;
  color: white;
  font-weight: bold;
}

.delete-btn:hover {
  background-color: #c82333;
}

.close-btn {
  background-color: #333;
  color: white;
  padding: 10px;
  position: absolute;
  top: 10px;
  right: 10px;
}

.close-btn:hover {
  background-color: #555;
}

</style>