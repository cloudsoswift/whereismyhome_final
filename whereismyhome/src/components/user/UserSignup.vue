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
                    <div class="col-lg-8 mt-5 mt-lg-0 w-65 p-3" style="margin: 0 auto;">
                        <div class="row">
                            <div class="col-md-6 form-group">
                                <input type="text" v-model="ID" class="form-control" id="ID"
                                    placeholder="Your ID" required />
                            </div>
                            <div class="col-md-6 form-group mt-3 mt-md-0">
                                <input type="Password" class="form-control" v-model="Password"
                                    id="Password" placeholder="Your Password" required />
                            </div>
                        </div>
                        <div class="form-group mt-3">
                            <input type="text" class="form-control" v-model="Name" id="Name"
                                placeholder="Your Name" required />
                        </div>
                        <div class="form-group mt-3">
                            <input type="text" class="form-control" v-model="Address"
                                id="Address" placeholder="Your Address" required />
                        </div>
                        <div class="form-group mt-3">
                            <input type="text" class="form-control" v-model="Phone" id="Phone"
                                placeholder="Your Phone Number" required />
                        </div>

                        <div class="text-center mt-3">
                            <button id="btn btn-outline-primary" @click="signUp">SignUp</button>
                        </div>
                    </div>
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
        };
    },

    mounted() {
        
    },

    methods: {
        signUp() {
            const options = {
                params: {
                    ID: this.ID,
                    Password: this.Password,
                    Name: this.Name,
                    Address: this.Address,
                    Phone: this.Phone
                }
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
};
</script>

<style lang="scss" scoped>

</style>