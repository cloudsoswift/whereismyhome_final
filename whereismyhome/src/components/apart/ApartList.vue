<template>
    <div>
        <section>
            <div class="container-fluid d-flex flex-column min-vh-100">
            <div style="height: 70px"></div>
            <b-row>
                <b-col cols=1></b-col>
                <b-col cols=10>
                
                <h2 class="col-3 ms-1 mt-5 mb-4 d-inline">아파트 매매 정보</h2>
                </b-col>
            </b-row>
            <!-- 검색 결과와 지도 -->
            <div id="result" class="container-fluid mt-4 mb-5">
                <div class="row w-100 m-auto">
                <div id="aptlist" class="col-4 p-0" v-if="houses.length != 0">
                    <h3>거래 정보</h3>

                </div>
                <div class="col-12">
                    <!-- Kakao Map start -->
                    <div class="mt-3 p-0 text-center">
                        <h3>매매 위치</h3>
                    </div>
                    <b-card class="ms-2 my-2 w-100 h-100 shadow rounded-4" 
                    style="position: absolute; z-index: 8; max-height: 676px; max-width: 300px"
                    title="매물 검색">
                        <button class="btn p-1 px-2 back-button" @click="goBack" v-if="tabIndex > 0">
                            <font-awesome-icon icon="fa-solid fa-arrow-left" />
                        </button>
                        <b-tabs v-model="tabIndex" no-nav-style no-key-nav>
                            <b-tab>
                                <b-list-group style="max-height: 600px; overflow-y: scroll;">
                                    <div class="accordion" v-for="(item, index) in sidos" :key="index" >
                                        <b-list-group-item button v-b-toggle="`${item.value}`" :value="item.value" @click="gugunSearch">{{item.text}}</b-list-group-item>
                                        <b-collapse v-if="sido == item.value" :id="item.value" accordion="sido">
                                            <b-list-group>
                                                <b-card>
                                                    <div v-for="(item, index) in guguns" :key="index">
                                                        <b-list-group-item button v-b-toggle="`${item.value}`" :value="item.value" @click="dongSearch">{{item.text}}
                                                            
                                                        <button type="button" class="btn p-0" style="width: 30px; height: 30px;" :data-code="item.value">
                                                            <font-awesome-icon :icon="item.interest_no < 0 ? 'fa-regular fa-bookmark' :'fa-solid fa-bookmark'" @click.prevent="InterestArea" :data-code="item.value"></font-awesome-icon>
                                                        </button>
                                                        </b-list-group-item>
                                                        <b-collapse v-if="gugun == item.value" :id="item.value" accordion="gugun">
                                                            <b-list-group>
                                                                <b-card>
                                                                    <div v-for="(item, index) in dongs" :key="index">
                                                                        <b-list-group-item button v-b-toggle="`${item.value}`" :value="item.value" @click="searchAptList">{{item.text}}</b-list-group-item>
                                                                    </div>
                                                                </b-card>
                                                            </b-list-group>
                                                        </b-collapse>
                                                    </div>
                                                </b-card>
                                            </b-list-group>
                                        </b-collapse>
                                    </div>
                                </b-list-group>
                            </b-tab>
                            <b-tab>
                                <apart-info-list></apart-info-list>
                            </b-tab>
                        </b-tabs>
                    </b-card>
                    <apart-map :addr="addr"></apart-map>
                    <!-- Kakao Map end -->
                </div>
                </div>
            </div>
            </div>
        </section>
    </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import ApartMap from "@/components/apart/ApartMap.vue";
