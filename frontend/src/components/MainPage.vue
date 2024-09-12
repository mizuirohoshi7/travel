<template>
  <div class="main-page">
    <!-- Header -->
    <header class="header">
      <div class="logo">
        K-travler
        <img src="@/assets/KT.png" alt="KT Logo" class="kt-logo" /> <!-- KT 로고 추가 -->
      </div>
      <nav class="nav-menu">
        <router-link to="/plan-management">Plan Management</router-link> <!-- 계획 관리 링크 -->
      </nav>
      <input class="search-bar" type="text" placeholder="Where are you planning to go?" />
      <div class="icons">
        <span class="icon" @click="goToNotifications">🔔</span>
        <span class="icon" @click="goToLogin">👤</span>
      </div>
    </header>

    <!-- Hero Section -->
    <section class="hero-section">
      <img src="@/assets/images/maini.jpg" alt="Adventure Image" class="hero-image" />
      <div class="hero-text">
        <h1 class="animated-text">Find your next adventure</h1>
        <p>Discover trips recommended by people you trust</p>
      </div>
    </section>

    <!-- Popular Plans List -->
    <section class="plan-list">
      <h2>Popular Plans</h2>
      <div v-for="plan in plans" :key="plan.id" class="plan-item">
        <img :src="plan.image" alt="plan image" class="plan-image" />
        <div class="plan-details">
          <h3>{{ plan.title }}</h3>
          <p>by {{ plan.author }}</p>
          <p>{{ plan.likes }} likes</p>
        </div>
        <div class="button-group">
          <button class="like-btn" @click="likePlan(plan)">👍 Like</button>
          <button class="view-btn" @click="viewPlan(plan)">View</button>
        </div>
      </div>
    </section>

    <!-- Popular Followers List -->
    <section class="follower-list">
      <h2>Popular Followers</h2>
      <div v-for="follower in followers" :key="follower.id" class="follower-item">
        <img :src="follower.image" alt="follower image" class="follower-image" />
        <div class="follower-details">
          <p>{{ follower.name }}</p>
          <p>{{ follower.followers }} followers</p>
          <button class="follow-btn" @click="followUser(follower)">
            {{ follower.followed ? 'Unfollow' : 'Follow' }}
          </button>
        </div>
      </div>
    </section>

    <!-- Plan Details Modal -->
    <div v-if="selectedPlan" class="plan-details-modal">
      <h2>{{ selectedPlan.location }} 여행 상세 정보</h2>
      <p>날짜: {{ formatDate(selectedPlan.travelDate) }}</p>
      <p>예산: {{ selectedPlan.budget }}원</p>
      <p>인원: {{ selectedPlan.groupSize }}명</p>
      <p>세부사항: {{ selectedPlan.details }}</p>
      <h3>AI 추천:</h3>
      <p>{{ selectedPlan.aiRecommendation || '아직 AI 추천이 없습니다.' }}</p>
      <button @click="closePlanDetails">닫기</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      plans: [
        { id: 1, title: "2 Weeks in Japan", author: "@Traveler123", likes: 18, image: require('@/assets/images/japan.jpg'), location: "일본", travelDate: "2024-10-01", budget: 1000000, groupSize: 2, details: "도쿄 여행", aiRecommendation: null },
        { id: 2, title: "1 Week in Italy", author: "@Traveler456", likes: 15, image: require('@/assets/images/italy.jpg'), location: "이탈리아", travelDate: "2024-09-15", budget: 700000, groupSize: 1, details: "로마 여행", aiRecommendation: null },
        { id: 3, title: "3 Weeks in France", author: "@Traveler789", likes: 12, image: require('@/assets/images/france.jpg'), location: "프랑스", travelDate: "2024-09-25", budget: 1500000, groupSize: 4, details: "파리 여행", aiRecommendation: null },
      ],
      followers: [
        { id: 1, name: "Traveler123", followers: "100k", image: require('@/assets/images/follower1.jpg'), followed: false },
        { id: 2, name: "Traveler456", followers: "80k", image: require('@/assets/images/follower2.jpg'), followed: false },
        { id: 3, name: "Traveler789", followers: "60k", image: require('@/assets/images/follower3.jpg'), followed: false },
      ],
      selectedPlan: null,
    };
  },
  methods: {
    likePlan(plan) {
      plan.likes += 1;
    },
    viewPlan(plan) {
      this.selectedPlan = plan;
    },
    closePlanDetails() {
      this.selectedPlan = null;
    },
    followUser(follower) {
      follower.followed = !follower.followed;
      this.updateFollowInMyPage(follower); // Update the follow status in MyPage
    },
    updateFollowInMyPage(follower) {
      const myPageComponent = this.$root.$children.find(component => component.$options.name === 'MyPage');
      if (myPageComponent) {
        const followIndex = myPageComponent.followingList.findIndex(f => f.id === follower.id);
        if (followIndex === -1 && follower.followed) {
          myPageComponent.followingList.push({ ...follower });
        } else if (followIndex !== -1 && !follower.followed) {
          myPageComponent.followingList.splice(followIndex, 1);
        }
      }
    },
    formatDate(dateString) {
      return new Date(dateString).toLocaleDateString();
    },
    goToLogin() {
      this.$router.push({ name: 'LoginPage' });
    },
    goToNotifications() {
      alert("This will show notifications!");
    },
  },
};
</script>

<style scoped>
/* Header */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background-color: white;
}

.logo {
  font-size: 24px;
  font-weight: bold;
  display: flex;
  align-items: center;
}

.kt-logo {
  width: 80px;
  height: 80px;
  margin-left: 8px; /* 로고와 텍스트 사이 간격 */
}

.nav-menu a {
  margin: 0 25px;
  text-decoration: none;
  color: black;
}

.search-bar {
  padding: 8px;
  border-radius: 20px;
  border: 1px solid #3984f4;
  width: 300px;
}

.icons .icon {
  margin-left: 20px;
  font-size: 20px;
  cursor: pointer;
}

/* Hero Section */
.hero-section {
  position: relative;
  text-align: center;
  color: white;
}

.hero-image {
  width: 100%;
  height: 550px;
  object-fit: cover;
  border-radius: 10px;
}

.hero-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.hero-text h1 {
  font-size: 48px;
  font-weight: bold;
  animation: fadeInUp 2s ease-in-out;
}

.hero-text p {
  font-size: 18px;
}

@keyframes fadeInUp {
  0% {
    opacity: 0;
    transform: translateY(50px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Plan List */
.plan-list {
  margin-top: 30px;
}

.plan-item {
  display: flex;
  align-items: center;
  background-color: white;
  padding: 15px;
  margin-bottom: 15px;
  border-radius: 10px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.plan-image {
  width: 150px;
  height: 100px;
  object-fit: cover;
  margin-right: 20px;
  border-radius: 10px;
}

.plan-details {
  flex-grow: 1;
}

.button-group {
  display: flex;
  gap: 10px;
}

.like-btn, .view-btn {
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 100px;
}

.like-btn:hover, .view-btn:hover {
  background-color: #0056b3;
}

/* Follower List */
.follower-list {
  margin-top: 30px;
}

.follower-item {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.follower-image {
  width: 50px;
  height: 50px;
  object-fit: cover;
  border-radius: 50%;
  margin-right: 10px;
}

.follower-details p {
  margin: 0;
}

.follow-btn {
  background-color: #007bff;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px;
}

.follow-btn:hover {
  background-color: #0056b3;
}

/* Plan Details Modal */
.plan-details-modal {
  background-color: #f8f9fa;
  padding: 20px;
  border-radius: 10px;
  margin-top: 30px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
}
</style>
