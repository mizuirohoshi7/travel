<template>

    <v-data-table
        :headers="headers"
        :items="followView"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'FollowViewView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            followView : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/followViews'))

            temp.data._embedded.followViews.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.followView = temp.data._embedded.followViews;
        },
        methods: {
        }
    }
</script>

