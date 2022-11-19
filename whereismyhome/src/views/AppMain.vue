<template>
    <div>
        <section id="hero">
            <div class="hero-container">
                <h3>Welcome to <strong>Home</strong></h3>
                <h1>어떤 집을 찾고 계세요?</h1>
                <h2>실거래가 확인하고 똑똑하게 집 찾자!</h2>
                <!-- <c:choose>
                    <c:when test="${!empty userInfo }">
                        <a href="/apart/" class="btn-get-started scrollto">Get Started</a>
                    </c:when>
                    <c:otherwise>
                        <a href="/user/login" class="btn-get-started scrollto">Get Started</a>
                    </c:otherwise>
                </c:choose> -->
            </div>
            </section>
            <!-- End Hero -->

            <main id="main">
            <!-- ======= About Section ======= -->
            <section id="info" class="about">
                <div class="container">
                <div class="section-title">
                    <h2>정보 확인하기</h2>
                    <h3>지혜롭게 <span>내 집</span> 마련하기</h3>
                    <p>지혜롭게 내 집을 마련해보세요.</p>
                </div>

                <div class="row content">
                    <div class="col-lg-6">
                    <p>
                        가용자금 확인 및 대출 계획 가용자금 확인 및 대출 계획 가용자금 확인 및 대출 계획
                    </p>
                    <ul>
                        <li>
                        <a href="#about" class="text-dark">
                            <i class="ri-check-double-line"></i>가용자금 확인 및 대출 계획
                        </a>
                        </li>
                        <li>
                        <a href="#about" class="text-dark"
                            ><i class="ri-check-double-line"></i>집 종류 및 지역 선택</a
                        >
                        </li>
                        <li>
                        <a href="#about" class="text-dark"
                            ><i class="ri-check-double-line"></i>정보 수집 &amp; 시세파악</a
                        >
                        </li>
                    </ul>
                    </div>
                    <div class="col-lg-6">
                    <p>
                        부동산 방문 & 집구경 부동산 방문 & 집구경 부동산 방문 & 집구경 부동산 방문 & 집구경
                    </p>
                    <ul>
                        <li>
                        <a href="#about" class="text-dark"
                            ><i class="ri-check-double-line"></i>부동산 방문 & 집구경</a
                        >
                        </li>
                        <li>
                        <a href="#about" class="text-dark"
                            ><i class="ri-check-double-line"></i>계약 및 잔금 치르기</a
                        >
                        </li>
                        <li>
                        <a href="#about" class="text-dark"
                            ><i class="ri-check-double-line"></i>소유권 이전등기</a
                        >
                        </li>
                    </ul>
                    </div>
                </div>
                </div>
            </section>
            </main>
            <!-- End About Section -->

            <!--- ======= notice Section ======= --->
            <section id="news" class="portfolio">
                <div class="container">
                <div class="section-title">
                    <h2>매물 확인하기</h2>
                    <h3>요즘 <span>핫한 매물</span> 확인하기</h3>
                    <p>
                    다양한 매물 확인해보세요! 다양한 매물 확인해보세요! 다양한 매물 확인해보세요! 다양한 매물 확인해보세요!
                    </p>
                </div>

                <div class="row">
                    <div class="col-lg-12 d-flex justify-content-center">
                    <ul id="portfolio-flters">
                        <li data-filter="*" class="filter-active">All</li>
                        <li data-filter=".filter-apt">아파트</li>
                        <li data-filter=".filter-house">주택</li>
                        <li data-filter=".filter-multihouse">다세대 주택</li>
                    </ul>
                    </div>
                </div>

                <div class="row portfolio-container">
                    <div class="col-lg-4 col-md-6 portfolio-item filter-apt" >
                    <img src="@/assets/img/apt/apt1.jpg" class="img-fluid" alt="" />
                    <div class="portfolio-info">
                        <h4>아파트 1</h4>
                        <p>아파트</p>
                        <a
                        href="@/assets/img/apt/apt1.jpg"
                        data-gallery="portfolioGallery"
                        class="portfolio-lightbox preview-link"
                        title="아파트 1"
                        ><i class="bx bx-plus"></i
                        ></a>
                        <a href="#" class="details-link" title="More Details"
                        ><i class="bx bx-link"></i
                        ></a>
                    </div>
                    </div>

                    <div class="col-lg-4 col-md-6 portfolio-item filter-multihouse">
                    <img src="@/assets/img/apt/multi1.jpg" class="img-fluid" alt="" />
                    <div class="portfolio-info">
                        <h4>다세대 1</h4>
                        <p>다세대</p>
                        <a
                        href="@/assets/img/apt/multi1.jpg"
                        data-gallery="portfolioGallery"
                        class="portfolio-lightbox preview-link"
                        title="다세대 1"
                        ><i class="bx bx-plus"></i
                        ></a>
                        <a href="#" class="details-link" title="More Details"
                        ><i class="bx bx-link"></i
                        ></a>
                    </div>
                    </div>

                    <div class="col-lg-4 col-md-6 portfolio-item filter-apt">
                    <img src="@/assets/img/apt/apt2.png" class="img-fluid" alt="" />
                    <div class="portfolio-info">
                        <h4>아파트 2</h4>
                        <p>아파트</p>
                        <a
                        href="@/assets/img/apt/apt2.png"
                        data-gallery="portfolioGallery"
                        class="portfolio-lightbox preview-link"
                        title="아파트 2"
                        ><i class="bx bx-plus"></i
                        ></a>
                        <a href="#" class="details-link" title="More Details"
                        ><i class="bx bx-link"></i
                        ></a>
                    </div>
                    </div>

                    <div class="col-lg-4 col-md-6 portfolio-item filter-house">
                    <img src="@/assets/img/apt/house1.png" class="img-fluid" alt="" />
                    <div class="portfolio-info">
                        <h4>주택 1</h4>
                        <p>주택</p>
                        <a
                        href="@/assets/img/apt/house1.png"
                        data-gallery="portfolioGallery"
                        class="portfolio-lightbox preview-link"
                        title="주택 1"
                        ><i class="bx bx-plus"></i
                        ></a>
                        <a href="#" class="details-link" title="More Details"
                        ><i class="bx bx-link"></i
                        ></a>
                    </div>
                    </div>

                    <div class="col-lg-4 col-md-6 portfolio-item filter-multihouse">
                    <img src="@/assets/img/apt/multi2.jpg" class="img-fluid" alt="" />
                    <div class="portfolio-info">
                        <h4>다세대 2</h4>
                        <p>다세대</p>
                        <a
                        href="@/assets/img/apt/multi2.jpg"
                        data-gallery="portfolioGallery"
                        class="portfolio-lightbox preview-link"
                        title="다세대 2"
                        ><i class="bx bx-plus"></i
                        ></a>
                        <a href="#" class="details-link" title="More Details"
                        ><i class="bx bx-link"></i
                        ></a>
                    </div>
                    </div>

                    <div class="col-lg-4 col-md-6 portfolio-item filter-apt">
                    <img src="@/assets/img/apt/apt3.jpg" class="img-fluid" alt="" />
                    <div class="portfolio-info">
                        <h4>아파트 3</h4>
                        <p>아파트</p>
                        <a
                        href="@/assets/img/apt/apt3.jpg"
                        data-gallery="portfolioGallery"
                        class="portfolio-lightbox preview-link"
                        title="아파트 3"
                        ><i class="bx bx-plus"></i
                        ></a>
                        <a href="#" class="details-link" title="More Details"
                        ><i class="bx bx-link"></i
                        ></a>
                    </div>
                    </div>

                    <div class="col-lg-4 col-md-6 portfolio-item filter-house">
                    <img src="@/assets/img/apt/house2.jpg" class="img-fluid" alt="" />
                    <div class="portfolio-info">
                        <h4>주택 2</h4>
                        <p>주택</p>
                        <a
                        href="@/assets/img/apt/house2.jpg"
                        data-gallery="portfolioGallery"
                        class="portfolio-lightbox preview-link"
                        title="주택 2"
                        ><i class="bx bx-plus"></i
                        ></a>
                        <a href="#" class="details-link" title="More Details"
                        ><i class="bx bx-link"></i
                        ></a>
                    </div>
                    </div>

                    <div class="col-lg-4 col-md-6 portfolio-item filter-house">
                    <img src="@/assets/img/apt/house3.jpg" class="img-fluid" alt="" />
                    <div class="portfolio-info">
                        <h4>주택 3</h4>
                        <p>주택</p>
                        <a
                        href="@/assets/img/apt/house3.jpg"
                        data-gallery="portfolioGallery"
                        class="portfolio-lightbox preview-link"
                        title="주택 3"
                        ><i class="bx bx-plus"></i
                        ></a>
                        <a href="#" class="details-link" title="More Details"
                        ><i class="bx bx-link"></i
                        ></a>
                    </div>
                    </div>

                    <div class="col-lg-4 col-md-6 portfolio-item filter-multihouse">
                    <img src="@/assets/img/apt/multi3.jpeg" class="img-fluid" alt="" />
                    <div class="portfolio-info">
                        <h4>다세대 3</h4>
                        <p>다세대</p>
                        <a
                        href="@/assets/img/apt/multi3.jpeg"
                        data-gallery="portfolioGallery"
                        class="portfolio-lightbox preview-link"
                        title="다세대 3"
                        ><i class="bx bx-plus"></i
                        ></a>
                        <a href="#" class="details-link" title="More Details"
                        ><i class="bx bx-link"></i
                        ></a>
                    </div>
                    </div>
                </div>
                </div>
            </section>
            <!-- End Portfolio Section -->
    </div>
</template>

<script>
export default {
    name: 'AppMain',

    data() {
        return {
            
        };
    },

    mounted() {
        
    },

    methods: {
        
    },
};
</script>

<style lang="scss" scoped>

</style>