<template>

    <v-data-table
        :headers="headers"
        :items="notificationView"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'NotificationViewView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            notificationView : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/notificationViews'))

            temp.data._embedded.notificationViews.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.notificationView = temp.data._embedded.notificationViews;
        },
        methods: {
        }
    }
</script>

