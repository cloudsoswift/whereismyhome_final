<template>
    <div>
        <section class="min-vh-100">
            <div class="row justify-content-center mt-5">
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                        <mark class="bg-light">QnA</mark>
                    </h2>
                </div>
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <div class="row align-self-center mb-2">
                        <div class="col-md-2 text-start">
                            <button type="button" id="btn-mv-register"
                                    class="btn btn-outline-primary btn-sm"
                                    v-if="isLogin"
                                    @click="$router.push({name: 'qnawrite'})">글쓰기</button>
                        </div>
                        <div class="col-md-7 offset-3">
                            <form class="d-flex" id="form-search" action="">
                                <select class="form-select form-select-sm ms-5 me-1 w-50"
                                    name="key" aria-label="검색조건">
                                    <option value="" selected>검색조건</option>
                                    <option value="subject">제목</option>
                                    <option value="content">내용</option>
                                </select>
                                <div class="input-group input-group-sm">
                                    <input type="text" class="form-control" v-model="word"
                                        placeholder="검색어..." @keyup.enter="search"/>
                                    <button id="btn-search" class="btn btn-dark" type="button" @click="search">검색</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <table class="table table-hover">
                        <tbody>
                            <tr v-for="(qna, index) in qnaList" :key="index">
                                <qna-item v-bind="qna"></qna-item>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <b-row class="justify-content-md-center">
                    <b-col lg="8" md="10">
                        <b-pagination-nav v-model="currentPage" :link-gen="linkGen" :number-of-pages="pageCount" use-router align="center"></b-pagination-nav>
                    </b-col>
                </b-row>
            </div>
        </section>
    </div>
</template>

<script>
import http from '@/util/http';
import { mapGetters, mapState } from 'vuex';
import QnaItem from './item/QnaItem.vue';
export default {
    components: { QnaItem },
    name: 'QnaList',

    data() {
        return {
            visible: false,
            qnaList: [],
            key: "",
            word: "",
            currentPage: 1,
            pageCount: 1,
        };
    },
    computed:{
        ...mapState(["user"]),
        ...mapGetters(["isLogin"])
    },
    mounted() {
        this.key = this.$route.query.key != undefined ? this.$route.query.key : "";
        this.word = this.$route.query.word != undefined ? this.$route.query.word : "";
        this.currentPage = this.$route.query.page;
        this.getQnaList(this.$route.query.page, {
            spp: 2,
            key: this.key,
            word: this.word,
        });
    },
    
    methods: {
        linkGen(pageNum) {
            return `?page=${pageNum}&key=${this.key}&word=${this.word}`
        },
        getQnaList(page, params) {
            http.get(`/qna/page/${page}`, {
                params: params
            })
            .then(({data, status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    this.qnaList = data.list;
                    this.pageCount = data.pgCount;
                    break;
                case 406:
                    //HttpStatus.NOT_ACCEPTABLE
                    alert("QNA 목록 불러오기에 실패했습니다. 다시 시도해주세요");
                    break;
                case 500:
                    //HttpStatus.INTERNAL_SERVER_ERROR
                    alert("서버와 통신중 에러가 발생했습니다.");
                    this.$router.push("/");
                    break;
                }   
            })
        },
        search() {
            this.$router.push({
                name: 'qnalist',
                query: {
                    page:1,
                    spp:2,
                    key:this.key,
                    word:this.word,
                }
            }).catch(()=>{});
        }
    },
    watch:{
        $route(to){
            if(to.query.page == undefined){
                this.$router.push({
                    name: 'qnalist',
                    query: {
                        page:1,
                    }
                }).catch(()=>{});
                return;
            }
            this.getQnaList(to.query.page, {
                spp: 2,
                key: this.key,
                word: this.word,
            });
            this.currentPage = to.query.page;
        }
    }
};
</script>

<style lang="scss" scoped>

</style>