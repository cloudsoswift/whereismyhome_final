<template>
    <div>
        <b-card class="shadow-sm mb-2">
            <a href="#none" @click="goCenter"><h4 class="card-title">{{apartmentName}}</h4></a>
            <b-card-text>{{roadName}}</b-card-text>
            <button type="button" class="btn p-0" style="width: 30px; height: 30px;" :data-code="aptCode">
                <font-awesome-icon :icon="like_id < 0 ? 'fa-regular fa-bookmark' :'fa-solid fa-bookmark'" @click.prevent="InterestApart" :data-code="aptCode"></font-awesome-icon>
            </button>
            <b-row>
                <button class="btn" @click="loadArea()" v-b-toggle="`${aptCode}`"><font-awesome-icon :icon="aptCode == apartCode ? 'fa-solid fa-angles-up' : 'fa-solid fa-angles-down'" /></button>
            </b-row> 
            <b-collapse v-if="aptCode == apartCode" :id="`${aptCode}`" accordion="area">
                <div>
                    <b-badge v-for="(area, index) in areas" :key="index" class="mx-1 text-bg-primary" href="#none" @click="loadDeal(area)">{{area}}m²</b-badge>
                </div>
                <canvas id="chartplace" width="100%" height="100%" v-if="isAreaChosen"></canvas>
            </b-collapse>
        </b-card>
    </div>
</template>

<script>
import http from '@/util/http';
import { Chart, LineController, LineElement, PointElement, CategoryScale, LinearScale } from 'chart.js';
import { mapMutations, mapState } from "vuex";

export default {
    name: 'ApartInfoItem',
    props: ['apartmentName', 'buildYear', 'aptCode', 'lat', 'lng', 'roadName', 'like_id'],
    data() {
        return {
            areas: [],
            myChart: undefined,
            isAreaChosen: false,
        };
    },
    computed: {
        ...mapState(['apartCode'])
    },
    mounted() {
        Chart.register(LineController, LineElement, PointElement, CategoryScale, LinearScale);
    },

    methods: {
        ...mapMutations(['SET_APART_CODE', 'SET_CENTER_POS']),
        goCenter() {
            this.SET_CENTER_POS({lat: this.lat, lng: this.lng});
        },
        loadArea(){
            if(this.aptCode == this.apartCode){
                this.SET_APART_CODE("");
                return;
            }
            this.SET_APART_CODE(this.aptCode);
            this.isAreaChosen = false;
            http.get(`/apart/area/${this.aptCode}`)
            .then(({data, status})=>{
                switch(status){
                    case 200:
                        this.areas = data.map((value)=>Math.floor(value));
                        this.areas = this.areas.sort();
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
            // if(this.isAreaChosen){
            //     this.isAreaChosen = !this.isAreaChosen;
            //     return;
            // }
            this.isAreaChosen = true;
            http.get(`/apart/list/${this.aptCode}/${area}`)
            .then(({status, data})=>{
                switch(status){
                    case 200:
                        console.log(data);
                        if(data.length != 0){

                            if(this.myChart != undefined) 
                                this.myChart.destroy();
                            this.myChart = new Chart(
                                document.getElementById('chartplace').getContext('2d'),
                                {
                                type: 'line',
                                data: {
                                    labels: data.map(row => row.date.split('.')[0]), // 연도
                                    datasets: [
                                    {
                                        label: '가격',
                                        data: data.map(row => Number(row.dealAmount.replace(',', ''))),
                                    }
                                    ]
                                },
                                options: {
                                    tooltips: {
                                        mode: 'label'
                                    }
                                }
                                }
                            );
                        }
                }
            })
        },
        InterestApart(){

        }
    },
};
</script>

<style scoped>
a {
    color: #e43c5c;
    text-decoration: none;
}
</style>