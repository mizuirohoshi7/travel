<template>

    <v-card outlined>
        <v-card-title>
            PlanView # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="MemberId" v-model="item.memberId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="GroupSize" v-model="item.groupSize" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Location" v-model="item.location" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="TravelDate" v-model="item.travelDate" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="Budget" v-model="item.budget" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Details" v-model="item.details" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="AiRecommendation" v-model="item.aiRecommendation" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="MemberName" v-model="item.memberName" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'PlanViewViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/planViews/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

