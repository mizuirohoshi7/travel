<template>

    <v-data-table
        :headers="headers"
        :items="planView"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'PlanViewView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "memberId", value: "memberId" },
                { text: "groupSize", value: "groupSize" },
                { text: "location", value: "location" },
                { text: "travelDate", value: "travelDate" },
                { text: "budget", value: "budget" },
                { text: "details", value: "details" },
                { text: "aiRecommendation", value: "aiRecommendation" },
                { text: "memberName", value: "memberName" },
            ],
            planView : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/planViews'))

            temp.data._embedded.planViews.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.planView = temp.data._embedded.planViews;
        },
        methods: {
        }
    }
</script>

