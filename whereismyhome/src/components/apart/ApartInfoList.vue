<template>
    <div>
        <b-card v-if="houses.length != 0" style="max-height: 600px; overflow-y: scroll;">
            <apart-info-item v-for="(apart, index) in houses" :key="index" v-bind="apart"></apart-info-item>
        </b-card>
        <b-card v-else title="데이터가 없습니다.">
        </b-card>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import ApartInfoItem from "./item/ApartInfoItem.vue";
export default {
    name: 'ApartInfoList',
    components: { ApartInfoItem },

    data() {
        return {
            
        };
    },
    computed: {
        ...mapState(['houses'])
    },
    mounted() {
        
    },

    methods: {
        
    },
};
</script>

<style lang="scss" scoped>

</style>