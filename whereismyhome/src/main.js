import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import "@/util/fontAwesomeIcon.js"; // fontAwesomeIcon.js 불러옴
import "@/util/bootstrapvue.js";

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
