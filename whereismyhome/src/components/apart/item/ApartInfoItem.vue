<template>
    <div>
        <ul class="list-group list-group-flush">
            <li class="list-group-item mb-1" v-for="(apt, index) in houses" :key="index">
                <a href="#" style="color:black;">
                    <div class="card" style="min-width: 18rem">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-lg-9 col-sm-12 text-lg-start text-center">
                                <h5 class="card-title mb-3">{{apt.apartmentName}}</h5>
                                </div>
                                <div class="col-lg-2 col-sm-12 text-lg-end text-center ms-3">
                                <button type="button" class="inter-apt btn" style="width: 40px" @click="InterestApart">
                                    <i :class="apt.like_id < 0 ? 'bi-star' :'bi-star-fill'" :data-code="apt.aptCode" :data-no="apt.no"></i>
                                </button>
                                </div>
                            </div>
                        <h6 class="card-subtitle mb-1">거래금액: {{apt.dealAmount}}만원</h6>
                        <p class="m-0 text-muted">면적: {{apt.area}}</p>
                        <p class="m-0 text-muted">층: {{apt.floor}}</p>
                        <p class="card-text">{{apt.date}}</p>
                        </div>
                    </div>
                </a>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    name: 'ApartInfoItem',

    data() {
        return {
            
        };
    },

    mounted() {
        
    },

    methods: {
        
    },
};
</script>

<style lang="scss" scoped>

</style>