import axios from 'axios';

export default {
  async getPlans() {
    try {
      const response = await axios.get(`/plans`);
      return response.data._embedded.plans;
    } catch (error) {
      console.error('Error fetching plans:', error);
      throw error;
    }
  },

  async getPlan(id) {
    try {
      const response = await axios.get(`/plans/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching plan with id ${id}:`, error);
      throw error;
    }
  },

  async createPlan(plan) {
    try {
      const response = await axios.post(`/plans`, plan);
      return response.data;
    } catch (error) {
      console.error('Error creating plan:', error);
      throw error;
    }
  },

  async updatePlan(id, plan) {
    try {
      const response = await axios.put(`/plans/${id}`, plan);
      return response.data;
    } catch (error) {
      console.error(`Error updating plan with id ${id}:`, error);
      throw error;
    }
  },

  async deletePlan(id) {
    try {
      await axios.delete(`/plans/${id}`);
    } catch (error) {
      console.error(`Error deleting plan with id ${id}:`, error);
      throw error;
    }
  },

  async requestAiRecommendation(id) {
    try {
      const response = await axios.put(`$/plans/${id}/require`);
      return response.data;
    } catch (error) {
      console.error(`Error requesting AI recommendation for plan with id ${id}:`, error);
      throw error;
    }
  }
};