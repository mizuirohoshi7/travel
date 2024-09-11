<template>

    <v-data-table
        :headers="headers"
        :items="memberModel"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'MemberModelView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "name", value: "name" },
            ],
            memberModel : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/memberModels'))

            temp.data._embedded.memberModels.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.memberModel = temp.data._embedded.memberModels;
        },
        methods: {
        }
    }
</script>

