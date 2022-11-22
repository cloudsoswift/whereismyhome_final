<template>
    <div>
        <section id="cta" class="cta">
            <div class="container">
                <div class="text-md-start">
                    <h3>안녕하세요</h3>

                    <p>
                        저희 웹 사이트는 고객님께 최고의 부동산 정보 제공서비스를 시행하고 있습니다. <br>HOMES를 통해 고객님의
                        보금자리를 알아보세요.
                    </p>
                </div>
            </div>
        </section>
        <!-- End Cta Section -->
        <div id="main" class="container d-flex flex-column vh-100">
            <section id="contact" class="contact d-flex flex-column"
                style="height: 100%; justify-content: center;">
                <div class="container">
                    <div class="section-title">
                        <h3>
                            로그인<br /> <span>Where Is My Home</span>
                        </h3>
                    </div>
                </div>
                <div class="row ">
                    <div class="col-lg-8 mt-3 mt-lg-0 w-65 p-3" style="margin: 0 auto;">
                        <div class="row">
                            <div class="col-md-6 form-group">
                                <input type="text" v-model="ID" class="form-control" id="ID"
                                    placeholder="Your ID" required />
                            </div>
                            <div class="col-md-6 form-group mt-3 mt-md-0">
                                <input type="Password" v-model="Password" class="form-control"
                                    id="Password" placeholder="Your Password" required @keyup.enter="Login"/>
                            </div>
                        </div>
                        <div class="text-center mt-3">
                            <button class="btn btn-outline-primary" @click="Login">Login</button>
                        </div>
                    </div>
                </div>
            </section>
        <!-- End Contact Section -->
        </div>
    </div>
</template>

<script>
import http from "@/util/http";
export default {
    name: 'UserLogin',

    data() {
        return {
            ID: "",
            Password: "",
        };
    },

    mounted() {
        
    },

    methods: {
        Login() {
            const options = {
                params: {
                    ID: this.ID,
                    Password: this.Password,
                },
                withCredentials: true
            }
            http.post("/user/login", {}, options)
            .then(({data, status}) => {
                switch(status){
                    case 200:
                        this.$store.commit("SET_USER", { 
                            userId: data.userId,
                            userName: data.userName,
                            userPhone: data.userPhone,
                            userAddress: data.userAddress,
                            joinDate: data.joinDate
                         });
                         this.$router.push("/");
                         break;
                    case 204:
                        alert("ID 혹은 비밀번호가 틀렸습니다.");
                        break;
                    case 500:
                        alert("로그인 중 서버 오류가 발생했습니다.");
                        break;
                }
            })
        },
        Logout() {
            http.get("/user/logout")
            .then(()=>{
                this.$store.commit("CLEAR_USER");
            })
        }
    },
};
</script>

<style lang="scss" scoped>

</style>