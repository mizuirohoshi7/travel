<template>
  <div class="main-page">
    <!-- 헤더 -->
    <header class="header">
      <div class="logo">Kt-ravler</div>
      <nav class="nav-menu">
    
        <router-link to="/plan-management">Plan Management</router-link> <!-- 계획 관리 링크 -->
        
      </nav>
      <input class="search-bar" type="text" placeholder="Where are you planning to go?" />
      <div class="icons">
        <span class="icon" @click="goToNotifications">🔔</span>
        <span class="icon" @click="goToLogin">👤</span>
      </div>
    </header>

    <!-- 상단 이미지 및 텍스트 -->
    <section class="hero-section">
      <img src="@/assets/images/maini.jpg" alt="Adventure Image" class="hero-image" />
      <div class="hero-text">
        <h1 class="animated-text">Find your next adventure</h1>
        <p>Discover trips recommended by people you trust</p>
      </div>
    </section>

    <!-- 인기 여행 계획 목록 -->
    <section class="plan-list">
      <h2>Popular Plans</h2>
      <div v-for="plan in plans" :key="plan.id" class="plan-item">
        <img :src="plan.image" alt="plan image" class="plan-image" />
        <div class="plan-details">
          <h3>{{ plan.title }}</h3>
          <p>by {{ plan.author }}</p>
          <p>{{ plan.likes }} likes</p>
        </div>
        <button class="view-btn">View</button>
      </div>
    </section>

    <!-- 인기 팔로워 목록 -->
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      plans: [
        { id: 1, title: "2 Weeks in Japan", author: "@Traveler123", likes: 18, image: require('@/assets/images/japan.jpg') },
        { id: 2, title: "1 Week in Italy", author: "@Traveler456", likes: 15, image: require('@/assets/images/italy.jpg') },
        { id: 3, title: "3 Weeks in France", author: "@Traveler789", likes: 12, image: require('@/assets/images/france.jpg') },
      ],
      followers: [
        { id: 1, name: "Traveler123", followers: "100k", image: require('@/assets/images/follower1.jpg'), followed: false },
        { id: 2, name: "Traveler456", followers: "80k", image: require('@/assets/images/follower2.jpg'), followed: false },
        { id: 3, name: "Traveler789", followers: "60k", image: require('@/assets/images/follower3.jpg'), followed: false },
      ],
    };
  },
  methods: {
    // 팔로우/언팔로우 메서드
    followUser(follower) {
      follower.followed = !follower.followed;
    },
    // 로그인 페이지로 이동
    goToLogin() {
      this.$router.push({ name: 'LoginPage' });
    },
    // 알림 페이지로 이동
    goToNotifications() {
      // 알림 기능 추가 가능
      alert("This will show notifications!");
    },
  },
};
</script>

<style scoped>
/* 헤더 스타일 */
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

/* 상단 이미지 및 텍스트 */
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
}

.hero-text p {
  font-size: 18px;
}

/* 애니메이션 효과 */
.animated-text {
  animation: fadeInUp 1.5s ease-in-out;
}

@keyframes fadeInUp {
  0% {
    opacity: 0;
    transform: translateY(30px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 인기 여행 계획 목록 스타일 */
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

.view-btn {
  background-color: #007bff;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.view-btn:hover {
  background-color: #0056b3;
}

/* 인기 팔로워 목록 스타일 */
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
</style>
