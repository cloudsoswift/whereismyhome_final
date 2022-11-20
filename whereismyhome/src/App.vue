<template>
  <div id="app">
    <the-header-nav-bar></the-header-nav-bar>
    <router-view/>
    <the-footer-bar></the-footer-bar>
  </div>
</template>

<script>
import TheFooterBar from "./components/TheFooterBar.vue"
import TheHeaderNavBar from "./components/TheHeaderNavBar.vue"
export default{
    name: "App",
    components: {
        TheHeaderNavBar,
        TheFooterBar,
  },
}
</script>

<style>
@import url("https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css");
@import url("https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i");

@import url("https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap");
@import url("@/assets/vendor/bootstrap/css/bootstrap.min.css");
@import url("@/assets/vendor/bootstrap-icons/bootstrap-icons.css");
@import url("@/assets/vendor/boxicons/css/boxicons.min.css");
@import url("@/assets/vendor/glightbox/css/glightbox.min.css");
@import url("https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css");
@import url("@/assets/vendor/remixicon/remixicon.css");
@import url("@/assets/vendor/swiper/swiper-bundle.min.css");
@import url("@/assets/css/style.css");
header {
	background-color: black;
}
</style>
