<template>
    <header id="header" class="fixed-top">
        <div class="container d-flex align-items-center justify-content-between">
            <h1 class="logo">
            <a href="/index">Homes</a>
            </h1>
            <!-- Uncomment below if you prefer to use an image logo -->
            <!-- <a href="index.html" class="logo"><img src="assets/img/logo.png" alt="" class="img-fluid"></a>-->

            <nav id="navbar" class="navbar w-100">
            <ul class="navbar-left">
                <li><router-link class="nav-link scrollto" :to="{ name: 'index'}">Home</router-link></li>
                <li><router-link class="nav-link scrollto" :to="{ name: 'index'}">News</router-link></li>
                <li><router-link class="nav-link scrollto" :to="{ name: 'qna'}">Qna</router-link></li>
                <li><router-link class="nav-link scrollto" :to="{ name: 'board'}">Board</router-link></li>
                <li><router-link class="nav-link scrollto" :to="{ name: 'apart'}" v-if="isLogin">Find</router-link></li>
            </ul>
            <ul class="navbar-right" v-if="!isLogin">
                <li><router-link class="nav-link scrollto" :to="{ name: 'join'}">SignUp</router-link></li>
                <li><router-link class="nav-link scrollto" :to="{ name: 'login'}">Login</router-link></li>
            </ul>
            <ul class="navbar-right" v-else>
                <li><router-link class="nav-link scrollto" :to="{ name: 'mypage'}">MyPage</router-link></li>
                <li>
                    <a href="#" class="nav-link scrollto" @click="Logout()">Logout</a>
                </li>
                <li style="color: #e43c5c; font-weight: 700">{{userInfo.userId}}({{userInfo.userName}})</li>
            </ul>
            <i class="bi bi-list mobile-nav-toggle navbar-right"></i>
            </nav>
            <!-- .navbar -->
        </div>
    </header>
</template>

<script>
import http from "@/util/http";
export default {
    name: 'TheHeaderNavBar',

    data() {
        return {
            
        };
    },

    mounted() {

    },

    computed: {
        userInfo() {
            return this.$store.state.user;
        }, 
        isLogin() {
            return this.$store.getters.isLogin;
        }
    },
    methods: {
        Logout() {
            
            http.get("/user/logout")
            .then(()=>{
                this.$store.commit("CLEAR_USER");
                this.$router.push("/");
            })
        }  
    },
};
</script>

<style lang="scss" scoped>

</style>