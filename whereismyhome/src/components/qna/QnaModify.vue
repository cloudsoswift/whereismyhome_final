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
                            v-model="qna.subject">
                    </h2>
                </div>
                <div class="row">
                    <div class="col-md-8 px-5">
                        <div class="clearfix align-content-center">
                            <img class="avatar me-2 float-md-start bg-light p-2"
                                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
                            <p>
                                <span class="fw-bold">{{qna.userId}}</span> <br /> <span
                                    class="text-secondary fw-light"> {{qna.registerTime}} </span>
                            </p>
                        </div>
                    </div>
                    <div class="col-md-4 align-self-center text-end">댓글 : 17</div>
                    <div class="divider mb-3"></div>
                    <input
                        class="form-control text-secondary border border-info rounded mx-auto"
                        style="width: 95%; height: 500px;" name="content"
                        v-model="qna.content">
                    <div class="divider mt-3 mb-3"></div>
                    <div class="d-flex justify-content-end">
                        <button type="button" id="btn-mv-modify"
                            class="btn btn-outline-success mb-3 ms-1" @click="modifyQna">수정</button>
                        <button type="button" id="btn-delete"
                            class="btn btn-outline-danger mb-3 ms-1" @click="deleteQna">삭제</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import http from '@/util/http';

export default {
    name: 'QnaModify',

    data() {
        return {
            qna:{
                qnano:"",
                userId:"",
                subject:"",
                content:"",
                registerTime:"",
            }
        };
    },
    created() {
        if(this.$route.params.qna == undefined){
            alert("글 내용을 불러오지 못했습니다. 다시 시도해주세요.");
            this.$router.push({
                name: 'qnalist',
            })
        }
        this.qna.qnano = this.$route.params.qna.qnano
        this.qna.userId = this.$route.params.qna.userId
        this.qna.subject = this.$route.params.qna.subject
        this.qna.content = this.$route.params.qna.content
        this.qna.registerTime = this.$route.params.qna.registerTime
    },
    mounted() {
        
    },

    methods: {
        modifyQna(){
            const options = {
                withCredentials: true
            }
            http.put(`/qna/modify/${this.qna.qnano}`, this.qna, options)
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("글 수정에 성공했습니다.");
                    this.$router.push({
                        name: 'qnalist',
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
            })
        },
        deleteQna(){
            http.delete(`/qna/${this.$route.params.id}`)
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("글 삭제에 성공했습니다.");
                    this.$router.push({name: 'qnalist'});
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
    },
};
</script>

<style lang="scss" scoped>

</style>