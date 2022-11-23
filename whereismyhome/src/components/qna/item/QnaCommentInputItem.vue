<template>
    <div>
        <hr>
        <div class="container mt-3">
            <div class="row">
                <div class="col-md-2">
                    <div class="card-title text-center">{{this.user.userId}}</div>
                </div>
                <div class="col-md-8">
                    <textarea class="form-control"
                    placeholder="댓글을 남겨주세요."
                    v-model="comment"
                    rows="3"
                    style="resize: none;"></textarea>
                </div>
                <div class="col-md-2">
                    <button class="btn btn-outline-secondary btn-lg w-100 h-100" @click="registerComment">등록</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import http from '@/util/http';
import { mapState, mapGetters } from 'vuex';
export default {
    name: 'QnaCommentInputItem',
    props: ['qnano'],
    data() {
        return {
            comment: "",
        };
    },
    computed: {
        ...mapState(['user','tokens']),
        ...mapGetters(['isLogin']),
    },
    mounted() {
        
    },

    methods: {
        registerComment() {
            const data = {
                content: this.comment
            }
            const options = {
                headers: {
                    "access-token": this.tokens.accessToken,
                }
            };
            http.post(`/qna/${this.qnano}/comment/`, data, options)
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    this.$router.go(); // 새로고침
                    break;
                case 403:
                    //HttpStatus.FORBIDDEN
                    alert("로그인이 만료되었습니다.");
                    this.$router.push("/user/login");
                    break;
                case 406:
                    //HttpStatus.NOT_ACCEPTABLE
                    alert("댓글 등록에 실패했습니다. 다시 시도해주세요");
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