<template>
    <div>
        <b-card :title="apartmentName" class="shadow-sm mb-2">
            {{roadName}}
            <button @click="loadArea()">불러오기</button>
            <div>
                <b-badge v-for="(area, index) in areas" :key="index" pill class="mx-1" style="background-color: #0d6efd;">{{area}}<button @click="loadDeal(area)"></button></b-badge>
            </div>
        </b-card>
    </div>
</template>

<script>
import http from '@/util/http';

export default {
    name: 'ApartInfoItem',
    props: ['apartmentName', 'aptCode', 'lat', 'lng', 'roadName', 'like_id'],
    data() {
        return {
            areas: [],
        };
    },

    mounted() {
        
    },

    methods: {
        loadArea(){
            http.get(`/apart/area/${this.aptCode}`)
            .then(({data, status})=>{
                switch(status){
                    case 200:
                        this.areas = data;
                        break;
                    case 204:
                        alert("평 데이터가 존재하지 않습니다.");
                        break;
                    case 500:                    
                        //HttpStatus.INTERNAL_SERVER_ERROR
                        alert("서버와 통신중 에러가 발생했습니다.");
                        this.$router.push("/");
                        break;
                }
            });
        },
        loadDeal(area){
            console.log(area)
            http.get(`/apart/list/${this.aptCode}/${area}`)
            .then((response)=>{
                console.log(response);
            })
        }
    },
};
</script>

<style lang="scss" scoped>

</style>