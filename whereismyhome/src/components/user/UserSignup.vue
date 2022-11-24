<template>
    <div>
        <section id="cta" class="cta">
            <div class="container">
                <div class="text-md-start">
                    <h3>환영합니다</h3>

                    <p>
                        저희 웹 사이트는 고객님께 최고의 부동산 정보 제공서비스를 시행하고 있습니다. <br>HOMES를 통해 고객님의
                        보금자리를 알아보세요.
                    </p>
                    <span style="color: white;">기존 회원이신가요?</span> <br> <a
                        class="cta-btn" href="/user/login">로그인 하기 </a>
                </div>
            </div>
        </section>
        <!-- End Cta Section -->
        <div id="main" class="container d-flex flex-column vh-100">
            <section id="contact " class="contact d-flex flex-column"
                style="height: 100%; justify-content: center;">
                <div class="container">
                    <div class="section-title">
                        <h3>
                            회원가입<br />
                            <span>Where Is My Home</span>
                        </h3>
                    </div>
                </div>
                <div class="row mt-4 ">
                    <b-form @submit="signUp">
                        <div class="col-lg-8 mt-5 mt-lg-0 w-65 p-3" style="margin: 0 auto;">
                            <div class="row">
                                <div class="col-md-6 form-group">
                                    <input type="text" v-model="ID" :class="isIdValid == true ? 'form-control is-valid' : 'form-control is-invalid'" id="ID"
                                        placeholder="Your ID" @focusout="idCheck" required />
                                    <div class="valid-feedback">
                                        사용할 수 있는 ID입니다.
                                    </div>
                                    <div id="validationFeedback" class="invalid-feedback">
                                        {{isIdValid}}
                                    </div>
                                </div>
                                <div class="col-md-6 form-group mt-3 mt-md-0">
                                    <input type="Password" :class="passwordCheck == true ? 'form-control is-valid' : 'form-control is-invalid'" v-model="Password"
                                    id="Password" placeholder="Your Password" required />
                                    <div class="valid-feedback">
                                        사용할 수 있는 패스워드 입니다.
                                    </div>
                                    <div id="validationFeedback" class="invalid-feedback">
                                        {{passwordCheck}}
                                    </div>
                                </div>
                            </div>
                            <div class="form-group mt-3">
                                <input type="text" class="form-control" v-model="Name" id="Name"
                                placeholder="Your Name" required />
                            </div>
                            <div class="form-group mt-3">
                                <input type="text" :class="emailCheck == true ? 'form-control is-valid' : 'form-control is-invalid'" v-model="Address"
                                    id="Address" placeholder="Your Address" required />
                                <div class="valid-feedback">
                                    사용할 수 있는 이메일 입니다.
                                </div>
                                <div id="validationFeedback" class="invalid-feedback">
                                    {{emailCheck}}
                                </div>
                            </div>
                            <div class="form-group mt-3">
                                <input type="text" :class="phoneCheck == true ? 'form-control is-valid' : 'form-control is-invalid'" v-model="Phone" id="Phone"
                                    placeholder="Your Phone Number" required />
                                <div class="valid-feedback">
                                </div>
                                <div id="validationFeedback" class="invalid-feedback">
                                    {{phoneCheck}}
                                </div>
                            </div>

                            <div class="text-center mt-3">
                                <button class="btn btn-outline-primary" type="submit">SignUp</button>
                            </div>
                        </div>
                    </b-form>
                </div>
            </section>
        </div>
    </div>
</template>

<script>
import http from '@/util/http';

export default {
    name: 'UserSignup',

    data() {
        return {
            ID:"",
            Password:"",
            Name:"",
            Address:"",
            Phone:"",
            isIdValid: "",
        };
    },

    mounted() {
        
    },

    methods: {
        idCheck() {
            if(this.ID.length > 16){
                this.isIdValid = "아이디의 길이는 16자 이하여야 합니다.";
                return;
            }
            http.get("/user/idcheck", { params:{
                id: this.ID
            }}).then(({status})=>{
                switch(status){
                    case 200:
                        // HttpStatus.OK
                        this.isIdValid = true;
                        break;
                    case 500:
                        //HttpStatus.INTERNAL_SERVER_ERROR
                        alert("서버와 통신중 에러가 발생했습니다.");
                        this.$router.push("/");
                        break;
                }
            }).catch(({response})=>{
                switch(response.status){
                    case 409:
                        // HttpStatus.Conflict
                        this.isIdValid = "중복되는 ID 입니다.";
                        break;
                    case 500:
                        //HttpStatus.INTERNAL_SERVER_ERROR
                        alert("서버와 통신중 에러가 발생했습니다.");
                        this.$router.push("/");
                        break;
                }
            })
        },
        signUp(event) {
            event.preventDefault();

            const options = {
                params: {
                    ID: this.ID,
                    Password: this.Password,
                    Name: this.Name,
                    Address: this.Address,
                    Phone: this.Phone
                }
            }
            if(this.isIdValid != true || this.passwordCheck != true || this.emailCheck != true || this.phoneCheck != true){
                alert("조건을 만족하지 않는 문항이 있습니다. 다시 시도해주세요.");
                return;
            }
            http.post("/user/join", {}, options)
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("회원 가입에 성공했습니다.");
                    this.$router.push("/user/login");
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
    computed: {
        passwordCheck() {
            const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})/;
            return (pattern.test(this.Password) && this.Password.length <= 16) || "비밀번호는 8자 이상, 16자 이하, 영문 대, 소문자 및 특수기호를 조합하여야 합니다.";
        },
        emailCheck() {
            const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return (this.Address.length < 20 && pattern.test(this.Address)) || '올바르지 않은 e-mail 입니다.'
        },
        phoneCheck() {
            const pattern = /^[0-9]{3}-[0-9]{4}-[0-9]{4}$/;
            return ( (this.Phone.length < 20 && pattern.test(this.Phone)) || "올바른 휴대폰 번호를 입력해 주세요.");
        },
    }
};
</script>

<style lang="scss" scoped>

</style>