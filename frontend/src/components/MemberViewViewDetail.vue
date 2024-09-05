<template>

    <v-card outlined>
        <v-card-title>
            MemberView # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <String label="OauthId" v-model="item.oauthId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Name" v-model="item.name" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Email" v-model="item.email" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="TokenAmount" v-model="item.tokenAmount" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'MemberViewViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/memberViews/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