import http from "@/util/http";
import ApartInfoList from "@/components/apart/ApartInfoList.vue";
import parser from "@/util/jwtParser";
export default {
    components: { ApartMap, ApartInfoList },
    name: 'ApartList',
    data() {
        return {
            // 동코드 저장요
            sido:"",
            gugun:"",
            dong:"",
            // 지도 위치이동을 위한 텍스트
            sidoText:"",
            gugunText:"",
            dongText:"",
            addr:"",
            tabIndex: 0,
        };
    },
    computed: {
        ...mapState(["sidos", "guguns", "dongs", "houses"]),
    },
    created() {

    },
    mounted() {
        this.CLEAR_SIDO_LIST();
        this.CLEAR_GUGUN_LIST();
        this.CLEAR_DONG_LIST();
        this.CLEAR_APT_LIST();
        this.getSido();
        console.log(parser("eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjY5MTA0ODYwOTI0LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjkxMDQ4NzAsInN1YiI6ImFjY2Vzcy10b2tlbiIsInVzZXJpZCI6InNzYWZ5IiwidXNlcm5hbWUiOiLsnbTssL3rr7wiLCJ1c2VyYWRkcmVzcyI6ImNtbGVlMDkxM0BuYXZlci5jb20iLCJ1c2VycGhvbmUiOiIwMTA5NjY4MzkzNiJ9.3dQma56K_bBU6wIIy04hRBUDAYZfi-c_VEY4ti2nKaw"));
    },

    methods: {
        ...mapActions(["getSido", "getGugun", "getDong", "getHouseList"]),
        ...mapMutations(["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST", "CLEAR_APT_LIST"]),
        goBack() {
            this.tabIndex = this.tabIndex > 0 ? this.tabIndex-1 : 0;
        },
        gugunSearch(event){
            this.gugun = "";
            this.CLEAR_GUGUN_LIST();
            this.sido = event.target.value;
            this.sidoText = event.target.innerText;
            this.getGugun(event.target.value);
        },
        dongSearch(event){
            console.log(event.target.innerText)
            this.dong = "";
            this.CLEAR_DONG_LIST();
            this.gugun = event.target.value;
            this.gugunText = event.target.innerText;
            this.getDong(event.target.value);
        },
        searchAptList(event){
            // let sidoSel = document.querySelector("#sido");
            // let gugunSel = document.querySelector("#gugun");
            // let dongSel = document.querySelector("#dong");

            // 서버로 보낼 데이터
            this.CLEAR_APT_LIST();
            this.dong = event.target.value;
            this.dongText = event.target.innerText;
            let param = {
                addr: this.dong,
            }
            this.addr =
                this.sidoText +
                " " +
                this.gugunText +
                " " +
                this.dongText;
            this.getHouseList(param).then(()=>{
                this.tabIndex = 1;
            });
        },
        InterestArea(event) {
            const dongCode = event.target.dataset.code;
            if(dongCode == ""){
                alert("지역을 선택 후 다시 시도해주세요.");
                return;
            }
            event.target.classList.toggle('fa-solid')
            event.target.classList.toggle('fa-regular')
            if(event.target.dataset.contains("bi-star-fill"))
            http.post(`/area/like/${dongCode}`, {}, {withCredentials: true})
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("관심지역 등록에 성공했습니다.");
                    
                    break;
                case 403:
                    //HttpStatus.FORBIDDEN
                    alert("로그인이 필요합니다.");
                    this.$router.push("/user/login");
                    break;
                case 406:
                    //HttpStatus.NOT_ACCEPTABLE
                    alert("관심지역 등록에 실패했습니다. 다시 시도해주세요");
                    break;
                case 500:
                    //HttpStatus.INTERNAL_SERVER_ERROR
                    alert("서버와 통신중 에러가 발생했습니다.");
                    this.$router.push("/");
                    break;
                }
            })
        },
        InterestApart(event){
            const dealNo = event.target.dataset.no;
            console.log(dealNo)
            if(dealNo == undefined) {
                alert("잠시후 시도해주세요")
                return;
            }
            if(event.target.classList.contains("bi-star-fill")){
                // DELETE
                http.delete(`/apart/like/${dealNo}`, {withCredentials: true})
                .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("관심매물 삭제에 성공했습니다.");
                    break;
                case 403:
                    //HttpStatus.FORBIDDEN
                    alert("로그인이 필요합니다.");
                    this.$router.push("/user/login");
                    break;
                case 406:
                    //HttpStatus.NOT_ACCEPTABLE
                    alert("관심매물 삭제에 실패했습니다. 다시 시도해주세요");
                    break;
                case 500:
                    //HttpStatus.INTERNAL_SERVER_ERROR
                    alert("서버와 통신중 에러가 발생했습니다.");
                    this.$router.push("/");
                    break;
                }
            })
            } else{
                // POST
                http.post(`/apart/like/${dealNo}`, {}, {withCredentials: true})
                .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("관심매물 등록에 성공했습니다.");
                    this.isLikedArea = true;
                    break;
                case 403:
                    //HttpStatus.FORBIDDEN
                    alert("로그인이 필요합니다.");
                    this.$router.push("/user/login");
                    break;
                case 406:
                    //HttpStatus.NOT_ACCEPTABLE
                    alert("관심매물 등록에 실패했습니다. 다시 시도해주세요");
                    break;
                case 500:
                    //HttpStatus.INTERNAL_SERVER_ERROR
                    alert("서버와 통신중 에러가 발생했습니다.");
                    this.$router.push("/");
                    break;
                }
            })
            }
        }
    }   
}
</script>

<style scoped>
button:has(+ .show) {
    background-color: #0d6efd;
    color: white;
}
.back-button {
    position: absolute;
    top:10px;
    right:20px;
}
</style>