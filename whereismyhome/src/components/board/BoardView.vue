<template>
    <div>
        <div class="row justify-content-center mb-5">
            <div class="col-lg-8 col-md-10 col-sm-12">
                <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <mark class="sky">글보기</mark>
                </h2>
            </div>
            <div class="col-lg-8 col-md-10 col-sm-12">
                <div class="row my-2">
                    <h2 class="text-secondary px-5">{{article.subject}}</h2>
                </div>
                <div class="row">
                    <div class="col-md-8 px-5">
                        <div class="clearfix align-content-center">
                            <p>
                                <span class="fw-bold">{{article.userId}}</span> <br />
                                <span class="text-secondary fw-light"> {{article.registerTime}}</span>
                            </p>
                        </div>
                    </div>
                    <div class="divider mb-3" />
                    <div
                        class="text-secondary border border-info rounded mx-auto"
                        style="width: 95%; height: 500px"
                    >
                        {{article.content}}
                    </div>
                    <div class="divider mt-3 mb-3" />
                    <div class="d-flex justify-content-end">
                        <button type="button" id="btn-list" class="btn btn-outline-primary mb-3"
                            @click="$router.push({name: 'boardlist'})">글목록</button>
                        <span v-if="isLogin && isAdmin">
                            <button type="button" id="btn-mv-modify" class="btn btn-outline-success mb-3 ms-1"
                                @click="modifyArticle">
                                글수정
                            </button>
                            <button type="button" id="btn-delete" class="btn btn-outline-danger mb-3 ms-1"
                                @click="deleteArticle">
                                글삭제
                            </button>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import http from '@/util/http';
import { mapState, mapGetters } from 'vuex';
export default {
    name: 'BoardView',

    data() {
        return {
            article:{}
        };
    },
    computed: {
        ...mapState(["user","tokens"]),
        ...mapGetters(['isLogin', 'isAdmin']),
    },
    created() {
        http.get(`/board/${this.$route.params.id}`)
        .then(({data, status})=>{
            switch(status){
                    case 200:
                    // HttpStatus.OK
                    this.article = data;
                    break;
                case 406:
                    //HttpStatus.NOT_ACCEPTABLE
                    alert("글 정보를 불러오는데 실패했습니다. 다시 시도해주세요");
                    this.$router.go(-1);
                    break;
                case 500:
                    //HttpStatus.INTERNAL_SERVER_ERROR
                    alert("서버와 통신중 에러가 발생했습니다.");
                    this.$router.push("/");
                    break;
                }
        });
    },
    mounted() {
        
    },

    methods: {
        modifyArticle(){
            this.$router.push({
                name: 'boardmodify',
                params:{
                    article: this.article
                }
            })
        },
        deleteArticle(){
            const options = {
                headers: {
                    "access-token": this.tokens.accessToken,
                }
            }
            http.delete(`/board/${this.$route.params.id}`, options)
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("글 삭제에 성공했습니다.");
                    this.$router.push({name: 'boardlist'}); // 새로고침
                    break;
                case 406:
                    //HttpStatus.NOT_ACCEPTABLE
                    alert("글 삭제에 실패했습니다. 다시 시도해주세요");
                    break;
                case 500:
                    //HttpStatus.INTERNAL_SERVER_ERROR
                    alert("서버와 통신중 에러가 발생했습니다.");
                    this.$router.push("/");
                    break;
                }
            }).catch(async({response})=>{
                switch(response.status){
                    case 401:
                        //HttpStatus.UNAUTHORIZED
                        await this.$store.dispatch("tokenRefresh")
                        if(!this.isLogin){
                            alert("로그인이 만료되었습니다.");
                            this.$router.push("/user/login");
                        } else {
                            alert("토큰을 갱신했습니다. 다시 시도해주세요");
                        }
                        break;
                }
            })
        }
    },
};
</script>

<style lang="scss" scoped>

</style>