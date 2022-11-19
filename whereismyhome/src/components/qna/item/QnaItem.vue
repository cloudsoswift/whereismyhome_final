<template>
    <div class="card my-2 mx-2">
        <div class="card-body">
            <h5 class="card-title text-center">{{subject}}</h5>
            <h6 class="card-subtitle mb-2 text-muted text-end">{{userId}}</h6>
            <h6 class="card-subtitle mb-2 text-muted text-end">{{registerTime}}</h6>
            <div v-if="visible">
                <div class="text-center card p-3">
                    <p class="card-text">{{content}}</p>
                </div>
                <div class="d-flex justify-content-end align-items-center my-2" v-if="userId == user.userId">
                    <div class="d-flex flex-row">
                        <button class="btn btn-outline-success mx-2" @click="modifyQna">수정</button>
                        <button class="btn btn-outline-danger" @click="deleteQna">삭제</button>
                    </div>
                </div>
                <qna-comment-input-item v-if="isLogin" :qnano="qnano"></qna-comment-input-item>
                <div v-for="(comment, index) in comments" :key="index">
                    <hr>
                    <qna-comment-item v-bind="comment"></qna-comment-item>
                </div>
            </div>
            <div class="text-center">
                <a href="#" class="card-link" @click="loadQna"><i :class="visible ? 'bi bi-caret-up-fill' : 'bi bi-caret-down-fill'"></i></a>
            </div>
        </div>
    </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex';
import QnaCommentInputItem from '@/components/qna/item/QnaCommentInputItem.vue';
import QnaCommentItem from './QnaCommentItem.vue';
import http from '@/util/http';
export default {
  components: { QnaCommentItem, QnaCommentInputItem },
    name:"QnaItem",
    data() {
        return {
            visible: false,
            comments: [],
        };
    },
    props:['content','qnano', 'registerTime', 'subject', 'userId'],
    computed:{
        ...mapGetters(['isLogin']),
        ...mapState(['user'])
    },
    methods:{
        loadQna() {
            if(this.visible){
                this.visible = !this.visible;
            } else{
                http.get(`/qna/${this.qnano}/comment`)
                .then(({status, data})=>{
                    switch(status){
                        case 200:
                        // HttpStatus.OK
                        this.comments = data;
                        this.visible = true;
                        break;
                    case 406:
                        //HttpStatus.NOT_ACCEPTABLE
                        alert("댓글 로딩에 실패했습니다. 다시 시도해주세요");
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
        modifyQna(){
            this.$router.push({
                name: 'qnamodify',
                params:{
                    id: this.qnano,
                    qna: {
                        'qnano':this.qnano, 
                        'userId': this.userId,  
                        'subject': this.subject, 
                        'content': this.content,
                        'registerTime': this.registerTime
                    }
                }
            })
        },
        deleteQna(){
            http.delete(`/qna/${this.qnano}`)
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("글 삭제에 성공했습니다.");
                    this.$router.go(); // 새로고침
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
            })
        }
    }
}
</script>

<style>

</style>