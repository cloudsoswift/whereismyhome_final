<template>
    <div>
        <div class="card row mb-3">
            <div>
                <div class="d-flex justify-content-between align-items-center">
                    <p class="my-1">
                    {{userId}} <span class="small">- {{registerTime}}</span>
                    </p>
                </div>
                <p class="small" v-if="!isModify">
                    {{content}}
                </p>
                <div class="row" v-else>
                    <div class="col-md-8">
                        <textarea class="form-control"
                        placeholder="댓글을 남겨주세요."
                        v-model="commentInput"
                        rows="3"
                        style="resize: none;" @keyup.enter="registerModifiedComment"></textarea>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-outline-secondary btn-lg w-100 h-100" @click="registerModifiedComment">등록</button>
                    </div>
                </div>
            </div>
            <div class="d-flex justify-content-end align-items-center mb-3" v-if="userId == user.userId">
                <div class="d-flex flex-row">
                    <button class="btn btn-outline-success mx-2" @click="modifyComment">{{isModify ? '취소' : '수정'}}</button>
                    <button class="btn btn-outline-danger" @click="deleteComment">삭제</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import http from '@/util/http';
import { mapGetters, mapState } from 'vuex';
export default {
    name: 'QnaCommentItem',
    props:['qnaNo', 'commentNo','content', 'registerTime', 'userId'],
    data() {
        return {
            isModify: false,
            commentInput: "",
        };
    },
    computed: {
        ...mapState(['user', 'tokens']),
        ...mapGetters(['isLogin']),
    },
    mounted() {
        
    },

    methods: {
        modifyComment(){
            this.isModify = !this.isModify;
            this.commentInput = this.content;
        },
        registerModifiedComment(){
            const options = {
                headers: {
                    "access-token": this.tokens.accessToken,
                }
            }
            http.put(`/qna/${this.qnaNo}/comment/${this.commentNo}`,{content: this.commentInput}, options)
            .then(({status})=>{
                switch(status){
                    case 200:
                        // HttpStatus.OK
                        alert("댓글 수정에 성공했습니다.");
                        this.$emit('commentChanged'); //새로고침
                        this.isModify = !this.isModify;
                        break;
                    case 406:
                        //HttpStatus.NOT_ACCEPTABLE
                        alert("댓글 수정에 실패했습니다. 다시 시도해주세요");
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
        deleteComment(){
            const options = {
                headers: {
                    "access-token": this.tokens.accessToken,
                }
            }
            http.delete(`/qna/${this.qnaNo}/comment/${this.commentNo}`, options)
            .then(({status})=>{
                switch(status){
                    case 200:
                        // HttpStatus.OK
                        alert("댓글 삭제에 성공했습니다.");
                        this.$emit('commentChanged'); //새로고침
                        break;
                    case 406:
                        //HttpStatus.NOT_ACCEPTABLE
                        alert("댓글 삭제에 실패했습니다. 다시 시도해주세요.");
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