<template>
    <div>
        <section id="contact" class="contact mt-5">
		<div class="container">
			<div class="section-title">
				<h3>
					<span>마이페이지</span>
				</h3>
			</div>

			<div class="row mt-5">
				<div class="col-lg-4">
					<div class="info">
						<div class="address">
							<i class="bi bi-envelope"></i>
							<h4>회원 정보 수정하기</h4>
							<p>아이디: {{user.userId }}</p>
						</div>
							<i class="bi bi-envelope"></i>
							<h4 class="d-inline ps-3">회원 탈퇴하기</h4>
							<button class="btn btn-secondary d-inline" id="deleteBtn" @click="deleteUser">삭제</button>
					</div>
				</div>

				<div class="col-lg-8 mt-5 mt-lg-0 w-65 p-3" style="margin: 0 auto;">
					<div class="row">
						<div class=" form-group mt-3 ">
							<label for="Password">비밀번호</label>
							<input type="Password"
								class="form-control" name="Password" id="userPassword"
								placeholder="Your Password" required
								v-model="user.userPassword" />
						</div>
					</div>
					<div class="form-group mt-3">
						<label for="userName">이름</label><input type="text"
							class="form-control" name="Name" id="Name"
							placeholder="Your Name" required v-model="user.userName" />
					</div>
					<div class="form-group mt-3">
						<label for="userAddress">주소</label><input type="text"
							class="form-control" name="Address" id="Address"
							placeholder="Your Address" required v-model="user.userAddress" />
					</div>
					<div class="form-group mt-3">
						<label for="userPhone">휴대폰번호</label><input type="text"
							class="form-control" name="Phone" id="Phone"
							placeholder="Your Phone Number" required
							v-model="user.userPhone" />
					</div>
					<div class="text-center mt-3">
						<button id="modifyBtn" class="btn btn-danger" @click="modifyUser">수정하기</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section id="contact" class="contact mt-5">
		<div class="container">
			<div class="section-title">
				<h3>
					<span>관심지역 / 매물 정보</span>
				</h3>
			</div>

			<div class="row">
				<div class="col-lg-4">
					<div class="info">
						<div class="email">
							<i class="bi bi-geo-alt"></i>
							<h4>관심 지역</h4>
						</div>
					</div>
				</div>

				<table id="interTable" class="table table-hover text-center">
					<thead>
						<th>시도</th>
						<th>구군</th>
					</thead>
					<tbody id="interRegion">
                        <tr v-for="(item, index) in interestArea" :key="index">
                            <td>{{item.sidoname}}</td>
                            <td>{{item.gugunName}}</td>
                        </tr>
					</tbody>
				</table>
			</div>

			<div class="row mt-5">
				<div class="col-lg-4">
					<div class="info">
						<div class="email">
							<i class="bi bi-geo-alt"></i>
							<h4>관심 매물</h4>
						</div>
					</div>
				</div>

				<table class="table table-hover text-center">
					<thead>
						<tr>
							<th>아파트이름</th>
							<th>면적</th>
							<th>층</th>
							<th>거래금액</th>
							<th>거래날짜</th>
						</tr>
					</thead>
					<tbody id="interApt">
                        <tr v-for="(item, index) in interestApart" :key="index">
                            <td>{{item.apartmentName}}</td>
                            <td>{{item.area}} m<sup>2</sup></td>
                            <td>{{item.floor}}층</td>
                            <td>{{item.dealAmount}}원</td>
                            <td>{{item.date}}</td>
                        </tr>
					</tbody>
				</table>
			</div>
		</div>
	</section>
    </div>
</template>

<script>
import http from '@/util/http';
import { mapState } from 'vuex';
export default {

    name: 'UserMyPage',

    data() {
        return {
            user:"",
            list:[],
        };
    },
    computed:{
        ...mapState(["user","tokens"]),
        interestArea() {
            return this.list.filter((data)=>{
                return data.sidoname != null
            })
        },
        interestApart(){
            return this.list.filter((data)=>{
                return data.apartmentName != null
            })
        }
    },
    created() {
        http.get("/user/mypage",{
            headers: {
            "access-token": this.tokens.accessToken,
          }
        })
        // .then((response)=>console.log(response));
        .then(({data, status})=>{
            switch(status){
                case 200:
                    // HttpStatus.OK
                    this.list = data.list;
                    this.user = data.user;
                    break;
                case 403:
                    //HttpStatus.FORBIDDEN
                    alert("로그인이 필요합니다.");
                    this.$router.push("/user/login");
                    break;
                case 500:
                    //HttpStatus.INTERNAL_SERVER_ERROR
                    alert("서버와 통신중 에러가 발생했습니다.");
                    this.$router.push("/");
                    break;
            }
        })
    },
    mounted() {
        
    },

    methods: {
        modifyUser() {
            http.put("/user/", {
                Password: this.user.userPassword,
                Name: this.user.userName,
                Address: this.user.userAddress,
                Phone: this.user.userPhone,
            },{withCredentials: true})
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("회원정보 수정에 성공했습니다.");
                    this.$router.go(); // 새로고침
                    break;
                case 401:
                    //HttpStatus.UNAUTHORIZED
                    alert("로그인이 만료되었습니다.");
                    this.$router.push("/user/login");
                    break;
                case 406:
                    //HttpStatus.NOT_ACCEPTABLE
                    alert("회원정보 수정에 실패했습니다. 다시 시도해주세요");
                    break;
                case 500:
                    //HttpStatus.INTERNAL_SERVER_ERROR
                    alert("서버와 통신중 에러가 발생했습니다.");
                    this.$router.push("/");
                    break;
                }
            }
            )
        },
        deleteUser() {
            http.delete("/user/",{
            headers: {
            "access-token": this.tokens.accessToken,
          }
        })
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("회원 탈퇴에 성공했습니다. 이용해주셔서 감사합니다.");
                    this.$router.push("/");
                    this.$store.commit("CLEAR_USER");
                    this.$store.commit("CLEAR_USER_TOKENS");
                    break;
                case 403:
                    //HttpStatus.FORBIDDEN
                    alert("로그인이 만료되었습니다.");
                    this.$router.push("/user/login");
                    break;
                case 406:
                    //HttpStatus.NOT_ACCEPTABLE
                    alert("회원 탈퇴에 실패했습니다. 다시 시도해주세요");
                    break;
                case 500:
                    //HttpStatus.INTERNAL_SERVER_ERROR
                    alert("서버와 통신중 에러가 발생했습니다.");
                    this.$router.push("/");
                    break;
                }
            })
        }
    },
};
</script>

<style lang="scss" scoped>

</style>