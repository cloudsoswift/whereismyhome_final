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
                        style="resize: none;" @keyup.enter="registerComment"></textarea>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-outline-secondary btn-lg w-100 h-100" @click="registerComment">등록</button>
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
import { mapState } from 'vuex';
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
        ...mapState(['user']),
    },
    mounted() {
        
    },

    methods: {
        modifyComment(){
            this.isModify = !this.isModify;
            this.commentInput = this.content;
        },
        registerComment(){
            http.put(`/qna/modify/${this.qnaNo}/${this.commentNo}`,{content: this.commentInput})
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("댓글 수정에 성공했습니다.");
                    this.$router.go(); //새로고침
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
            })
        },
        deleteComment(){
            http.delete(`/qna/${this.qnaNo}/${this.commentNo}`)
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("댓글 삭제에 성공했습니다.");
                    this.$router.go(); //새로고침
                    break;
                case 406:
                    //HttpStatus.NOT_ACCEPTABLE
                    alert("댓글 삭제에 실패했습니다. 다시 시도해주세요");
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