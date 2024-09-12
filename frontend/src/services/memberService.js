import axios from 'axios';

export default {
  async getMembers() {
    try {
      const response = await axios.get(`/members`);
      return response.data._embedded.members;
    } catch (error) {
      console.error('Error fetching members:', error);
      throw error;
    }
  },

  async getMember(id) {
    try {
      const response = await axios.get(`/members/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching member with id ${id}:`, error);
      throw error;
    }
  },

  async createMember(member) {
    try {
      const response = await axios.post(`/members`, member);
      return response.data;
    } catch (error) {
      console.error('Error creating member:', error);
      throw error;
    }
  },

  async updateMember(id, member) {
    try {
      const response = await axios.put(`/members/${id}`, member);
      return response.data;
    } catch (error) {
      console.error(`Error updating member with id ${id}:`, error);
      throw error;
    }
  },

  async deleteMember(id) {
    try {
      await axios.delete(`/members/${id}`);
    } catch (error) {
      console.error(`Error deleting member with id ${id}:`, error);
      throw error;
    }
  },

  async getMemberProfile(id) {
    try {
      const response = await axios.get(`/members/${id}/profile`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching profile for member with id ${id}:`, error);
      throw error;
    }
  },

  async updateMemberProfile(id, profile) {
    try {
      const response = await axios.put(`/members/${id}/profile`, profile);
      return response.data;
    } catch (error) {
      console.error(`Error updating profile for member with id ${id}:`, error);
      throw error;
    }
  },

  async login(email) {
    try {
      const response = await axios.post(
        '/members/login',
        { email },  // 이메일을 객체 형태로 전달
        {
          headers: {
            'Content-Type': 'application/json'  // JSON 형식으로 명시
          }
        }
      );
      // 로그인 성공 시 멤버 정보를 로컬 스토리지에 저장
      localStorage.setItem('member', JSON.stringify(response.data));
      return response.data;
    } catch (error) {
      console.error('Error logging in:', error);
      throw error;
    }
  },
  
  // 로그아웃 메서드
  async logout() {
    try {
      // 로컬 스토리지에서 저장된 멤버 정보 제거
      localStorage.removeItem('member');
    } catch (error) {
      console.error('Error logging out:', error);
      throw error;
    }
  },

  // 현재 로그인한 사용자 정보 가져오기
  getCurrentUser() {
    try {
      // 로컬 스토리지에서 멤버 정보 불러오기
      const member = localStorage.getItem('member');
      if (member) {
        return JSON.parse(member);
      }
      return null;
    } catch (error) {
      console.error('Error fetching current user:', error);
      throw error;
    }
  }
};