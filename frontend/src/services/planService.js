import axios from 'axios'

const API_URL = '/plans'

const axiosInstance = axios.create({
  baseURL: API_URL,
  withCredentials: true,
  timeout: 10000
})

export default {
  async getPlans() {
    const response = await axiosInstance.get('')
    return response.data
  },

  async createPlan(planData) {
    const response = await axiosInstance.post('', planData)
    return response.data
  },

  async updatePlan(planId, planData) {
    const response = await axiosInstance.put(`/${planId}`, planData)
    return response.data
  },

  async deletePlan(planId) {
    await axiosInstance.delete(`/${planId}`)
  },

  async getPlanDetail(planId) {
    const response = await axiosInstance.get(`/${planId}`)
    return response.data
  },
}