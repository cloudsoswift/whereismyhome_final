<template>
    <div>
        <b-card class="shadow-sm mb-2">
            <a href="#none" @click="goCenter"><h4 class="card-title">{{apartmentName}}</h4></a>
            <b-card-text>{{roadName}}</b-card-text>
            <b-card-sub-title text-start>준공: {{buildYear}} 년</b-card-sub-title>
            <button type="button" class="btn p-0 position-absolute top-0 end-0" style="width: 30px; height: 30px;" @click.prevent="InterestApart" v-if="isLogin">
                <font-awesome-icon :icon="!isBookmark ? 'fa-regular fa-bookmark' :'fa-solid fa-bookmark'" :data-code="aptCode"></font-awesome-icon>
            </button>
            <b-row>
                <button class="btn" @click="loadArea()" v-b-toggle="`${aptCode}`"><font-awesome-icon :icon="aptCode == apartCode ? 'fa-solid fa-angles-up' : 'fa-solid fa-angles-down'" /></button>
            </b-row> 
            <b-collapse v-if="aptCode == apartCode" :id="`${aptCode}`" accordion="area">
                <div>
                    <b-badge v-for="(area, index) in areas" :key="index" class="mx-1 text-bg-primary" href="#none" @click="loadDeal(area)">{{area}}m²</b-badge>
                </div>
                <div>
                    <div v-if="isAreaChosen">
                        <table class="table table-sm rounded-4 shadow-sm mb-2 caption-top">
                            <caption class="text-center">최근 거래 내역</caption>
                            <thead class="table-light">
                                <tr>
                                    <th scope="col">거래 일</th>
                                    <th scope="col">거래 금액</th>
                                </tr>
                            </thead>
                            <tbody class="table-group-divider">
                                <tr v-for="(deal, index) in leastDeals" :key="index">
                                    <td>{{deal.date}}</td>
                                    <td>{{deal.dealAmount}}</td>
                                </tr>
                            </tbody>
                        </table>
                        <canvas id="chartplace" width="100%" height="100%"></canvas>
                    </div>
                </div>
            </b-collapse>
        </b-card>
    </div>
</template>

<script>
import http from '@/util/http';
import { Chart, LineController, LineElement, PointElement, CategoryScale, LinearScale } from 'chart.js';
import { mapGetters, mapMutations, mapState } from "vuex";

export default {
    name: 'ApartInfoItem',
    props: ['apartmentName', 'buildYear', 'aptCode', 'lat', 'lng', 'roadName', 'like_id'],
    data() {
        return {
            areas: [],
            leastDeals: [],
            myChart: undefined,
            isAreaChosen: false,
            isBookmark: this.like_id > 0 ? true : false,
        };
    },
    computed: {
        ...mapState(['apartCode', 'tokens']),
        ...mapGetters(['isLogin'])
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
            this.goCenter();
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
                        this.leastDeals = data.slice(-5);
                        this.leastDeals.reverse();
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
            const options = {
                headers: {
                    "access-token": this.tokens.accessToken,
                }
            }
            if(this.isBookmark){
                // 북마크임 -> 북마크 삭제
                http.delete(`/apart/like/${this.aptCode}`, options)
                .then(({status})=>{
                    switch(status){
                        case 200:
                        // HttpStatus.OK
                        this.isBookmark = false;
                        break;
                    case 403:
                        //HttpStatus.FORBIDDEN
                        alert("북마크 삭제에 실패했습니다. 다시 시도해주세요");
                        break;
                    case 500:
                        //HttpStatus.INTERNAL_SERVER_ERROR
                        alert("서버와 통신중 에러가 발생했습니다.");
                        this.$router.push("/");
                        break;
                    }
                }).catch(async({response})=>{
                    switch(response.status){
                        case 401:
                            //HttpStatus.UNAUTHORIZED
                            await this.$store.dispatch("tokenRefresh")
                            if(!this.isLogin){
                                alert("로그인이 만료되었습니다.");
                                this.$router.push("/user/login");
                            } else {
                                alert("토큰을 갱신했습니다. 다시 시도해주세요");
                            }
                            break;
                    }
                })
            } else {
                // 북마크 아님 -> 북마크 등록
                http.post(`/apart/like/${this.aptCode}`, {}, options)
                            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    this.isBookmark = true;
                    break;
                case 403:
                    //HttpStatus.FORBIDDEN
                    alert("북마크 삭제에 실패했습니다. 다시 시도해주세요");
                    break;
                case 500:
                    //HttpStatus.INTERNAL_SERVER_ERROR
                    alert("서버와 통신중 에러가 발생했습니다.");
                    this.$router.push("/");
                    break;
                }
                }).catch(async({response})=>{
                    switch(response.status){
                        case 401:
                            //HttpStatus.UNAUTHORIZED
                            await this.$store.dispatch("tokenRefresh")
                            if(!this.isLogin){
                                alert("로그인이 만료되었습니다.");
                                this.$router.push("/user/login");
                            } else {
                                alert("토큰을 갱신했습니다. 다시 시도해주세요");
                            }
                            break;
                    }
                })
            }
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