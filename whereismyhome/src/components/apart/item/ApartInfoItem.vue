<template>
    <div>
        <b-card :title="apartmentName" class="shadow-sm mb-2">
            <b-card-text>{{roadName}}</b-card-text>
            <b-row>
                <button class="btn" @click="loadArea()" v-b-toggle="`${aptCode}`"><font-awesome-icon :icon="aptCode == apartCode ? 'fa-solid fa-angles-up' : 'fa-solid fa-angles-down'" /></button>
            </b-row> 
            <b-collapse v-if="aptCode == apartCode" :id="`${aptCode}`" accordion="area">
                <div>
                    <b-badge v-for="(area, index) in areas" :key="index" pill class="mx-1" style="background-color: #0d6efd; text-decoration: none;" href="#" @click="loadDeal(area)">{{area}}</b-badge>
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
    props: ['apartmentName', 'aptCode', 'lat', 'lng', 'roadName', 'like_id'],
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
        loadArea(){
            if(this.aptCode == this.apartCode){
                this.SET_APART_CODE("");
                return;
            }
            this.SET_APART_CODE(this.aptCode);
            this.isAreaChosen = false;
            this.SET_CENTER_POS({lat: this.lat, lng: this.lng});
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
                        if(this.myChart != undefined) 
                            this.myChart.destroy();
                        this.myChart = new Chart(
                            document.getElementById('chartplace').getContext('2d'),
                            {
                            type: 'line',
                            data: {
                                labels: data.map(row => row.date.split('.')[0]),
                                datasets: [
                                {
                                    label: 'Acquisitions by year',
                                    data: data.map(row => Number(row.dealAmount.replace(',', '')))
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
            })
        }
    },
};
</script>

<style lang="scss" scoped>

</style>