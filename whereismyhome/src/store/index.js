import Vue from "vue";
import Vuex from "vuex";
import http from "@/util/http";
import createPersistedState from "vuex-persistedstate";
import jwtParser from "@/util/jwtParser";
import router from "@/router";
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
    pageCount: 0,
  },
  getters: {
    isLogin(state) {
      return JSON.stringify(state.tokens) != "{}";
    },
    isAdmin(state) {
      return state.user.role == 1;
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
        state.dongs.push({ value: dong.dongcode, text: dong.dongName, count: dong.count });
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
    SET_PAGE_COUNT(state, pageCount) {
      state.pageCount = pageCount;
    },
    CLEAR_PAGE_COUNT(state) {
      state.pageCount = 0;
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
    getGugun({ commit, dispatch }, regCode) {
      http
        .get(`/area/gugun/${regCode}`, {
          headers: {
            "access-token": this.state.tokens.accessToken,
          },
        })
        .then(({ data }) => {
          // console.log(data);
          commit("SET_GUGUN_LIST", data);
        })
        .catch(async ({ response }) => {
          switch (response.status) {
            case 401:
              //HttpStatus.UNAUTHORIZED
              await dispatch("tokenRefresh");
              if (!this.isLogin) {
                alert("로그인이 만료되었습니다.");
                router.push("/user/login");
              } else {
                alert("토큰을 갱신했습니다. 다시 시도해주세요");
              }
              break;
          }
        });
    },
    getDong({ commit }, regCode) {
      http
        .get(`/area/dong/${regCode}`)
        .then(({ data }) => {
          console.log(data);
          commit("SET_DONG_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getHouseList({ commit, getters, state, dispatch }, input) {
      let options = {};
      if (getters.isLogin == true) {
        options = {
          headers: {
            "access-token": state.tokens.accessToken,
          },
        };
      }
      http
        .get(`/apart/list/${input.addr}`, options)
        .then(({ data }) => {
          console.log(data);
          commit("SET_HOUSE_LIST", data);
        })
        .catch(async ({ response }) => {
          switch (response.status) {
            case 401:
              //HttpStatus.UNAUTHORIZED
              await dispatch("tokenRefresh");
              if (!getters.isLogin) {
                alert("로그인이 만료되었습니다.");
                router.push("/user/login");
              } else {
                dispatch("getHouseList", { commit: commit, getters: getters, state: state, dispatch: dispatch }, input);
              }
              break;
          }
        });
    },
    getBoardList({ commit }, params) {
      http
        .get(`/board/page/${params[0]}`, { params: params[1] })
        .then(({ data }) => {
          // console.log(commit, response);
          commit("SET_BOARD_LIST", data.list);
          commit("SET_PAGE_COUNT", data.pgCount);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 유저 관련
    login({ commit }, loginInput) {
      return http
        .post("/user/login", loginInput)
        .then(({ data, status }) => {
          let userData = {};
          switch (status) {
            case 200:
              //HttpStatus.OK
              commit("SET_USER_TOKENS", {
                accessToken: data["access-token"],
                refreshToken: data["refresh-token"],
              });
              userData = JSON.parse(jwtParser(data["access-token"]));
              console.log(userData);
              commit("SET_USER", {
                userId: userData.userid,
                userName: userData.username,
                userPhone: userData.userphone,
                userAddress: userData.useraddress,
                role: userData.role,
                joinDate: userData.joindate,
              });
              break;
          }
          return status;
        })
        .catch(({ response }) => {
          return response.status;
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
    tokenRefresh({ commit }) {
      return http
        .post(
          "/user/refresh",
          {},
          {
            headers: {
              "refresh-token": this.state.tokens.refreshToken,
            },
          }
        )
        .then(({ status, data }) => {
          switch (status) {
            case 200:
              //HttpStatus.OK
              commit("SET_USER_TOKENS", {
                accessToken: data["access-token"],
                refreshToken: this.state.tokens.refreshToken,
              });
              break;
            case 401:
              //HttpStatus.UNAUTHORIZED
              //Refresh Token도 만료 됨.
              commit("CLEAR_USER_TOKENS");
              commit("CLEAR_USER");
              break;
            case 500:
              //HttpStatus.INTERNAL_SERVER_ERROR
              break;
          }
          return status;
        })
        .catch(({ response }) => {
          switch (response.status) {
            case 401:
              //HttpStatus.UNAUTHORIZED
              //Refresh Token도 만료 됨.
              commit("CLEAR_USER_TOKENS");
              commit("CLEAR_USER");
              break;
          }
          return response.status;
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
