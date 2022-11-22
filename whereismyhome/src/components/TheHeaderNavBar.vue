<template>
    <header id="header" class="fixed-top">
        <b-container class="d-flex align-items-center justify-content-between">
            <h1 class="logo">
            <b-navbar-brand href="/index">Homes</b-navbar-brand>
            </h1>
            <!-- Uncomment below if you prefer to use an image logo -->
            <!-- <a href="index.html" class="logo"><img src="assets/img/logo.png" alt="" class="img-fluid"></a>-->

            <b-navbar id="navbar" class="w-100">
                <b-navbar-nav>
                    <b-nav-item :to="{ name: 'index'}">Home</b-nav-item>
                    <b-nav-item :to="{ name: 'index'}">News</b-nav-item>
                    <b-nav-item :to="{ name: 'qna'}">Qna</b-nav-item>
                    <b-nav-item :to="{ name: 'board'}">Board</b-nav-item>
                    <b-nav-item :to="{ name: 'apart'}" v-if="isLogin">Find</b-nav-item>
                </b-navbar-nav>
                <b-navbar-nav class="ms-auto" v-if="!isLogin">
                    <b-nav-item :to="{ name: 'join'}">SignUp</b-nav-item>
                    <b-nav-item :to="{ name: 'login'}">Login</b-nav-item>
                </b-navbar-nav>
                <b-navbar-nav class="ms-auto" v-else>
                    <b-nav-item :to="{ name: 'mypage'}">MyPage</b-nav-item>
                    <b-nav-item href="#" class="nav-link scrollto" @click="doLogout()">Logout</b-nav-item >
                    <b-nav-text style="color: #e43c5c; font-weight: 700">{{userInfo.userId}}({{userInfo.userName}})</b-nav-text>
                </b-navbar-nav>
                <i class="bi bi-list mobile-nav-toggle navbar-right"></i>
            </b-navbar>
            <!-- .navbar -->
        </b-container>
    </header>
</template>

<script>
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
        doLogout() {
            this.$store.dispatch('logout').then((status)=>{
                switch(status){
                    case 500:
                        //INTERNAL_SERVER_ERROR
                        alert('로그아웃 도중 에러가 발생했습니다.');
                        break;
                }
                if(this.$route.name != "index")
                    this.$router.push('/');
            })
        }  
    },
};
</script>

<style lang="scss" scoped>

</style>