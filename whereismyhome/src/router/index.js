import Vue from "vue";
import VueRouter from "vue-router";

import AppMain from "@/views/AppMain";
import AppApart from "@/views/AppApart";
import AppBoard from "@/views/AppBoard";
import AppQna from "@/views/AppQna";
import AppUser from "@/views/AppUser";

Vue.use(VueRouter);

const routes = [
  { path: "/", name: "index", component: AppMain, alias: "/index" },
  { path: "/apart", name: "apart", component: AppApart },
  {
    path: "/user",
    name: "user",
    component: AppUser,
    children: [
      {
        path: "login",
        name: "login",
        component: () => import("@/components/user/UserLogin"),
      },
      {
        path: "join",
        name: "join",
        component: () => import("@/components/user/UserSignup"),
      },
      {
        path: "mypage",
        name: "mypage",
        component: () => import("@/components/user/UserMyPage"),
      },
    ],
  },
  {
    path: "/board",
    name: "board",
    component: AppBoard,
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardlist",
        component: () => import("@/components/board/BoardList"),
        beforeEnter: (to, from, next) => {
          if(to.query.page == undefined){
            next({name: 'boardlist', query: { page: 1}})
          } else
            next()
        }
      },
      {
        path: "write",
        name: "boardwrite",
        component: () => import("@/components/board/BoardWrite"),
      },
      {
        path: "view/:id",
        name: "boardview",
        component: () => import("@/components/board/BoardView"),
      },
      {
        path: "modify/:id",
        name: "boardmodify",
        component: () => import("@/components/board/BoardModify"),
      },
    ],
  },
  {
    path: "/qna",
    name: "qna",
    component: AppQna,
    redirect: "/qna/list",
    children: [
      {
        path: "list",
        name: "qnalist",
        component: () => import("@/components/qna/QnaList"),
        beforeEnter: (to, from, next) => {
          if(to.query.page == undefined){
            next({name: 'qnalist', query: { page: 1}})
          } else
            next()
        }
      },
      {
        path: "write",
        name: "qnawrite",
        component: () => import("@/components/qna/QnaWrite"),
      },
      {
        path: "modify/:id",
        name: "qnamodify",
        component: () => import("@/components/qna/QnaModify"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
