<template>
    <div>
        <section>
            <div class="container d-flex flex-column min-vh-100">
            <div style="height: 70px"></div>
            <h2 class="col-3 ms-1 mt-5 mb-4 d-inline">아파트 매매 정보</h2>
            <div class="row col-md-12 justify-content-center mb-2">
                <div class="form-group col-md-2">
                <select class="form-select bg-secondary text-light" id="sido" v-model="sido" @change="gugunSearch">
                    <option v-for="(item, index) in sidos" :key="index" :value="item.value">{{item.text}}</option>
                </select>
                </div>
                <div class="form-group col-md-2">
                <select class="form-select bg-secondary text-light" id="gugun" v-model="gugun" @change="dongSearch">
                    <option v-for="(item, index) in guguns" :key="index" :value="item.value">{{item.text}}</option>
                </select>
                </div>
                <div class="form-group col-md-2">
                <select class="form-select bg-secondary text-light" id="dong" v-model="dong">
                    <option v-for="(item, index) in dongs" :key="index" :value="item.value">{{item.text}}</option>
                </select>
                </div>
                <div class="form-group col-md-2">
                <select class="form-select bg-dark text-light" id="year" v-model="year">
                    <option value="">매매년도선택</option>
                </select>
                </div>
                <div class="form-group col-md-2">
                <select class="form-select bg-dark text-light" id="month" v-model="month">
                    <option value="">매매월선택</option>
                </select>
                </div>
                <div class="form-group col-sm">
                <button type="button" id="list-btn" class="btn btn-outline-primary d-inline" @click="searchAptList">
                    검색
                </button>
                </div>
                <div class="form-group col-sm">
                <button
                    id="inter-sido"
                    type="button"
                    class="btn btn-outline-secondary d-inline text-warning"
                    style="width: 40px"
                    @click="addInterestArea"
                >
                    <i id="inter-sido-i" class="bi bi-star"></i>
                </button>
                </div>
            </div>
            <!-- 검색 결과와 지도 -->
            <div id="result" class="container mt-4 mb-5">
                <div class="row w-100 m-auto">
                <div id="aptlist" class="col-4 p-0" v-if="houses.length != 0">
                    <h3>거래 정보</h3>
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
                <div class="col-12">
                    <!-- Kakao Map start -->
                    <div class="mt-3 p-0 text-center">
                        <h3>매매 위치</h3>
                    </div>
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
export default {
    components: { ApartMap },
    name: 'ApartList',
    data() {
        return {
            sido:"",
            gugun:"",
            dong:"",
            year:"",
            month:"",
            isHidden: true,
            addr:"",
            isLikedArea: false,
        };
    },
    computed: {
        ...mapState(["sidos", "guguns", "dongs", "houses"]),
    },
    created() {

    },
    mounted() {
        let date = new Date();

        this.CLEAR_SIDO_LIST();
        this.CLEAR_GUGUN_LIST();
        this.CLEAR_DONG_LIST();
        this.CLEAR_APT_LIST();
        this.getSido();
          // 년도 정보 초기화
        let yearEl = document.querySelector("#year");
        let yearOpt = `<option value="">매매년도선택</option>`;
        let year = date.getFullYear();
        for (let i = year; i > year - 20; i--) {
            yearOpt += `<option value="${i}">${i}년</option>`;
        }
        yearEl.innerHTML = yearOpt;

        document.querySelector("#year").addEventListener("change", function () {
        let month = date.getMonth() + 1;
        let monthEl = document.querySelector("#month");
        let monthOpt = `<option value="">매매월선택</option>`;
        let yearSel = document.querySelector("#year");
        let m = yearSel[yearSel.selectedIndex].value == date.getFullYear() ? month : 13;
        for (let i = 1; i < m; i++) {
            monthOpt += `<option value="${i < 10 ? "0" + i : i}">${i}월</option>`;
        }
        monthEl.innerHTML = monthOpt;
        });
    },

    methods: {
        ...mapActions(["getSido", "getGugun", "getDong", "getHouseList"]),
        ...mapMutations(["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST", "CLEAR_APT_LIST"]),
        gugunSearch(event){
            this.gugun = "";
            this.CLEAR_GUGUN_LIST();
            this.getGugun(event.target.value);
        },
        dongSearch(event){
            this.dong = "";
            this.CLEAR_DONG_LIST();
            this.getDong(event.target.value);
        },
        searchAptList(){
            let sidoSel = document.querySelector("#sido");
            let gugunSel = document.querySelector("#gugun");
            let dongSel = document.querySelector("#dong");
            let yearSel = document.querySelector("#year");
            let monthSel = document.querySelector("#month");

            // 모두 선택했는지 확인
            if (
                !sidoSel[sidoSel.selectedIndex].value ||
                !gugunSel[gugunSel.selectedIndex].value ||
                !dongSel[dongSel.selectedIndex].value ||
                !yearSel[yearSel.selectedIndex].value ||
                !monthSel[monthSel.selectedIndex].value
            ) {
                alert("모두 선택해주세요!");
                return;
            }
            // 서버로 보낼 데이터
            this.CLEAR_APT_LIST();
            let param = {
                addr: this.dong,
                year: this.year,
                month: this.month,
            }
            this.addr =
                sidoSel[sidoSel.selectedIndex].textContent +
                " " +
                gugunSel[gugunSel.selectedIndex].textContent +
                " " +
                dongSel[dongSel.selectedIndex].textContent;
            this.getHouseList(param).then(()=>{
                this.isHidden = false;
            });
        },
        goCenter(lat, lng){
            console.log(lat, lng);
        },
        addInterestArea() {
            if(this.gugun == ""){
                alert("지역을 선택 후 다시 시도해주세요.");
                return;
            }
            http.post(`/area/like/${this.gugun}`, {}, {withCredentials: true})
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("관심지역 등록에 성공했습니다.");
                    this.isLikedArea = true;
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

<style lang="scss" scoped>

</style>