import Vue from "vue";
import Vuex from "vuex";
import http from "@/util/http";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    //아파트 목록용
    sidos: [],
    guguns: [],
    dongs: [],
    houses: [],
    //회원정보 보관용
    user: {},
    //게시판용
    board: [],
  },
  getters: {
    isLogin(state) {
      return JSON.stringify(state.user) != "{}";
    },
  },
  mutations: {
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
    SET_USER(state, user) {
      state.user = user;
    },
    CLEAR_USER(state) {
      state.user = {};
    },
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
        .get(`/apart/list/${input.addr}/${input.year}/${input.month}`)
        .then(({ data }) => {
          commit("SET_HOUSE_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    detailHouse({ commit }, house) {
      // 나중에 house.일련번호를 이용하여 API 호출
      // console.log(commit, house);
      commit("SET_DETAIL_HOUSE", house);
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
  },
  modules: {},
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
