<template>
    <div>
        <div class="row justify-content-center mb-5">
            <div class="col-lg-8 col-md-10 col-sm-12">
                <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <mark class="sky">글보기</mark>
                </h2>
            </div>
            <div class="col-lg-8 col-md-10 col-sm-12">
                <div class="row my-2 form-group">
                    <h2 class="text-secondary">글 수정
                        <input type="text" class="form-control" name="subject"
                            v-model="article.subject">
                    </h2>
                </div>
                <div class="row">
                    <div class="col-md-8 px-5">
                        <div class="clearfix align-content-center">
                            <img class="avatar me-2 float-md-start bg-light p-2"
                                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
                            <p>
                                <span class="fw-bold">{{article.userId}}</span> <br /> <span
                                    class="text-secondary fw-light"> {{article.registerTime}} </span>
                            </p>
                        </div>
                    </div>
                    <div class="col-md-4 align-self-center text-end">댓글 : 17</div>
                    <div class="divider mb-3"></div>
                    <input
                        class="form-control text-secondary border border-info rounded mx-auto"
                        style="width: 95%; height: 500px;" name="content"
                        v-model="article.content">
                    <div class="divider mt-3 mb-3"></div>
                    <div class="d-flex justify-content-end">
                        <button type="button" id="btn-mv-modify"
                            class="btn btn-outline-success mb-3 ms-1" @click="modifyArticle">글수정</button>
                        <button type="button" id="btn-delete"
                            class="btn btn-outline-danger mb-3 ms-1" @click="deleteArticle">글삭제</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import http from '@/util/http';
import { mapState, mapGetters } from "vuex";

export default {
    name: 'BoardModify',

    data() {
        return {
            article:{
                boardNo:"",
                content:"",
                registerTime:"",
                subject:"",
                userId:"",
            }
        };
    },
        computed: {
        ...mapState(['tokens']),
        ...mapGetters(['isLogin']),
    },
    created() {
        if(this.$route.params.article == undefined){
            alert("글 내용을 불러오지 못했습니다. 다시 시도해주세요.");
            this.$router.push({
                name: 'boardview',
                params:{
                    id: this.$route.params.id
                }
            })
        }
        this.article.boardNo = this.$route.params.article.boardNo
        this.article.content = this.$route.params.article.content
        this.article.registerTime = this.$route.params.article.registerTime
        this.article.subject = this.$route.params.article.subject
        this.article.userId = this.$route.params.article.userId
    },
    mounted() {
        
    },

    methods: {
        modifyArticle(){
            const article = {
                boardNo: this.article.boardNo,
                subject: this.article.subject,
                content: this.article.content
            }
            const options = {
                headers: {
                    "access-token": this.tokens.accessToken,
                }
            }
            http.put(`/board/${this.$route.params.id}/`, article, options)
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("글 수정에 성공했습니다.");
                    this.$router.push({
                        name: 'boardview',
                        params:{
                            id: this.$route.params.id
                        }
                    })
                    break;
                case 406:
                    //HttpStatus.NOT_ACCEPTABLE
                    alert("글 수정에 실패했습니다. 다시 시도해주세요");
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