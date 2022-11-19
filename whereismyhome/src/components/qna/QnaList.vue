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
                                    <option value="userid">작성자</option>
                                </select>
                                <div class="input-group input-group-sm">
                                    <input type="text" class="form-control" name="word"
                                        placeholder="검색어..." />
                                    <button id="btn-search" class="btn btn-dark" type="button">검색</button>
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
                <div class="row">
                    <ul class="pagination justify-content-center">
                        <li class="page-item"><a class="page-link" href="#">이전</a></li>
                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                        <li class="page-item active"><a class="page-link" href="#">2</a>
                        </li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item"><a class="page-link" href="#">4</a></li>
                        <li class="page-item"><a class="page-link" href="#">5</a></li>
                        <li class="page-item"><a class="page-link" href="#">다음</a></li>
                    </ul>
                </div>
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
            naeyong: "이게바로글내용인데니가뭘안다고지껄여?",
            visible: false,
            qnaList: []
        };
    },
    computed:{
        ...mapState(["user"]),
        ...mapGetters(["isLogin"])
    },
    mounted() {
        this.getQnaList();
    },
    
    methods: {
        getQnaList() {
            http.get("/qna/")
            .then(({data, status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    this.qnaList = data;
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
        }
    },
};
</script>

<style lang="scss" scoped>

</style>