<template>
    <div>
        <section class="min-vh-100">
            <div class="row justify-content-center mt-5">
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <mark class="bg-light">게시글 작성</mark>
                    </h2>
                </div>
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <form id="form-register" method="POST" action="">
                    <div class="mb-3">
                        <label for="subject" class="form-label">제목 : </label>
                        <input
                        type="text"
                        class="form-control"
                        id="subject"
                        v-model="subject"
                        placeholder="제목..."
                        />
                    </div>
                    <div class="mb-3">
                        <label for="content" class="form-label">내용 : </label>
                        <textarea class="form-control" id="content" v-model="content" rows="7"></textarea>
                    </div>
                    <div class="col-auto text-center">
                        <button type="button" id="btn-register" 
                        class="btn btn-outline-primary mb-3" @click="boardWrite">
                        글작성
                        </button> 
                        <button type="button" id="btn-list" class="btn btn-outline-danger mb-3" @click="$router.push({name:'boardlist'})">
                        목록으로이동...
                        </button>
                    </div>
                    </form>
                </div>
            </div>
        </section>
    </div>
</template>

<script>
import http from '@/util/http';

export default {
    name: 'BoardWrite',

    data() {
        return {
            subject:"",
            content:"",
        };
    },

    mounted() {
        
    },

    methods: {
        boardWrite(){
            const data = {
                subject: this.subject,
                content: this.content,
            }
            const options =  {
                    withCredentials: true
            };
            http.post("/board/write", data, options)
            .then(({status})=>{
                switch(status){
                    case 200:
                    // HttpStatus.OK
                    alert("공지사항 등록 성공했습니다.");
                    this.$router.push("/board/list"); // 새로고침
                    break;
                case 403:
                    //HttpStatus.FORBIDDEN
                    alert("로그인이 만료되었습니다.");
                    this.$router.push("/user/login");
                    break;
                case 406:
                    //HttpStatus.NOT_ACCEPTABLE
                    alert("게시물 등록에 실패했습니다. 다시 시도해주세요");
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