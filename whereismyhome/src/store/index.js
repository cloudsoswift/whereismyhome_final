import Vue from "vue";
import Vuex from "vuex";
import http from "@/util/http";
import createPersistedState from "vuex-persistedstate";
import jwtParser from "@/util/jwtParser";
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    //아파트 목록용
    sidos: [],
    guguns: [],
    dongs: [],
    houses: [],
    apartCode: "",
    centerPos: {},
    //회원정보 보관용
    user: {},
    tokens: {},
    //게시판용
    board: [],
  },
  getters: {
    isLogin(state) {
      return JSON.stringify(state.tokens) != "{}";
    },
  },
  mutations: {
    // 아파트 리스트 용
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidocode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.guguncode, text: gugun.gugunName, interest_no: gugun.interest_no });
      });
    },
    SET_DONG_LIST(state, dongs) {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongcode, text: dong.dongName, interest_no: dong.interest_no });
      });
    },
    SET_HOUSE_LIST(state, houses) {
      state.houses = houses;
    },
    CLEAR_SIDO_LIST(state) {
      state.sidos = [];
    },
    CLEAR_APT_LIST(state) {
      state.houses = [];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [];
    },
    CLEAR_DONG_LIST(state) {
      state.dongs = [];
    },
    SET_APART_CODE(state, apartCode) {
      state.apartCode = apartCode;
    },
    CLEAR_APART_CODE(state) {
      state.apartCode = "";
    },
    SET_CENTER_POS(state, centerPos) {
      state.centerPos = centerPos;
    },
    // 유저
    SET_USER(state, user) {
      state.user = user;
    },
    CLEAR_USER(state) {
      state.user = {};
    },
    SET_USER_TOKENS(state, tokens) {
      state.tokens = tokens;
    },
    CLEAR_USER_TOKENS(state) {
      state.tokens = {};
    },
    // 게시판 용
    SET_BOARD_LIST(state, board) {
      state.board = board;
    },
    CLEAR_BOARD(state) {
      state.board = [];
    },
  },
  actions: {
    getSido({ commit }) {
      http
        .get(`/area/sido/1`)
        .then(({ data }) => {
          // console.log(data);
          commit("SET_SIDO_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getGugun({ commit }, regCode) {
      http
        .get(`/area/gugun/${regCode}`)
        .then(({ data }) => {
          // console.log(data);
          commit("SET_GUGUN_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getDong({ commit }, regCode) {
      http
        .get(`/area/dong/${regCode}`)
        .then(({ data }) => {
          // console.log(commit, response);
          commit("SET_DONG_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getHouseList({ commit }, input) {
      http
        .get(`/apart/list/${input.addr}`)
        .then(({ data }) => {
          console.log(data);
          commit("SET_HOUSE_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getBoardList({ commit }) {
      http
        .get("/board/")
        .then(({ data }) => {
          // console.log(commit, response);
          commit("SET_BOARD_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    login({ commit }, loginInput) {
      return http.post("/user/login", loginInput).then(({ data, status }) => {
        let userData = {};
        switch (status) {
          case 200:
            //HttpStatus.OK
            commit("SET_USER_TOKENS", {
              accessToken: data["access-token"],
              refreshToken: data["refresh-token"],
            });
            userData = JSON.parse(jwtParser(data["access-token"]));
            commit("SET_USER", {
              userId: userData.userid,
              userName: userData.username,
              userPhone: userData.userphone,
              userAddress: userData.useraddress,
            });
            break;
          case 406:
            //HttpStatus.NOT_ACCEPTABLE
            alert("ID 혹은 비밀번호가 틀렸습니다.");
            break;
          case 500:
            //HttpStatus.INTERNAL_SERVER_ERROR
            alert("로그인 중 서버 오류가 발생했습니다.");
            this.$router.push("/");
            break;
        }
        return status;
      });
    },
    logout() {
      return http
        .get("/user/logout", {
          headers: {
            "access-token": this.state.tokens.accessToken,
          },
        })
        .then((status) => {
          this.commit("CLEAR_USER");
          this.commit("CLEAR_USER_TOKENS");
          return status;
        })
        .catch((status) => {
          this.commit("CLEAR_USER");
          this.commit("CLEAR_USER_TOKENS");
          return status;
        });
    },
  },
  modules: {},
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
