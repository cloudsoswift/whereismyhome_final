<template>
    <div>
        <section class="min-vh-100">
            <div class="row justify-content-center mt-5">
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                        <mark class="bg-light">공지사항 게시판</mark>
                    </h2>
                </div>
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <div class="row align-self-center mb-2">
                        <div class="col-md-2 text-start">
                            <button type="button" id="btn-mv-register"
                                    class="btn btn-outline-primary btn-sm"
                                    v-if="isLogin && isAdmin"
                                    @click="$router.push({name: 'boardwrite'})">글쓰기</button>
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
                        <thead>
                            <tr class="text-center">
                                <th scope="col">글번호</th>
                                <th scope="col">제목</th>
                                <th scope="col">작성자</th>
                                <th scope="col">작성일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="text-center" v-for="(article, index) in board" :key="index">
                                <th scope="row">{{article.boardNo}}</th>
                                <td class="text-start"><router-link :to="{name: 'boardview', params:{id: article.boardNo}}"
                                    class="article-title link-dark" :data-no="article.boardNo"
                                    style="text-decoration: none">{{article.subject}}</router-link></td>
                                <td>{{article.userId}}</td>
                                <td>{{article.registerTime}}</td>
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
import { mapActions, mapGetters, mapState } from 'vuex';
export default {
    name: 'BoardList',

    data() {
        return {

        };
    },
    computed:{
        ...mapState(["user", "board"]),
        ...mapGetters(["isLogin", "isAdmin"])
    },
    mounted() {
        this.getBoardList();
    },
    
    methods: {
        ...mapActions(["getBoardList"]),
    },
};
</script>

<style lang="scss" scoped>

</style>